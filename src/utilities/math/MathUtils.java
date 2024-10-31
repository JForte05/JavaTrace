package utilities.math;

public class MathUtils {
    /**
     * Calculates the greatest common denominator between 2 non negative integers.
     * <P>NOTE: If {@code a = 0}, this method will return 0, but if {@code b = 0} this method will return {@code a}.
     * @param a First number
     * @param b Second number
     * @return The greatest common denominator
     * @throws IllegalArgumentException If either of the arguments are negative.
     */
    public static int greatestCommonDenominator(int a, int b) throws IllegalArgumentException{
        if (a < 0 || b < 0){
            throw new IllegalArgumentException(String.format("Can only calculate gcd for positive numbers. Recieved %d, %d", a, b));
        }

        int temp;
        while(b != 0){
            temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}
