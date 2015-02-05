package com.izymes;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ConditionCombinerElement implements ConditionElement {

    final List<ConditionElement> elements = new ArrayList<ConditionElement>(2);
    final Operation operation;


    public ConditionCombinerElement(Operation op, ConditionElement... conditionElements) {
        this.elements.addAll(Lists.newArrayList(conditionElements));
        this.operation = op;
    }
    public ConditionCombinerElement(Operation op, ConditionElement e1, ConditionElement e2) {
        this.elements.add(e1);
        this.elements.add(e2);
        this.operation = op;
    }

    public ConditionCombinerElement(ConditionElement e1, ConditionElement e2) {
        this(Operation.AND, e1, e2);
    }

    @Override
    public int accept(ConditionVisitor visitor) {
        return visitor.visit(this);
    }
}
