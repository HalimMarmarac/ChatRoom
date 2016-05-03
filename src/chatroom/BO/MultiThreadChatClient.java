package chatroom.BO;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import chatroom.DAO.UserDAO;
import chatroom.DTO.User;

public class MultiThreadChatClient implements Runnable {

	// data fields
	static Socket clientSocket = null;
	static PrintStream os = null;
	static DataInputStream is = null;
	static BufferedReader inputLine = null;
	static boolean closed = false;

	public static void main(String[] args) throws SQLException, Exception {

		int port_number = 8000; // default port
		String host = "localhost";

		UserDAO userDao = new UserDAO(); // dao object

		Scanner in = new Scanner(System.in);
		int choice = -1; // input

		do {
			System.out.println("-> 1. Login");
			System.out.println("-> 2. Register");
			System.out.println("-> 0. Close");
			System.out.print("\n-> ");
			try {
				choice = in.nextInt();
				if (choice < 0 || choice > 2)
					System.out.println("\nChoice doesn't exist!\n");
			} catch (InputMismatchException e) {
				System.out.println("\nWrong input!\n");
				in.nextLine();
			}
		} while (choice < 0 || choice > 2);

		// 0 to close app
		if (choice == 0)
			System.exit(0);

		else if (choice == 1) {

			System.out.println("\nEnter your username & password...");
			System.out.print("Username: ");
			String username = in.next();
			System.out.print("Password: ");
			String password = in.next();

			User user = userDao.getUser(username, password);

			if (user == null) {

				int numoftry = 0;

				while (numoftry < 3) {

					System.out.println("\nWrong username or password!\n");
					System.out.println("Try again...");
					System.out.print("Username: ");
					username = in.next();
					System.out.print("Password: ");
					password = in.next();
					user = userDao.getUser(username, password);
					numoftry++;
					if (numoftry == 3)
						System.out.println(
								"\nYou entered wrong username or password to many times, app is now closing...");
					if (user != null)
						break;
				}
			}

			if (user != null) {
				// Try to open a socket on a given host and port
				// Try to open input and output streams
				try {
					clientSocket = new Socket(host, port_number);
					inputLine = new BufferedReader(new InputStreamReader(System.in));
					os = new PrintStream(clientSocket.getOutputStream());
					is = new DataInputStream(clientSocket.getInputStream());
				} catch (UnknownHostException e) {
					System.err.println("Don't know about host " + host);
				} catch (IOException e) {
					System.err.println("Couldn't get I/O for the connection to thehost " + host);
				}

				/* If everything has been initialized then we want to write some data
				 * to the socket we have opened a connection to on port port_number
				 */
				if (clientSocket != null && os != null && is != null) {
					try {
						// Create a thread to read from the server
						new Thread(new MultiThreadChatClient()).start();

						while (!closed) {
							os.println(inputLine.readLine());
						}
						// close resources
						os.close();
						is.close();
						clientSocket.close();
					} catch (IOException e) {
						System.err.println("IOException:  " + e);
					}
				}
			}
		}

		else if (choice == 2) {
			System.out.println("\nEnter your registration username & password...");
			System.out.print("Username: ");
			String usernameR = in.next();
			System.out.print("Password: ");
			String passwordR = in.next();

			User user = userDao.getUserInfo(usernameR);

			if (user != null) {

				int numoftry = 0;

				while (numoftry < 3) {

					System.out.println("\nUsername already exists!\n");
					System.out.println("Try different username...");
					System.out.print("Username: ");
					usernameR = in.next();
					user = userDao.getUserInfo(usernameR);
					numoftry++;
					if (numoftry == 3 && user != null)
						System.out.println("\nYou entered existing username to many times, app is now closing...");
					if (user == null)
						break;
				}
			}

			if (user == null) {

				System.out.print("Password: ");
				passwordR = in.next();

				userDao.addUser(usernameR, passwordR);

				System.out.println("\nCongratulations, you have successfully registered!");

				// Try to open a socket on a given host and port
				// Try to open input and output streams
				try {
					clientSocket = new Socket(host, port_number);
					inputLine = new BufferedReader(new InputStreamReader(System.in));
					os = new PrintStream(clientSocket.getOutputStream());
					is = new DataInputStream(clientSocket.getInputStream());
				} catch (UnknownHostException e) {
					System.err.println("Don't know about host " + host);
				} catch (IOException e) {
					System.err.println("Couldn't get I/O for the connection to thehost " + host);
				}
				
				/* If everything has been initialized then we want to write some data
				 * to the socket we have opened a connection to on port port_number
				 */
				if (clientSocket != null && os != null && is != null) {
					try {
						// Create a thread to read from the server
						new Thread(new MultiThreadChatClient()).start();

						while (!closed) {
							os.println(inputLine.readLine());
						}
						// close resources
						os.close();
						is.close();
						clientSocket.close();
					} catch (IOException e) {
						System.err.println("IOException:  " + e);
					}
				}
			}
		}
		
		in.close();
	}

	@SuppressWarnings("deprecation")
	public void run() {
		
		String responseLine;

		// Keep on reading from the socket till we receive the "Bye" from the server
		try {
			while ((responseLine = is.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("Bye ") != -1)
					break;
				System.out.println();
			}
			closed = true;
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}
}
