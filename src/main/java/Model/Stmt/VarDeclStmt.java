package Model.Stmt;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.Value.RefValue;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name=name;
        this.type=type;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name))
            throw new ExpException("Symbol " + name + " is already defined");
        else {
            Value val;
            if (type.equals(new BoolType()) || type.equals(new IntType()) || type.equals(new StringType()) || type.getClass()==RefType.class)
                val = type.default_value();
            else
                throw new ExpException("Invalid type of value\n");
            symTbl.put(name, val);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        typeEnv.put(name,type);
        return typeEnv;
    }

    @Override
    public String toString(){
        return type + " " + name+";";
    }
}
