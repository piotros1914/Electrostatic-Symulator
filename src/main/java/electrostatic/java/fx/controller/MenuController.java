package electrostatic.java.fx.controller;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuController {

	private MainController mainController;
	private Stage dialogStage;

	@FXML
	private CheckMenuItem showGridMenuItem;

	@FXML
	public void initialize() {
		dialogStage = new Stage();

		showGridMenuItem.selectedProperty().addListener((obs, oldValue, newValue) -> {
			mainController.getAppController().showGrid(newValue);
		});
	}

	@FXML
	void openFileAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Otwórz Plik");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki TSV", "*.tsv"));

		File file = fileChooser.showOpenDialog(dialogStage);

		if (file != null) {
//			mainController.getAppController().loadData(file);

		}
	}

	@FXML
	void closeAction() {
		Platform.exit();
	}

	@FXML
	void aboutAction() {

		AnchorPane aboutPane = new AnchorPane();
		FXMLLoader aboutLoader = new FXMLLoader(this.getClass().getResource("/fxml/AboutView.fxml"));

		try {
			aboutPane = aboutLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene aboutScene = new Scene(aboutPane);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(mainController.getMainBorderPane().getScene().getWindow());
		dialogStage.setScene(aboutScene);
		dialogStage.setTitle("O programie");
		dialogStage.getIcons().add(new Image("/icons/About-icon.png"));
		dialogStage.show();

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}
