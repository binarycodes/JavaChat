package org.ju.mtech.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public final class ChatProcess {
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	private static Socket socket = null;
	private static BooleanProperty connected = new SimpleBooleanProperty(false);
	private static Thread clientThread;
	private static Stage myStage;

	private ChatProcess() {
	}

	public static void connect(String serverAddress, int port, String name) {
		try {
			socket = new Socket(serverAddress, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String hello = in.readLine();
			if (".HELLO".equalsIgnoreCase(hello)) {
				connected.set(true);
				sendACK(name);
				myStage.setTitle(myStage.getTitle().concat(" - ").concat(name));
			} else {
				socket.close();
				in.close();
				out.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendData(final String data) {
		out.println(data);
		out.flush();
	}

	public static void sendACK(final String name) {
		out.println(name);
		out.println(".ACK");
		out.flush();
	}

	public static void sendQUIT() {
		if (out != null) {
			out.println(".QUIT");
			out.flush();
		}
		try {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage() + e);
		}
	}

	public static BufferedReader getIn() {
		return in;
	}

	public static BooleanProperty getConnected() {
		return connected;
	}

	public static Thread getClientThread() {
		return clientThread;
	}

	public static void setClientThread(Thread clientThread) {
		ChatProcess.clientThread = clientThread;
	}

	public static void showTitle(Stage primaryStage) {
		myStage = primaryStage;
	}

}
