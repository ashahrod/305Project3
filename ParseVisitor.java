package project_3;

import java.util.List;

public interface ParseVisitor{

	public List<Article> visit(SimpleNews parser, String str, LogError logger) throws Exception;
	public List<Article> visit(NewsAPI parser, String str, LogError logger) throws Exception;

}
