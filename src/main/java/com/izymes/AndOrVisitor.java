package com.izymes;

public class AndOrVisitor extends AbstractConditionVisitor {

    @Override
    public boolean getVerdict() {
        throw new RuntimeException("use return result");
    }

    @Override
    public int visit(ConditionCombinerElement element) {
        int score = 0;
        int size = element.elements.size();
        for (ConditionElement e : element.elements){
            score += e.accept(this);
        }
        switch (element.operation) {
            case AND:
                if (score == size) {
                    return 1;
                }
                break;
            case OR:
                if (score >= 1) {
                    return 1;
                }
        }
        return 0;
    }

}
