package electrostatic.java.fx;
import java.io.IOException;
import java.util.Locale;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


import javafx.stage.Stage;

public class Main  extends Application{

	
	
	@Override
    public void start(Stage primaryStage) {
		Locale.setDefault(new Locale("pl", "PL"));
	
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainView.fxml"));
		
		BorderPane borderPane = null;
		try {
			borderPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		borderPane.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
        Scene scene = new Scene(borderPane);
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
   
 
    public static void main(String[] args) {
        launch(args);
    }

}
