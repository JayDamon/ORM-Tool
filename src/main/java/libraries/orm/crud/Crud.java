package libraries.orm.crud;

import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Crud<C extends Crudable, D> {

    protected D dataSource;
    protected Class<C> crudable;

    public Crud(Class<C> crudable, D dataSource) {
        this.crudable = crudable;
        this.dataSource = dataSource;
    }

    public abstract boolean create(C crudable) throws InvocationTargetException, IllegalAccessException;

    public abstract List<C> read();

    public abstract List<C> read(String... columnNames);

    public abstract List<C> read(ArrayList<Condition> conditions);

    public abstract List<C> read(ArrayList<Condition> conditions, String... columnNames);

    public abstract boolean update(C crudable) throws InvocationTargetException, IllegalAccessException;

    public abstract boolean update(C crudable, ArrayList<Condition> conditions) throws InvocationTargetException, IllegalAccessException;

    public abstract boolean delete(C crudable) throws InvocationTargetException, IllegalAccessException;

    public abstract boolean exists(C crudable, ArrayList<Condition> conditions);

    public abstract boolean exists(C crudable) throws InvocationTargetException, IllegalAccessException;

}
