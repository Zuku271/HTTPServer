package paczka;

import java.util.HashMap;
import java.util.Map;

public class ContentType
{
	HashMap<Object, String> content;
	
	public ContentType(String key, String value)
	{
		this.content.put(key, value);
	}
	
	public HashMap<Object, String>getContent()
	{
		return content;
	}
	
	public void setContent(String key, String value)
	{
		this.content.put(key, value);
	}
}
