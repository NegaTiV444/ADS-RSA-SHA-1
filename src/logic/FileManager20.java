package logic;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager20 {

    //Standard functions

    public static byte[] ReadByteArr(File InputFile)
    {
        int len = (int)InputFile.length();
        byte[] res = new byte[len];
        try(FileInputStream fin = new FileInputStream(InputFile))
        {
            System.out.printf("File size: %d bytes \n", fin.available());
            res = fin.readAllBytes();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void SaveByteArr(byte[] data, File OutputFile)
    {
        try(FileOutputStream fout = new FileOutputStream(OutputFile))
        {
            fout.write(data);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    //Work with signature

    public static byte[] ReadByteArrWithSignature(File InputFile)
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), "UTF-8"));
            ArrayList<String> arrS = new ArrayList<String>();
            ArrayList<Byte> arrB = new ArrayList<Byte>();
            String s, sPrev;
            byte[] temp;
            int len = 0, lastlen = 0, tempLen;
            s = reader.readLine();
            sPrev = s;
            while ((s = reader.readLine()) != null) {
                arrS.add(sPrev);
                lastlen = sPrev.getBytes().length;
                len += lastlen;
                temp = sPrev.getBytes();
                tempLen = temp.length;
                for (int i = 0; i < tempLen; i++)
                    arrB.add(temp[i]);
                sPrev = s;
            }
            tempLen = arrB.size();
            byte[] res = new byte[tempLen];
            for (int i = 0; i < tempLen; i++)
                res[i] = arrB.get(i);
            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger ReadSignature(File InputFile)
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), "UTF-8"));
            ArrayList<String> arrS = new ArrayList<String>();
            ArrayList<Byte> arrB = new ArrayList<Byte>();
            String s, sPrev;
            byte[] temp;
            int len = 0, lastlen = 0, tempLen;
            s = reader.readLine();
            sPrev = s;
            while ((s = reader.readLine()) != null) {
                sPrev = s;
            }
            BigInteger res = new BigInteger(sPrev);
            return res;
        } catch (IOException e) {
            System.out.println("Что то пошло не так (с) ReadSignature()");
            e.printStackTrace();
        }
        return null;
    }

    public static void AppendStr(String strData, File OutputFile) //To append signature to file
    {
        try(FileWriter fout = new FileWriter(OutputFile, true))
        {
            fout.append("\n");
            fout.append(strData);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
