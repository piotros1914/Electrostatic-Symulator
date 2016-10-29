package electrostatic.java.fx.model;

import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;




public class Box extends Pane {
	
	private NumberAxis xTopAxis;
	private NumberAxis xDownAxis;
	private NumberAxis yLeftAxis;
	private NumberAxis yRightAxis;
	private int tickUnit = 100;
	private int axisDistance = 5;
	
	
	public Box(){
		this(0, 0);
	}
	public Box(int boxWidth, int boxHeight){
			
		xTopAxis = new NumberAxis(0, boxWidth, tickUnit);
		xDownAxis = new NumberAxis(0, boxWidth, tickUnit);
		yLeftAxis = new NumberAxis(0, boxHeight, tickUnit);
		yRightAxis = new NumberAxis(0, boxHeight, tickUnit);

		xTopAxis.getStyleClass().add("axe");
		xTopAxis.setSide(Side.TOP);
		xTopAxis.setMinorTickVisible(false);

		xDownAxis.getStyleClass().add("axe");
		xDownAxis.setSide(Side.BOTTOM);
		xDownAxis.setMinorTickVisible(false);

		yLeftAxis.getStyleClass().add("axe");
		yLeftAxis.setSide(Side.LEFT);
		yLeftAxis.setMinorTickVisible(false);

		yRightAxis.getStyleClass().add("axe");
		yRightAxis.setSide(Side.RIGHT);
		yRightAxis.setMinorTickVisible(false);

		xTopAxis.prefHeightProperty().set(30);
		xDownAxis.prefHeightProperty().set(30);
		yLeftAxis.prefWidthProperty().set(30);
		yRightAxis.prefWidthProperty().set(30);

//		box = new Pane();
		this.prefWidthProperty().set(boxWidth);
		this.prefHeightProperty().set(boxHeight);
		this.getStyleClass().add("box_grid");
		this.getChildren().addAll(xTopAxis, xDownAxis, yLeftAxis, yRightAxis);
		
		xTopAxis.layoutYProperty().set(-xTopAxis.getPrefHeight() - axisDistance);
		xDownAxis.layoutYProperty().set(prefHeightProperty().get() + axisDistance);
		
		
		yLeftAxis.layoutXProperty().set(-yLeftAxis.getPrefWidth() - axisDistance);
		yRightAxis.layoutXProperty().set(getPrefWidth() + axisDistance);
		
		
		xTopAxis.prefWidthProperty().bind(prefWidthProperty());
		xDownAxis.prefWidthProperty().bind(prefWidthProperty());
		yLeftAxis.prefHeightProperty().bind(prefHeightProperty());
		yRightAxis.prefHeightProperty().bind(prefHeightProperty());
		
		
		
		
		xTopAxis.upperBoundProperty().bind(widthProperty());
		xDownAxis.upperBoundProperty().bind(widthProperty());

		yRightAxis.layoutXProperty().bind(widthProperty().add(axisDistance));
		
		
		
		
		// ustawienie skali na osi
		yLeftAxis.upperBoundProperty().bind(this.heightProperty());
		yRightAxis.upperBoundProperty().bind(this.heightProperty());
		
		xDownAxis.layoutYProperty().bind(heightProperty().add(axisDistance));
	
	}
	
	
	
}
