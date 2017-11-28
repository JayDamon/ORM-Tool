package libraries.orm.crud.relationaldatabase.clauses;

import java.util.LinkedHashMap;

public abstract class Clause {

    private String clause;
    private LinkedHashMap<String, Object> conditions;

    public Clause(LinkedHashMap<String, Object> conditions) {
        this.conditions = conditions;
        this.clause = writeClause(conditions);
    }

    protected abstract String writeClause(LinkedHashMap<String, Object> conditions);

    public String toString() {
        return this.clause;
    }

    public LinkedHashMap<String, Object> getConditions() {
        return this.conditions;
    }
}
