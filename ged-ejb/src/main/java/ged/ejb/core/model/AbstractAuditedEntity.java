package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import ged.ejb.user.User;

@AnalyzerDef(name = "stdAnalyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class), @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
		@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = { @Parameter(name = "minGramSize", value = "3"),
				@Parameter(name = "maxGramSize", value = "20") }) })
@Analyzer(definition = "stdAnalyzer")
@MappedSuperclass
@EntityListeners(AuditedListener.class)
public abstract class AbstractAuditedEntity extends AbstractEntity implements Auditable, Erasable, Ownerable {

	private static final long serialVersionUID = 6203444960560029390L;

	@Column(nullable = false)
	@Field
	private boolean deleted;

	private User owner;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

	@Override
	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	@Override
	public void setUser(final User user) {
		this.user = user;
	}
}
