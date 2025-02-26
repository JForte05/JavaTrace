package utilities.math;

import java.util.Random;

public class MathUtils {
    private static Random random = new Random();

    static{
        random.nextDouble();
    }

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

    public static double randDouble(){
        return random.nextDouble();
    }
    public static double randDouble(double min, double max){
        return random.nextDouble(max - min) + min;
    }

    public static double facos(double x){
        // https://developer.download.nvidia.com/cg/acos.html
        double negate = x < 0.0 ? 1.0 : 0.0;
        x = Math.abs(x);
        double ret = -0.0187293;
        ret = ret * x;
        ret = ret + 0.0742610;
        ret = ret * x;
        ret = ret - 0.2121144;
        ret = ret * x;
        ret = ret + 1.5707288;
        ret = ret * Math.sqrt(1.0-x);
        ret = ret - 2 * negate * ret;
        return negate * 3.14159265358979 + ret;
    }
}
