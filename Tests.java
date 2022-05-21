package project_3;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Tests {
	
	Logger obj = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    LogError logger = new LogError(obj);
	
	Article inv1 = new Article(null, "Description", "URL", "published");
	Article inv2 = new Article("Title", null, "URL", "published");
	Article inv3 = new Article("Title", "Description", null, "published");
	Article inv4 = new Article("Title", "Description", "URL", null);
	Article val1 = new Article("Title", "Description", "URL", "published");
	Article val2 = new Article("Bird Sighting", "Eagle spotted near Alamo.", "https//...", "2022-04-20T10:25:00");
	
	@Test
//	test the CheckArticles method with invalid Articles, from the NewsAPI parser
	public void testInvalidArticles() {
	
		List<Article> all = new ArrayList<>();
		all.add(inv1);
		all.add(inv2);
		all.add(inv3);
		all.add(inv4);
		NewsAPI parse = new NewsAPI();
		List<Article> onlyValid = parse.checkArticles(all, logger);
		assertEquals(0, onlyValid.size());
	}
	
	@Test
//	test the CheckArticles method with invalid Articles, from the SimpleNews parser 
	public void testSimpleInvalidArticles() {
		
		List<Article> all = new ArrayList<>();
		all.add(inv1);
		all.add(inv2);
		all.add(inv3);
		all.add(inv4);
		SimpleNews parse = new SimpleNews();
		List<Article> onlyValid = parse.checkArticles(all, logger);
		assertEquals(0, onlyValid.size());
	}
	
	@Test
//	test only valid articles with the CheckArticles function
	public void testValidArticles() {
		List<Article> all = new ArrayList<>();
		all.add(val1);
		all.add(val2);
		NewsAPI parse = new NewsAPI();
		List<Article> onlyValid = parse.checkArticles(all, logger);
		assertEquals(2, onlyValid.size());
		
	}
	
	@Test
//	test a set of valid and invalid articles with the CheckArticles function
	public void testMixedArticles() {
	
		List<Article> all = new ArrayList<>();
		all.add(val1);
		all.add(val2);
		all.add(inv1);
		all.add(inv2);
		all.add(inv3);
		all.add(inv4);
		NewsAPI parse = new NewsAPI();
		List<Article> onlyValid = parse.checkArticles(all, logger);
		assertEquals(2, onlyValid.size());
		assertEquals(onlyValid.get(0), val1);
		assertEquals(onlyValid.get(1), val2);
		
	}
	
	@Test
//	test the getter methods of Article class
	public void testFields() {
		assertEquals(val1.getTitle(), "Title");
		assertEquals(val1.getUrl(), "URL");
		assertEquals(val1.getDescription(), "Description");
		assertEquals(val1.getPublishedAt(), "published");
		assertEquals(inv1.getTitle(), null);
		assertEquals(inv3.getUrl(), null);
		assertEquals(inv2.getDescription(), null);
		assertEquals(inv4.getPublishedAt(), null);
	}
	
	@Test
//	test the SimpleNews parser with a valid article, and check the fields
	public void testSimpleParser() throws IOException {
		Driver driver = new Driver();
		SimpleNews simp = new SimpleNews();
		String file = driver.jsonFromFile("inputs/simple.txt");
		List<Article> simpleArticle = simp.parseJson(file, logger);
		Article article = simpleArticle.get(0);
		assertEquals("Assignment #2", article.getTitle());
		assertEquals("Extend Assignment #1 to support multiple sources and to introduce source processor.", article.getDescription());
		assertEquals("2021-04-16 09:53:23.709229", article.getPublishedAt());
		assertEquals("https://canvas.calpoly.edu/courses/55411/assignments/274503", article.getUrl());
		
	}
	
	@Test
//	test the NewsAPI parser with a valid source, and check the first articles fields
	public void testParse() throws IOException {
		NewsAPI parse = new NewsAPI();
		Driver driver = new Driver();
		String str = driver.jsonFromFile("inputs/example.json");
		List<Article> arr = parse.parseJson(str, logger);
		Article article0 = arr.get(0);
		assertEquals("The latest on the coronavirus pandemic and vaccines: Live updates - CNN", article0.getTitle());
		assertEquals("The coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.", article0.getDescription());
		assertEquals("https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html", article0.getUrl());
		assertEquals("2021-03-24T22:32:00Z" , article0.getPublishedAt());
		
	}
	
	@Test
//	test the creation of parsers, with the SourceFormat notation
	public void testParserCreation() throws IOException {
		
	    SourceFormat format = new SourceFormat(0, 3);
	    DataProcessor parse_type = format.parseType();
	    assertEquals(NewsAPI.class, parse_type.getClass());
	    
	    SourceFormat format2 = new SourceFormat(0, 2);
	    DataProcessor parse_type2 = format2.parseType();
	    assertEquals(SimpleNews.class, parse_type2.getClass());
	    
	    SourceFormat format3 = new SourceFormat(1, 3);
	    DataProcessor parse_type3 = format3.parseType();
	    assertEquals(NewsAPI.class, parse_type3.getClass());
	    
	}
	
	@Test
//	test that the Visitor Pattern implementation runs correctly
	public void testVisitorPattern() throws SecurityException, IOException {
		Driver driver = new Driver();
		String file1 = driver.jsonFromFile("inputs/newsapi.txt");
		
	    SourceFormat format = new SourceFormat(0, 3);
	    DataProcessor parse_type = format.parseType();
	    LogError logger = parse_type.createLogger();
	    List<Article> validArticles1 = format.accept(parse_type, file1, logger);
	    assertTrue(!validArticles1.isEmpty());
	    
	    String file2 = driver.jsonFromFile("inputs/simple.txt");  
	    SourceFormat format2 = new SourceFormat(0, 2);
	    DataProcessor parse_type2 = format2.parseType();
	    List<Article> validArticles2 = format.accept(parse_type2, file2, logger);
	    assertEquals(1, validArticles2.size());
	    
}
}
