package electrostatic.java.fx.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ChargeModel{

	private ObservableList<Charge> chargeObservableList;

	private List<Charge> chargeList;

	private Pane box;
//	private MainController mainController;

	double distanceX, distanceY;
	
    private  ObjectProperty<Charge> currentCharge = new SimpleObjectProperty<Charge>();
	 
	
	public ObjectProperty<Charge> currentChargeProperty() {
        return currentCharge ;
    }
	
	public  Charge getCurrentCharge() {
        return currentCharge.get();
    }

  

    
	public ChargeModel() {
		
		chargeList = new ArrayList<Charge>();
		chargeObservableList = FXCollections.observableArrayList(chargeList);
			
//		 setChargeObservableListListener();

		// addCharge(100, 100, 30, 1);
		// addCharge(200, 100, 30, 1);
		// addCharge(100, 200, 30, 1);
		// addCharge(200, 200, 30, 1);
	}

//	public void setChargeObservableListListener() {
//		this.chargeObservableList.addListener(new ListChangeListener<Charge>() {
//			@Override
//			public void onChanged(ListChangeListener.Change<? extends Charge> c) {
//				while (c.next()) {
//					box.getChildren().addAll(c.getAddedSubList());
//		
//
////					for (Charge charge : c.getRemoved()) {
////						mainController.getLeftController().getChargesListView().getItems().remove(charge.getName());
////					}
//
//				}
//				updateForce();
//			}
//		});
//	}

//	public void addCharge() {
//		addCharge(box.getWidth() / 2, box.getHeight() / 2);
//	}

	public void addCharge(double positionX, double positionY) {
		addCharge(positionX, positionY,  1);		
	}

	public void addCharge(double positionX, double positionY,  double chargeValue) {		
		Charge charge = new Charge(positionX, positionY, chargeValue);	
		box.getChildren().add(charge);
		setCurrentCharge(charge);
		setChargeEvents(charge);		
		chargeObservableList.add(charge);	
	}

	private void setChargeEvents(Charge charge) {
		charge.setOnMouseClicked(event -> {
			setCurrentCharge(charge);
			updateForce();
		});
		charge.setOnMousePressed(event -> {
			if (charge.isSelected())				
				calculateOffset(charge, event);
		});
		charge.setOnMouseDragged(event -> {
			if (charge.isSelected()) {
				updatePosition(charge, event);
				updateForce();
			}
		});
	}

	private void updatePosition(Charge charge, MouseEvent event) {
		charge.setCenterX(event.getScreenX() + distanceX);
		charge.setCenterY(event.getScreenY() + distanceY);		
	}

	private void calculateOffset(Charge charge, MouseEvent event) {
		distanceX = charge.getCenterX() - event.getScreenX();
		distanceY = charge.getCenterY() - event.getScreenY();
	}

	public void deleteCurrentCharge() {
		chargeObservableList.remove(currentChargeProperty().get());
		box.getChildren().remove(currentChargeProperty().get());
	}


	  public  void setCurrentCharge(Charge charge) {
		  	deselectCurrentCharge();
			charge.setSelected(true);			
			System.out.print(charge.toString());

	     
	        currentCharge = new SimpleObjectProperty<Charge>();
	        currentCharge.set(charge);
		
	   }
	
	
	public void deselectCurrentCharge() {
		if (currentCharge.get() != null)
			currentCharge.get().setSelected(false);
				
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

//	public void saveChargesPosition() {
//		for (Charge charge : chargeObservableList) {
//			charge.safePosition();
//		}
//	}
//
//	public void writeChargesPosition() {
//		for (Charge charge : chargeObservableList) {
//			charge.writePosition();
//		}
//	}

	public ObservableList<Charge> getChargeObservableList() {
		return chargeObservableList;
	}

	public void setAppStackPane(Pane appStackPane) {
		this.box = appStackPane;
	}
	public void saveCharges(){
		
	}
	
	public void writeCharges(){
		
	}
}
