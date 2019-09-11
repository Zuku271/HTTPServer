package paczka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Blacklist
{
	private String fileName;
	private List<SocketAddress> blocked_list;
	
	public Blacklist(String fileName)
	{
		this.fileName = fileName;
	}
	
	public boolean isBlocked(SocketAddress client_adress)
	{
		return true;
	}
	
	public boolean load()
	{
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8))
		{
			 String line = br.readLine();
			 while (line != null)
			 {
				 String[] attributes = line.split(",");
				 //SocketAddress entry = new SocketAddress(attributes);
				 
				 //blocked_list.add();
				 line = br.readLine();
			 }

		}
		catch (Exception e)
		{
			return false;
		}
	}
}
