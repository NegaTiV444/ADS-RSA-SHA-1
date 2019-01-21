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

    public static BigInteger getE(BigInteger f, BigInteger d)
    {
        MyResult res = gcdWide(f, d);
        if (res.y.compareTo(BigInteger.valueOf(0)) < 0)
            res.y = res.y.add(f);
        return res.y;
    }

}
