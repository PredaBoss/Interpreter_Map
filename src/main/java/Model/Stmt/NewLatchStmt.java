package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.ILatchTable;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.IntValue;

import java.io.IOException;

public class NewLatchStmt implements IStmt{

    String var;
    Exp exp;

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        ILatchTable<Integer,Integer> latchTable= state.getLatchTable();

        Value expValue =exp.eval(symTable,heap);
        int num1 = ((IntValue)expValue).getVal();

        synchronized (latchTable){
            Integer freeAddress = latchTable.getNextFreeLocation();
            latchTable.put(freeAddress,num1);
            symTable.update(var,new IntValue(freeAddress));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);
        Type typexp = exp.typecheck(typeEnv);

        if(!typevar.equals(new IntType()))
            throw new StmtException("NewLatch: var is not of type int!");
        if(!typexp.equals(new IntType()))
            throw new StmtException("NewLatch: exp is not of type int!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "newLatch("+var+","+exp.toString()+");";
    }
}
