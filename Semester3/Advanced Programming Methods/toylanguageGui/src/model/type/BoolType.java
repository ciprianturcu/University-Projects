package model.type;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type{

    public BoolType() {

    }

    public boolean equals(Object another)
    {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

}
