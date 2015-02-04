package com.izymes;

public interface ConditionElement {
    public void accept(ConditionVisitor visitor );
}
