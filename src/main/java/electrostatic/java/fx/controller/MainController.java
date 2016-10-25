package electrostatic.java.fx.controller;

import java.io.File;
import java.io.IOException;

import electrostatic.java.fx.model.SymulatorTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainController {
	
		
	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private VBox topMenuVBox;
	
	@FXML
	private CheckMenuItem showGridMenuItem;
	
	@FXML
	private Button playBtn;
	
	@FXML
	private Button pauseBtn;
	
	@FXML
	private Button stopBtn;
	
	@FXML
	private Label timeAnimationLabel;
	
	@FXML
	private VBox downVBox;
	
	@FXML
	private Label cursorPositionXLabel;
	
	@FXML
	private Label cursorPositionYLabel;
	
	
	private Pane appPane;
	private ScrollPane scrollPane;
	private AnchorPane rightMenuAnchorPane;
	private AnchorPane leftMenuAnchorPane;	
	private AppController appController;
	private RightMenuController rightMenuController;
	private LeftMenuController leftMenuController;
	
	private  SymulatorTimer symulatorTimer;
	
	private Stage dialogStage;
	
		
	@FXML
	void initialize(){
		
		dialogStage = new Stage();
		
		FXMLLoader appLoader = new FXMLLoader(this.getClass().getResource("/fxml/AppView.fxml"));
		
		try {
			
			appPane = appLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
		appController = appLoader.getController();
		appController.setMainController(this);
		
		scrollPane = new ScrollPane(appPane);
		scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.getStyleClass().add("scroll");
		mainBorderPane.setCenter( scrollPane);	
			
		
		FXMLLoader rightMenuLoader = new FXMLLoader(this.getClass().getResource("/fxml/RightMenuView.fxml"));
		
		try {
			rightMenuAnchorPane = rightMenuLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rightMenuController = rightMenuLoader.getController();
		rightMenuController.setMainController(this);
		mainBorderPane.setRight(rightMenuAnchorPane);
		
			
		FXMLLoader leftMenuLoader = new FXMLLoader(this.getClass().getResource("/fxml/LeftMenuView.fxml"));
		
		try {
			leftMenuAnchorPane = leftMenuLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		leftMenuController = leftMenuLoader.getController();
		leftMenuController.setMainController(this);
		mainBorderPane.setLeft(leftMenuAnchorPane);
		
		
		// Wydarzenia dla menu - widok. Listenery
		showGridMenuItem.selectedProperty().addListener((obs, oldValue, newValue) -> {			
			appController.showGrid(newValue);
		});
		
		
		// Symulator		
		symulatorTimer = new SymulatorTimer(timeAnimationLabel);
		symulatorTimer.setMainController(this);
	
		// Parametry szerokoœci i wysokoœci

		appPane.minWidthProperty().bind(scrollPane.prefWidthProperty());
		appPane.minHeightProperty().bind(scrollPane.prefHeightProperty());
		appPane.setPrefWidth(appPane.getMinWidth());
		appPane.setPrefHeight(appPane.getMinHeight());	
		leftMenuAnchorPane.setPrefHeight(mainBorderPane.getPrefHeight() - topMenuVBox.getPrefHeight() - downVBox.getPrefHeight());
		rightMenuAnchorPane.setPrefHeight(mainBorderPane.getPrefHeight() - topMenuVBox.getPrefHeight()  - downVBox.getPrefHeight());
		sizeListener();	
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@FXML
	void openFileAction(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Otwórz Plik");
		 fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki TSV", "*.tsv"));
	      
	        File plik = fileChooser.showOpenDialog(dialogStage);
	     
	        if (plik != null) {
	          
	            System.out.println("Plik: "+ plik.getAbsolutePath());
	 
	        }
	}
	
	@FXML
    void closeAction() {
		Platform.exit();
    }
	
	@FXML
	void aboutAction(){
		    
      
        AnchorPane aboutPane = new AnchorPane();
        FXMLLoader aboutLoader = new FXMLLoader(this.getClass().getResource("/fxml/AboutView.fxml"));
		
		try {
			aboutPane = aboutLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene aboutScene = new Scene(aboutPane);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainBorderPane.getScene().getWindow());
		dialogStage.setScene(aboutScene);
		dialogStage.setTitle("O programie");
		dialogStage.getIcons().add(new Image("/icons/About-icon.png"));
		dialogStage.show();
	
	}
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	
	@FXML
	public void playAction(){		
		if(!symulatorTimer.wasPause()){
			appController.getChargeControll().saveChargesPosition();		
		}				
		symulatorTimer.setPause(false);
        symulatorTimer.start();      
	}
	
	@FXML
	public void pauseAction(){
		symulatorTimer.setPause(true);
	}
	
	@FXML
	public void stopAction(){
		symulatorTimer.stop();
		appController.getChargeControll().writeChargesPosition();
		symulatorTimer.restart();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void sizeListener(){
		
		mainBorderPane.widthProperty().addListener((obs, oldValue, newValue) ->{			
			scrollPane.setPrefWidth(newValue.doubleValue() - rightMenuAnchorPane.getPrefWidth() - leftMenuAnchorPane.getPrefWidth() );											
		});
		
		mainBorderPane.heightProperty().addListener((obs, oldValue, newValue) -> {			
			scrollPane.setPrefHeight(newValue.doubleValue() - topMenuVBox.getPrefHeight());				
			rightMenuAnchorPane.setPrefHeight(newValue.doubleValue() - topMenuVBox.getPrefHeight()  - downVBox.getPrefHeight());
			leftMenuAnchorPane.setPrefHeight(newValue.doubleValue() - topMenuVBox.getPrefHeight() - downVBox.getPrefHeight());			
		});
	}
		
	public AppController getAppController() {
		return appController;
	}

	public void setAppController(AppController appController) {
		this.appController = appController;
	}

	public RightMenuController getRightMenuController() {
		return rightMenuController;
	}

	public void setRightMenuController(RightMenuController rightMenuController) {
		this.rightMenuController = rightMenuController;	
	}

	public LeftMenuController getLeftMenuController() {
		return leftMenuController;
	}

	public void setLeftMenuController(LeftMenuController leftMenuController) {
		this.leftMenuController = leftMenuController;
	}

	public AnchorPane getLeftMenuAnchorPane() {
		return leftMenuAnchorPane;
	}

	public void setLeftMenuAnchorPane(AnchorPane leftMenuAnchorPane) {
		this.leftMenuAnchorPane = leftMenuAnchorPane;
	}

	public Label getCursorPositionXLabel() {
		return cursorPositionXLabel;
	}

	public void setCursorPositionXLabel(Label cursorPositionXLabel) {
		this.cursorPositionXLabel = cursorPositionXLabel;
	}

	public Label getCursorPositionYLabel() {
		return cursorPositionYLabel;
	}

	public void setCursorPositionYLabel(Label cursorPositionYLabel) {
		this.cursorPositionYLabel = cursorPositionYLabel;
	}

}
