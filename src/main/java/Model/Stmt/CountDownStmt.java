package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.ILatchTable;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.io.IOException;

public class CountDownStmt implements IStmt{

    String var;

    public CountDownStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        ILatchTable<Integer,Integer> latchTable= state.getLatchTable();
        int foundIndex = ((IntValue)symTable.lookup(var)).getVal();

        synchronized (latchTable) {
            if (!latchTable.isDefined(foundIndex))
                throw new StmtException("The foundIndex is not in LatchTable!");

            if (latchTable.get(foundIndex) > 0)
                latchTable.update(foundIndex, latchTable.get(foundIndex) - 1);

            state.getOut().add(new IntValue(state.getId()));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);

        if(!typevar.equals(new IntType()))
            throw new StmtException("CountDown: var is not of type int!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "countDown("+var+");";
    }
}