import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;

public class Main2 {

    public static void main(String args[])
    {
        BigInteger[] ciphers = new BigInteger[8];
        ciphers[0] = new BigInteger("68AF0BEF7F39982DA975B5E6D06947E61C22748C94A2155CFCCC464DEAFB6F4844DB2D7312ED192B6B7251580C61D5A296964E824A16648B16B9",16);
        ciphers[1] = new BigInteger("70A20FBD7E209324A979BFE2997A46E61B22749692EB1655FA995D46A9FA654F43C93F2114A21E3E227714580A6790B88BD74F9E09107D8B0EAC",16);
        ciphers[2] = new BigInteger("6FA20DBA622CDD28EC68F0F0C16D41A7023778C29EB8455EFC894B46EDA96C46459E2D2A1CEF1239707F571604618CEB9DD85E955013628B0DAE",16);
        ciphers[3] = new BigInteger("6FA20DBA6220893AA970A4B5CD664CE609286D8799B80010F68A0F56FAE868405BD72A2A51E118386E7214520E6994AC9D964E824A16648B16B9",16);
        ciphers[4] = new BigInteger("71A80AAA6227DD20FB68A0E1D6695BA71C3864C285AE1445F09E4A50A9EA6B5B52D82B3F51E3192922645D5100769ABE8B965C89480F6F910BB3",16);
        ciphers[5] = new BigInteger("7DA30ABD753A8E63FB70BEF1D66340BC0D24748D99EB065FEC804B03F9FB6F5F52D02A731CE31B24617F5B431C2496AA94DA1D865D17778109B3",16);
        ciphers[6] = new BigInteger("75B34EA66369932CFD31A0E7D86D5DAF0F3171C283A44542FC805603FAE6664C5BC77E3C1FA204346F7B51421D6D96EB9DD85E955013628B0DAE",16);
        ciphers[7] = new BigInteger("75E71DA771259163E774A6F0CB2E5BA3192378C283A30010EA8D4246A9F96B5A44C9312115A21823227B415A1B6D85A79D965C844A0C638C16B3",16);

        //creating the space string and bigint
        StringBuilder spaces = new StringBuilder("");
        for(int i=0;i<58;i++)
        {
            spaces.append("20");
        }
        BigInteger spacesInt = new BigInteger(spaces.toString(),16);


        BigInteger resultInt[][] = new BigInteger[8][8];
        String[][] resultHex = new String[8][8];
        Hashtable<Integer, Integer> spacesPositions = new Hashtable<>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                resultInt[i][j] = ciphers[i].xor(ciphers[j]); //xor ciphers with each others
                resultInt[i][j] = resultInt[i][j].xor(spacesInt); //xor output with space bigint
                resultHex[i][j] = resultInt[i][j].toString(16); //convert output bigint to hex string

                //convert string to char array for checking the bytes
                char[] arr =resultHex[i][j].toCharArray();

                for(int k=0;k<116;k+=2)
                {
                    if((arr[k]=='2'&& arr[k+1]=='0')||
                            arr[k]=='6'||
                            (arr[k]=='7'&& ('0'<=arr[k+1]&&arr[k+1]<='a')))
                    {
                        //update occurrences in hashtable next to its index
                        if(spacesPositions.containsKey(k))
                        {
                            spacesPositions.replace(k,spacesPositions.get(k),spacesPositions.get(k)+1);
                        }
                        else{
                            spacesPositions.put(k,1);
                        }
                    }
                }
            }
            Enumeration<Integer> e = spacesPositions.keys();
            while (e.hasMoreElements()) {
                int key = e.nextElement();
                //checking the spaces indices if equal to 7+1(redundant value of multiplication of cipher by itself)
                if(spacesPositions.get(key)==8){
                    System.out.print("\nPossible Space Indices for ciphertext "
                            + i +" is byte number: "+ key/2 + "\n");
                }
            }
            spacesPositions.clear();
            System.out.print("-------------------------------------" +
                    "-----------------------------------\n");
        }

        BigInteger guessedPlainText = new BigInteger("69207368616c6c206e65766572207265757365207468652073616d652070617373776f7264206f6e206d756c7469706c65206163636f756e7473",16);
        BigInteger key = guessedPlainText.xor(ciphers[7]);
        BigInteger[] plainTexts = new BigInteger[8];
        for(int i=0;i<8;i++){
            plainTexts[i] = ciphers[i].xor(key);
            System.out.print("PLAINTEXT NUMBER "+ (i+1) +" IN HEX :  " + plainTexts[i].toString(16)+"\n");
        }

        //the open design principle increases confidence in security
        //learning how to write secure software is a necessary skill
        //secure key exchange is needed for symmetric key encryption
        //security at the expense of usability could damage security
        //modern cryptography requires careful and rigorous analysis
        //address randomization could prevent malicious call attacks
        //it is not practical to rely solely on symmetric encryption
        //i shall never reuse the same password on multiple accounts

    }
}
