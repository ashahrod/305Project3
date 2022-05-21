package project_3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class DataProcessor {
	
	protected LogError logger;
	
	/* Check the articles provided to see if they are valid or not.
	 * Will check across the four parameters title, description, URL, and time published to see if they exist.
	 * @param articles - list of articles to be checked
	 * @param logger - the LogError object that is used to log our errors
	 * @return returns only the valid articles in a list
	 * */
	public List<Article> checkArticles(List<Article> articles, LogError logger) {
		List<Article> validArticles = new ArrayList<>();
		//check four fields
		for (Article article : articles) {
			if (article.getTitle() == null || article.getDescription() == null ||
					article.getPublishedAt() == null || article.getUrl() == null) {
				logger.makeArticleLog(article);
			}
			else {
			validArticles.add(article);
			}
		}
		return validArticles;
	}
	
	/* creates the LogError object and sets the file to write logs to
	 * @return LogError object that will be used to log invalid articles
	 * */
	public LogError createLogger() throws SecurityException, IOException {
		Logger obj = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		obj.setUseParentHandlers(false);
		FileHandler fh = new FileHandler("src/MyLogFile.log");
		obj.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
	    fh.setFormatter(formatter); 
	    return new LogError(obj);
	}
	
	public abstract List<Article> visit(String str, LogError logger) throws IOException;
	
	
	
	
}
