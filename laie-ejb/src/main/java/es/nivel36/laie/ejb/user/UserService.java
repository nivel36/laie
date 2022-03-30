package es.nivel36.laie.ejb.user;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;

@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserMapper userMapper;

	private UserMerger userMerger;

	@EJB
	private FileService fileService;

	@Inject
	@Repository
	private UserDao userDao;

	@PostConstruct
	public void init() {
		userMapper = new UserMapper(fileService);
		userMerger = new UserMerger();
	}

	public UserDto addUser(final UserDto user, final String managerUid)
			throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user);
		logger.debug("Insert user {}", user);
		final String email = user.getEmail();
		this.checkDuplicateEmail(email, null);
		final User entity = new User();
		this.userMerger.merge(entity, user);
		this.userDao.insert(entity);
		this.changeUsersManager(entity, managerUid);
		return this.userMapper.map(entity);
	}

	public void updateUser(final UserDto user) throws DuplicateEmailException {
		Objects.requireNonNull(user);
		logger.debug("Update user {}", user);
		final String userUid = user.getUid();
		final User entity = this.userDao.findUserByUid(userUid);
		final String email = user.getEmail();
		final String entityEmail = entity.getEmail();
		this.checkDuplicateEmail(email, entityEmail);
		this.userMerger.merge(entity, user);
	}

	private void checkDuplicateEmail(final String newEmail, final String oldEmail) throws DuplicateEmailException {
		if (oldEmail == null || !newEmail.equals(oldEmail)) {
			if (this.userDao.checkDuplicateEmail(newEmail)) {
				throw new DuplicateEmailException();
			}
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

	public FileDto changeUsersImage(final String userUid, final InputStream image) {
		Objects.requireNonNull(userUid);
		Objects.requireNonNull(image);
		logger.debug("Change image to user {}", userUid);
		final FileDto newImage = this.fileService.uploadFile(image, userUid + "_picture", true);
		final User user = this.userDao.findUserByUid(userUid);
		final String oldImage = user.getPictureUid();
		if (oldImage != null) {
			logger.trace("Remove user {} old image", user);
			this.fileService.removeFile(oldImage);
		}
		user.setPictureUid(newImage.getUid());
		return newImage;
	}

	public void changeUsersManager(final String userUid, final String managerUid) throws BadManagerException {
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		this.changeUsersManager(user, managerUid);
	}

	private void changeUsersManager(final User user, final String managerUid) throws BadManagerException {
		if (managerUid == null) {
			logger.debug("Delete manager to user {}", user);
			user.setManager(null);
			return;
		}
		// Not all users have a manager, so it may be null.
		final User oldManager = user.getManager();

		final User newManager = this.userDao.findUserByUid(managerUid);
		if (newManager.equals(oldManager)) {
			return;
		}
		logger.debug("Change manager from {} to {} of user {}", oldManager, newManager, user);
		if (user.equals(newManager)) {
			logger.warn("The user {} can't be his/her manager", user);
			throw new BadManagerException("User can't be his/her manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("The user {} is the manager of {}", user, newManager);
			throw new BadManagerException("User is the manager of his new manager");
		}
		user.setManager(newManager);
		this.userDao.update(user);
	}

	public void deleteUsersImage(final String userUid) {
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		logger.debug("Delete user's image of user {}", user);
		final String oldImage = user.getPictureUid();
		if (oldImage != null) {
			this.fileService.removeFile(oldImage);
		}
		user.setPictureUid(null);
	}

	public void changePassword(final String email, final String oldPassword, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(newPassword);
		logger.debug("Change password for user {}", email);
		// TODO: añadir lógica con el password antiguo
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
		return userMapper.mapList(user);
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
		Objects.requireNonNull(page);
		final SearchResult<User> restul = this.userDao.search(searchText, page, sortField, searchFacets);
		final List<User> resultData = restul.getResultData();
		final List<UserDto> mapList = userMapper.mapList(resultData);
		final Map<String, List<Facet>> allFacets = restul.getAllFacets();
		final int count = restul.getCount();
		return new SearchResult<UserDto>(mapList, count, allFacets);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}