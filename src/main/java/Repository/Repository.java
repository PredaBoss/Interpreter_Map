package Repository;

import Exceptions.ContainerException;
import Model.Containers.*;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> states;
    int index;
    private String logFilePath;

    public Repository(PrgState state,String logFilePath){
        this.logFilePath = logFilePath;
        states = new ArrayList<>();
        this.states.add(state);
        index = 0;
    }

    @Override
    public List<PrgState> getPrgList(){
        return states;
    }

    public void setPrgList(List<PrgState> new_states){
        this.states = new_states;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws IOException{
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        MyIStack<IStmt> stk = prgState.getStk();
        MyIDictionary<String, Value> dict = prgState.getSymTable();
        MyIList<Value> out = prgState.getOut();
        IFileTable<StringValue, BufferedReader> fileTable = prgState.getFileTable();
        IHeap<Integer, Value> heap = prgState.getHeap();

        logFile.println("------------------------ PrgState with id "+prgState.getId()+" ----------------------\n");

        logFile.println("ExeStack:");
        logFile.println(stk);

        logFile.println("SymTable:");
        logFile.println(dict);

        logFile.println("Heap:");
        logFile.println(heap);

        logFile.println("Out:");
        logFile.println(out);

        logFile.println("FileTable:");
        logFile.println(fileTable);

        logFile.close();
    }

    public void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(logFilePath);
        writer.print("");
        writer.close();
    }
}
