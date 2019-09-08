package paczka;

import java.util.HashMap;

public class Config
{
	private int port = 8800;
	private HashMap<Object, String> pages;
	private HashMap<Object, String> contenttype;
	private String logFilename;

	
	public Config(int port, HashMap<Object, String> pages, HashMap<Object, String> contenttype)
	{
		this.port = port;
		this.pages = pages;
		this.contenttype = contenttype;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public HashMap<Object, String> getPages()
	{
		return pages;
	}
	
	public void setPages(HashMap<Object, String> pages)
	{
		this.pages = pages;
	}
	
	public HashMap<Object, String> getContentType()
	{
		return contenttype;
	}
	
	public void setContentType(HashMap<Object, String> content)
	{
		this.contenttype = content;
	}
	
	public String getLogFilename()
	{
		return logFilename;
	}
	
	public void setLogFilename(String filename)
	{
		this.logFilename = filename;
	}
}
