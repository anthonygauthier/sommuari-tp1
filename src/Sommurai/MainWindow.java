package Sommurai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("MainWindow.fxml").openStream());
        primaryStage.setTitle("Sommurai");
        primaryStage.setScene(new Scene(root, 800, 300));
        primaryStage.show();

        //On affiche la première somme
        Controller controller = (Controller) fxmlLoader.getController();
        controller.afficheSomme();
    }
}
