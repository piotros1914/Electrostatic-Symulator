package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.Box;
import electrostatic.java.fx.model.Converter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BottomController {
	
	@FXML
	private Label cursorPositionXLabel;

	@FXML
	private Label cursorPositionYLabel;
	
	private Box box;
	
	@FXML
	public void initialize(){
		
	}
	
	public void initModel(Box box){
		box.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {		
			String cursorPositionX = String.valueOf(mouseEvent.getX());
			cursorPositionXLabel.setText(cursorPositionX);
			String cursorPositionY = Converter.mouseYConverter(mouseEvent.getY(), box.getWidth());
			cursorPositionYLabel.setText(cursorPositionY);
		});
	}

	public void setCursorPositionXLabel(Label cursorPositionXLabel) {
		this.cursorPositionXLabel = cursorPositionXLabel;
	}

	public void setCursorPositionYLabel(Label cursorPositionYLabel) {
		this.cursorPositionYLabel = cursorPositionYLabel;
	}

	public Label getCursorPositionXLabel() {
		return cursorPositionXLabel;
	}

	public Label getCursorPositionYLabel() {
		return cursorPositionYLabel;
	}
}
