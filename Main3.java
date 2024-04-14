import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Main3 {
    //public static void main(String args[])
    {

        //creating an array to store ciphertexts
        ArrayList<BigInteger> ciphers = new ArrayList<>();
        //variable for storing length of ciphertext for later usage
        int textSize = 0;

        //reading ciphertexts from file
        try
        {
            File file=new File("src\\ciphers.txt");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            String line;
            //read all file lines till reaching null
            while((line=br.readLine())!=null)
            {
                textSize = line.length();
                try{
                    //storing each hexadecimal ciphertext into a BigInteger with radix 16
                    ciphers.add(new BigInteger(line,16));
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
            }
            fr.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //creating the spaces hexadecimal string
        StringBuilder spaces = new StringBuilder();
        for(int i=0;i<textSize/2;i++)
        {
            spaces.append("20");
        }
        //converting the hexadecimal string to bigint
        BigInteger spacesInt = new BigInteger(spaces.toString(),16);

        //2d array to store the output values of the xor in
        int n = ciphers.size();
        BigInteger resultInt[][] = new BigInteger[n][n];
        String[][] resultHex = new String[n][n];
        //hashtable to store the frequency of occurrence of possible spaces in each ciphertext
        Hashtable<Integer, Integer> spacePositions = new Hashtable<>();
        //enum for looping through hashtable
        Enumeration<Integer> e = spacePositions.keys();

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //xoring each cipher with the other ciphers
                resultInt[i][j] = ciphers.get(i).xor(ciphers.get(j));
                //xoring the result with the spaces bigint
                resultInt[i][j] = resultInt[i][j].xor(spacesInt);
                //convert the xor result with spaces into hexadecimal format
                resultHex[i][j] = resultInt[i][j].toString(16);
                //convert the hexadecimal format result into char array to loop through
                //and check the possible space positions
                char[] arr =resultHex[i][j].toCharArray();
                for(int k=0;k<textSize;k+=2)
                {
                    if((arr[k]=='2'&& arr[k+1]=='0')||
                            arr[k]=='6'||
                            (arr[k]=='7'&& ('0'<=arr[k+1]&&arr[k+1]<='a')))
                    {
                        if(spacePositions.containsKey(k))
                        {
                            spacePositions.replace(k,spacePositions.get(k),spacePositions.get(k)+1);
                        }
                        else{
                            spacePositions.put(k,1);
                        }
                    }
                }
            }
            while (e.hasMoreElements()) {
                int key = e.nextElement();
                if(spacePositions.get(key)==8){
                    System.out.print("\nPossible Space Indices for ciphertext "
                            + i +" is "+ key/2 + "\n");
                }
            }
            spacePositions.clear();
        }

        //guessing a plaintext randomly using the 2d array and the hashtable result
        BigInteger guessedPlainText = new BigInteger("6C6561726E696E6720686F7720746F2077726974652073656375726520736F6674776172652069732061206E656365737361727920736B696C6C",16);
        BigInteger key = guessedPlainText.xor(ciphers.get(1));
        BigInteger[] plainTexts = new BigInteger[n];
        for(int i=0;i<n;i++){
            plainTexts[i] = ciphers.get(i).xor(key);
            System.out.print("PLAINTEXT NUMBER "+ (i+1) +" IN HEX =  " + plainTexts[i].toString(16)+"\n");
        }

    }
}
