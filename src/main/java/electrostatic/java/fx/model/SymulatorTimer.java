package electrostatic.java.fx.model;

import electrostatic.java.fx.controller.MainController;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class SymulatorTimer extends AnimationTimer {

	
	private MainController mainController;
	private Label timeAnimationLabel;
	
	private double timeStep;
	private boolean wasPause;
	private boolean isPause;
	
	private double clock = 0;
	private long old = 0;

	public SymulatorTimer (Label timeAnimationLabel){

		this.timeAnimationLabel = timeAnimationLabel;	
		timeStep = 0.001;
		wasPause = false;
		isPause  = false;
				
	}
	
    @Override
    public void handle(long now) {
    	 	
    	if(old == 0)
    		old = now;

    	if(!isPause ){
    		clock += (now - old);
    		timeAnimationLabel.setText(String.valueOf( Converter.round(clock/1000000000.0 , 2)) + " s");
    	  
        	mainController.getAppController().getChargeControll().updateForce();
    		mainController.getAppController().getChargeControll().updatePosition(timeStep);
    		mainController.getAppController().getChargeControll().checkColisions();
    	
    	}
		old = now;
    }

    public boolean wasPause(){
    	return this.wasPause;
    }
    
    public void setPause(boolean pause){
    	this.isPause = pause;
    	this.wasPause = true;
    }
    
	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	public void restart() {
		this.isPause = false;
    	this.wasPause = false;
    	old = 0;
    	clock = 0;
    	timeAnimationLabel.setText("");
	}
}
        
        
        
