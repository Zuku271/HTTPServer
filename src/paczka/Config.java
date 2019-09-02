package paczka;


import java.util.Map;

public class Config
{
	private int port = 8800;
	private Pages pages;
	private ContentType contenttype;

	public int getPort()
	{
		return port;
	}
	
	public Pages getPages()
	{
		return pages;
	}
	
	public ContentType getContentType()
	{
		return contenttype;
	}
}
