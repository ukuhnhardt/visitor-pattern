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
    public void accept(ConditionVisitor visitor) {
        System.out.println("accept " + this.getClass().getSimpleName());
        visitor.visit(this);
    }

}
