/**
 * Disclaimer: 
 * This code is for illustration purposes.
 * Do not use in real-world deployments.
 */

public class AESDemo {

    private static class Sender {
        private byte[] secretKey;
        private String secretMessage = "Top secret!";

        public Sender(byte[] secretKey) {
            this.secretKey = secretKey;
        }

        // This will return both iv and ciphertext
        public byte[] encrypt() {
            return AESDemo.encrypt(secretKey, secretMessage);
        }
    }

    private static class Receiver {
        private byte[] secretKey;

        public Receiver(byte[] secretKey) {
            this.secretKey = secretKey;
        }

        // Padding Oracle (Notice the return type)
        public boolean isDecryptionSuccessful(byte[] ciphertext) {
            return AESDemo.decrypt(secretKey, ciphertext) != null;
        }
    }

    public static class Adversary {

        // This is where you are going to develop the attack
        // Assume you cannot access the key.
        // You shall not add any methods to the Receiver class.
        // You only have access to the receiver's "isDecryptionSuccessful" only.
        public String extractSecretMessage(Receiver receiver, byte[] ciphertext) {

            byte[] iv = AESDemo.extractIV(ciphertext);
            byte[] ciphertextBlocks = AESDemo.extractCiphertextBlocks(ciphertext);
            boolean result = receiver.isDecryptionSuccessful(AESDemo.prepareCiphertext(iv, ciphertextBlocks));
            System.out.println(result); // This is true initially, as the ciphertext was not altered in any way.

            // TODO: WRITE THE ATTACK HERE.

            int padLength;
            byte temp;
            //getting length of ciphertext by changing value of each byte
            for(int i = 0; i < iv.length; i++){
                temp = iv[i];
                iv[i] ^= 0x10;
                result = receiver.isDecryptionSuccessful(AESDemo.prepareCiphertext(iv, ciphertextBlocks));
                iv[i] = temp;
                if(!result){
                    padLength = iv.length - i;
                    break;
                }
            }

            byte[] plainText = new byte[iv.length];
            byte[] decryptFunc = new byte[iv.length];
            int count = iv.length - padLength;

            //adding the padding values to the plaintext
            while(count < iv.length){
                plainText[count] = (byte) padLength;
                decryptFunc[count] = (byte) (plainText[count]^iv[count]);
                count++;
            }

            int index = 1;
            byte[] new_iv = new byte[iv.length];
            //creating the rest of plaintext
            for(int i = iv.length - padLength - 1; i >= 0 ; i--){
                new_iv = iv;
                byte currentVal = (byte) (padLength+index);
                for(int j = i + 1; j < iv.length ; j++){
                    new_iv[j] = (byte)( decryptFunc[j] ^ currentVal);
                }
                result = receiver.isDecryptionSuccessful(AESDemo.prepareCiphertext(new_iv,ciphertextBlocks);
                if(!result)){
                    //brute force through this byte from values 0 till FF hex till decryption success
                    new_iv[i] = (byte) 255;
                    while (!receiver.isDecryptionSuccessful(AESDemo.prepareCiphertext(new_iv,ciphertextBlocks)
                    && new_iv[i] >= 0){
                        new_iv[i] = (byte) (new_iv[i] - 1);
                    }
                    decryptFunc[i] = (byte) new_iv[i]^currentVal;
                    plainText[i] = (byte) decryptFunc[i]^iv[i];
                }
                else {
                    plainText[i] = currentVal;
                    decryptFunc[i] = (byte) (plainText[i] ^ iv[i]);
                }
                index++;
            }
            return plainText;
        }
    }

    public static void main(String[] args) {

        byte[] secretKey = AESDemo.keyGen();
        Sender sender = new Sender(secretKey);
        Receiver receiver = new Receiver(secretKey);

        // The adversary does not have the key
        Adversary adversary = new Adversary();

        // Now, let's get some valid encryption from the sender
        byte[] ciphertext = sender.encrypt();

        // The adversary  got the encrypted message from the network.
        // The adversary's goal is to extract the message without knowing the key.
        String message = adversary.extractSecretMessage(receiver, ciphertext);

        System.out.println("Extracted message = " + message);
    }
}