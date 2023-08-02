package subway.util;

public class Check {

    private Check() {

    }

    public static boolean isNumeric(String input) {

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}