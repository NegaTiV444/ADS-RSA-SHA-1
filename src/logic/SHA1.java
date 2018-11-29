package logic;

import java.math.BigInteger;

import static java.lang.Math.abs;

public class SHA1 {

    static int k1 = 0x5A827999;
    static int k2 = 0x6ED9EBA1;
    static int k3 = 0x8F1BBCDC;
    static int k4 = 0xCA62C1D6;


    private static byte[] init(byte[] msg) {
        int len = msg.length;
        int t = len / 64;
        long llen = len * 8;
        int delta = (t + 1) * 64 - len;
        if (delta > 8) {
            byte[] data = new byte[(t + 1) * 64];
            for (int i = 0; i < len; i++)
                data[i] = msg[i];
            data[len] |= 0x80;
            for (int i = len + delta - 8, j = 7; i < len + delta; i++, j--)
                data[i] = (byte) (llen >> (j * 8));
            return data;
        } else {
            byte[] data = new byte[(t + 2) * 64];
            for (int i = 0; i < len; i++)
                data[i] = msg[i];
            data[len] = (byte)(1 << 7);
            for (int i = len + delta + 64 - 8, j = 7; i < len + delta + 64; i++, j--)
                data[i] = (byte) (llen >> (j * 8));
            return data;
        }
    }

    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    private static int[] getBlock(byte[] data) {
        int[] block = new int[80];
        int temp;

        for (int i = 0; i < 16; i++){
                temp = abs(data[i * 4]);
                block[i] = temp << 24;
                temp = abs(data[i * 4 + 1]);
                block[i] += temp << 16; //TODO Fix (done)
                temp = abs(data[i * 4 + 2]);
                block[i] += temp << 8;
                temp = abs(data[i * 4 + 3]);
                block[i] += temp;
            }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(Integer.toBinaryString(block[0]));
        System.out.println(Integer.toBinaryString(block[1]));

        for (int i = 16; i < 80; i++)
            block[i] = rol(block[i - 3] ^ block[i - 8] ^ block[i - 14] ^ block[i - 16], 1);
        return block;
    }

    public static BigInteger getHash(byte[] msg) {
        int h0 = 0x67452301;
        int h1 = 0xEFCDAB89;
        int h2 = 0x98BADCFE;
        int h3 = 0x10325476;
        int h4 = 0xC3D2E1F0;
        byte[] data = init(msg);
        byte[] temp = new byte[64];
        int[] block = new int[80];
        int a, b, c, d, e, f, k, m, t;
        int len = data.length / 64;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 64; j++)
                temp[j] = data[i * 64 + j];
            block = getBlock(temp);
            a = h0;
            b = h1;
            c = h2;
            d = h3;
            e = h4;
            for (int j = 0; j < 80; j++) {
                if (j < 20) {
                    f = (b & c) | ((~b) & d);
                    k = k1;
                } else if (j < 40) {
                    f = b ^ c ^ d;
                    k = k2;
                } else if (j < 60) {
                    f = ((b & c) | (b & d) | (c & d));
                    k = k3;
                } else {
                    f = b ^ c ^ d;
                    k = k4;
                }

               t = rol(a, 5) + f + e + k + block[j];
//                    t = rol(a, 5) + e + block[j] +
//                            ( (j < 20) ?  1518500249 + ((b & c) | ((~b) & d))
//                                    : (j < 40) ?  1859775393 + (b ^ c ^ d)
//                                    : (j < 60) ? -1894007588 + ((b & c) | (b & d) | (c & d))
//                                    : -899497514 + (b ^ c ^ d) );
                    e = d;
                    d = c;
                    c = rol(b, 30);
                    b = a;
                    a = t;
            }
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
        }
        StringBuilder tempH0 = new StringBuilder(Integer.toHexString(h0));
        StringBuilder tempH1 = new StringBuilder(Integer.toHexString(h1));
        StringBuilder tempH2 = new StringBuilder(Integer.toHexString(h2));
        StringBuilder tempH3 = new StringBuilder(Integer.toHexString(h3));
        StringBuilder tempH4 = new StringBuilder(Integer.toHexString(h4));

        StringBuilder strHash = tempH0.append(tempH1.append(tempH2).append(tempH3.append(tempH4)));

        BigInteger hash;

        hash = new BigInteger(strHash.toString(), 16);
        return hash;
    }

    public static String encodeHex(String str) {

        // Convert a string to a sequence of 16-word blocks, stored as an array.
        // Append padding bits and the length, as described in the SHA1 standard

        byte[] x = str.getBytes();
        int[] blks = new int[(((x.length + 8) >> 6) + 1) * 16];
        int i;

        for(i = 0; i < x.length; i++) {
            blks[i >> 2] |= x[i] << (24 - (i % 4) * 8);
        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[blks.length - 1] = x.length * 8;
        System.out.println(Integer.toBinaryString(blks[0]));
        System.out.println(Integer.toBinaryString(blks[1]));
        // calculate 160 bit SHA1 hash of the sequence of blocks

        int[] w = new int[80];

        int a =  1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d =  271733878;
        int e = -1009589776;
        int test = blks.length;
        for(i = 0; i < blks.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++) {
                w[j] = (j < 16) ? blks[i + j] :
                        ( rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1) );

                int t = rol(a, 5) + e + w[j] +
                        ( (j < 20) ?  1518500249 + ((b & c) | ((~b) & d))
                                : (j < 40) ?  1859775393 + (b ^ c ^ d)
                                : (j < 60) ? -1894007588 + ((b & c) | (b & d) | (c & d))
                                : -899497514 + (b ^ c ^ d) );
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
            }

            a = a + olda;
            b = b + oldb;
            c = c + oldc;
            d = d + oldd;
            e = e + olde;
        }

        // Convert 160 bit hash to base64


        int[] words = {a,b,c,d,e};
        StringBuilder sb = new StringBuilder();

        for(int word : words) {
            String hexWord = Integer.toHexString(word);
            //Because to hexstring apparently doesn't pad?
            while(hexWord.length() < 8) {
                hexWord = "0" + hexWord;
            }
            sb.append(hexWord);
        }

        return sb.toString();
    }

}
