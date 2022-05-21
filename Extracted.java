package project_3;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

public class Extracted {
	@JsonIgnoreProperties({"source", "author", "urlToImage", "content"})

	private final ArrayList<Article> articles;
	
	@JsonCreator private Extracted(@JsonProperty("status") String status, @JsonProperty("totalResults") int totalResults, @JsonProperty("articles") ArrayList<Article> articles)
	{

		this.articles = articles;
	}

	public Extracted(List<Article> articles) {
		this.articles = (ArrayList<Article>) articles;
	}

	public List<Article> getArticles() {
		return articles;
	}
}

