package libraries.orm.crud;

import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Crud<C extends Crudable, I> {

    protected Class<C> crudable;

    public Crud(Class<C> crudable) {
        this.crudable = crudable;
    }

    public abstract boolean create(C crudable);

    public abstract C findByID(I id);

    public abstract List<C> read();

    public abstract List<C> read(String... columnNames);

    public abstract List<C> read(ArrayList<Condition> conditions);

    public abstract List<C> read(ArrayList<Condition> conditions, String... columnNames);

    public abstract boolean update(C crudable);

    public abstract boolean update(C crudable, ArrayList<Condition> conditions);

    public abstract boolean delete(C crudable);

    public abstract boolean exists(C crudable, ArrayList<Condition> conditions);

    public abstract boolean exists(C crudable);

}
