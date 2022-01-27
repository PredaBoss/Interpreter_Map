package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolType) return true;
        else return false;
    }

    @Override
    public Value default_value() {
        return new BoolValue();
    }

    @Override
    public String toString() {
        return "bool";
    }
}
