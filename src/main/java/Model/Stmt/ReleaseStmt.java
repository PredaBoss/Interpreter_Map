package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.ISemaphore;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class ReleaseStmt implements IStmt{

    String var;

    public ReleaseStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        ISemaphore semaphore = state.getSemaphore();

        int foundIndex = ((IntValue)symTable.lookup(var)).getVal();
        if(!semaphore.isDefined(foundIndex))
            throw new StmtException("The foundIndex was not found in SemaphoreTable!");

        synchronized (semaphore){
            semaphore.remove(foundIndex,state.getId());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);

        if(!typevar.equals(new IntType()))
            throw new StmtException("Release: var is not of type int!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "release("+var+");";
    }
}
