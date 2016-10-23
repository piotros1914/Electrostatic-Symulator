package electrostatic.java.fx.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class LeftMenuController {

	@FXML
	private ListView<String> chargesListView;

	@FXML
	private Label forceLabel;

	@FXML
	private Label forceXLabel;

	@FXML
	private Label forceYLabel;

	@FXML
	private AnchorPane leftMenuAnchorPane;

	private MainController mainController;

	@FXML
	void initialize() {

		chargesListView.setCellFactory(lv -> {

			ListCell<String> cell = new ListCell<>();

			ContextMenu contextMenu = new ContextMenu();

			MenuItem editItem = new MenuItem();
			MenuItem deleteItem = new MenuItem();

			editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
			editItem.setOnAction(event -> {

				String item = cell.getItem();
				// code to edit item...
			});

			deleteItem.textProperty().set("Usuñ");
			deleteItem.setOnAction(event -> {
				chargesListView.getItems().remove(cell.getItem());
				mainController.getAppController().getChargeControll().deleteCharge();
			});

			contextMenu.getItems().addAll(editItem, deleteItem);

			cell.textProperty().bind(cell.itemProperty());
			cell.emptyProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					cell.setContextMenu(null);
				else
					cell.setContextMenu(contextMenu);
			});
			return cell;
		});

		chargesListView.setEditable(true);
		chargesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (newValue != null) {
							mainController.getAppController().getChargeControll().selectCharge(newValue);

						}

					}
				});

			}

		});

	}

	public void clear() {
		chargesListView.getSelectionModel().clearSelection();
		forceLabel.textProperty().set("0");
		forceXLabel.textProperty().set("0");
		forceYLabel.textProperty().set("0");
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public ListView<String> getChargesListView() {
		return chargesListView;
	}

	public void setChargesListView(ListView<String> chargesListView) {
		this.chargesListView = chargesListView;
	}

	public Label getForceLabel() {
		return forceLabel;
	}

	public void setForceLabel(Label forceLabel) {
		this.forceLabel = forceLabel;
	}

	public Label getForceXLabel() {
		return forceXLabel;
	}

	public void setForceXLabel(Label forceXLabel) {
		this.forceXLabel = forceXLabel;
	}

	public Label getForceYLabel() {
		return forceYLabel;
	}

	public void setForceYLabel(Label forceYLabel) {
		this.forceYLabel = forceYLabel;
	}

}
