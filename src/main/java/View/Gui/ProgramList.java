package View.Gui;

import Controller.Controller;
import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.MyDictionary;
import Model.PrgState;
import Model.Stmt.IStmt;
import Repository.Repository;
import View.Data;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramList implements Initializable {

    private ProgramData programData;

    @FXML
    private Button executeButton;

    @FXML
    private ListView<IStmt> listOfPrograms;

    public void setProgramData(ProgramData programData) {
        this.programData = programData;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOfPrograms.setItems(FXCollections.observableArrayList(Data.getPrograms()));
        executeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                int index = listOfPrograms.getSelectionModel().getSelectedIndex();
                if(index < 0)
                    return;
                IStmt stmt= Data.getPrograms().get(index);
                try {
                    stmt.typecheck(new MyDictionary<>());
                    PrgState state = new PrgState(stmt);
                    Repository repository = new Repository(state,"log.txt");
                    Controller ctrl = new Controller(repository);
                    programData.setController(ctrl);
                } catch (StmtException | ExpException | FileNotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(e.getMessage());
                    a.showAndWait();
                }


            }
        });
    }
}
