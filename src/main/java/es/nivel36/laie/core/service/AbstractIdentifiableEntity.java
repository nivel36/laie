package es.nivel36.laie.core.service;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDef(name = "stdAnalyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
		@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
				@Parameter(name = "minGramSize", value = "3"), @Parameter(name = "maxGramSize", value = "10") }) })
@MappedSuperclass
public abstract class AbstractIdentifiableEntity extends AbstractEntity implements Identifiable {

	private static final long serialVersionUID = 155912255575142601L;

	@NotNull
	@Column(nullable = false, unique = true)
	private String uid;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final AbstractIdentifiableEntity other = (AbstractIdentifiableEntity) obj;
		return Objects.equals(this.uid, other.uid);
	}

	/**
	 * Returns the unique identifier of the user.<br/>
	 * This 6 digits value in hexadecimal is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @return <tt>String</tt> with the unique identifier of the user in the
	 *         application. This value is a six-character string in hexadecimal.
	 */
	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(this.uid);
		return result;
	}

	/**
	 * Sets the unique identifier of the user.<br/>
	 * This 6 digits value in hexadecimal is created by the application and is not
	 * related to the user's identifier in the company.<br/>
	 * This value cannot be null or duplicated.
	 * 
	 * @param uid <tt>String</tt> with the unique identifier of the user in the
	 *            application. This value is a six-character string in hexadecimal.
	 */
	@Override
	public void setUid(final String uid) {
		this.uid = uid;
	}
}
