package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Exp {

    Exp e1;
    Exp e2;
    int op;

    LogicExp(Exp e1, Exp e2, int op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws ExpException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                if (op == 1)
                    return new BoolValue(b1.getVal() && b2.getVal());
                else if (op == 2)
                    return new BoolValue(b1.getVal()  || b2.getVal());
                else
                    throw new ExpException("Invalid logic operator");

            }
            else
                throw new ExpException("Second operand is not boolean\n");
        }
        else
            throw new ExpException("First operand is not of type boolean\n");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException {

        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new ExpException("second operand is not a boolean");
        }
        else
            throw new ExpException("first operand is not a boolean");
    }
}
