import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Main {

//    public static String convertStringToBinary(String input) {
//
//        StringBuilder result = new StringBuilder();
//        char[] chars = input.toCharArray();
//        for (char aChar : chars) {
//            result.append(
//                    Integer.toBinaryString(aChar)); // char -> int, auto-cast
//                    // zero pads
//        }
//        return result.toString();
//    }

    static int[] findIndex(String str)
    {
        String s = "20";
        int[] indices = new int[str.length()/2];
        int j =0;
        boolean flag = false;
        for (int i = 0; i < str.length() - s.length() + 1; i++) {
            if (str.substring(i, i + s.length()).equals(s)) {
                System.out.print(i + " ");
                indices[j++]=i;
                flag = true;
            }
        }

        if (flag == false) {
            return null;
        }
        return indices;
    }

    //public static void main(String args[])
    {
        int textSize = 0;
        ArrayList<BigInteger> ciphers = new ArrayList<>();
        try
        {
            File file=new File("src\\ciphers.txt");    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line);      //appends line to string buffer
                sb.append("\n");     //line feed
                textSize = line.length();
                System.out.println(textSize);
                StringBuilder output = new StringBuilder("");
                try{
//                    for (int i = 0; i < line.length(); i += 2) {
//                        String str = line.substring(i, i + 2);
//                        output.append((char) Integer.parseInt(str, 16));
//                        System.out.print(output);
                        //ciphers.add(new BigInteger(line, 16));
                    //}
                    ciphers.add(new BigInteger(line,16));
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
            }
            fr.close();    //closes the stream and release the resources
            System.out.println("Contents of File: ");
            System.out.println(sb.toString());   //returns a string that textually represents the object
            System.out.println(ciphers);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        int n = ciphers.size();
        int spaceLength = (String.valueOf(ciphers.get(0)).length());
        //System.out.print(spaceLength);
        StringBuilder spaces = new StringBuilder();
        String[][] outputXOR = new String[8][8];
        //ArrayList<Integer> indices = new ArrayList<>();
        Hashtable<Integer, Integer> ht2 = new Hashtable<>();

        for(int i=0; i < 58 ; i++){
            spaces.append(new BigInteger("20",16));
            //spaces.append(Integer.parseInt("20",16));
        }

        System.out.print(spaces);
        BigInteger bg = new BigInteger(spaces.toString());
        System.out.print(bg.toString(16));
        BigInteger res1[][] = new BigInteger[8][8];
        BigInteger res2[][] = new BigInteger[8][8];
//      Integer res1[][] = new Integer[n][n];
//      Integer res2[][] = new Integer[n][n];
        for(int i =0;i<n;i++){
            for(int j=0;j<n;j++){
//                res1[i][j] = ciphers.get(i)^(ciphers.get(j));
//                fdres2[i][j] = res1[i][j]^(Integer.valueOf(spaces.toString()));
//              outputXOR[i][j]=Integer.toHexString(res2[i][j]);
                res1[i][j] = ciphers.get(i).xor(ciphers.get(j));
                res2[i][j] = res1[i][j].xor(new BigInteger
                        (spaces.toString()));
                //Integer val = res2[i][j].intValue();
                outputXOR[i][j]=res2[i][j].toString(16);
                //outputXOR[i][j]=val.toHexString(val);
                System.out.print("ROW = "+ i + " COLUMN = " + j + " RESULT = \n" +
                        outputXOR[i][j]+ "\n");
                System.out.print("---------------------------------------" +
                        "------------------------------------\n");
                char[] arr =outputXOR[i][j].toCharArray();
                System.out.print(arr.length);
                for(int k=0;k<116;k+=2)
                {
                    if((arr[k]=='2'&& arr[k+1]=='0')||
                            arr[k]=='6'||
                            (arr[k]=='7'&& ('0'<=arr[k+1]&&arr[k+1]<='a')))
                    {
                        if(ht2.containsKey(k))
                        {
                            ht2.replace(k,ht2.get(k),ht2.get(k)+1);
                        }
                        else{
                            ht2.put(k,1);
                        }
                    }
                }
                //outputXOR[i][j] = String.format("%02X", outputXOR[i][j]);
//                StringBuilder sb = new StringBuilder(outputXOR[i][j]);
//                for (int k=0; k<sb.length(); k+=3)
//                    sb.insert(k, ' ');
//                outputXOR[i][j]=sb.toString();
            }
            Enumeration<Integer> e = ht2.keys();
            while (e.hasMoreElements()) {
                // Getting the key of a particular entry
                int key = e.nextElement();
                if(ht2.get(key)==n-1){
                    System.out.print("\nPossible Space Indices for ciphertext "
                            + i +" is "+ key/2 + "\n");
                }
            }
            ht2.clear();
        }
        //BigInteger spaces
        System.out.print(spaces);
//        for(int i =0;i<n;i++){
//            for(int j=0;j<n;j++){
//                res2[i][j] = res1[i][j].xor(new BigInteger
//                        (spaces.toString()));
//            }
//        }

//        for(int i =0;i<n;i++){
//            for(int j=0;j<n;j++){
//                outputXOR[i][j]=res2[i][j].toString(16);
//                System.out.print("ROW = "+ i + " COLUMN = " + j + " RESULT = \n" +
//                        outputXOR[i][j]+ "\n");
//                System.out.print("---------------------------------------" +
//                        "------------------------------------\n");
//                char[] arr =outputXOR[i][j].toCharArray();
//                for(int k =0;k<outputXOR[i][j].length();k+=2)
//                {
//                    if((arr[k]=='2'&& arr[k+1]=='0')||
//                            arr[k]=='6'||
//                            (arr[k]=='7'&& ('0'<=arr[k+1]&&arr[k+1]<='a')))
//                    {
//                        if(ht2.containsKey(k))
//                        {
//                            ht2.replace(k,ht2.get(k),ht2.get(k)+1);
//                        }
//                        else{
//                            ht2.put(k,1);
//                        }
//                    }
//                }
//                //outputXOR[i][j] = String.format("%02X", outputXOR[i][j]);
////                StringBuilder sb = new StringBuilder(outputXOR[i][j]);
////                for (int k=0; k<sb.length(); k+=3)
////                    sb.insert(k, ' ');
////                outputXOR[i][j]=sb.toString();
//            }
//            Enumeration<Integer> e = ht2.keys();
//            while (e.hasMoreElements()) {
//                // Getting the key of a particular entry
//                int key = e.nextElement();
//                if(ht2.get(key)==n-1){
//                    System.out.print("\nPossible Space Indices for ciphertext "
//                            + i/2 +" is "+ key + "\n");
//                }
//            }
//            ht2.clear();
//        }
       BigInteger a = new BigInteger("8af",16);
       System.out.print(a.toString(16));
    }
}
