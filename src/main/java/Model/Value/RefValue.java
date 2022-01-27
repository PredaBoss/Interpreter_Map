package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;

public class RefValue implements Value{

    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }
}
