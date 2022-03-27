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
        app.decode();
        app.decrypt();
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

    public void decrypt(){
        String encryptionKey = computeEncryptionKey();
        for (int i=0; i< Messages.ciphertexts.length; i++) {
            String decryptedHex = Util.xorHexStrings(Messages.ciphertexts[i], encryptionKey);
            System.out.println(Util.hexToPlainText(decryptedHex, false));
        }
    }


    public void decode(String hexPair){
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

    public void decode(){
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
