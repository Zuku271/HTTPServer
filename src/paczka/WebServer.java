package paczka;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketPermission;
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
			/*Server instance = new Server();*/
			File configFile = new File("config.json");
			ObjectMapper configObjectMapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Config.class, new ItemDeserializer());
			configObjectMapper.registerModule(module);
			
			Config config = configObjectMapper.readValue(configFile, Config.class);

		
			try (ServerSocket s = new ServerSocket(config.getPort()))
			{
				System.out.println("The web server is running...");
				Executor pool = Executors.newFixedThreadPool(8);
				SecurityManager security = new SecurityManager();
				
				SocketPermission sm = new SocketPermission("localhost:1024-65535", "accept, connect, listen");
				
				//System.setSecurityManager(security);
				
				while (true)
				{
					pool.execute(new Server(s.accept(), config));
				}

			
			}
		} catch (IOException e)
		{
			System.out.println("");
		}
		
	}
}
