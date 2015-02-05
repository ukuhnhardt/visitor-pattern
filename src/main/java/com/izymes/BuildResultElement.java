package com.izymes;

public class BuildResultElement implements ConditionElement {
    final boolean result;

    public BuildResultElement() {
        this(false);
    }

    public BuildResultElement(boolean result) {
        this.result = result;
    }

    @Override
    public int accept(ConditionVisitor visitor) {
        return visitor.visit(this);
    }

}
