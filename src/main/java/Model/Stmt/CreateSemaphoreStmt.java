package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.ISemaphore;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.*;

import java.io.IOException;

public class CreateSemaphoreStmt implements IStmt{

    String var;
    Exp exp;

    public CreateSemaphoreStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        ISemaphore semaphore = state.getSemaphore();

        Value expValue =exp.eval(symTable,heap);
        int num1 = ((IntValue)expValue).getVal();

        synchronized (semaphore){
            int freeAddress = semaphore.getNextFreeLocation();
            semaphore.put(freeAddress,num1);
            symTable.update(var,new IntValue(freeAddress));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);
        Type typexp = exp.typecheck(typeEnv);

        if(!typevar.equals(new IntType()))
            throw new StmtException("CreateSemaphore: var is not of type int!");
        if(!typexp.equals(new IntType()))
            throw new StmtException("CreateSemaphore: exp is not of type int!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "createSemaphore("+var+", "+exp+");";
    }
}
