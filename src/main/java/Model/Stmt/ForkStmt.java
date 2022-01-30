package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.*;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ForkStmt implements IStmt{

    IStmt stmt;

    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {

        MyIStack<IStmt> exeStack = new MyStack<>();
        exeStack.push(stmt);
        MyIDictionary<String, Value> symTable = state.getSymTable().deepcopy();
        MyIList<Value> out = state.getOut();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer,Value> heap = state.getHeap();


        return new PrgState(exeStack,symTable,out,fileTable,heap,state.getLatchTable());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        stmt.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork(\n"+stmt+"\n)";
    }
}
