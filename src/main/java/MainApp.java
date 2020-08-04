import model.User;
import model.UserDBManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/registration_form.fxml"));
        stage.setTitle("J2SE Application");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        UserDBManager um = new UserDBManager();
        um.createDB();
        um.createTable();
        um.getDatabaseOID();
        launch(args);
    }
}
