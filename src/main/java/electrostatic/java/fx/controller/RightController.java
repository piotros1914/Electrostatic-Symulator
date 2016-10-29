package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.ChargeModel;
import electrostatic.java.fx.model.Converter;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class RightController {
	
	@FXML
	private AnchorPane rightMenuAnchorPane;
	
	@FXML
	private TextField positionXTextField;

	@FXML
	private TextField positionYTextField;

	@FXML
	private TextField radiusTextField;

	@FXML
	private TextField chargeTextField;

	@FXML
	private ColorPicker colorPicker;

	@FXML
	private Button deleteBtn;

	@FXML
	private TitledPane chargeTitledPane;
	
	/////////////////////////////////////////////////////////////
	
	@FXML
	private TextField widthTextField;
	
	@FXML
	private TextField heightTextField;

	//////////////////////////////////////////////////
	
	MainController mainController;
	private ChargeModel chargeModel;

	

	@FXML
	public void deleteElectricCharge(ActionEvent event) {
		chargeModel.deleteCurrentCharge();
	}

	@FXML
	void initialize() {

		chargeTitledPane.disableProperty().set(true);
	
	}

	
	public void initModel(ChargeModel chargeModel){
		this.chargeModel = chargeModel;
		 chargeModel.currentChargeProperty().addListener((observable, oldCharge, newCharge) -> {
		 if (oldCharge != null) {
             Bindings.unbindBidirectional(positionXTextField.textProperty(), newCharge.centerXProperty());
//             Bindings.unbindBidirectional(positionYTextField.textProperty(), newCharge.centerYProperty());
             Bindings.unbindBidirectional(radiusTextField.textProperty(), newCharge.radiusProperty());
             Bindings.unbindBidirectional(colorPicker.valueProperty(), newCharge.colorProperty());
             chargeTitledPane.disableProperty().unbind();

         }
         if (newCharge == null) {
             positionXTextField.setText("");
             positionYTextField.setText("");
             radiusTextField.setText("");
             chargeTextField.setText("");
             colorPicker.setValue(Color.WHITE);
         } 
         else {
    		Bindings.bindBidirectional(positionXTextField.textProperty(), newCharge.centerXProperty(), Converter.normalConverter);        		
//        		Bindings.bindBidirectional(positionYTextField.textProperty(), newCharge.centerYProperty(), Converter.yInverseConverter(box.getHeight()));        		
    		Bindings.bindBidirectional(radiusTextField.textProperty(), newCharge.radiusProperty(), Converter.normalConverter);
    		Bindings.bindBidirectional(chargeTextField.textProperty(), newCharge.chargeProperty(), Converter.normalConverter);        		      		
    		Bindings.bindBidirectional(colorPicker.valueProperty(), newCharge.colorProperty());       		
    		chargeTitledPane.disableProperty().bind(newCharge.getDisabledProperty());
         }
	
		  });
	}
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public AnchorPane getRightMenuAnchorPane() {
		return rightMenuAnchorPane;
	}
	
}
