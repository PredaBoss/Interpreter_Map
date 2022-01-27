package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IFileTable;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{

    Exp exp;
    String var_name;

    public ReadFileStmt(Exp exp, String var_name){
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(!symTable.isDefined(var_name) && (symTable.lookup(var_name).getType().equals(new IntType())))
            throw new ExpException("The variable "+var_name+" is not defined or its type is not IntType");

        if(!(exp.eval(symTable,heap).getType() instanceof StringType))
            throw new ExpException("The expression needs to be of StringType");

        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue stringValue = (StringValue) exp.eval(symTable,heap);
        if(!fileTable.isDefined(stringValue))
            throw new ExpException("This filename does not exist");

        BufferedReader reader = fileTable.lookup(stringValue);
        String line;
        line = reader.readLine();
        int val;
        if(line==null)
            val = 0;
        else
            val = Integer.parseInt(line);

        symTable.update(var_name,new IntValue(val));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (!(typevar instanceof IntType))
            throw new StmtException("ReadFile: an int type is required for variable");
        if(!(typexp instanceof StringType))
            throw new StmtException("ReadFile: a string type is required for expression");
        return typeEnv;
    }

    @Override
    public String toString(){ return "readFile(" +exp.toString()+","+var_name+");";}
}
