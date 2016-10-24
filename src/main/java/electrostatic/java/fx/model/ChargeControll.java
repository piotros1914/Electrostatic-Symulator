package electrostatic.java.fx.model;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import electrostatic.java.fx.controller.MainController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;

public class ChargeControll implements Cloneable {

	private ObservableList<Charge> chargeObservableList;
	private List<Charge> chargeList;

	private Pane box;
	private MainController mainController;

	double deltaX, deltaY;
	int count;

	Charge selectedCharge;
	

	public ChargeControll(Pane box) {
		this.box = box;
		this.chargeList = new ArrayList<Charge>();

		this.chargeObservableList = FXCollections.observableArrayList(chargeList);

		this.count = 1;
		// setChargeObservableListListener();

		// addCharge(100, 100, 30, 1);
		// addCharge(200, 100, 30, 1);
		// addCharge(100, 200, 30, 1);
		// addCharge(200, 200, 30, 1);
		
	

	}

	public void setChargeObservableListListener() {
		this.chargeObservableList.addListener(new ListChangeListener<Charge>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Charge> c) {
				while (c.next()) {
					box.getChildren().addAll(c.getAddedSubList());

					for (Charge charge : c.getAddedSubList()) {
						mainController.getLeftMenuController().getChargesListView().getItems().add(charge.getName());
						mainController.getLeftMenuController().getChargesListView().getSelectionModel()
								.select(charge.getName());
					}

					for (Charge charge : c.getRemoved()) {
						mainController.getLeftMenuController().getChargesListView().getItems().remove(charge.getName());
					}

				}
				updateForce();
			}
		});
	}

	public void addCharge() {

		addCharge(box.getWidth() / 2, box.getHeight() / 2, 30, 1);
	}

	public void addCharge(double positionX, double positionY) {

		addCharge(positionX, positionY, 30, 1);
		System.out.println("positionX: " + positionX + "	positionY: " + positionY);
	}

	public void addCharge(double positionX, double positionY, double radius, double chargeValue) {

		Charge charge = new Charge(positionX, positionY, radius, chargeValue);
		charge.setId(String.valueOf(count));
		charge.setName("£adunek " + String.valueOf(count));
		selectCharge(charge);

		charge.setOnMouseClicked((event) -> {			
				selectCharge(charge);				
				updateForce();
		});

		charge.setOnMousePressed((event) ->{			
			if (charge.isSelected()) {
				deltaX = charge.getCenterX() - event.getScreenX();
				deltaY = charge.getCenterY() - event.getScreenY();
			}			
		});

		charge.setOnMouseDragged((event) ->{	
			if (charge.isSelected()) {
				charge.setCenterX(event.getScreenX() + deltaX);
				charge.setCenterY(event.getScreenY() + deltaY);
				updateForce();
			}
		});
		
		this.count++;
		chargeObservableList.add(charge);
	}

	public void deleteCharge() {
		this.chargeObservableList.remove(selectedCharge);
		this.box.getChildren().remove(selectedCharge);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void unbindAllCharge() {
		if (!chargeObservableList.isEmpty())
			chargeObservableList.forEach(charge -> {
				Bindings.unbindBidirectional(
						mainController.getRightMenuController().getPositionXTextField().textProperty(),
						charge.centerXProperty());
			
				Bindings.unbindBidirectional(
						mainController.getRightMenuController().getPositionYTextField().textProperty(),
						charge.centerYProperty());
				Bindings.unbindBidirectional(
						mainController.getRightMenuController().getRadiusTextField().textProperty(),
						charge.radiusProperty());
				Bindings.unbindBidirectional(
						mainController.getRightMenuController().getChargeTextField().textProperty(),
						charge.chargeProperty());
				Bindings.unbindBidirectional(mainController.getLeftMenuController().getForceLabel().textProperty(),
						charge.getForce().getForceProperty());
				Bindings.unbindBidirectional(mainController.getLeftMenuController().getForceXLabel().textProperty(),
						charge.getForce().getForceXProperty());
				Bindings.unbindBidirectional(mainController.getLeftMenuController().getForceYLabel().textProperty(),
						charge.getForce().getForceYProperty());
				Bindings.unbindBidirectional(mainController.getRightMenuController().getColorPicker().valueProperty(),
						charge.colorProperty());

				mainController.getRightMenuController().getChargeTitledPane().disableProperty().unbind();

			});
	}

	private void bindCharge(Charge charge) {
		Bindings.bindBidirectional(mainController.getRightMenuController().getPositionXTextField().textProperty(),
				charge.centerXProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getRightMenuController().getPositionYTextField().textProperty(),
				charge.centerYProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getRightMenuController().getRadiusTextField().textProperty(),
				charge.radiusProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getRightMenuController().getChargeTextField().textProperty(),
				charge.chargeProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getLeftMenuController().getForceLabel().textProperty(),
				charge.getForce().getForceProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getLeftMenuController().getForceXLabel().textProperty(),
				charge.getForce().getForceXProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getLeftMenuController().getForceYLabel().textProperty(),
				charge.getForce().getForceYProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(mainController.getRightMenuController().getColorPicker().valueProperty(),
				charge.colorProperty());

		mainController.getRightMenuController().getChargeTitledPane().disableProperty()
				.bind(charge.getDisabledProperty());

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void selectCharge(Charge charge) {
		deselectAllCharges();
		setSelectedCharge(charge);
		
		bindCharge(charge);
		mainController.getLeftMenuController().getChargesListView().getSelectionModel().select(charge.getName());
	}

	public void selectCharge(String chargeName) {
		for (Charge charge : chargeObservableList) {
			if (charge.getName() == chargeName) {
				selectCharge(charge);
				return;
			}
		}
	}

	public void deselectAllCharges() {
		if (!chargeObservableList.isEmpty())
			chargeObservableList.forEach(charge -> {
				charge.setSelected(false);
			});
		this.selectedCharge = null;
		unbindAllCharge();
	}

	public void setSelectedCharge(Charge charge) {
		charge.setSelected(true);
		this.selectedCharge = charge;
	}

	public Charge getSelectedCharge() {
		return selectedCharge;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateForce() {
		double k = 9e9;

		for (Charge charge1 : chargeObservableList) {
			Force force = new Force();

			for (Charge charge2 : chargeObservableList) {
				double deltaX = 0;
				double deltaY = 0;
				double componentForceX = 0;
				double componentForceY = 0;

				if (!charge1.equals(charge2)) {

					deltaX = charge1.getCenterX() - charge2.getCenterX();
					deltaY = charge1.getCenterY() - charge2.getCenterY();

					double r = (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));

					if (r > (charge1.getRadius() + charge2.getRadius())) {
						double forceValue = k * charge1.getCharge() * charge2.getCharge() / (r * r);
						double arctgAlfa = Math.atan2(deltaY, deltaX);

						componentForceX = forceValue * Math.cos(arctgAlfa);
						componentForceY = forceValue * Math.sin(arctgAlfa);
					}

					force.setForce(force.getForceX() + componentForceX, force.getForceY() + componentForceY);

				}
				charge1.getForce().setForceX(force.getForceX());
				charge1.getForce().setForceY(force.getForceY());

			}
		}
	}

	public void updatePosition(double deltaTime) {
		double dt = (double) deltaTime;
		for (Charge charge : chargeObservableList) {

			double deltaX = 0.5 * (charge.getForce().getForceX() / charge.getMass()) * dt * dt;
			double deltaY = 0.5 * (charge.getForce().getForceY() / charge.getMass()) * dt * dt;

			charge.setCenterX(charge.getCenterX() + deltaX);
			charge.setCenterY(charge.getCenterY() + deltaY);
		}
	}

	public void checkColisions() {

		chargeObservableList.forEach(charge -> {
			// dla lewej
			if (0 > (charge.getCenterX() - charge.getRadius())) 
				charge.setCenterX(charge.getRadius());

			// dla prawej
			if ((box.getWidth()) < (charge.getCenterX() + charge.getRadius()))
				charge.setCenterX(box.getWidth() - charge.getRadius());

			// dla góry
			if (0 > (charge.getCenterY() - charge.getRadius()))
				charge.setCenterY(charge.getRadius());		

			// dla do³u
			if ((box.getHeight()) < (charge.getCenterY() + charge.getRadius()))
				charge.setCenterY(box.getHeight() - charge.getRadius());
		});
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void saveChargesPosition() {
		for (Charge charge : chargeObservableList) {
			charge.safePosition();
		}
	}

	public void writeChargesPosition() {
		for (Charge charge : chargeObservableList) {
			charge.writePosition();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ObservableList<Charge> getChargeObservableList() {
		return chargeObservableList;
	}

	public void setChargeObservableList(ObservableList<Charge> chargeObservableList) {
		this.chargeObservableList = chargeObservableList;
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	

	public Pane getAppStackPane() {
		return box;
	}

	public void setAppStackPane(Pane appStackPane) {
		this.box = appStackPane;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
