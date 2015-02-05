package com.izymes;

public interface ConditionVisitor {
    public int visit(QuotaElement element);
    public int visit(BuildResultElement element);
    public int visit(GroupQuotaElement element);
    public int visit(ConditionCombinerElement element);
    public boolean getVerdict();
}
