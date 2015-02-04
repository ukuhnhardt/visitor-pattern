package com.izymes;

public class AndElement implements ConditionElement {
    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }
}
