package org.ju.mtech.chat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class EllipticGridUI {

	public GridPane createEllipticGrid() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.add(new Label("Elliptic Curve Equation: y2=x3+ax+b"), 0, 0, 2, 1);
		Label labelY = new Label();
		labelY.setVisible(false);
		Pair<Label, TextField> inputPairX = createInputPair("x", 10, labelY);
		gridPane.add(inputPairX.getKey(), 0, 1);
		gridPane.add(inputPairX.getValue(), 1, 1);
		Pair<Label, TextField> inputPairA = createInputPair("a", 10, labelY);
		gridPane.add(inputPairA.getKey(), 0, 2);
		gridPane.add(inputPairA.getValue(), 1, 2);
		Pair<Label, TextField> inputPairB = createInputPair("b", 10, labelY);
		gridPane.add(inputPairB.getKey(), 0, 3);
		gridPane.add(inputPairB.getValue(), 1, 3);
		gridPane.add(labelY, 0, 4, 2, 1);
		Button submitButton = new Button("Compute Y");
		submitButton.setOnAction(
				submitEllipticAction(inputPairA.getValue(), inputPairB.getValue(), inputPairX.getValue(), labelY));
		gridPane.add(submitButton, 1, 5);
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				inputPairA.getValue().setText(null);
				inputPairB.getValue().setText(null);
				inputPairX.getValue().setText(null);
				toggleLabel(labelY, null, false);
			}
		});
		gridPane.add(resetButton, 0, 5);
		return gridPane;
	}

	private Pair<Label, TextField> createInputPair(final String labelText, final int prefColumnCount,
			final Label labelField) {
		TextField textField = new TextField();
		textField.setPrefColumnCount(prefColumnCount);
		textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) {
				if (textField.getText() != null && !textField.getText().isEmpty()
						&& !textField.getText().matches("[0-9]+")) {
					textField.setText(null);
					this.toggleLabel(labelField, "Please enter only integer value", true);
				} else {
					this.toggleLabel(labelField, null, false);
				}
			}
		});
		return new Pair<Label, TextField>(new Label(labelText), textField);
	}

	private void toggleLabel(final Label labelField, final String text, final boolean visibility) {
		labelField.setText(text);
		labelField.setVisible(visibility);
	}

	private EventHandler<ActionEvent> submitEllipticAction(final TextField aText, final TextField bText,
			final TextField xText, final Label yLabel) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String aValue = aText.getText();
				String bValue = bText.getText();
				String xValue = xText.getText();
				if (aValue == null || aValue.isEmpty() || bValue == null || bValue.isEmpty() || xValue == null
						|| xValue.isEmpty()) {
					yLabel.setText("Please provide all input data");
					yLabel.setVisible(true);
				} else {
					long a = Long.parseLong(aValue);
					long b = Long.parseLong(bValue);
					long x = Long.parseLong(xValue);
					EllipticCurve ellipticCurve = new EllipticCurve(a, b, x);
					yLabel.setText("y is " + ellipticCurve.getY().toString());
					yLabel.setVisible(true);
				}
			}
		};
	}

}
