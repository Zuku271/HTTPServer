package paczka;

import java.io.BufferedReader;

import pl.edu.agh.kis.Main2;

public class Server
{
	private static final int PORT = 8800;
	
	public static void main(String[] args) {
		System.out.println("Uruchamiam serwer na porcie: " + PORT);
		Main2 instance = new Main2();
		instance.runServer(PORT);
	}
}
