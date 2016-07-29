package org.ju.mtech.chat;

import org.ju.mtech.client.ChatProcess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaChat extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Java Chat");
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		Scene scene = new Scene(vBox, 400, 350);
		scene.setFill(Color.OLDLACE);
		this.createInternals(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			ChatProcess.getClientThread().stop();
			ChatProcess.sendQUIT();
			Platform.exit();
		});
		ChatProcess.showTitle(primaryStage);
	}

	private void createInternals(Scene scene) {
		// ((VBox) scene.getRoot()).getChildren().addAll(createMenu());
		((VBox) scene.getRoot()).getChildren().add(new ChatUI().create());
		/*
		 * ((VBox) scene.getRoot()).getChildren().addAll(new
		 * EllipticGridUI().createEllipticGrid());
		 */
	}

	/*
	 * private MenuBar createMenu() { MenuBar menuBar = new MenuBar(); Menu
	 * fileMenu = new Menu("Help"); menuBar.getMenus().addAll(fileMenu); return
	 * menuBar; }
	 */
}
