package electrostatic.java.fx.controller;

import electrostatic.java.fx.model.ChargeModel;
import electrostatic.java.fx.model.SymulatorTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnimationToolBarController {
	
	@FXML
	private Label timeAnimationLabel;

	private MainController mainController;
	private SymulatorTimer symulatorTimer;
	private ChargeModel chargeModel;
	
	@FXML
	public void initialize(){
		
		symulatorTimer = new SymulatorTimer(timeAnimationLabel);
		
	}
	public void initModel(ChargeModel chargeModel){
		this.chargeModel = chargeModel;
	}
	
	@FXML
	public void playAction() {
		if (!symulatorTimer.wasPause()) {
//			mainController.getAppController().getChargeControll().saveChargesPosition();
		}
		symulatorTimer.setPause(false);
		symulatorTimer.start();
	}

	@FXML
	public void pauseAction() {
		symulatorTimer.setPause(true);
	}

	@FXML
	public void stopAction() {
		symulatorTimer.stop();
//		chargeModel.writeChargesPosition();
		symulatorTimer.restart();
	}
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
//		symulatorTimer.setMainController(mainController);
	}	
}
