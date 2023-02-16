package model.type;

import model.values.StringValue;
import model.values.Value;

import javax.management.StringValueExp;

public class StringType implements Type{
    public StringType() {
    }

    public boolean equals(Object anotherType)
    {
        return anotherType instanceof StringType;

    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
