package com.izymes;

public class AllConditionsVisitor extends AbstractConditionVisitor {

    @Override
    public boolean getVerdict() {
        return score == visits;
    }

    @Override
    public void visit(QuotaElement element) {
        visits++;
        super.visit(element);
    }

    @Override
    public void visit(BuildResultElement element) {
        visits++;
        super.visit(element);
    }

    @Override
    public void visit(GroupQuotaElement element) {
        visits++;
        super.visit(element);
    }



}
