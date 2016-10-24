package electrostatic.java.fx.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.SetBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

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
	private TitledPane chargeTitledPane;
	
	/////////////////////////////////////////////////////////////
	
	@FXML
	private TextField widthTextField;
	
	@FXML
	private TextField heightTextField;

	//////////////////////////////////////////////////
	
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

		chargeTitledPane.disableProperty().set(true);
		


	}
	private void setBinding(){
		Bindings.bindBidirectional(widthTextField.textProperty(), mainController.getAppController().getBox().prefWidthProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(heightTextField.textProperty(), mainController.getAppController().getBox().prefHeightProperty(), new NumberStringConverter());
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
		setBinding();
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

	public TitledPane getChargeTitledPane() {
		return chargeTitledPane;
	}

	public void setChargeTitledPane(TitledPane chargeTitledPane) {
		this.chargeTitledPane = chargeTitledPane;
	}

	public TextField getWidthTextField() {
		return widthTextField;
	}

	public void setWidthTextField(TextField widthTextField) {
		this.widthTextField = widthTextField;
	}

	public TextField getHeightTextField() {
		return heightTextField;
	}

	public void setHeightTextField(TextField heightTextField) {
		this.heightTextField = heightTextField;
	}

	
	
	
}
