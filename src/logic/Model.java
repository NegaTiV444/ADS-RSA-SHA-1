package logic;

import java.math.BigInteger;

import static logic.MathProvider.gcdWide;

public class Model {

    private static byte[] data;

    public static byte[] getData() {
        return data;
    }

    public static void setData(byte[] data) {
        Model.data = data;
    }

    public static int getHash(int[] data, int n)
    {
        int h = 100;
        int len = data.length;
        for (int i = 0; i < len; i++)
            h = ((h + data[i]) * (h + data[i])) % n;
        return h;
    }

//    public static boolean isPrime(BigInteger n)
//    {
//        if (n.equals(BigInteger.ONE))
//            return false;
//        else
//        {
//            BigInteger max = n.sqrt();
//            for (long i = 2; i < max.longValue(); i++)
//                if (n.mod(BigInteger.valueOf(i)).equals(BigInteger.ZERO))
//                    return false;
//                return true;
//        }
//
//    }

    public static boolean isPrime(int n)
    {
        if (n == 1)
            return false;
        else
        {
            double max = Math.sqrt(n);
            for (int i = 2; i < max; i++)
                if (n % i == 0)
                    return false;
            return true;
        }
    }



    public static BigInteger pow(BigInteger x, BigInteger n, BigInteger mod)
    {
        BigInteger res = BigInteger.ONE;
        for (BigInteger p = x; n.compareTo(BigInteger.ZERO) > 0; n = n.shiftRight(1), p = p.multiply(p).mod(mod))
            if ((n.and(BigInteger.ONE).compareTo(BigInteger.ZERO)) != 0)
                res = res.multiply(p).mod(mod);
        return res;
    }

    public static int pow(long x, int n, long mod) {
        int res = 1;
        for (long p = x; n > 0; n >>= 1, p = (p * p) % mod) {
            if ((n & 1) != 0) {
                res = (int) (res * p % mod);
            }
        }
        return res;
    }


    public static BigInteger getE(BigInteger f, BigInteger d)
    {
        triplBig res = gcdWide(f, d);
        if (res.y.compareTo(BigInteger.valueOf(0)) < 0)
            res.y = res.y.add(f);
        return res.y;
    }

}
