package model.values;

import com.sun.jdi.BooleanType;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;

public class BoolValue implements Value{

    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object anotherValue)
    {
        if(!(anotherValue instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) anotherValue;
        return this.val == castValue.val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
