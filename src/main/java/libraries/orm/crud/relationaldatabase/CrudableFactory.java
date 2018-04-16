package libraries.orm.crud.relationaldatabase;

import com.sun.org.apache.xpath.internal.operations.Bool;
import libraries.orm.annotations.Column;
import libraries.orm.orm.Crudable;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CrudableFactory {

    private static final Map<Class<?>, Class<?>> primitivesMap = getPrimativeTypesMap();
    private static final Map<Class<?>, Object> primitiveDefaults = getPrimitiveDefaults();

    public static <C extends Crudable> C getCrudable(Class<C> crudableClass, Map<String, Object> results) throws IllegalArgumentException {
        Field[] fields = crudableClass.getDeclaredFields();
        try {
            C crudable = crudableClass.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Column.class)) {
                    Column column = f.getAnnotation(Column.class);
                    String name = column.name().toUpperCase();
                    if (results.containsKey(name)) {
                        Class<?> fieldType = f.getType().isPrimitive() ? primitivesMap.get(f.getType()) : f.getType();
                        if (results.get(name) == null) {
                            results.put(name, primitiveDefaults.get(fieldType));
                        }
                        Class<?> fieldClass = results.get(name).getClass();
                        Class<?> resultType = fieldClass.isPrimitive() ? primitivesMap.get(fieldClass) : fieldClass;
                        if (resultType.equals(fieldType)) {
                            f.set(crudable, results.get(name));
                        }
                        if (resultType == java.sql.Date.class && fieldType == Calendar.class) {
                            Calendar cal = Calendar.getInstance();
                            java.sql.Date date = (java.sql.Date)results.get(name);
                            cal.setTime(
                                    (java.sql.Date)results.get(name)
                            );
                            f.set(crudable, cal);
                        }
                    }
                }
            }
            return crudable;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Could not instantiate class" + crudableClass.getName());
        }
    }

    private static Map<Class<?>, Object> getPrimitiveDefaults() {
        Map<Class<?>, Object> map = new HashMap<>();
        map.put(Boolean.class, false);
        map.put(Byte.class, 0);
        map.put(Short.class, 0);
        map.put(Integer.class, 0);
        map.put(Long.class, 0L);
        map.put(Float.class, 0F);
        map.put(Double.class, 0D);
        return map;
    }

    private static Map<Class<?>, Class<?>> getPrimativeTypesMap() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);

        return map;
    }
}
