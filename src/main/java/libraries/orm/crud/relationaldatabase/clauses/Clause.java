package libraries.orm.crud.relationaldatabase.clauses;

public abstract class Clause {

    private String clause;

    public Clause(String[] conditionColumns) {
        this.clause = writeClause(conditionColumns);
    }

    protected abstract String writeClause(String... conditionColumns);

    public String toString() {
        return this.clause;
    }
}
