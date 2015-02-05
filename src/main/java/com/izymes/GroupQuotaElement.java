package com.izymes;

import java.util.HashMap;
import java.util.Map;

public class GroupQuotaElement implements ConditionElement {
    final Map<String, Integer> groupApprovals;
    final int quota;

    public GroupQuotaElement(Map<String, Integer> groupApprovals, int quota) {
        this.groupApprovals = groupApprovals;
        this.quota = quota;
    }

    public GroupQuotaElement() {
        this(new HashMap<String, Integer>(), 0);
    }

    @Override
    public int accept(ConditionVisitor visitor) {
        return visitor.visit(this);
    }

}
