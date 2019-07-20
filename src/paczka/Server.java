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


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.*; 

public class Server
{
	private static int PORT = 8800;
	
	
	private void runServer(String configFile)
	{
		ServerSocket serverSocket;
		try
		{
			FileInputStream configFileInput = new FileInputStream(configFile);
			Properties config = new Properties();
			config.load(configFileInput);
			
			
			if ((PORT = Integer.valueOf(config.getProperty("PORT"))) == 0)
			{
				PORT = 8800;
			}
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Uruchamiam serwer na porcie: ");
		Server instance = new Server();
		instance.runServer("config.txt");
	}
}
