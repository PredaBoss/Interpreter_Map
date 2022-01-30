package View.Gui;

import Controller.Controller;
import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.StringValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import Model.Value.Value;
import Model.Value.StringValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProgramData implements Initializable {

    private Controller controller;

    @FXML
    private TableView<HeapPair> heapTable;

    @FXML
    private TableColumn<HeapPair,String> address;

    @FXML
    private TableColumn<HeapPair,String> valueHeap;

    @FXML
    private TableView<LockPair> lockTable;

    @FXML
    private TableColumn<LockPair,String> locationLock;

    @FXML
    private TableColumn<LockPair,String> valueLock;

    @FXML
    private ListView<String> out;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<Integer> programStateList;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private TableView<SymPair> symTable;

    @FXML
    private TableColumn<SymPair,String> variable;

    @FXML
    private TableColumn<SymPair,String> valueSym;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private Button oneStep;

    ObservableList<SymPair> lst = FXCollections.observableArrayList(new SymPair("hei",new StringValue("10")));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        address.setCellValueFactory(new PropertyValueFactory<HeapPair,String>("address"));
        valueHeap.setCellValueFactory(new PropertyValueFactory<HeapPair,String>("valueHeap"));
        locationLock.setCellValueFactory(new PropertyValueFactory<LockPair,String>("locationLock"));
        valueLock.setCellValueFactory(new PropertyValueFactory<LockPair,String>("valueLock"));
        variable.setCellValueFactory(new PropertyValueFactory<SymPair,String>("variable"));
        valueSym.setCellValueFactory(new PropertyValueFactory<SymPair,String>("valueSym"));
        lockTable.setItems(FXCollections.observableList(new ArrayList<LockPair>()));
        heapTable.setItems(FXCollections.observableList(new ArrayList<HeapPair>()));

        oneStep.setOnAction(actionEvent -> {
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            boolean programStateLeft = Objects.requireNonNull(getCurrentProgramState()).getStk().isEmpty();
            if(programStateLeft){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                controller.oneStepForAllPrg(controller.getPrgStates());
                controller.afterOneStep();
                populate();
            } catch (StmtException | ContainerException | ExpException | IOException | InterruptedException interpreterError) {
                Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        programStateList.setOnMouseClicked(mouseEvent -> populate());
    }
    private PrgState getCurrentProgramState(){
        if (controller.getPrgStates().size() == 0)
            return null;
        int currentId = programStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getPrgStates().get(0);
        return controller.getPrgStates().get(currentId);
    }

    public void setController(Controller controller) throws FileNotFoundException {
        this.controller = controller;
        controller.beforeSteps();
        populate();
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
        populateLock();
    }

    private void populateHeap() {
        Map<Integer,Value> heap = controller.getPrgStates().get(0).getHeap().getContent();
        ObservableList<HeapPair> heapTableList = FXCollections.observableArrayList();
        heap.keySet().forEach(key->{heapTableList.add(new HeapPair(key,heap.get(key)));});
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getPrgStates();
        var idList = programStates.stream().map(PrgState::getId).collect(Collectors.toList());
        programStateList.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText("" + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (controller.getPrgStates().size() > 0)
            files = controller.getPrgStates().get(0).getFileTable().keySet().stream().map(StringValue::toString).collect(Collectors.toCollection(ArrayList::new));
        else files = new ArrayList<>();
        fileTable.setItems(FXCollections.observableArrayList(files));
        fileTable.refresh();
    }

    private void populateOutput() {
        out.getItems().clear();
        out.getItems().addAll(controller.getPrgStates().get(0).getOut().getList());
        out.refresh();
    }

    private void populateSymbolTable() {
        PrgState state = getCurrentProgramState();
        List<SymPair> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new SymPair(entry.getKey(), entry.getValue()));
        symTable.setItems(FXCollections.observableList(symbolTableList));
        symTable.refresh();
    }

    private void populateExecutionStack() {
        PrgState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        if (state != null)
            for(IStmt s : state.getStk().getContent()){
                executionStackListAsString.add(s.toString());
            }
        exeStack.setItems(FXCollections.observableList(executionStackListAsString));
        exeStack.refresh();
    }

    private void populateLock() {
        Map<Integer,Integer> lock = controller.getPrgStates().get(0).getLock().getContent();
        ObservableList<LockPair> lockTableList = FXCollections.observableArrayList();
        lock.keySet().forEach(key->{lockTableList.add(new LockPair(key,lock.get(key)));});
        lockTable.setItems(FXCollections.observableList(lockTableList));
        lockTable.refresh();
    }
}
