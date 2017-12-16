package libraries.orm.crud.relationaldatabase;

import libraries.orm.annotations.ColumnName;
import libraries.orm.orm.Crudable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CrudableFactory {

    private static final Map<Class<?>, Class<?>> primitivesMap = getPrimativeTypesMap();

    public static <C extends Crudable> C getCrudable(Class<C> crudableClass, Map<String, Object> results) throws IllegalArgumentException {
        Field[] fields = crudableClass.getDeclaredFields();
        try {
            C crudable = crudableClass.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(ColumnName.class)) {
                    ColumnName columnName = f.getAnnotation(ColumnName.class);
                    String name = columnName.name().toUpperCase();
                    if (results.containsKey(name)) {
                        Class<?> fieldClass = results.get(name).getClass();
                        Class<?> resultType = fieldClass.isPrimitive() ? primitivesMap.get(fieldClass) : fieldClass;
                        Class<?> fieldType = f.getType().isPrimitive() ? primitivesMap.get(f.getType()) : f.getType();
                        if (resultType.equals(fieldType)) {
                            f.set(crudable, results.get(name));
                        }
                    }
                }
            }
            return crudable;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Could not instantiate class" + crudableClass.getName());
        }
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
