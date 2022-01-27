package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Character op, Exp e1, Exp e2){

        if (op.equals('+'))
            this.op = 1;
        else if (op.equals('-'))
            this.op = 2;
        else if (op.equals('*'))
            this.op = 3;
        else if (op.equals('/'))
            this.op = 4;
        else
            this.op = -1;
        this.e1 = e1;
        this.e2 = e2;

    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws ExpException {
        if(op == -1)
            throw new ExpException("Operator " + op + " is invalid\n");

        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1)
                    return new IntValue(n1 + n2);
                if (op == 2)
                    return new IntValue(n1 - n2);
                if (op == 3)
                    return new IntValue(n1 * n2);
                if (op == 4)
                    if (n2 == 0)
                        throw new ExpException("division by zero");
                    else
                        return new IntValue(n1 / n2);
                throw new ExpException("Operator " + op + " is invalid\n");
            } else
                throw new ExpException("second operand is not an integer");
        } else
            throw new ExpException("first operand is not an integer");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException{

        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new ExpException("second operand is not an integer");
        }
        else
            throw new ExpException("first operand is not an integer");
    }

    @Override
    public String toString() {
        String operator = "";
        if (op == 1) operator = "+";
        if (op == 2) operator = "-";
        if (op == 3) operator = "*";
        if (op == 4) operator = "/";
        return e1 + operator + e2;
    }

}