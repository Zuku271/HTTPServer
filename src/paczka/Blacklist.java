package paczka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Blacklist extends ServerSocket
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
		
		return false;
	}
	
	public Socket accept() throws IOException
	{
		return Socket;
	}
}
