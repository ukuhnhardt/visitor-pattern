package com.izymes;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractConditionVisitor implements ConditionVisitor {
    protected int score = 0;
    protected int visits = 0;

    @Override
    public abstract boolean getVerdict();

    @Override
    public int visit(QuotaElement element) {
        if (element.quota >= element.target){
            score++;
            return 1;
        }
        return 0;
    }

    @Override
    public int visit(BuildResultElement element) {
        if (element.result ){
            score++;
            return 1;
        }
        return 0;
    }

    @Override
    public int visit(GroupQuotaElement element) {
        for (String group : element.groupApprovals.keySet()){
            if (element.groupApprovals.get(group) < element.quota){
                return 0;
            }
        }
        score += 1;
        return 1;
    }

    @Override
    public int visit(ConditionCombinerElement element) {
        for (ConditionElement e : element.elements) e.accept(this);
        return 0;
    }

}
