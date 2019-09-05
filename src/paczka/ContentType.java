package paczka;

import java.util.Map;

public class ContentType
{
	Map<String, String> content;
	
	public ContentType(String key, String value)
	{
		this.content.put(key, value);
	}
	
	public Map<String, String>getContent()
	{
		return content;
	}
	
	public void setContent(String key, String value)
	{
		this.content.put(key, value);
	}
}
