package es.nivel36.laie.ejb.core;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class LaieLuceneAnalysisConfig implements LuceneAnalysisConfigurer {

	/**
	 * @AnalyzerDef(name = "stdAnalyzer", tokenizer = @TokenizerDef(factory =
	 *                   WhitespaceTokenizerFactory.class), filters = {
	 * @TokenFilterDef(factory = LowerCaseFilterFactory.class),
	 * @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
	 * @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
	 * @Parameter(name = "minGramSize", value = "3"), @Parameter(name =
	 *                 "maxGramSize", value = "10") }) })
	 * @Analyzer(definition = "stdAnalyzer")
	 * @param context
	 */
	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		context.analyzer("spanish").custom().tokenizer(StandardTokenizerFactory.class)
				.charFilter(HTMLStripCharFilterFactory.class).tokenFilter(LowerCaseFilterFactory.class)
				.tokenFilter(SnowballPorterFilterFactory.class).param("language", "Spanish")
				.tokenFilter(ASCIIFoldingFilterFactory.class);
	}
}