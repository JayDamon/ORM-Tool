package libraries.orm.utility;

public class StringUtility {
    public static String capitalizeFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
