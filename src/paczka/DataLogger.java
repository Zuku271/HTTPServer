package paczka;

import java.time.ZonedDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;

public class DataLogger
{
	private String path;
	
	
	public DataLogger(String logFilename)
	{
		this.path = new File((logFilename)).getPath();	
	}
	
	public void setPath(String logFilename)
	{
		this.path = new File((logFilename)).getPath();	
	}
	
	public void save(ZonedDateTime time, InetAddress IP, String user_agent, String requestedPath) throws IOException
	{
		StringBuilder entry = new StringBuilder(time.toString()).append(",");
		entry.append(IP.toString()).append(",");
		entry.append(user_agent).append(",");
		entry.append(requestedPath).append("\r\n");
				
		synchronized (this)
		{
			Writer app = new BufferedWriter(new FileWriter(path, true));
			app.append(entry);
			app.close();
		}
	}
}
