package com.izymes;

public class AndOrVisitor extends AbstractConditionVisitor {

    @Override
    public boolean getVerdict() {
        throw new RuntimeException("use return result");
    }

    @Override
    public int visit(ConditionCombinerElement element) {
        int e1 = element.elements.get(0).accept(this);
        int e2 = element.elements.get(1).accept(this);
        switch (element.operation) {
            case AND:
                if (e1 + e2 == 2) {
                    return 1;
                }
                break;
            case OR:
                if (e1 + e2 >= 1) {
                    return 1;
                }
        }
        return 0;
    }

}
