package paczka;

import java.util.Map;

public class Config
{
	private int port = 8800;
	private Map<String, String> pages;
	private Map<String, String> content_type;

	public int getPort()
	{
		return port;
	}
	
	public String getPage(String pageName)
	{
		return pages.get(pageName);
	}
	
	public String getContentType(String type)
	{
		return content_type.get(type);
	}
}
