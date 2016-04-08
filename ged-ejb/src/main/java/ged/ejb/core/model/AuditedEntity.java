package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDef(name = "myanalyzer",
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
@Analyzer(definition = "myanalyzer")
@MappedSuperclass
public abstract class AuditedEntity extends AbstractEntity {

	private static final long serialVersionUID = 6203444960560029390L;

	@Column(nullable = true)
	private Boolean deleted;

	public Boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(final Boolean deleted) {
		this.deleted = deleted;
	}
}
