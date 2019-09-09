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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.*; 

public class Server
{
	private static int PORT = 8800;
	private static String startPage = "";
	private Config config;
	
	private enum PageStatus
	{
		wasFound,
		wasNotFound
	}
	
	
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
	
	private String findUserAgentLine(List<String> headerLines)
	{
		for (String s : headerLines)
		{
			if (s.startsWith("User-Agent"))
			{
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

			File configFile = new File(configFilePath);
			ObjectMapper configObjectMapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Config.class, new ItemDeserializer());
			configObjectMapper.registerModule(module);
			
			config = configObjectMapper.readValue(configFile, Config.class);
						
			PORT = config.getPort();
			startPage = config.getPages().get("startPage");
			DataLogger log = new DataLogger("log.csv");
			
			serverSocket = new ServerSocket(PORT);
			/*if (serverSocket.isBound())
			{
				log = new DataLogger(config.getLogFilename());
			}*/

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

				log.save(ZonedDateTime.now(), s.getRemoteSocketAddress(), findUserAgentLine(headerLines), requestedPath);
				
				String pageContent = null;

				if (requestedPath.contentEquals("/"))
				{
					pageContent = generatePageFromFile(config.getPages().get("startPage"), PageStatus.wasFound);
				}
				else if (requestedPath.startsWith("/dyn/date"))
				{
					//pageContent = generateJSONDatePage();
				}
				else
				{
					String localPath = "res" + requestedPath;
					pageContent = generatePageFromFile(localPath, PageStatus.wasFound);
					System.out.println("Path: " + localPath);
				}
				
				if (pageContent == null)
				{
					pageContent = generatePageFromFile(config.getPages().get("statusNotFoundtPage"), PageStatus.wasNotFound);
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
	
	private String generatePageFromFile(String path, PageStatus status)
	{
		StringBuilder sb = new StringBuilder();
		String extension = "";

		int i = path.lastIndexOf('.');
		if (i > 0)
		{
		    extension = path.substring(i+1);
		}
		
		if (status == PageStatus.wasFound)
		{
			sb.append("HTTP/1.1 200 OK");
		}
		else
		{
			sb.append("HTTP/1.1 404 Not Found");
		}
		sb.append("\r\n").append("Connection: close").append("\r\n");
		//sb.append("Content-Type: text/html; charset=utf-8").append("\r\n");
		sb.append("Content-Type: ");
		
		if (config.getContentType().get(extension) != null)
		{
			sb.append(config.getContentType().get(extension) + ";" );
		}
		else
		{
			sb.append("text/html;");
		}
		
		sb.append(" charset=utf-8").append("\r\n");
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
		instance.runServer("config.json");
		System.out.println("Uruchamiam serwer na porcie: ");
	}
}
