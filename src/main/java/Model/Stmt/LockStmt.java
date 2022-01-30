package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.ILock;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.*;

import java.io.IOException;

public class LockStmt implements IStmt{

    String var;

    public LockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String,Value> symTable = state.getSymTable();
        ILock<Integer,Integer> lock = state.getLock();
        int foundIndex  =((IntValue)symTable.lookup(var)).getVal();

        synchronized (lock){
            if(!lock.isDefined(foundIndex))
                throw new StmtException("The foundIndex is not in LockTable!");

            if(lock.lookup(foundIndex) == -1)
                lock.update(foundIndex,state.getId());
            else
                state.getStk().push(this);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);
        if(!typevar.equals(new IntType()))
            throw new StmtException("Lock: var doesn't exist or it doesn't have IntType!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "lock("+var+");";
    }
}
