package libraries.orm.crud;

import libraries.orm.orm.Crudable;

import java.lang.reflect.InvocationTargetException;
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

    public abstract List<Map<String, Object>> read();

    public abstract boolean update() throws InvocationTargetException, IllegalAccessException;

    public abstract boolean delete();

}
