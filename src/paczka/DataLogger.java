package paczka;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.util.List;


public class DataLogger
{
	private Path pathToLogFile;
	
	public DataLogger(String logFilename)
	{
		this.pathToLogFile = Paths.get(logFilename);	
	}
	
	public void save(ZonedDateTime time, InetAddress IP, String user_agent, String requestedPath) throws IOException
	{
		//List<String> file_content = load();
		StringBuilder entry = new StringBuilder(time.toString()).append(";");
		entry.append(IP.toString()).append(";");
		entry.append(user_agent).append(";");
		entry.append(requestedPath).append("\r\n");
				
		Writer app = new BufferedWriter(new FileWriter(pathToLogFile.getFileName().toString(), true));
		app.append(entry);
		app.close();
	}
	
	public List<String> load() throws IOException
	{
		return Files.readAllLines(pathToLogFile, Charset.defaultCharset());
	}
}
