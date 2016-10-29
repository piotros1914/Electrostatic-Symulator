package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.Box;
import electrostatic.java.fx.model.ChargeModel;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class MainController {

	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private VBox topVBox;
	@FXML
	private VBox bottomVBox;

	@FXML
	private AppController appController;

	@FXML
	private RightController rightController;

	@FXML
	private LeftController leftController;
	
	@FXML
	private MenuController menuController;

	@FXML
	private AnimationToolBarController animationToolBarController;
	
	@FXML
	private BottomController bottomController;
	
	
	@FXML
	void initialize() {
		ChargeModel chargeModel = new ChargeModel();	
		Box box = new Box();
		initModelInControllers(chargeModel, box);	
		
		setMainController();			
			
		ScrollPane appScrollPane = appController.getAppScrollPane();
		AnchorPane leftAnchorPane = leftController.getLeftMenuAnchorPane();
		AnchorPane rightAnchorPane = rightController.getRightMenuAnchorPane();		
		setSizeListeners(appScrollPane, leftAnchorPane, rightAnchorPane);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initModelInControllers(ChargeModel chargeModel, Box box) {
		rightController.initModel(chargeModel);
		bottomController.initModel(box);
		appController.initModel(chargeModel, box);
		leftController.initModel(chargeModel);
		animationToolBarController.initModel(chargeModel);
	}

	private void setMainController() {
		appController.setMainController(this);
		rightController.setMainController(this);
		leftController.setMainController(this);
		menuController.setMainController(this);
		animationToolBarController.setMainController(this);
	}

	private void setSizeListeners(ScrollPane appScrollPane, AnchorPane rightAnchorPane, AnchorPane leftAnchorPane) {

		mainBorderPane.widthProperty().addListener((obs, oldValue, newValue) -> {
			double width = newValue.doubleValue() - rightAnchorPane.getPrefWidth() - leftAnchorPane.getPrefWidth();
			appScrollPane.setPrefWidth(width);
		});

		mainBorderPane.heightProperty().addListener((obs, oldValue, newValue) -> {
			double height = newValue.doubleValue() - topVBox.getPrefHeight() - bottomVBox.getPrefHeight();
			appScrollPane.setPrefHeight(height);
			rightAnchorPane.setPrefHeight(height);
			leftAnchorPane.setPrefHeight(height);
		});
	}

	public AppController getAppController() {
		return appController;
	}

	public RightController getRightController() {
		return rightController;
	}

	public LeftController getLeftController() {
		return leftController;
	}

	public BottomController getBottomController() {
		return bottomController;
	}

	public BorderPane getMainBorderPane() {
		return mainBorderPane;
	}	
}
