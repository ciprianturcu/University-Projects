package model.values;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{

    int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public boolean equals(Object anotherValue)
    {
        if(!(anotherValue instanceof IntValue))
            return false;
        IntValue castValue = (IntValue) anotherValue;
        return this.val == castValue.val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
