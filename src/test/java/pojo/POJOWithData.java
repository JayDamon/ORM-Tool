package pojo;

import java.sql.Date;
import java.util.Calendar;

public class POJOWithData {

    public static POJOWithAnnotations getPojoWithAnnotationsPrimary() {
        POJOWithAnnotations pojo = new POJOWithAnnotations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.NOVEMBER, 5);
        pojo.setId(1);
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(calendar.getTimeInMillis()));

        return pojo;
    }

}
