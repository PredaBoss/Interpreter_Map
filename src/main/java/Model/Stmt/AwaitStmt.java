package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.ILatchTable;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.IntValue;

import java.io.IOException;

public class AwaitStmt implements IStmt{

    String var;

    public AwaitStmt(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String,Value> symTable = state.getSymTable();
        ILatchTable<Integer,Integer> latchTable= state.getLatchTable();

        if(!(symTable.isDefined(var) && (symTable.lookup(var).getType().equals(new IntType()))))
            throw new StmtException("The variable "+var+" is not defined or its type is not IntType");
        int foundIndex = ((IntValue)symTable.lookup(var)).getVal();

        synchronized (latchTable) {
            if (!latchTable.isDefined(foundIndex))
                throw new StmtException("The foundIndex is not in LatchTable!");

            if (latchTable.get(foundIndex) != 0)
                state.getStk().push(this);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var);

        if(!typevar.equals(new IntType()))
            throw new StmtException("Await: var is not of type int!");

        return typeEnv;
    }

    @Override
    public String toString(){
        return "await("+var+");";
    }
}