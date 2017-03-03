package edu.cmu.cs.mvelezce.analysis.taint;

import edu.cmu.cs.mvelezce.analysis.cfg.BasicBlock;
import edu.cmu.cs.mvelezce.analysis.cfg.CFG;
import edu.cmu.cs.mvelezce.analysis.cfg.CFGBuilder;
import edu.cmu.cs.mvelezce.language.Helper;
import edu.cmu.cs.mvelezce.language.ast.expression.*;
import edu.cmu.cs.mvelezce.language.ast.statement.*;
import edu.cmu.cs.mvelezce.language.lexer.Lexer;
import edu.cmu.cs.mvelezce.language.parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by miguelvelez on 2/5/17.
 */
public class TaintAnalysisTest {
    @Test
    public void testJoin1() {
        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        Assert.assertEquals(set, TaintAnalysis.join(set, set));
    }

    @Test
    public void testJoin2() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        Assert.assertEquals(set, TaintAnalysis.join(new HashSet<>(), set));
    }

    @Test
    public void testJoin3() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        Assert.assertEquals(set, TaintAnalysis.join(set, set));
    }

    @Test
    public void testJoin4() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        Set<TaintAnalysis.PossibleTaint> set2 = new HashSet<>();
        set2.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set2.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));
        Assert.assertEquals(set2, TaintAnalysis.join(set, set1));
    }

    @Test
    public void testJoin5() {
        Set<ExpressionConfigurationConstant> configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("A"));
        configurations1.add(new ExpressionConfigurationConstant("B"));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations1));

        Set<ExpressionConfigurationConstant> configurations2 = new HashSet<>();
        configurations2.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set2 = new HashSet<>();
        set2.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations2));

        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("A"));
        configurations.add(new ExpressionConfigurationConstant("B"));
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations));

        Assert.assertEquals(set, TaintAnalysis.join(set1, set2));
    }

    @Test
    public void testJoin6() {
        Set<ExpressionConfigurationConstant> configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations1));

        Set<ExpressionConfigurationConstant> configurations2 = new HashSet<>();
        configurations2.add(new ExpressionConfigurationConstant("D"));

        Set<TaintAnalysis.PossibleTaint> set2 = new HashSet<>();
        set2.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations2));

        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));
        configurations.add(new ExpressionConfigurationConstant("D"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations));

        Assert.assertEquals(set, TaintAnalysis.join(set1, set2));
    }

    @Test
    public void testJoin7() {
        Set<ExpressionConfigurationConstant> configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("A"));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("y"), configurations1));

        configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("C"));

        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("z"), configurations1));

        configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("B"));
        configurations1.add(new ExpressionConfigurationConstant("C"));

        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("w"), configurations1));

        configurations1 = new HashSet<>();
        configurations1.add(new ExpressionConfigurationConstant("C"));
        configurations1.add(new ExpressionConfigurationConstant("D"));

        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations1));

        Set<ExpressionConfigurationConstant> configurations2 = new HashSet<>();
        configurations2.add(new ExpressionConfigurationConstant("A"));
        configurations2.add(new ExpressionConfigurationConstant("B"));
        configurations2.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set2 = new HashSet<>();
        set2.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations2));


        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("A"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("y"), configurations));

        configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("z"), configurations));

        configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("B"));
        configurations.add(new ExpressionConfigurationConstant("C"));

        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("w"), configurations));

        configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("A"));
        configurations.add(new ExpressionConfigurationConstant("B"));
        configurations.add(new ExpressionConfigurationConstant("C"));
        configurations.add(new ExpressionConfigurationConstant("D"));

        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations));

        Assert.assertEquals(set, TaintAnalysis.join(set1, set2));
    }

    @Test
    public void testTransfer1() {
        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        BasicBlock basicBlock = new BasicBlock(new StatementSleep(new ExpressionConstantInt(1)));

        Assert.assertEquals(set, TaintAnalysis.transfer(set, basicBlock));
    }

    @Test
    public void testTransfer2() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("a"), new ExpressionConfigurationConstant("C")));

        Assert.assertEquals(set, TaintAnalysis.transfer(new HashSet<>(), basicBlock));
    }

    @Test
    public void testTransfer3() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));
        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("a"), new ExpressionConfigurationConstant("C")));

        Assert.assertEquals(set, TaintAnalysis.transfer(set1, basicBlock));
    }

    @Test
    public void testTransfer4() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("a"), new ExpressionConstantInt(0)));

        Assert.assertEquals(new HashSet<>(), TaintAnalysis.transfer(set1, basicBlock));
    }

    @Test
    public void testTransfer5() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));
        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations));

        List<Statement> statements = new LinkedList<>();
        statements.add(new StatementAssignment(new ExpressionVariable("x"), new ExpressionConstantInt(0)));

        StatementIf statementIf = new StatementIf(new ExpressionVariable("a"), new StatementBlock(statements));

        List<Expression> conditions = new ArrayList<>();
        conditions.add(statementIf.getCondition());

        BasicBlock basicBlock = null;
        for (Statement trueStatement : ((StatementBlock) statementIf.getThenBlock()).getStatements()) {
            basicBlock = new BasicBlock("1| " + trueStatement, trueStatement, conditions);
        }

        Assert.assertEquals(set1, TaintAnalysis.transfer(set, basicBlock));
    }

    @Test
    public void testTransfer6() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("x"), configurations));

        List<Statement> statements = new LinkedList<>();
        statements.add(new StatementAssignment(new ExpressionVariable("x"), new ExpressionConstantInt(0)));

        StatementIf statementIf = new StatementIf(new ExpressionBinary(new ExpressionVariable("a"), "+", new ExpressionVariable("b")),
                new StatementBlock(statements));

        List<Expression> conditions = new ArrayList<>();
        conditions.add(new ExpressionVariable("a"));
        conditions.add(new ExpressionVariable("b"));

        BasicBlock basicBlock = null;
        for (Statement trueStatement : ((StatementBlock) statementIf.getThenBlock()).getStatements()) {
            basicBlock = new BasicBlock("1| " + trueStatement, trueStatement, conditions);
        }

        Assert.assertEquals(set1, TaintAnalysis.transfer(set, basicBlock));
    }

    @Test
    public void testTransfer7() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("b"),
                new ExpressionVariable("a")));

        Assert.assertEquals(set, TaintAnalysis.transfer(set1, basicBlock));
    }

    @Test
    public void testTransfer8() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("a"),
                new ExpressionBinary(new ExpressionConstantInt(1), "+", new ExpressionConfigurationConstant("C"))));

        Assert.assertEquals(set, TaintAnalysis.transfer(new HashSet<>(), basicBlock));
    }

    @Test
    public void testTransfer9() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("a"),
                new ExpressionBinary(new ExpressionVariable("b"), "+", new ExpressionConfigurationConstant("C"))));

        Assert.assertEquals(set, TaintAnalysis.transfer(new HashSet<>(), basicBlock));
    }

    @Test
    public void testTransfer10() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("d"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("d"),
                new ExpressionBinary(new ExpressionVariable("b"), "+", new ExpressionVariable("a"))));

        Assert.assertEquals(set, TaintAnalysis.transfer(set1, basicBlock));
    }

    @Test
    public void testTransfer11() {
        Set<ExpressionConfigurationConstant> configurations = new HashSet<>();
        configurations.add(new ExpressionConfigurationConstant("C"));

        Set<TaintAnalysis.PossibleTaint> set = new HashSet<>();
        set.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));

        Set<TaintAnalysis.PossibleTaint> set1 = new HashSet<>();
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("a"), configurations));
        set1.add(new TaintAnalysis.PossibleTaint(new ExpressionVariable("b"), configurations));

        BasicBlock basicBlock = new BasicBlock(new StatementAssignment(new ExpressionVariable("b"),
                new ExpressionBinary(new ExpressionVariable("x"), "+", new ExpressionConstantInt(2))));

        Assert.assertEquals(set, TaintAnalysis.transfer(set1, basicBlock));
    }

}