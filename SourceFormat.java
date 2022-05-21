package project_3;

import java.io.IOException;
import java.util.List;

public class SourceFormat {

	final static int FILE = 0;
	final static int URL = 1;
	final static int SIMPLE = 2;
	final static int NEWSAPI = 3;
	//Simple would return SimpleNews parser, NewsAPI would return NewsAPI parser
	int source;
	int format;
	
	SourceFormat(int source, int format){
		this.source = source;
		this.format = format;
	}
	
	/* determines the parser that should be used
	 * @param None
	 * @return the correct parser that extends type DataProcessor
	 * */
	public DataProcessor parseType() {
		if(this.format == SIMPLE) {
			return new SimpleNews();
		}	
		else {
			return new NewsAPI();
		}
	}

	/* in tandem with the visit method uses the parser type to visit the correct parse method
	 * @param parse_type - the correct parser that extends type DataProcessor
	 * @param str - is the string that will be parsed
	 * @param logger - LogError object used to log the invalid articles
	 * @return is a List<Article> object with only valid articles
	 * */
	public List<Article> accept(DataProcessor parse_type, String str, LogError logger) throws IOException {
		return parse_type.visit(str, logger);
}
	
	
}
