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
    public void accept(ConditionVisitor visitor) {
        System.out.println("accept " + this.getClass().getSimpleName());
        visitor.visit(this);
    }
}
