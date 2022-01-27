package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer,Value> hp) throws ExpException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException;
}
