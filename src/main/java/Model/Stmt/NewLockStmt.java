package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.ILock;
import Model.Containers.MyIDictionary;
import Model.Exp.ValueExp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.IntValue;

import java.io.IOException;

public class NewLockStmt implements IStmt{

    String var;

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        ILock<Integer,Integer> lock = state.getLock();
        MyIDictionary<String, Value> symTable = state.getSymTable();

        synchronized (lock){
            Integer freeAddress = lock.getNextFreeLocation();
            lock.put(freeAddress,-1);
            symTable.update(var,new IntValue(freeAddress));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);
        if(!typevar.equals(new IntType()))
            throw new StmtException("NewLock: var doesn't exist or it doesn't have IntType!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "newLock( "+var+ ");";
    }
}
