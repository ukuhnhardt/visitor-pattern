package com.izymes;

public interface ConditionElement {
    /**
     *
     * @param visitor
     * @return 1 if result true, 0 if result false
     */
    public int accept(ConditionVisitor visitor );
}
