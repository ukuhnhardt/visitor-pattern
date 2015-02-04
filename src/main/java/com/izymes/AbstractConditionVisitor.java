package com.izymes;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractConditionVisitor implements ConditionVisitor {
    protected int score = 0;
    protected int visits = 0;

    @Override
    public abstract boolean getVerdict();

    @Override
    public void visit(QuotaElement element) {
        System.out.println(StringUtils.repeat("-", visits) + "visit " + element.getClass().getSimpleName());
        score += element.quota >= element.target ? 1 : 0;
    }

    @Override
    public void visit(BuildResultElement element) {
        System.out.println(StringUtils.repeat("-", visits) + "visit " + element.getClass().getSimpleName());
        score += element.result ? 1 : 0;
    }

    @Override
    public void visit(GroupQuotaElement element) {
        System.out.println(StringUtils.repeat("-", visits) + "visit " + element.getClass().getSimpleName());
        for (String group : element.groupApprovals.keySet()){
            if (element.groupApprovals.get(group) < element.quota){
                return;
            }
        }
        score += 1;
    }

    @Override
    public void visit(ConditionCombinerElement element) {
        System.out.println(StringUtils.repeat("-", visits) + "visit " + element.getClass().getSimpleName());
    }

    @Override
    public void visit(AndElement element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
