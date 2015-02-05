package com.izymes;

public class AllConditionsVisitor extends AbstractConditionVisitor {

    @Override
    public boolean getVerdict() {
        return score == visits;
    }

    @Override
    public int visit(QuotaElement element) {
        visits++;
        return super.visit(element);
    }

    @Override
    public int visit(BuildResultElement element) {
        visits++;
        return super.visit(element);
    }

    @Override
    public int visit(GroupQuotaElement element) {
        visits++;
        return super.visit(element);
    }



}
