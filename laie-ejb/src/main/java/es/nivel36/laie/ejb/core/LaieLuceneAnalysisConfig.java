package es.nivel36.laie.ejb.core;



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
		context.analyzer("english").custom().tokenizer(StandardTokenizerFactory.class)
				.charFilter(HTMLStripCharFilterFactory.class).tokenFilter(LowerCaseFilterFactory.class)
				.tokenFilter(SnowballPorterFilterFactory.class).param("language", "English")
				.tokenFilter(ASCIIFoldingFilterFactory.class);

		context.normalizer("lowercase").custom().tokenFilter(LowerCaseFilterFactory.class)
				.tokenFilter(ASCIIFoldingFilterFactory.class);

		context.analyzer("french").custom().tokenizer(StandardTokenizerFactory.class)
				.charFilter(HTMLStripCharFilterFactory.class).tokenFilter(LowerCaseFilterFactory.class)
				.tokenFilter(SnowballPorterFilterFactory.class).param("language", "French")
				.tokenFilter(ASCIIFoldingFilterFactory.class);
	}
}