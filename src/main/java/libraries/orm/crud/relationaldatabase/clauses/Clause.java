package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.Condition;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Clause {

    private String clause;
    private ArrayList<Condition> conditions;

    public Clause(ArrayList<Condition> conditions) {
        this.conditions = conditions;
        this.clause = writeClause(conditions);
    }

    protected abstract String writeClause(ArrayList<Condition> conditions);

    public String toString() {
        return this.clause;
    }

    public ArrayList<Condition> getConditions() {
        return this.conditions;
    }
}
