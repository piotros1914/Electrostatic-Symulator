package electrostatic.java.fx.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Force {

//	private double force;
//	private double forceX;
//	private double forceY;
	private DoubleProperty forceProperty;
	private DoubleProperty forceXProperty;
	private DoubleProperty forceYProperty;
	
	
	public Force(){
		
		this.forceProperty = new SimpleDoubleProperty(0);
		this.forceXProperty  = new SimpleDoubleProperty(0);
		this.forceYProperty = new SimpleDoubleProperty(0);
	
	}

	public void setForce(double forceX, double forceY) {
		
		this.forceXProperty.set(forceX); 
		this.forceYProperty.set(forceY);		
		this.forceProperty.set(Math.sqrt(forceX * forceX + forceY * forceY));
	}

	
	public void setForceY(double forceY) {
		setForce(this.forceXProperty.get(), forceY);
	}


	public void setForceX(double forceX) {
		setForce(forceX, this.forceYProperty.get());
	}

	public double getForceY() {
		return forceYProperty.get();
	}

	public double getForce() {
		return forceProperty.get();
	}

	public double getForceX() {
		return forceXProperty.get();
	}
	
	@Override
	public String toString() {
	
		return String.valueOf(forceProperty.get());
	}

	public DoubleProperty getForceProperty() {
		return forceProperty;
	}

	public DoubleProperty getForceXProperty() {
		return forceXProperty;
	}	

	public DoubleProperty getForceYProperty() {
		return forceYProperty;
	}
	
}



