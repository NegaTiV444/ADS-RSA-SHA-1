package logic;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class FileManager {

    public static int[] ReadByteArrAndConvertToIntArr(File InputFile)
    {
        int len = (int)InputFile.length();
        byte[] ByteArr = new byte[len];
        int[] res = new int[len];
        try(FileInputStream fin = new FileInputStream(InputFile))
        {
            System.out.printf("File size: %d bytes \n", fin.available());

            ByteArr = fin.readAllBytes();
            for (int i = 0; i < len; i++)
                res[i] = ByteArr[i];
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static byte[] ReadTxtToIntArrWithSHA1Signature(File InputFile)
    {
        try {
            FileInputStream reader = new FileInputStream(InputFile);
            byte[] temp = reader.readAllBytes();
            byte[] temp1 = new byte[temp.length - 41];
            int len = temp1.length;
            for (int i = 0; i < len; i++)
                temp1[i] = temp[i];
            return temp1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] ReadTxtToIntArrWithSignature(File InputFile)
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), "UTF-8"));
            ArrayList<String> arrS = new ArrayList<String>();
            String s;
            while ((s = reader.readLine()) != null)
            {
                arrS.add(s);
            }
                String[] temp1 = arrS.get(arrS.size() - 1).split(" ");
                int sign = 0;
                    sign = Integer.valueOf(temp1[temp1.length - 1]);
                String[] newTemp = new String[temp1.length - 1];
                for (int i = 0; i < newTemp.length; i++)
                    newTemp[i] = temp1[i];
                StringBuilder tempStr = new StringBuilder();
                for (int i = 0; i < newTemp.length; i++)
                    tempStr.append(newTemp[i]);
                arrS.remove(arrS.size() - 1);
                arrS.add(tempStr.toString());
                ArrayList<Integer> arrI = new ArrayList<Integer>();
                for (int i = 0; i < arrS.size(); i++) {
                    String temp = arrS.get(i);
                    int len = temp.length();
                    for (int j = 0; j < len; j++)
                        arrI.add((int) temp.charAt(j));
                }
                int[] res;
                    res = new int[arrI.size() + 1];
                int len = arrI.size();
                for (int i = 0; i < len; i++)
                    res[i] = arrI.get(i);
                    res[res.length - 1] = sign;
                return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] ReadTxtToIntArr(File InputFile)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), "UTF-8"));
            ArrayList<String> arrS = new ArrayList<String>();
            String s;
            while ((s = reader.readLine()) != null)
            {
                arrS.add(s);
            }
            ArrayList<Integer> arrI = new ArrayList<Integer>();
            for (int i = 0; i < arrS.size(); i++)
            {
                String temp = arrS.get(i);
                int len = temp.length();
                for (int j = 0; j < len; j++)
                    arrI.add((int)temp.charAt(j));
            }
            int[] res = new int[arrI.size()];
            int len = arrI.size();
            for (int i = 0; i < len; i++)
                res[i] = arrI.get(i);
            return res;

        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static int[] ReadIntArr(File InputFile)
    {
        int len = (int)InputFile.length()/4;
        int res[] = new int[len];
        try(FileInputStream fin = new FileInputStream(InputFile))
        {
            System.out.printf("File size: %d bytes \n", fin.available());
            for (int i = 0; i < len; i++)
                res[i] = fin.read();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        return res;
    }

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

    public static void SaveIntArrConvertToChar(int[] data, File OutputFile, BigInteger additional)
    {
        try(FileWriter writer = new FileWriter(OutputFile, false))
        {
            int len = data.length;
            for (int i = 0; i < len; i++)
            {
                writer.write((char)data[i]);
            }
            writer.write(additional.toString());
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void SaveIntArrConvertToChar(int[] data, File OutputFile, int additional)
    {
        try(FileWriter writer = new FileWriter(OutputFile, false))
        {
            int len = data.length;
            for (int i = 0; i < len; i++)
            {
                writer.write((char)data[i]);
            }
            writer.write(" " + Integer.toString(additional));
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void SaveIntArrConvertToString(int[] data, File OutputFile)
    {
        try(FileWriter writer = new FileWriter(OutputFile, false))
        {
            int len = data.length;
            writer.write(Integer.toString(data[0]));
            for (int i = 1; i < len; i++)
            {
                writer.write( " " + Integer.toString(data[i]));
            }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void SaveIntArr(int[] data, File OutputFile)
    {
        try(FileWriter writer = new FileWriter(OutputFile, false))
        {
            int len = data.length;
            for (int i = 0; i < len; i++)
            {
                writer.write(data[i]);
            }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static int[] ReadTxtAndConvertToIntArr(File OutputFile)
    {
        int[] res = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(OutputFile)));
            String str = reader.readLine();
            String [] strArr = str.split(" ");
            int[] result = new int[strArr.length];

            for (int i = 0; i < strArr.length; i++)//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                result[i] = Integer.parseInt(strArr[i]);
            return result;
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void SaveByteArr(byte[] data, File OutputFile)
    {
        try(FileOutputStream fout = new FileOutputStream(OutputFile))
        {
            int len = data.length;
                fout.write(data);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


}
