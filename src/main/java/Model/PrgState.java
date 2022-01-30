package Model;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.*;
import Model.Stmt.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.TreeSet;

import static java.lang.Math.abs;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IFileTable<StringValue, BufferedReader> fileTable;
    IHeap<Integer,Value> heap;
    ILock<Integer,Integer> lock;
    IStmt   originalProgram; //optional field, but good to have
    private static final TreeSet<Integer> ids = new TreeSet<>();
    private final Integer id;

    public PrgState(IStmt prg){
        symTable = new MyDictionary<>();
        out = new MyList<>();
        exeStack = new MyStack<>();
        fileTable = new FileTable<>();
        heap = new Heap<>();
        lock = new Lock<>();
        id = newId();

        //originalProgram=deepCopy(prg);//recreate the entire original prg
        exeStack.push(prg);
    }

    public PrgState(MyIStack<IStmt> exeStack ,MyIDictionary<String, Value> symTable,MyIList<Value> out,IFileTable<StringValue, BufferedReader> fileTable,IHeap<Integer,Value> heap,ILock<Integer,Integer> lock){
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lock = lock;
        id = newId();
    }

    private static Integer newId() {
        Random random = new Random();
        Integer id;
        synchronized (ids) {
            do {
                id = abs(random.nextInt()%10) + 1;
            } while (ids.contains(id));
            ids.add(id);
        }
        return id;
    }

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public MyIDictionary<String,Value> getSymTable(){
        return symTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public IFileTable<StringValue,BufferedReader> getFileTable(){
        return fileTable;
    }

    public IHeap<Integer, Value> getHeap() { return heap; }

    public ILock<Integer, Integer> getLock() {
        return lock;
    }

    public Integer getId() { return id; }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws StmtException, ExpException, IOException, ContainerException {
        if(exeStack.isEmpty())
            throw new ContainerException("prgstate stack is empty");
        IStmt  crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString(){
        return "("+id+")PrgState{" +
                "\nexeStack=" + exeStack +
                ", \nsymTable=" + symTable +
                ", \nout=" + out +
                ", \noriginalProgram=" + originalProgram +
                "}\n---------------------------------------------------\n";
    }
}
