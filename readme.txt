Mariam Mohamed Bahaa 6401
Steps for approach:
1. Saving the ciphers and creating a string for spaces (all hex 20) the size of the ciphers.
2. Using BigInteger for the conversions from hex string to integer for the xor.
3. Building a 2d array for storing the results of the xor of each cipher with one another (8x8 array).
3. Xoring the ciphers with one another then xoring the result with the spaces.
4. Converting the result from bigint to hex hen to char array for checking each byte if is within the range of accepted characters which are: 20(space), from 61 to 6F and from 71 to 7A.
5. Using a hashtable to store the frequency of occurrence of each index if the output is within range of accepted characters and incrementing each time if the index was stored before.
6. At the end of each cipher getting xored with the others, hashtable is checked for each cipher to get the index which had frequency of occurrence equal to the number of ciphers = 7 (so this index is surely space)
---space xor char xor space = char (must be within range of characters)
---char xor char xor space = special char not within range!!
7. As I know the spaces in each cipher now, use each space index in each past xor to get the character of the other cipher in that same place and do the same for a specific cipher till you guess its plaintext.
8. Xor the the plaintext withits cipher to get the key.
9. Xor the key to get the rest of the plaintexts.

        the open design principle increases confidence in security
        learning how to write secure software is a necessary skill
        secure key exchange is needed for symmetric key encryption
        security at the expense of usability could damage security
        modern cryptography requires careful and rigorous analysis
        address randomization could prevent malicious call attacks
        it is not practical to rely solely on symmetric encryption
        i shall never reuse the same password on multiple accounts