package project_3;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogError {
	
	private Logger logger;

	LogError(Logger logger) {
		this.logger = logger;
	}
	

	
	/* Log the Article errors to the logger
	 * @param article - an Article that has errors that need to be logged
	 * @return void
	 * */
	void makeArticleLog(Article article)
    {
		StringBuilder str = new StringBuilder();
		str.append("BAD ARTICLE\n" + "TITLE: " + article.getTitle());
		str.append(" DESCRIPTION: " + article.getDescription());
		String strDone = str.toString();
		logger.log(Level.INFO, strDone);
  
    }

}
