package libraries.orm.crud;

import libraries.orm.crud.relationaldatabase.exceptions.QueryException;
import libraries.orm.orm.Crudable;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Crud<T> {

    protected Crudable crudable;
    protected T dataSource;

    public Crud(Crudable crudable, T dataSource) {
        this.crudable = crudable;
        this.dataSource = dataSource;
    }

    public abstract boolean create() throws InvocationTargetException, IllegalAccessException;

    public abstract List<ArrayList<Condition>> read();

    public abstract List<ArrayList<Condition>> read(ArrayList<Condition> conditions);

    public abstract List<ArrayList<Condition>> read(ArrayList<Condition> conditions, String... columnNames);

    public abstract boolean update() throws InvocationTargetException, IllegalAccessException;

    public abstract boolean update(ArrayList<Condition> conditions) throws InvocationTargetException, IllegalAccessException;

    public abstract boolean delete() throws InvocationTargetException, IllegalAccessException;

    public abstract boolean exists(ArrayList<Condition> conditions);

    public abstract boolean exists() throws InvocationTargetException, IllegalAccessException;


}
