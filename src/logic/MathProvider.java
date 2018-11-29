package logic;

import java.math.BigInteger;
import java.util.Random;

public class MathProvider {

    //long

    public static long  mul(long  a,  long b,  long m){
        if(b==1)
            return a;
        if(b%2==0){
            long t = mul(a, b/2, m);
            return (2 * t) % m;
        }
        return (mul(a, b-1, m) + a) % m;
    }



    public static long  pows( long a,  long b,  long m){
        if(b==0)
            return 1;
        if(b%2==0){
            long t = pows(a, b/2, m);
            return mul(t , t, m) % m;
        }
        return ( mul(pows(a, b-1, m) , a, m)) % m;
    }

    public static long gcd(long a, long b){
        if(b==0)
            return a;
        return gcd(b, a%b);
    }

    public static boolean isPrime(long x){ //Ferma's test
        if(x == 2)
            return true;
        Random rand = new Random();
        for(int i=0;i<100;i++){
            long a = (rand.nextInt() % (x - 2)) + 2;
            if (gcd(a, x) != 1)
                return false;
            if( pows(a, x-1, x) != 1)
                return false;
        }
        return true;
    }

    //BigInteger

    public static BigInteger pows(BigInteger a, BigInteger b, BigInteger m){
        if(b.compareTo(BigInteger.valueOf(0)) == 0)
            return BigInteger.ONE;
        if(b.mod(BigInteger.valueOf(2)) == BigInteger.ZERO){
            BigInteger t = pows(a, b.divide(BigInteger.valueOf(2)), m);
            return mul(t , t, m).mod(m);
        }
        return (mul(pows(a, b.subtract(BigInteger.valueOf(1)), m) , a, m)).mod(m);
    }

    public static BigInteger mul(BigInteger  a,  BigInteger b,  BigInteger m){
        if(b.compareTo(BigInteger.valueOf(1)) == 0)
            return a;
        if(b.mod(BigInteger.valueOf(2)) == BigInteger.ZERO){
            BigInteger t = mul(a, b.divide(BigInteger.valueOf(2)), m);
            return (t.multiply(BigInteger.valueOf(2))).mod(m);
        }
        return (mul(a, b.subtract(BigInteger.valueOf(1)), m).add(a)).mod(m);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b){
        if(b.compareTo(BigInteger.valueOf(0)) == 0)
            return a;
        return gcd(b, a.mod(b));
    }

    public static boolean isPrime(BigInteger x){ //Ferma's test
        if(x.compareTo(BigInteger.valueOf(2)) == 0)
            return true;
        Random rand = new Random();
        for(int i = 0;i < 100;i++){
            BigInteger a = (BigInteger.valueOf(rand.nextInt()).mod(x.subtract(BigInteger.valueOf(2)))).add(BigInteger.valueOf(2));
            if (gcd(a, x).compareTo(BigInteger.valueOf(1)) != 0)
                return false;
            if  (pow(a, x.subtract(BigInteger.valueOf(1)), x).compareTo(BigInteger.valueOf(1)) != 0)
                return false;
        }
        return true;
    }

    //Test BigInteger

    public static BigInteger pow(BigInteger x, BigInteger n, BigInteger mod)
    {
        BigInteger res = BigInteger.ONE;
        for (BigInteger p = x; n.compareTo(BigInteger.ZERO) > 0; n = n.shiftRight(1), p = p.multiply(p).mod(mod))
            if ((n.and(BigInteger.ONE).compareTo(BigInteger.ZERO)) != 0)
                res = res.multiply(p).mod(mod);
        return res;
    }

    public static triplBig gcdWide(BigInteger a, BigInteger b)
    {
        triplBig temphere = new triplBig(a,BigInteger.ONE,BigInteger.ZERO);
        triplBig temphere2 = new triplBig();

        if(b == BigInteger.ZERO)
        {
            return temphere;
        }

        temphere2 = gcdWide(b, a.mod(b));
        temphere = new triplBig();

        temphere.d=  temphere2.d;
        temphere.x = temphere2.y;
        temphere.y = temphere2.x.subtract(a.divide(b).multiply(temphere2.y));

        return temphere;
    }

}
