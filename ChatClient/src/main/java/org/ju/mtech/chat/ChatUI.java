package org.ju.mtech.chat;

import org.ju.mtech.chat.api.encryption.ecc.ECC;
import org.ju.mtech.chat.api.util.ChatUtil;
import org.ju.mtech.client.ChatProcess;
import org.ju.mtech.client.ClientThread;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ChatUI {
	public VBox create() {
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(5, 5, 5, 5));
		vBox.getChildren().add(ConnectionUI.create());

		TextArea displayArea = new TextArea();
		displayArea.setEditable(false);
		displayArea.setFocusTraversable(false);
		vBox.getChildren().add(displayArea);

		TextArea chatArea = new TextArea();
		vBox.getChildren().add(chatArea);

		Button sendButton = new Button("Send");
		sendButton.setOnAction(sendChat(chatArea, displayArea));

		BooleanBinding disableSendButton = Bindings.createBooleanBinding(() -> !ChatProcess.getConnected().get(),
				ChatProcess.getConnected());
		sendButton.disableProperty().bind(disableSendButton);

		vBox.getChildren().add(sendButton);
		Thread clientThread = new Thread(new ClientThread(displayArea));
		clientThread.start();
		ChatProcess.setClientThread(clientThread);
		return vBox;
	}

	private EventHandler<ActionEvent> sendChat(final TextArea chatArea, final TextArea displayArea) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String chatContent = chatArea.getText();
				if (chatContent != null && !chatContent.trim().isEmpty()) {
					chatArea.clear();
					ChatProcess.sendData(ECC.encrypt(chatContent));
					String displayChatContent = ChatUtil.dislayContent(ConnectionUI.getName(), chatContent);
					displayArea.appendText(displayChatContent);
				}
			}
		};
	}
}
