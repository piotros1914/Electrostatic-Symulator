package electrostatic.java.fx.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;

public class RightMenuController {

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
	private TitledPane rightTitledPane;

	MainController mainController;

	// @FXML
	// public void addElectricCharge(ActionEvent event) {
	//
	// mainController.getAppController().addElectricCharge();
	//
	// }

	@FXML
	public void deleteElectricCharge(ActionEvent event) {
		mainController.getAppController().deleteElectricCharge();
	}

	@FXML
	void initialize() {

		rightTitledPane.disableProperty().set(true);
//		positionYTextField.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//			System.out.println("newValue = " + newValue);
//			System.out.println("oldValue = " + oldValue);
//				
//			newValue = String.valueOf(-Double.valueOf(newValue).doubleValue());
//			positionYTextField.textProperty().set(newValue);
			


	}

	public void clear() {

		positionXTextField.clear();
		positionYTextField.clear();
		chargeTextField.clear();
		radiusTextField.clear();
		colorPicker.setValue(new Color(0, 0, 0, 1));

	}

	public TextField getPositionXTextField() {
		return positionXTextField;
	}

	public void setPositionXTextField(TextField positionXTextField) {
		this.positionXTextField = positionXTextField;
	}

	public TextField getPositionYTextField() {
		return positionYTextField;
	}

	public void setPositionYTextField(TextField positionYTextField) {
		this.positionYTextField = positionYTextField;
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public TextField getRadiusTextField() {
		return radiusTextField;
	}

	public void setRadiusTextField(TextField radiusTextField) {
		this.radiusTextField = radiusTextField;
	}

	public TextField getChargeTextField() {
		return chargeTextField;
	}

	public void setChargeTextField(TextField chargeTextField) {
		this.chargeTextField = chargeTextField;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(Button deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	public void setColorPicker(ColorPicker colorPicker) {
		this.colorPicker = colorPicker;
	}

	public TitledPane getRightTitledPane() {
		return rightTitledPane;
	}

	public void setRightTitledPane(TitledPane rightTitledPane) {
		this.rightTitledPane = rightTitledPane;
	}

}
