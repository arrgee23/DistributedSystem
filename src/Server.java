//package assignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

public class Server // extends Thread
{
	private static final int BASE_SOCKET = 20000;
	private ServerSocket serverSocket;
	Hashtable<Integer, Integer> table;

	// initialize server
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000000);
		table = new Hashtable<Integer, Integer>();
	}

	public void isstart() {
		
		// SERVER FUNCTIONS
		while (true) {
			try {
				Socket server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				
				String incoming = in.readUTF();
						
				
				// first 3 letters are always put or get
				String method = incoming.substring(0, 3);
				int value = Integer.parseInt(incoming.substring(3,incoming.length()));
				
				System.out.println("Just received " + incoming);

				if (method.equals("put")) {
					table.put(value, value);
					in.close();
				} 
				else if (method.equals("get")) {
					
					try {
						out.writeUTF(table.get(value).toString());
						out.close();
						in.close();
					}

					catch (Exception e) {
						System.out.println("Data not found in DHT");
						out.writeUTF("-1");
						out.close();
						in.close();
					}
				}

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = BASE_SOCKET + Integer.parseInt(args[0]);
		System.out.println("Stareted Server at port: " + port);
		System.out.println("Usage: put/get <number>");

		// start client thread
		Client c = new Client();
		c.start();

		try {
			Server s = new Server(port);
			s.isstart();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}