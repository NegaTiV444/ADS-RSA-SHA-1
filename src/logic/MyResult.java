package logic;

import java.math.BigInteger;

public class MyResult
{
    MyResult(BigInteger one, BigInteger two, BigInteger three)
    {
        d = one;
        x = two;
        y = three;
    }

    MyResult()
    {
        ;
    }

    public BigInteger d;
    public BigInteger x;
    public BigInteger y;
}
