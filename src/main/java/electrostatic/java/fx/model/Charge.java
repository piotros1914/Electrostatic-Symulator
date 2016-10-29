package electrostatic.java.fx.model;

import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class Charge extends Circle {

	private Force force;
	private RadialGradient radialGradient;

	private Color stroke;
	private Color selectedStroke;

	private double opacity;
	private double selectedOpacity;

	private ObjectProperty<Color> color;
	private DoubleProperty chargeProperty;
	private BooleanProperty selectedProperty;
	private BooleanProperty disabledProperty;
	private String name;
	private int id;
	private double mass;
	private double safePositionX;
	private double safePositionY;
	private static int count = 1;

	public Charge(double positionX, double positionY, double charge) {
		
		
		super(positionX, positionY, 30);
		name = "£adunek " + String.valueOf(count);
		id = count;

		this.chargeProperty = new SimpleDoubleProperty(charge);
		this.selectedProperty = new SimpleBooleanProperty(true);
		this.disabledProperty = new SimpleBooleanProperty(!selectedProperty.get());
		this.color = new SimpleObjectProperty<Color>(doColor());

		this.force = new Force();
		this.mass = 1;

		this.radialGradient = doRadialGradient(0.5, 0.5, color.get());
		
		this.stroke = Color.BLACK;
		this.selectedStroke = Color.WHITE;
		this.setStrokeWidth(1);

		this.opacity = 1;
		this.selectedOpacity = 0.7;

		setSelected(true);
		count++;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ObjectProperty<Color> colorProperty() {

		this.radialGradient = doRadialGradient(0.5, 0.5, color.getValue());
		setSelected(isSelected());
		return color;

	}

	private Color doColor() {
		Random rand = new Random(System.currentTimeMillis());
		int red = rand.nextInt(200);
		int green = rand.nextInt(200);
		int blue = rand.nextInt(200);
		return Color.rgb(red, green, blue, 0.9);
	}

	private RadialGradient doRadialGradient(double x, double y, Color color) {

		RadialGradient radialGradient = new RadialGradient(0, // focusAngle
				0, // focusDistanse
				x, // centerX
				y, // centerY
				.5, // radius
				true, // proportional
				CycleMethod.NO_CYCLE, // cycleMethod
				new Stop(0.0, Color.WHITE), new Stop(1.0, color)); // stop
		return radialGradient;
	}

	public boolean isSelected() {
		return this.selectedProperty.get();
	}

	public void setSelected(boolean selected) {
		if (!selected) {
		
			this.setStroke(stroke);
			this.setOpacity(opacity);
		}

		else {
			this.setStroke(selectedStroke);
			this.setOpacity(selectedOpacity);

		}
		this.setFill(radialGradient);
		this.selectedProperty.set(selected);
		this.disabledProperty.set(!selected);
}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public double getCharge() {
		return (double) chargeProperty.getValue();
	}

	public void setCharge(double charge) {
		this.chargeProperty.setValue(charge);

	}

	public DoubleProperty chargeProperty() {
		return chargeProperty;
	}

	public void setChargeProperty(DoubleProperty chargeProperty) {
		this.chargeProperty = chargeProperty;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public BooleanProperty getSelectedProperty() {
		return selectedProperty;
	}

	public void setSelectedProperty(BooleanProperty selectedProperty) {
		this.selectedProperty = selectedProperty;
		this.disabledProperty.set(!selectedProperty.get());
	}

	public BooleanProperty getDisabledProperty() {
		return disabledProperty;
	}

	public void setDisabledProperty(BooleanProperty disabledProperty) {
		this.disabledProperty = disabledProperty;
		this.selectedProperty.set(!disabledProperty.get());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Force getForce() {
		return force;
	}

	public void setForce(Force force) {
		this.force = force;
	}

	public double getMass() {
		return mass;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void safePosition() {

		this.safePositionX = this.centerXProperty().get();
		this.safePositionY = this.centerYProperty().get();
	}

	public void writePosition() {

		this.centerXProperty().set(safePositionX);
		this.centerYProperty().set(safePositionY);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getIdNumber() {
		return id;
	}
	
	
	
}
