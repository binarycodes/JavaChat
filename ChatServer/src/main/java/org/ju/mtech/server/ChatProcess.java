package org.ju.mtech.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatProcess implements Runnable {
	private Socket socket;
	private ServerMain main;

	public ChatProcess(Socket socket, ServerMain main) {
		this.socket = socket;
		this.main = main;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// Send a welcome message to the client.
			out.println(".HELLO");
			out.flush();
			String name = in.readLine();
			String ack = in.readLine();

			if (ack.equalsIgnoreCase(".ACK") && name != null && !name.isEmpty()) {
				main.getClientSocketMap().put(name, socket);
			}

			while (true) {
				String input = in.readLine();
				if (input == null || input.equals(".QUIT")) {
					log("Client send .QUIT " + name);
					in.close();
					out.close();
					socket.close();
					this.main.getClientSocketMap().remove(name);
					break;
				}
				this.main.broadcast(name, input);
			}
		} catch (IOException e) {
			log("Error handling client# " + e);
		}
	}

	private void log(String message) {
		System.out.println(message);
	}
}
