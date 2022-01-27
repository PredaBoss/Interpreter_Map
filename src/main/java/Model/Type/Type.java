package Model.Type;

import Model.Value.Value;

public interface Type {
    public boolean equals(Object another);
    Value default_value();
}
