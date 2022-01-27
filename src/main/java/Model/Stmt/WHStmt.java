package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.RefValue;

import javax.xml.validation.Validator;
import java.io.IOException;

public class WHStmt implements IStmt{

    String var_name;
    Exp exp;

    public WHStmt(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(!symTable.isDefined(var_name) && (symTable.lookup(var_name).getType().equals(new RefType())))
            throw new ExpException("The variable "+var_name+" is not defined or its type is not IntType");

        RefValue refValue = (RefValue)symTable.lookup(var_name);
        if(!heap.isDefined(refValue.getAddr()))
            throw new StmtException("The address contained in this variable was not found in the heap!");

        Value expValue = exp.eval(symTable,heap);
        if(!(refValue.getLocationType()).equals(expValue.getType()))
            throw new StmtException("The locationType of the variable and the type returned by expression are not the same!");

        heap.update(refValue.getAddr(),expValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = new RefType(exp.typecheck(typeEnv));
        if (typevar.equals(typexp))
            return typeEnv;
        throw new StmtException("WHStmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){
        return "wH( "+var_name+" , "+exp.toString()+" );";
    }
}
