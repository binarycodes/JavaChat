package org.ju.mtech.chat;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.ju.mtech.client.ChatProcess;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ConnectionUI {

	private static TextField ipInput;
	private static TextField portInput;
	private static TextField nameInput;
	private static Button connectButton;

	public static HBox create() {
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		ipInput = new TextField();
		try {
			ipInput.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			ipInput.setText("127.0.0.1");
		}
		hBox.getChildren().add(ipInput);
		portInput = new TextField();
		portInput.setText("9898");
		hBox.getChildren().add(portInput);
		nameInput = new TextField();
		hBox.getChildren().add(nameInput);
		connectButton = new Button("Connect");
		connectButton.setOnAction(connectToServer());
		hBox.getChildren().add(connectButton);
		bindDisableFields();
		return hBox;
	}

	private static EventHandler<ActionEvent> connectToServer() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String ipAddress = ipInput.getText();
				Integer portNo = Integer.valueOf(portInput.getText());
				String name = nameInput.getText();
				log(String.format("connection to %s:%d as %s", ipAddress, portNo, name));
				ChatProcess.connect(ipAddress, portNo, name);
			}
		};
	}

	private static void bindDisableFields() {
		BooleanBinding disableConnectionUI = Bindings.createBooleanBinding(() -> ChatProcess.getConnected().get(),
				ChatProcess.getConnected());
		connectButton.disableProperty().bind(disableConnectionUI);
		ipInput.disableProperty().bind(disableConnectionUI);
		portInput.disableProperty().bind(disableConnectionUI);
		nameInput.disableProperty().bind(disableConnectionUI);
	}

	private static void log(String data) {
		System.out.println(data);
		System.out.println(System.getProperty("line.separator"));
	}

	public static String getName() {
		String name = "";
		if (nameInput != null && nameInput.getText() != null) {
			name = nameInput.getText();
		}
		return name;
	}
}
