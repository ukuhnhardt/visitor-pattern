package com.izymes;

public interface ConditionVisitor {
    public void visit(QuotaElement element);
    public void visit(BuildResultElement element);
    public void visit(GroupQuotaElement element);
    public void visit(ConditionCombinerElement element);

    public void visit(AndElement element);

    public boolean getVerdict();
}
