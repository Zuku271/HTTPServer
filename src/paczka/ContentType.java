package paczka;

import java.util.Map;

public class ContentType
{
	Map<String, String> contenttype;
	
	public String getContentType(String name)
	{
		return contenttype.get(name);
	}
	
	public void setContentType(String key, String value)
	{
		this.contenttype.put(key, value);
	}
}
