package Application;

import Database.DatabaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboard extends Application {

    private static final AdminDashboardController controller = new AdminDashboardController();

    private final DatabaseConnector dbConnector = DatabaseConnector.getInstance();












    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
            "/com/nichga/proj97/test.fxml"
            )
        );
        loader.setController(controller);



        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
}
