package org.ju.mtech.client;

import java.io.IOException;

import org.ju.mtech.chat.api.encryption.ecc.ECC;

import javafx.scene.control.TextArea;

public class ClientThread implements Runnable {
	private TextArea displayArea;

	public ClientThread(final TextArea displayArea) {
		this.displayArea = displayArea;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (ChatProcess.getConnected().getValue() && ChatProcess.getIn().ready()) {
					String response = ECC.decrypt(ChatProcess.getIn().readLine());
					if (response != null) {
						this.displayArea.appendText(response.concat(System.getProperty("line.separator")));
					} else {
						System.out.println("read null from thread!");
					}
				} else {
					System.out.println("not connected");
				}
			}
		} catch (IOException e) {
			this.displayArea.appendText(e.getMessage().concat(System.getProperty("line.separator")));
		}
	}

}
