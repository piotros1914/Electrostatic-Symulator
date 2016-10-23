package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.ChargeControll;
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

	private NumberAxis xAxis;
	private NumberAxis xDownAxis;
	private NumberAxis yAxis;
	private NumberAxis yRightAxis;
	private int tickUnit = 100;

	private ContextMenu contextMenu;
	private MenuItem addMenuItem;
	private MenuItem delateMenuItem;

	private double cursorPositionX;
	private double cursorPositionY;

	@FXML
	void initialize() {
		xAxis = new NumberAxis(0, boxWidth, tickUnit);
		xDownAxis = new NumberAxis(0, boxWidth, tickUnit);
		yAxis = new NumberAxis(0, boxHeight, tickUnit);
		yRightAxis = new NumberAxis(0, boxHeight, tickUnit);

		xAxis.setStyle("-fx-color: black;" + " -fx-background-color: transparent;" + "");
		xAxis.setSide(Side.TOP);
		xAxis.setMinorTickVisible(false);

		xDownAxis.setStyle("-fx-color: black;" + " -fx-background-color: transparent;" + "");
		xDownAxis.setSide(Side.BOTTOM);
		xDownAxis.setMinorTickVisible(false);

		yAxis.setStyle("-fx-color: black;" + " -fx-background-color: transparent;" + "");
		yAxis.setSide(Side.LEFT);
		yAxis.setMinorTickVisible(false);

		yRightAxis.setStyle("-fx-color: black;" + " -fx-background-color: transparent;" + "");
		yRightAxis.setSide(Side.RIGHT);
		yRightAxis.setMinorTickVisible(false);

		xAxis.prefHeightProperty().set(30);
		xDownAxis.prefHeightProperty().set(30);
		yAxis.prefWidthProperty().set(30);
		yRightAxis.prefWidthProperty().set(30);

		box = new Pane();
		box.minWidthProperty().set(boxWidth);
		box.minHeightProperty().set(boxHeight);
		box.prefWidthProperty().set(boxWidth);
		box.prefHeightProperty().set(boxHeight);
		box.setStyle(" -fx-border-style: solid inside;" + " -fx-border-color: black;" + " -fx-border-width: 1;"
				+ " -fx-background-color: transparent,"
				+ "  linear-gradient(from 0.5px 0px to 50.5px 0px, repeat, #808080 1%, transparent 1%),"
				+ "  linear-gradient(from 0px 0.5px to 0px 50.5px, repeat, #808080 0.1%, transparent 1%);");
		box.getChildren().addAll(xAxis, xDownAxis, yAxis, yRightAxis);

		appPane.getChildren().add(box);

		// listenery dla wymiarów i layoutu
		setAppPaneListeners();

		chargeControll = new ChargeControll(box);

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

		// wy³¹czenie zaznaczonego ³adunku
		appPane.setOnMouseClicked(mouseEvent -> {
			if (chargeControll.getSelectedCharge() != null)
				if (mouseEvent.getX() < box.getLayoutX() || mouseEvent.getX() > (box.getLayoutX() + box.getPrefWidth())
						|| mouseEvent.getY() < box.getLayoutY() || mouseEvent.getY() > (box.getLayoutY() + box.getPrefHeight())) {
				
						chargeControll.deselectAllCharges();
						mainController.getLeftMenuController().clear();
						mainController.getRightMenuController().clear();
				}
		});
		//
// dupa dupa
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
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setAppPaneListeners() {
		appPane.prefWidthProperty().addListener((obs, oldValue, newValue) -> {
			int x = (int) (newValue.doubleValue() / 2 - boxWidth / 2);
			box.layoutXProperty().set(x);
			box.prefWidthProperty().set(boxWidth);

			xAxis.prefWidthProperty().bind(box.prefWidthProperty());
			xAxis.layoutYProperty().set(-xAxis.prefHeightProperty().get() - 5);

			xDownAxis.prefWidthProperty().bind(box.prefWidthProperty());
			xDownAxis.layoutYProperty().set(box.prefHeightProperty().get() + 5);
		});

		appPane.prefHeightProperty().addListener((obs, oldValue, newValue) -> {
			int y = (int) (newValue.doubleValue() / 2 - boxHeight / 2);
			box.layoutYProperty().set(y);
			box.prefHeightProperty().set(boxHeight);

			yAxis.prefHeightProperty().bind(box.prefHeightProperty());
			yAxis.layoutXProperty().set(-yAxis.prefWidthProperty().getValue() - 5);

			yRightAxis.prefHeightProperty().bind(box.prefHeightProperty());
			yRightAxis.layoutXProperty().set(box.prefWidthProperty().get() + 5);
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

}
