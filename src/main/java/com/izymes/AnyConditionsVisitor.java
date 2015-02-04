package com.izymes;

public class AnyConditionsVisitor extends AbstractConditionVisitor {

    @Override
    public boolean getVerdict() {
        return score > 0;
    }
}
