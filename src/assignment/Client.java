//package assignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	static final String serverName = "localhost";
	private static final int BASE_SOCKET = 20000;
	
	public static void putUtil(int v,int port){
		try
	      {
	        /* System.out.println("Connecting to " + serverName
	                             + " on port " + port);*/
	         Socket client = new Socket(serverName, port);
	         /*System.out.println("Just connected to "
	                      + client.getRemoteSocketAddress());*/
	         
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("put"+v);
	         /*out.writeUTF("Hello from "
	                      + client.getLocalSocketAddress());*/
	         
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         System.out.println("written: " + in.readUTF());
	         
	         client.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }

	public static int getUtil(int v,int port){
		int ret=Integer.MIN_VALUE;
		try
	      {
	        /* System.out.println("Connecting to " + serverName
	                             + " on port " + port);*/
	         Socket client = new Socket(serverName, port);
	         /*System.out.println("Just connected to "
	                      + client.getRemoteSocketAddress());*/
	         
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("get"+v);
	         /*out.writeUTF("Hello from "
	                      + client.getLocalSocketAddress());*/
	         
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         ret = Integer.parseInt(in.readUTF());
	         
	         client.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
		return ret;
	}
	public static void put(int v){
		putUtil(v,BASE_SOCKET+v%4);
	}
	
	public static int get(int k){
		return getUtil(k,BASE_SOCKET+k%4);
	}
	public static void main(String[] args) {
		put(3);put(4);
		System.out.println(get(3));
		
		System.out.println(get(4));
	}
	      
}
