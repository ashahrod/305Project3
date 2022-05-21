package project_3;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NewsAPI extends DataProcessor {
	
	/* Parse the provided JSON String and map it to an Extracted object
	 * @param str - is the string to be read from and parsed
	 * @param logger - LogError object used to log the invalid articles
	 * @return is a List<Article> object with only valid articles
	 * */
	public List<Article> parseJson(String str, LogError logger) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Extracted exc = mapper.readValue(str, Extracted.class);
		return this.checkArticles(exc.getArticles(), logger);
	}

	/* calls the parseJson method 
	 * @param str - is the string that will be parsed
	 * @param logger - LogError object used to log the invalid articles
	 * @return is a List<Article> object with only valid articles
	 * */
	public List<Article> visit(String str, LogError logger) throws IOException{
		return this.parseJson(str, logger);
	}


}
