package es.nivel36.laie.ejb.user;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileDto;
import es.nivel36.laie.ejb.core.file.FileJpaDao;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserMapper userMapper;

	private UserMerger userMerger;

	@Inject
	private FileService fileService;
	
	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@Repository
	private UserDao userDao;

	@PostConstruct
	public void init() {
		userMapper = new UserMapper();
		userMerger = new UserMerger();
	}
	
	public void addUser(final UserDto user, final String managerUid) throws DuplicateEmailException {
		Objects.requireNonNull(user);
		logger.debug("Insert user {}", user);
		final String email = user.getEmail();
		checkDuplicateEmail(email);
		final User entity = new User();
		this.userMerger.merge(entity, user);
		this.userDao.insert(entity);
		final String uid = entity.getUid();
		this.changeUsersManager(uid, managerUid);
	}

	public void updateUser(final UserDto user) throws DuplicateEmailException {
		Objects.requireNonNull(user);
		logger.debug("Update user {}", user);
		final String userUid = user.getUid();
		final User entity = this.userDao.findUserByUid(userUid);
		final String email = user.getEmail();
		final String entityEmail = entity.getEmail();
		if (!entityEmail.equals(email)) {
			checkDuplicateEmail(email);
		}
		this.userMerger.merge(entity, user);
		this.userDao.update(entity);
	}

	private void checkDuplicateEmail(final String email) throws DuplicateEmailException {
		if (this.userDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException();
		}
	}

	public UserDto findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		final User user = this.userDao.findUserByEmail(email);
		return this.userMapper.map(user);
	}

	public UserDto findUserByUid(final String userUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Find user by uid {}", userUid);
		final User user = this.userDao.findUserByUid(userUid);
		return this.userMapper.map(user);
	}

	public String changeUsersImage(final String userUid, final InputStream image) {
		Objects.requireNonNull(userUid);
		Objects.requireNonNull(image);
		final User user = this.userDao.findUserByUid(userUid);
		logger.debug("Change image to user {}", user);
		final File oldImage = user.getPicture();
		final FileDto newImage = this.fileService.uploadFile(image, userUid + "_picture", true);
		final File file = fileDao.findFileByUid(newImage.getUid());
		user.setPicture(file);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", user);
			this.fileService.removeFile(oldImage.getUid());
		}
		return newImage.getPath();
	}

	public void changeUsersManager(final String userUid, final String managerUid) throws BadManagerException {
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		if (managerUid == null) {
			logger.debug("Delete manager to user {}", user);
			user.setManager(null);
			return;
		}
		final User oldManager = user.getManager();
		final User newManager = this.userDao.findUserByUid(managerUid);
		if (oldManager.equals(newManager)) {
			return;
		}
		logger.debug("Change manager from {} to {} of user {}", oldManager, newManager, userUid);
		if (user.equals(newManager)) {
			logger.warn("The user {} can't be his/her manager", user);
			throw new BadManagerException("User can't be his/her manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("The user {} is the manager of {}", user, newManager);
			throw new BadManagerException("User is the manager of his new manager");
		}
		user.setManager(newManager);
	}
	public void deleteUsersImage(final String userUid) {
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		logger.debug("Delete user's image of user {}", user);
		final File oldImage = user.getPicture();
		if (oldImage != null) {
			this.fileService.removeFile(oldImage.getUid());
		}
	}
	
	public void changePassword(final String email, final String oldPassword, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(newPassword);
		logger.debug("Change password for user {}", email);
		//TODO: añadir lógica con el password antiguo
		final Credential credential = this.userDao.findCredential(email);
		credential.setPassword(newPassword);
	}

	public Credential findCredential(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find credential for user with email {}", email);
		return this.userDao.findCredential(email);
	}

	public List<UserDto> findSubordinateUsers(final String userUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Finding subordinate users of user {}", userUid);
		final List<User> user = this.userDao.findSubordinateUsers(userUid);
		return new UserMapper().mapList(user);
	}
	
	public boolean isSubordinateUser(final String userUid, final String managerUid) {
		Objects.requireNonNull(userUid);
		logger.debug("Find is user {} is subordinate of {}", userUid, managerUid);
		final User user = this.userDao.findUserByUid(userUid);
		final User manager = this.userDao.findUserByUid(managerUid);
		return this.userDao.isSubordinateUser(user, manager);
	}

	public SearchResult<UserDto> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<UserDto> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final SearchResult<User> restul = this.userDao.search(searchText, page, sortField, searchFacets);
		final List<User> resultData = restul.getResultData();
		final List<UserDto> mapList = new UserMapper().mapList(resultData);
		final Map<String, List<Facet>> allFacets = restul.getAllFacets();
		final int count = restul.getCount();
		return new SearchResult<UserDto>(mapList, count, allFacets);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}