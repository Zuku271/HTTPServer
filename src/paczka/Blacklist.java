package paczka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Blacklist
{
	private String fileName;
	private List<String> blocked_list;
	
	public Blacklist(String fileName)
	{
		this.fileName = fileName;
		this.blocked_list = new ArrayList<String>();
	}
	
	public boolean isBlocked(InetSocketAddress client_adress)
	{
		return true;
	}
	
	public boolean load()
	{
		try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			String ip = "";
	        while ((ip = br.readLine()) != null)
	        {
	        	List<String> splitted_ip = Arrays.asList(ip.split(","));
	            
	        	for (String ip_addr : splitted_ip)
	        	{
	        		blocked_list.add(ip_addr);
	        	}
	        }
	    } catch (IOException e)
		{
	      System.out.println("Blacklist file not found");
	    }
		return true;
	}
	
	public boolean checkAccept(Socket s) throws AccessDeniedException
	{
		System.out.println("Blacklist check: " + s.getInetAddress().toString());
		if (blocked_list.indexOf(s.getInetAddress().toString()) == -1)
		{
			return true;
		}
		return false;
	}
}
