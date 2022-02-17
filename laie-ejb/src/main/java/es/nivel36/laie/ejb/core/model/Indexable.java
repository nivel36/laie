package es.nivel36.laie.ejb.core.model;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDef(name = "stdAnalyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
		@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
				@Parameter(name = "minGramSize", value = "3"), @Parameter(name = "maxGramSize", value = "10") }) })
@Analyzer(definition = "stdAnalyzer")
public interface Indexable {

}
