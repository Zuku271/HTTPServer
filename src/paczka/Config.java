package paczka;

public class Config
{
	private int port = 8800;
	private Pages pages;
	private ContentType contenttype;

	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public Pages getPages()
	{
		return pages;
	}
	
	public void setPages(Pages pages)
	{
		this.pages = pages;
	}
	
	public ContentType getContentType()
	{
		return contenttype;
	}
	
	public void setContenttype(ContentType contenttype)
	{
		this.contenttype = contenttype;
	}
}
