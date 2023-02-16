package model.values;

import model.type.StringType;
import model.type.Type;

import java.util.Objects;

public class StringValue implements Value{
    String value;
    public StringValue(String value) {
        this.value=value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object anotherValue)
    {
        if(!(anotherValue instanceof StringValue))
            return false;
        StringValue castValue = (StringValue) anotherValue;
        return Objects.equals(this.value, castValue.value);

    }

    @Override
    public String toString() {
        return this.value;
    }
}
