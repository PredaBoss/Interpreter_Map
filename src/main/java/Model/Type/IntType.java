package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {

    @Override
    public boolean equals(Object another){
        if (another instanceof  IntType)
            return true;
        else
            return false;
    }

    @Override
    public Value default_value() {
        return new IntValue();
    }

    public String toString() {
        return "int";
    }
}
