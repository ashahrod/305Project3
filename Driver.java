package project_3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// need to do testing also for invalid simple articles


public class Driver {
	@JsonIgnoreProperties({"status", "source", "author", "urlToImage", "content"})
	public static String API_URL = "http://newsapi.org/v2/top-headlines?country=us&apiKey=";
	public static String API_KEY = "aeee1b39ed1f4fb8b812cd58756369ad";
	
	
	public static void main(String[] args) throws Exception {
		Driver driver = new Driver();
		String file1 = driver.jsonFromFile("inputs/newsapi.txt");
		String file2 = driver.jsonFromFile("inputs/simple.txt");  
		String file3 = driver.jsonFromNewsURL(API_URL, API_KEY);
		
	    SourceFormat format = new SourceFormat(0, 3);
	    DataProcessor parse_type = format.parseType();
	    LogError logger = parse_type.createLogger();
	    List<Article> validArticles1 = format.accept(parse_type, file1, logger);
	    
	    SourceFormat format2 = new SourceFormat(0, 2);
	    DataProcessor parse_type2 = format2.parseType();
	    List<Article> validArticles2 = format.accept(parse_type2, file2, logger);

	    SourceFormat format3 = new SourceFormat(1, 3);
	    DataProcessor parse_type3 = format3.parseType();
	    List<Article> validArticles3 = format.accept(parse_type3, file3, logger);
	    
		driver.printOut(validArticles1);
		System.out.println("Next file...\n\n\n");
		driver.printOut(validArticles2);
		System.out.println("Next file...\n\n\n");
		driver.printOut(validArticles3);
		
	}

	/* prints out the four fields of the Article class for a list of valid articles
	 * @param articles - List of Valid Articles
	 * @return - void
	 * */
	public void printOut(List<Article> articles) {
		for (Article article: articles) {
			System.out.println(article.getTitle());
			System.out.println(article.getDescription());
			System.out.println(article.getPublishedAt());
			System.out.println(article.getUrl() + "\n");
			
		}
	}
	
	/* turns the News URL into a JSON String that we will be using to parse the contents
	 * @param URL - A string containing the URL
	 * @param key - API Key for the URL
	 * @return a JSON String that is created from the URL
	 * */
	String jsonFromNewsURL(String URL, String key) {
        try (Scanner scanner = new Scanner(new URL(URL + key).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
	
	/* turns a File into a JSON String
	 * @param filePath - A string that contains the path to the file
	 * @return a JSON String that is created from the file
	 * */
	String jsonFromFile(String filePath) {
        Path path = Paths.get(filePath);

        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
