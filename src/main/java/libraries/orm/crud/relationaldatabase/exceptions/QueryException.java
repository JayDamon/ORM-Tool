package libraries.orm.crud.relationaldatabase.exceptions;

import java.util.ArrayList;

public class QueryException extends Exception {
    private ArrayList exceptions;

    public QueryException(ArrayList exceptions) {
        this.exceptions = exceptions;
    }

    public ArrayList getExceptions() {
        return exceptions;
    }
}
