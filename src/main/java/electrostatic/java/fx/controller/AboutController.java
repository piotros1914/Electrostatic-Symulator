package electrostatic.java.fx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutController {
	
	@FXML
	private Button closeBtn;
		
	@FXML
	public void initialize(){	
		
	}
	
	@FXML
	public void closeAction(){
		closeBtn.getScene().getWindow().hide();
	}
}
