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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.*; 

public class Server
{
	private static int PORT = 8800;
	private static String startPage = "";
	
	private List<String> getHttpHeader(BufferedReader br)
	{

		List<String> headerLines = new ArrayList<>();
		String ln = "";

		try {
			while ((ln = br.readLine()) != null) {
				// oczekiwanie na koniec naglowka
				if (ln.equals("")) {
					break;
				}
				headerLines.add(ln);
			}
		} catch (IOException e) {
		}

		return headerLines;
	}

	private String findGETLine(List<String> headerLines)
	{
		for (String s : headerLines) {
			if (s.toUpperCase().startsWith("GET ")) {
				return s;
			}
		}
		return null;
	}

	private String getPathFromGetLine(String getLine)
	{
		String path = new String(getLine);
		path = path.replaceFirst("[Gg][Ee][Tt] +", "");
		path = path.replaceFirst(" .*", "");
		return path;
	}

	
	private void runServer(String configFilePath)
	{
		ServerSocket serverSocket = null;
		try
		{
			//FileInputStream configFileInput = new FileInputStream(configFilePath);
			//Properties config = new Properties();
			//config.load(configFileInput);
			File configFile = new File(configFilePath);
			ObjectMapper config = new ObjectMapper();
			config.readValue(configFile, Config.class);
			
			
			if ((PORT = Integer.valueOf(config.getProperty("PORT"))) == 0)
			{
				PORT = 8800;
			}
			
			startPage = config.getProperty("startPage");
			
			serverSocket = new ServerSocket(PORT);
			System.out.println("Uruchamiam serwer na porcie: " + PORT);
			while (true)
			{
				Socket s = serverSocket.accept();
				System.out.println("Polaczony klient: " + s.getRemoteSocketAddress());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
				List<String> headerLines = getHttpHeader(br);

				String requestedPath = "";
				String getLine = findGETLine(headerLines);
				if (getLine != null)
				{
					requestedPath = getPathFromGetLine(getLine);
				}
				System.out.println("GET Path:" + requestedPath);

				String pageContent = null;
				
				if (requestedPath.contentEquals("/"))
				{
					pageContent = generatePageFromFile(config.getProperty("startPage"));
				}
				else if (requestedPath.startsWith("/dyn/date"))
				{
					//pageContent = generateJSONDatePage();
				}
				else
				{
					String localPath = "res" + requestedPath;
					pageContent = generatePageFromFile(localPath);
				}
				
				/*if (pageContent == null || requestedPath == "/")
				{
					pageContent = generateDefaultPage();
				}*/
				// wyslanie strony
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
				pw.println(pageContent);
				pw.flush();
				s.getOutputStream().close();
				s.close();
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String readTextFile(String path)
	{
		StringBuilder sb = new StringBuilder();

		File filePath = new File(path);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8")))
		{
			String ln = "";
			while ((ln = br.readLine()) != null) 
			{
				sb.append(ln).append("\n");
			}
		}
		catch (Exception e)
		{
			return null;
		}

		return sb.toString();
	}
	
	private String generatePageFromFile(String path)
	{
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 200 OK").append("\r\n");
		sb.append("Connection: close").append("\r\n");
		sb.append("Content-Type: text/html; charset=utf-8").append("\r\n");
		sb.append("\r\n");

		// tresc http (html)
		String fileContent = readTextFile(path);
		if (fileContent == null) {
			return null;
		}
		sb.append(fileContent);

		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		Server instance = new Server();
		instance.runServer("config.txt");
		System.out.println("Uruchamiam serwer na porcie: ");
	}
}
