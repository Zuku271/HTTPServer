package paczka;


import java.util.Map;

public class Config
{
	private int port = 8800;
	private Pages pages;
	private Content_type content_type;

	public int getPort()
	{
		return port;
	}
	
	public Pages getPages()
	{
		return pages;
	}
	
	public Content_type getContent_Type()
	{
		return content_type;
	}
}
