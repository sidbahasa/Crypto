package week1;
/**
 *
 * @author sshaw
 */
public class Week1 {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Week1 app = new Week1();
        //app.decode("45");
        //app.decode("4f");
        app.decodeUsingCipherTexts();
        app.decryptUsingEncryptionKey();
    }

    public String computeEncryptionKey(){
        byte[] bytes = Messages.decrypted.getBytes();
        int size = bytes.length;
        StringBuilder hexMessage = new StringBuilder("");
        
        for (int i=0;i<size;i++) {
            int c = bytes[i];
            String hex = Integer.toHexString(c);
            while (hex.length() < 2) {
                hex = "0" + hex;
            }
            hexMessage.append(hex);
        }
        
        return Util.xorHexStrings(hexMessage.toString(), Messages.target);
    }

    /**
     * Decrypts all cyphertexts using the encryption key
     */
    public void decryptUsingEncryptionKey(){
        String encryptionKey = computeEncryptionKey();
        for (int i=0; i< Messages.ciphertexts.length; i++) {
            String decryptedHex = Util.xorHexStrings(Messages.ciphertexts[i], encryptionKey);
            System.out.println(Util.hexToPlainText(decryptedHex, false));
        }
    }

    /** Use brute force approach to decode a hex value for a char */
    public void bruteForceDecode(String hexPair){
        assert(hexPair.length() == 2);
        for (int i=0; i < 128; i++) {
            String keyHex = Integer.toHexString(i);
            if (keyHex.length() < 2) {
                keyHex = "0" + keyHex;
            }
            String xor = Util.xorHexStringsToAscii(hexPair, keyHex);
            if (Util.isValidChar(xor.charAt(0))) {
                System.out.println("Found: " + xor);
                //break;
            }
        }   
    }

    /**
     * XORs each cyphertext in Messages.cyphertext with the target cyphertext in order
     * to force as many collisions between the encoded characters of the target
     * cyphertext with encoded ' ' from Messages.cyphertext.  
     */
    public void decodeUsingCipherTexts(){
        int cipherLength = Messages.ciphertexts.length;

        StringBuilder sb = new StringBuilder();
        for  (int i=0; i< (Messages.target.length()/2); i++){
            sb.append('^');
        }

        for (int i=0;i<cipherLength;i++)
        {
            String ascii = Util.xorHexStringsToAscii(Messages.ciphertexts[i],Messages.target);
            int l = ascii.length();
            for (int k=0; k<l;k++){
                char c = ascii.charAt(k);
                if (Util.isValidChar(c)){
                    sb.setCharAt(k, c);
                }
            }
        }

        System.out.println("Decode: " + sb.toString());
        //tHMESECUETSMESZAGEDISYTwHTNRUSANWEWSSTREAMNCIPHERETNEVIRTUSEATHEOKEYWMORENTHANRONCE
        //tHM SECUET MESZAGE IS  wHTN USANW W STREAM CIPHER  NEVIR USE THE KEY MORE THAN ONCE  
    }
}
