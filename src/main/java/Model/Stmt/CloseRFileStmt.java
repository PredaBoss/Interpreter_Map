package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IFileTable;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{

    private Exp exp;
    public CloseRFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if (!(exp.eval(symTable,heap ).getType() instanceof StringType))
            throw new ExpException("The expression needs to be of StringType");

        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue stringValue = (StringValue) exp.eval(symTable,heap );
        if(!fileTable.isDefined(stringValue))
            throw new ExpException("This filename does not exist");

        BufferedReader reader = fileTable.lookup(stringValue);
        reader.close();
        fileTable.remove(stringValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typexp = exp.typecheck(typeEnv);
        if(typexp instanceof StringType)
            return typeEnv;
        throw new StmtException("The expression is not an instance of StringType");
    }

    @Override
    public String toString(){ return "closeRFile(" +exp.toString()+");";}
}
