package org.ju.mtech.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ju.mtech.chat.api.util.ChatUtil;

public class ServerMain {

	private Map<String, Socket> clientSocketMap;

	public ServerMain() {
		clientSocketMap = new HashMap<>();
	}

	public void serverStart() throws Exception {
		ServerSocket listener = new ServerSocket(9898);
		try {
			while (true) {
				Socket clientSocket = listener.accept();
				new Thread(new ChatProcess(clientSocket, this)).start();
			}
		} finally {
			for (Entry<String, Socket> socketEntry : clientSocketMap.entrySet()) {
				System.out.println("Checking socket - " + socketEntry.getKey());
				if (!socketEntry.getValue().isClosed()) {
					socketEntry.getValue().close();
				}
			}
			listener.close();
		}
	}

	public void broadcast(String name, String message) {
		try {
			for (Entry<String, Socket> socketEntry : clientSocketMap.entrySet()) {
				System.out.println("Checking socket - " + socketEntry.getKey());
				if (!socketEntry.getKey().equalsIgnoreCase(name) && !socketEntry.getValue().isClosed()) {
					PrintWriter out = new PrintWriter(socketEntry.getValue().getOutputStream(), true);
					String displayChatContent = ChatUtil.dislayContent(name, message);
					out.print(displayChatContent);
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Socket> getClientSocketMap() {
		return clientSocketMap;
	}

	public static void main(String[] args) throws Exception {
		ServerMain serverMain = new ServerMain();
		serverMain.serverStart();
	}
}
