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

	
	private void runServer(String configFile)
	{
		ServerSocket serverSocket = null;
		try
		{
			FileInputStream configFileInput = new FileInputStream(configFile);
			Properties config = new Properties();
			config.load(configFileInput);
			
			
			if ((PORT = Integer.valueOf(config.getProperty("PORT"))) == 0)
			{
				PORT = 8800;
			}
			
			startPage = config.getProperty("startPage");
			
			serverSocket = new ServerSocket(PORT);
			while (true)
			{
				Socket s = serverSocket.accept();
				System.out.println("Polaczony klient: " + s.getRemoteSocketAddress());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
				List<String> headerLines = getHttpHeader(br);

				String requestedPath = "";
				String getLine = findGETLine(headerLines);
				if (getLine != null) {
					requestedPath = getPathFromGetLine(getLine);
				}
				System.out.println("GET Path:" + requestedPath);

				String pageContent = null;
				if (requestedPath.startsWith("/dyn/date"))
				{
					//pageContent = generateJSONDatePage();
				}
				
				else
				{
					String localPath = "res" + requestedPath;
					//pageContent = generatePageFromFile(localPath);
				}
				
				if (pageContent == null)
				{
					//pageContent = generateDefaultPage();
				}
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
	
	public static void main(String[] args) {
		System.out.println("Uruchamiam serwer na porcie: ");
		Server instance = new Server();
		instance.runServer("config.txt");
	}
}
