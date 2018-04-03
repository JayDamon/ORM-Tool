package libraries.orm.crud;

import libraries.orm.orm.Crudable;

public abstract class DatabaseManagement<C extends Crudable> {

    protected Class<C> crudable;

    public DatabaseManagement(Class<C> crudable) {
        this.crudable = crudable;
    }

    public abstract boolean dropTable(C crudable);

    public abstract boolean tableCreated(C crudable);

}
