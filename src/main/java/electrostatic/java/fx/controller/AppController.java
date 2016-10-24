package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.ChargeControll;
import electrostatic.java.fx.model.Converter;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class AppController {

	@FXML
	private Pane appPane;

	private MainController mainController;
	private ChargeControll chargeControll;

	private Pane box;
	private int boxWidth = 300;
	private int boxHeight = 300;

	private NumberAxis xTopAxis;
	private NumberAxis xDownAxis;
	private NumberAxis yLeftAxis;
	private NumberAxis yRightAxis;
	private int tickUnit = 100;
	private int axisDistance = 5;

	private ContextMenu contextMenu;
	private MenuItem addMenuItem;
	private MenuItem delateMenuItem;

	private double cursorPositionX;
	private double cursorPositionY;

	@FXML
	void initialize() {
		xTopAxis = new NumberAxis(0, boxWidth, tickUnit);
		xDownAxis = new NumberAxis(0, boxWidth, tickUnit);
		yLeftAxis = new NumberAxis(0, boxHeight, tickUnit);
		yRightAxis = new NumberAxis(0, boxHeight, tickUnit);

		xTopAxis.getStyleClass().add("axe");
		xTopAxis.setSide(Side.TOP);
		xTopAxis.setMinorTickVisible(false);

		xDownAxis.getStyleClass().add("axe");
		xDownAxis.setSide(Side.BOTTOM);
		xDownAxis.setMinorTickVisible(false);

		yLeftAxis.getStyleClass().add("axe");
		yLeftAxis.setSide(Side.LEFT);
		yLeftAxis.setMinorTickVisible(false);

		yRightAxis.getStyleClass().add("axe");
		yRightAxis.setSide(Side.RIGHT);
		yRightAxis.setMinorTickVisible(false);

		xTopAxis.prefHeightProperty().set(30);
		xDownAxis.prefHeightProperty().set(30);
		yLeftAxis.prefWidthProperty().set(30);
		yRightAxis.prefWidthProperty().set(30);

		box = new Pane();
		// box.minWidthProperty().set(boxWidth);
		// box.minHeightProperty().set(boxHeight);
		box.prefWidthProperty().set(boxWidth);
		box.prefHeightProperty().set(boxHeight);
		box.getStyleClass().add("box");
		box.getChildren().addAll(xTopAxis, xDownAxis, yLeftAxis, yRightAxis);

		xTopAxis.layoutYProperty().set(-xTopAxis.prefHeightProperty().get() - axisDistance);
		xDownAxis.layoutYProperty().set(box.prefHeightProperty().get() + axisDistance);
		yLeftAxis.layoutXProperty().set(-yLeftAxis.prefWidthProperty().getValue() - axisDistance);
		yRightAxis.layoutXProperty().set(box.prefWidthProperty().get() + axisDistance);

		appPane.getChildren().add(box);

		// listenery dla ustawien wymiarów boxa, jego wysrodkowania i ustawien jego osi
		setListeners();

		// inicjacja g³ównego sterownika ³adunków
		chargeControll = new ChargeControll(box);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// tworzenie menu kontekstowego
		contextMenu = new ContextMenu();

		addMenuItem = new MenuItem("Nowy ³adunek");
		addMenuItem.setOnAction(e -> chargeControll.addCharge(cursorPositionX, cursorPositionY));

		delateMenuItem = new MenuItem("Usuñ");
		delateMenuItem.setOnAction(e -> chargeControll.deleteCharge());

		// Przechwytywanie eventu przed wykonaniem akcji. Event uwa¿any jest za
		// wykonany jesli prawym klawiszem klikniete zosta³o menu kontekstowe.
		contextMenu.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
			if (mouseEvent.getButton() == MouseButton.SECONDARY)
				mouseEvent.consume();
		});

		// przechwytywanie uruchomienia menu kontekstowego
		box.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
			cursorPositionX = mouseEvent.getX();
			cursorPositionY = mouseEvent.getY();
			contextMenu.getItems().clear();
			contextMenu.getItems().add(addMenuItem);
			if (mouseEvent.getButton() == MouseButton.SECONDARY) {
				if (chargeControll.getSelectedCharge() != null)
					if (chargeControll.getSelectedCharge().contains(mouseEvent.getX(), mouseEvent.getY())) {
						contextMenu.getItems().clear();
						contextMenu.getItems().add(delateMenuItem);
					}
				contextMenu.show(appPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
			} else
				contextMenu.hide();
		});

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		

		box.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent-> {
			mainController.getCursorPositionXLabel().setText(String.valueOf(mouseEvent.getX()));
			mainController.getCursorPositionYLabel().setText(Converter.mouseYConverter(mouseEvent.getY(), box.getWidth()));
			
		});
		
		// wy³¹czenie zaznaczonego ³adunku
		appPane.setOnMouseClicked(mouseEvent -> {
			if (chargeControll.getSelectedCharge() != null)
				if (mouseEvent.getX() < box.getLayoutX() || mouseEvent.getX() > (box.getLayoutX() + box.getPrefWidth())
						|| mouseEvent.getY() < box.getLayoutY()
						|| mouseEvent.getY() > (box.getLayoutY() + box.getPrefHeight())) {

					chargeControll.deselectAllCharges();
					mainController.getLeftMenuController().clear();
					mainController.getRightMenuController().clear();
				}
		});

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setListeners() {

		xTopAxis.prefWidthProperty().bind(box.prefWidthProperty());
		xDownAxis.prefWidthProperty().bind(box.prefWidthProperty());
		yLeftAxis.prefHeightProperty().bind(box.prefHeightProperty());
		yRightAxis.prefHeightProperty().bind(box.prefHeightProperty());

		
		appPane.widthProperty().addListener((obs, oldValue, newValue) -> {
			// wysrodkowanie boxa			
			int x = (int) (newValue.doubleValue() / 2 - box.getPrefWidth() / 2);
			box.layoutXProperty().set(x);					
		});

		appPane.heightProperty().addListener((obs, oldValue, newValue) -> {	
			// wysrodkowanie boxa		
			int y = (int) (newValue.doubleValue() / 2 - box.getPrefHeight() / 2);
			box.layoutYProperty().set(y);			
		});

		box.prefWidthProperty().addListener((obs, oldValue, newValue) -> {
			// wysrodkowanie boxa
			int x = (int) (appPane.getWidth() / 2 - newValue.doubleValue() / 2);
			box.layoutXProperty().set(x);

			// ustawienie skali na osi
			xTopAxis.upperBoundProperty().set(newValue.doubleValue());
			xDownAxis.upperBoundProperty().set(newValue.doubleValue());

			// ustawienie pozycji prawej osi
			yRightAxis.layoutXProperty().set(box.prefWidthProperty().get() + axisDistance);
			
			//ustawienie szerokosci appPane
			appPane.setPrefWidth(newValue.doubleValue() + 200);
		
		});

		box.prefHeightProperty().addListener((obs, oldValue, newValue) -> {
			// wysrodkowanie boxa
			int y = (int) (appPane.getHeight() / 2 - newValue.doubleValue() / 2);
			box.layoutYProperty().set(y);

			// ustawienie skali na osi
			yLeftAxis.upperBoundProperty().set(newValue.doubleValue());
			yRightAxis.upperBoundProperty().set(newValue.doubleValue());

			// ustawienie pozycji dolnej osi
			xDownAxis.layoutYProperty().set(box.prefHeightProperty().get() + axisDistance);
			// ustawienie wysokosci appPane
			appPane.setPrefHeight(newValue.doubleValue() + 200);
		});

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	public void addElectricCharge() {

		chargeControll.addCharge();
	}

	@FXML
	public void deleteElectricCharge() {

		chargeControll.deleteCharge();
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
		chargeControll.setMainController(mainController);
		chargeControll.setChargeObservableListListener();		
	}

	public ChargeControll getChargeControll() {
		return chargeControll;
	}

	public void setChargeControll(ChargeControll chargeControll) {
		this.chargeControll = chargeControll;
	}

	public Pane getBox() {
		return box;
	}

	public void setBox(Pane box) {
		this.box = box;
	}

}
