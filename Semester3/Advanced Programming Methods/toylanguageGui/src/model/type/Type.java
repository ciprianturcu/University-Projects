package model.type;

import model.values.Value;

public interface Type {
    Value defaultValue();
    Type deepCopy();
}
