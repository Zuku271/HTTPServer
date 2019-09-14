package paczka;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;
import java.time.ZonedDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class WebServer
{
	public static void main(String[] args)
	{
		try
		{
			File configFile = new File("config.json");
			ObjectMapper configObjectMapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Config.class, new ItemDeserializer());
			configObjectMapper.registerModule(module);
			
			Config config = configObjectMapper.readValue(configFile, Config.class);
			Blacklist bl = new Blacklist("blacklist.csv");
			bl.load();
		
			try (ServerSocket s = new ServerSocket(config.getPort()))
			{
				System.out.println("The web server is running...");
				Executor pool = Executors.newFixedThreadPool(100);
						
				while (true)
				{
					Socket sc = s.accept();
					if (bl.checkAccept(sc))
					{
						pool.execute(new Server(sc, config));
					}
					else
					{
						sc.close();
					}
				} 
			}
		} catch (IOException e)
		{
			System.out.println("");
		}
		
	}
}
