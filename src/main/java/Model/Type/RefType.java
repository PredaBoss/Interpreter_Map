package Model.Type;

import Model.Value.Value;
import Model.Value.RefValue;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) {
        this.inner=inner;
    }

    public RefType() {

    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" +inner.toString()+")";
    }

    @Override
    public Value default_value() {
        return new RefValue(0,inner);
    }
}
