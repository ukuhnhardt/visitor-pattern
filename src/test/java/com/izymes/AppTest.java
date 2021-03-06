package com.izymes;


import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    public void testAllConditionsPass()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(50, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",4);
        ConditionElement allCombined = new ConditionCombinerElement(new GroupQuotaElement(groups, 2), twoCombined);
        ConditionVisitor visitor = new AllConditionsVisitor();
        twoCombined.accept(visitor) ;
        assertTrue(visitor.getVerdict());
        visitor = new AllConditionsVisitor();
        allCombined.accept(visitor) ;
        assertTrue(visitor.getVerdict());
    }
    @Test
    public void testAllConditionsFail()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(50, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        ConditionElement allCombined = new ConditionCombinerElement(twoCombined, new GroupQuotaElement(groups, 2));
        ConditionVisitor visitor = new AllConditionsVisitor();
        twoCombined.accept(visitor) ;
        assertTrue(visitor.getVerdict());
        visitor = new AllConditionsVisitor();
        allCombined.accept(visitor) ;
        assertFalse(visitor.getVerdict());
    }
    @Test
    public void testAnyConditions()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(50, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        ConditionElement allCombined = new ConditionCombinerElement(twoCombined, new GroupQuotaElement(groups, 2));
        ConditionVisitor visitor = new AnyConditionsVisitor();
        twoCombined.accept(visitor) ;
        assertTrue(visitor.getVerdict());
        visitor = new AnyConditionsVisitor();
        allCombined.accept(visitor) ;
        assertTrue(visitor.getVerdict());
    }

    @Test
    public void testOrConditionsPassCombined_tree()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(30, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(Operation.OR, build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        ConditionElement allCombined = new ConditionCombinerElement(Operation.OR, new GroupQuotaElement(groups, 2), twoCombined);
        ConditionVisitor visitor = new AndOrVisitor();
        assertEquals(1, allCombined.accept(visitor));
    }
    @Test
    public void testOrConditionsPassCombined()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(30, 40);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        ConditionElement allCombined = new ConditionCombinerElement(Operation.OR, new GroupQuotaElement(groups, 2), build, quota);
        ConditionVisitor visitor = new AndOrVisitor();
        assertEquals(1, allCombined.accept(visitor));
    }

    @Test
    public void testAndConditionsFail_tree()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(30, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(Operation.OR, build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        GroupQuotaElement group = new GroupQuotaElement(groups, 2);
        ConditionElement allCombined = new ConditionCombinerElement(Operation.AND, group, twoCombined);
        ConditionVisitor visitor = new AndOrVisitor();
        int result = allCombined.accept(visitor);
        assertEquals(0, result);
    }
    @Test
    public void testAndConditionsPass_tree()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(50, 40);
        ConditionElement twoCombined = new ConditionCombinerElement(Operation.AND, build, quota);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        GroupQuotaElement group = new GroupQuotaElement(groups, 2);
        ConditionElement allCombined = new ConditionCombinerElement(Operation.OR, group, twoCombined);
        ConditionVisitor visitor = new AndOrVisitor();
        int result = allCombined.accept(visitor);
        assertEquals(1, result);
    }
    @Test
    public void testAndConditionsFail()    {

        ConditionElement build = new BuildResultElement(true);
        ConditionElement quota = new QuotaElement(30, 40);
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        GroupQuotaElement group = new GroupQuotaElement(groups, 2);
        ConditionElement allCombined = new ConditionCombinerElement(Operation.AND, group, quota, build);
        ConditionVisitor visitor = new AndOrVisitor();
        int result = allCombined.accept(visitor);
        assertEquals(0, result);
    }


    @Test
    public void testExpressionParser_Fail(){
        Expression<String> expression = ExprParser.parse("B & ( Q & G )");
        ConditionElement quota = new QuotaElement(40, 40);
        ConditionVisitor aVisitor = new AllConditionsVisitor();
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        GroupQuotaElement group = new GroupQuotaElement(groups, 2);
        ConditionVisitor gVisitor = new AllConditionsVisitor();
        Expression<String> ass = RuleSet.assign(expression, ImmutableMap.of("B", Boolean.TRUE
                , "Q", quota.accept(aVisitor) > 0
                , "G", group.accept(gVisitor) > 0) );
        assertFalse(Boolean.valueOf(ass.toString()) );
    }

    @Test
    public void testExpressionParser_Pass(){
        Expression<String> expression = ExprParser.parse("B & ( Q | G )");
        ConditionElement quota = new QuotaElement(40, 40);
        ConditionVisitor aVisitor = new AllConditionsVisitor();
        Map<String, Integer> groups = ImmutableMap.of("abba",2, "acdc",1);
        GroupQuotaElement group = new GroupQuotaElement(groups, 2);
        ConditionVisitor gVisitor = new AllConditionsVisitor();
        Expression<String> ass = RuleSet.assign(expression, ImmutableMap.of("B", Boolean.TRUE
                , "Q", quota.accept(aVisitor) > 0
                , "G", group.accept(gVisitor) > 0) );
        assertTrue(Boolean.valueOf(ass.toString()) );
    }

}
