package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import ged.ejb.user.User;

@AnalyzerDef(name = "stdAnalyzer",
		// Split input into tokens according to tokenizer
		tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), //
		filters = { //
				// Normalize token text to lowercase, as the user is unlikely to
				// care about casing when searching for matches
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				// Index partial words starting at the front, so we can provide
				// Autocomplete functionality
				@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "minGramSize", value = "3"),
						@Parameter(name = "maxGramSize", value = "20") }),
		// Close filters & Analyzerdef
		})
@Analyzer(definition = "stdAnalyzer")
@MappedSuperclass
public abstract class AbstractAuditedEntity extends AbstractEntity implements AuditedEntity<Long> {

	private static final long serialVersionUID = 6203444960560029390L;

	@Column(nullable = true)
	@Field
	private Boolean deleted;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.AuditedEntity#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.AuditedEntity#getUser()
	 */
	@Override
	public User getUser() {
		return this.user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.AuditedEntity#isDeleted()
	 */
	@Override
	public Boolean isDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.AuditedEntity#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(final Boolean deleted) {
		this.deleted = deleted;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.AuditedEntity#setUser(ged.ejb.user.User)
	 */
	@Override
	public void setUser(final User user) {
		this.user = user;
	}
}
