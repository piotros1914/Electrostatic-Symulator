package electrostatic.java.fx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import electrostatic.java.fx.model.ChargeModel;
import electrostatic.java.fx.model.Converter;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import electrostatic.java.fx.model.Box;

public class AppController {

	@FXML
	private Pane appPane;
	
	@FXML
	private ScrollPane appScrollPane;

	private MainController mainController;

	private int boxWidth = 300;
	private int boxHeight = 300;
	private int distanceFromBox = 300;


	private ContextMenu contextMenu;
	private MenuItem addMenuItem;
	private MenuItem delateMenuItem;

	private double cursorPositionX;
	private double cursorPositionY;
	
	private ChargeModel chargeModel;
	private Box box;
	
	
	public void initModel(ChargeModel chargeModel, Box box){
		this.chargeModel = chargeModel;
		chargeModel.setAppStackPane(box);
		
		this.box = box;
		box.setPrefHeight(boxHeight);
		box.setPrefWidth(boxWidth);
		
		appPane.getChildren().add(box);
		
		setPositionCentenerListeners();
		
		
		// przechwytywanie uruchomienia menu kontekstowego
				box.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
					cursorPositionX = mouseEvent.getX();
					cursorPositionY = mouseEvent.getY();
					contextMenu.getItems().clear();
					contextMenu.getItems().add(addMenuItem);
					if (mouseEvent.getButton() == MouseButton.SECONDARY) {
						if (chargeModel.getCurrentCharge() != null)
							if (chargeModel.getCurrentCharge().contains(mouseEvent.getX(), mouseEvent.getY())) {
								contextMenu.getItems().clear();
								contextMenu.getItems().add(delateMenuItem);
							}
						contextMenu.show(appPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
					} else
						contextMenu.hide();
				});
	}

	@FXML
	void initialize() {
		appScrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);		
		
		appPane.minWidthProperty().bind(appScrollPane.widthProperty());
		appPane.minHeightProperty().bind(appScrollPane.heightProperty());
		appPane.setPrefWidth(appPane.getMinWidth());
		appPane.setPrefHeight(appPane.getMinHeight());
			

		// tworzenie menu kontekstowego
		contextMenu = new ContextMenu();
		addMenuItem = new MenuItem("Nowy ³adunek");
		addMenuItem.setOnAction(e -> chargeModel.addCharge(cursorPositionX, cursorPositionY));
		delateMenuItem = new MenuItem("Usuñ");
		delateMenuItem.setOnAction(e -> chargeModel.deleteCurrentCharge());

		// Przechwytywanie eventu przed wykonaniem akcji. Event uwa¿any jest za
		// wykonany jesli prawym klawiszem klikniete zosta³o menu kontekstowe.
		contextMenu.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
			if (mouseEvent.getButton() == MouseButton.SECONDARY)
				mouseEvent.consume();
		});
		// wy³¹czenie zaznaczonego ³adunku
		appPane.setOnMouseClicked(mouseEvent -> {
			if (chargeModel.getCurrentCharge() != null)
				if (mouseEvent.getX() < box.getLayoutX() || mouseEvent.getX() > (box.getLayoutX() + box.getWidth())
						|| mouseEvent.getY() < box.getLayoutY()
						|| mouseEvent.getY() > (box.getLayoutY() + box.getHeight())) {

					chargeModel.deselectCurrentCharge();
				}
		});

	}

	public void showGrid(boolean show) {
		box.getStyleClass().clear();
		if (show)
			box.getStyleClass().add("box_grid");
		else
			box.getStyleClass().add("box");
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setPositionCentenerListeners() {
		appPane.widthProperty().addListener((obs, oldValue, newValue) -> {			
			int x = getPositionAfterCentering(newValue.doubleValue(), box.getWidth());
			box.layoutXProperty().set(x);
		});

		appPane.heightProperty().addListener((obs, oldValue, newValue) -> {
			int y = getPositionAfterCentering(newValue.doubleValue(), box.getHeight());
			box.layoutYProperty().set(y);
		});

		box.widthProperty().addListener((obs, oldValue, newValue) -> {		
			appPane.setPrefWidth(newValue.doubleValue() + distanceFromBox);
			int x = getPositionAfterCentering(appPane.getWidth(), newValue.doubleValue());
			box.layoutXProperty().set(x);		
		});
		
		box.heightProperty().addListener((obs, oldValue, newValue) -> {
			appPane.setPrefHeight(newValue.doubleValue() + distanceFromBox);
			int y = getPositionAfterCentering(appPane.getHeight(), newValue.doubleValue());
			box.layoutYProperty().set(y);		
		});
	}

	private int getPositionAfterCentering(double back, double top ){		
		return (int) (back/2 - top/2);
	}
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public ScrollPane getAppScrollPane() {
		return appScrollPane;
	}			
}
