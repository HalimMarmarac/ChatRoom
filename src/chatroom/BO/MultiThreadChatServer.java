package chatroom.BO;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadChatServer {

	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	// This chat server can accept up to 20 clients
	static clientThread t[] = new clientThread[20];

	public static void main(String args[]) {

		int port_number = 8000; // default port

		// Try to open a server socket on port port_number (default 8000)
		try {
			serverSocket = new ServerSocket(port_number);
		} // try
		catch (IOException e) {
			System.out.println(e);
		}

		System.out.println("Multithreaded server started at " + new Date() + ".\n");

		// Create a socket object from the ServerSocket to listen and accept
		// connections
		// Open input and output streams
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < t.length; i++) {
					if (t[i] == null) {
						(t[i] = new clientThread(clientSocket, t)).start();
						break;
					}
				}
				if (i == t.length) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

class clientThread extends Thread {

	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	clientThread t[];

	public clientThread(Socket clientSocket, clientThread[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		String line;
		String name;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			os.println("Type your username again: ");
			name = is.readLine();
			os.println("Hello " + name + ". To leave enter 'quit'...\nstart typing> ");
			while (true) {
				line = is.readLine();
				if (line.startsWith("quit"))
					break;
				for (int i = 0; i < t.length; i++) {
					if (t[i] != null && t[i] != this) {
						t[i].os.println(name + ":> " + line);
					}
				}
			}
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null && t[i] != this)
					t[i].os.println(name + " left the chat room...");
			}
			os.println("Bye " + name);

			// Set to null the current thread variable such that other client
			// could be accepted by the server
			for (int i = 0; i < t.length; i++)
				if (t[i] == this)
					t[i] = null;

			// close resources
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
