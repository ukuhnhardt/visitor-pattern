package com.izymes;

public class QuotaElement implements ConditionElement {
    final int quota;
    final int target;

    public QuotaElement() {
        this(0, 100);
    }

    public QuotaElement(int quota, int target) {
        this.quota = quota;
        this.target = target;
    }

    @Override
    public int accept(ConditionVisitor visitor) {
        return visitor.visit(this);
    }
}
