package edu.cmu.cs.mvelezce.interpreter.ast.statement;

import edu.cmu.cs.mvelezce.interpreter.ast.expression.Expression;
import edu.cmu.cs.mvelezce.interpreter.visitor.Visitor;

/**
 * Created by mvelezce on 2/1/17.
 */
public class StatementSleep extends Statement {
    private Expression time;

    public StatementSleep(Expression time) {
        this.time = time;
    }

    @Override
    public <T> void accept(Visitor<T> visitor) {
        visitor.visitStatementSleep(this);
    }

    public Expression getTime() { return this.time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatementSleep that = (StatementSleep) o;

        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public String toString() { return "sleep(" + this.time + ")";
    }
}
