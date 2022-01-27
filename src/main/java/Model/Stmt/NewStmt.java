package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.RefValue;

import java.io.IOException;

public class NewStmt implements IStmt{

    private String var_name;
    private Exp exp;

    public NewStmt(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(!symTable.isDefined(var_name) && (symTable.lookup(var_name).getType().equals(new RefType())))
            throw new ExpException("The variable "+var_name+" is not defined or its type is not RefType");

        Type locationType = ((RefValue) symTable.lookup(var_name)).getLocationType();
        if (!locationType.equals(exp.eval(symTable,heap).getType()))
            throw new StmtException("The location type of the variable and the given expression do not have the same type!");

        Integer freeAddress = heap.getNextFreeLocation();
        heap.put(freeAddress,exp.eval(symTable, heap));

        symTable.update(var_name,new RefValue(freeAddress,locationType));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StmtException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){ return "new( "+var_name+" , " +exp.toString()+" );";}
}
