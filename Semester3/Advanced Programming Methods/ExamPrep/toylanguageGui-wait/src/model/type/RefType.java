package model.type;

import model.values.RefValue;
import model.values.Value;

import java.util.Objects;

public class RefType implements Type{

    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof RefType)
        {
            return inner.equals(((RefType) another).getInner());
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Ref "+inner.toString()+"";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
