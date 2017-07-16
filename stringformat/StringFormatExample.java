public class StringFormatExample {
    public static void main(String[] args) {
        System.out.println(formatDoubleNumber(1234));
        System.out.println(formatDoubleNumber(1234d));
        System.out.println(formatDoubleNumber(null));
    }

    private static String formatDoubleNumber(Object argument) {
        Double doubleValue = null;
        if (argument instanceof Number) {
            doubleValue = ((Number) argument).doubleValue();
        }
        return String.format("Number: %.4f", doubleValue);
    }
}
