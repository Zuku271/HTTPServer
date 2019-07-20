package paczka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Server
{
	private static final int PORT = 8800;
	Map<String, String> config = new TreeMap<>();
	
	private void runServer(String configFile)
	{
		ServerSocket serverSocket;
		try
		{
			File configScanner = new File(configFile);
			BufferedReader br = new BufferedReader(new FileReader(configScanner));
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Uruchamiam serwer na porcie: " + PORT);
		Server instance = new Server();
		instance.runServer("config.txt");
	}
}
