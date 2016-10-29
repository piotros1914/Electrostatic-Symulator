package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.Charge;
import electrostatic.java.fx.model.ChargeModel;
import electrostatic.java.fx.model.Converter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class LeftController {

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
	
	private ChargeModel chargeModel;

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
				chargeModel.deleteCurrentCharge();
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

//		chargesListView.setEditable(true);
//		chargesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//				Platform.runLater(new Runnable() {
//					@Override
//					public void run() {
//						if (newValue != null) {
//							mainController.getAppController().getChargeControll().selectCharge(newValue);
//
//						}
//
//					}
//				});
//				System.out.println("Przyczyna?");
//			}
//
//		});

	}

	
	public void initModel(ChargeModel chargeModel){
		this.chargeModel = chargeModel;		
		ObservableList<Charge> chargeObservableList = chargeModel.getChargeObservableList();
		
		chargeObservableList.addListener(new ListChangeListener<Charge>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Charge> c) {
				while (c.next()) {
					
					for (Charge charge : c.getAddedSubList()) {
						chargesListView.getItems().add(charge.getName());
						chargesListView.getSelectionModel().select(charge.getName());
					}
					for (Charge charge : c.getRemoved()) {
						chargesListView.getItems().remove(charge.getName());
					}
				}
				
			}
		});
		
		 chargeModel.currentChargeProperty().addListener((observable, oldCharge, newCharge) -> {
		 if (oldCharge != null) {
            Bindings.unbindBidirectional(forceXLabel.textProperty(), newCharge.getForce().getForceXProperty());
            Bindings.unbindBidirectional(forceYLabel.textProperty(), newCharge.getForce().getForceYProperty());
            Bindings.unbindBidirectional(forceLabel.textProperty(), newCharge.getForce().getForceProperty());
        }
        if (newCharge == null) {
           forceXLabel.setText("");
           forceYLabel.setText("");
           forceLabel.setText("");
    
        } 
        else {
            Bindings.bindBidirectional(forceXLabel.textProperty(), newCharge.getForce().getForceXProperty(), Converter.normalConverter);
            Bindings.bindBidirectional(forceYLabel.textProperty(), newCharge.getForce().getForceYProperty(), Converter.normalConverter);
            Bindings.bindBidirectional(forceLabel.textProperty(), newCharge.getForce().getForceProperty(), Converter.normalConverter);
  		  		      		
        }	
		 });
		
	
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

	public AnchorPane getLeftMenuAnchorPane() {
		return leftMenuAnchorPane;
	}

	
}
