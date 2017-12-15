package libraries.orm.crud;

import libraries.orm.orm.Crudable;

import java.util.List;

public abstract class CrudRepository<D, R extends Result> {

    protected D dataSource;

    public CrudRepository(D dataSource) {
        this.dataSource = dataSource;
    }

    public abstract List<R> findAll();

    public abstract List<R> findAll(List<Condition> conditions);

    public abstract List<R> findAll(String... columnNames);

    public abstract List<R> findAll(List<Condition> conditions, String... columnNames);

}
