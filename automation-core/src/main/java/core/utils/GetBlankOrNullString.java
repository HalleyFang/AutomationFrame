package core.utils;

public class GetBlankOrNullString {

    public static String getNullString() {
        return "";
    }

    public static String getBlank() {
        return " ";
    }

    public static String getBlank(int num) {
        String str = " ";
        if (num > 1) {
            for (int i = 1; i < num; i++) {
                str += " ";
            }
        }
        return str;
    }

}
