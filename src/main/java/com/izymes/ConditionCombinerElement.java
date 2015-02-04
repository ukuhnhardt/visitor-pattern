package com.izymes;

import java.util.ArrayList;
import java.util.List;

public class ConditionCombinerElement implements ConditionElement {

    final List<ConditionElement> elements = new ArrayList<ConditionElement>(2);
    final Operation operation;


    public ConditionCombinerElement(Operation op, ConditionElement e1, ConditionElement e2) {
        this.elements.add(e1);
        this.elements.add(e2);
        this.operation = op;
    }

    public ConditionCombinerElement(ConditionElement e1, ConditionElement e2) {
        this(Operation.AND, e1, e2);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        System.out.println("accept " + this.getClass().getSimpleName());
        visitor.visit(this);
        for (ConditionElement element : elements){
            element.accept(visitor);
        }

    }
}
