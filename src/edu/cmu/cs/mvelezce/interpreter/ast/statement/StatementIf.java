package edu.cmu.cs.mvelezce.interpreter.ast.statement;

import edu.cmu.cs.mvelezce.interpreter.ast.expression.Expression;
import edu.cmu.cs.mvelezce.interpreter.visitor.Visitor;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by mvelezce on 2/1/17.
 */
public class StatementIf extends Statement {
    private Expression condition;
    private Statement statementThen;

    public StatementIf(Expression condition, Statement statementThen) {
        this.condition = condition;
        this.statementThen = statementThen;
    }

    @Override
    public <T> void accept(Visitor<T> visitor) {
        visitor.visitStatementIf(this);
    }

    public Expression getCondition() { return this.condition; }

    public Statement getStatementThen() { return this.statementThen; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatementIf that = (StatementIf) o;

        if (!condition.equals(that.condition)) return false;
        return statementThen.equals(that.statementThen);
    }

    @Override
    public int hashCode() {
        int result = condition.hashCode();
        result = 31 * result + statementThen.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result = "if(" + this.condition +") {\n";
        String[] statements = StringUtils.split(this.statementThen.toString(), '\n');

        for(String statement : statements) {
            result += "    " + statement + "\n";
        }

        result += "}";

        return result;
    }
}
