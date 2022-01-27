package View.Gui;

import Model.Stmt.IStmt;
import View.Data;
import com.map.map_project.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args){ launch(); }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("program-list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Choose a program to run!");
        stage.setScene(scene);
        stage.show();
        ProgramList prgListController = fxmlLoader.getController();

        FXMLLoader programDataLoader = new FXMLLoader(Main.class.getResource("program-data.fxml"));
        Stage dataStage = new Stage();
        Scene dataScene = new Scene(programDataLoader.load(), 850, 620);
        prgListController.setProgramData(programDataLoader.getController());
        dataStage.setTitle("Run the selected program!");
        dataStage.setScene(dataScene);
        dataStage.show();
    }
}
