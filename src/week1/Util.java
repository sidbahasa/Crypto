package week1;
/**
 *
 * @author JShaw
 */
public class Util {
        
    /**
     * Converts a hex string to an ascii string
     * @param hexString The hex string to convert.
     * @return The ascii version of the hex string
     */
    public static String hexToPlainText(String hexString, boolean reverse)
    {
        int size = hexString.length();
        int i = 0;
        StringBuilder sb = new StringBuilder(size/2);
        while (i < size)
        {
            String subHex = getHexPairAt(hexString, i);
            if (subHex.equals("00")) {
                sb.append(' ');
            }
            else {
                int h = Integer.parseInt(subHex,16);
                if (reverse) {
                    if (Util.isAlpha((char)h)) {
                        if (h >= 97) {
                            h -= 32;
                        }
                        else {
                            h += 32;
                        }
                    }
                }
                char c = (char)h;
                if (Character.isWhitespace(c)){
                    sb.append(' ');
                }
                else {
                    sb.append(c);
                }
            }
            i+=2;
        }
        String s = sb.toString();
        return s;
    }
    
    /**
     * Converts ascii to hex
     * <li>Converts a char to 8 bit binary
     * <li>Converts 8 bit binary to 2 char hex
     * @param asciiString
     * @return 
     */
    public static String plainTextToHex(String asciiString)
    {
        int size = asciiString.length();
        
        StringBuilder sb = new StringBuilder(size*2);
        
        for (int i=0; i<size; i++)
        {
            char c = asciiString.charAt(i);
        
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 8)
            {
                bin = "0" + bin;
            }
        
            int h1 = Integer.parseInt(bin.substring(0,4),2);
            int h2 = Integer.parseInt(bin.substring(4),2);

            String hex = Integer.toHexString(h1) + Integer.toHexString(h2);

            assert(hex.length() == 2);
        
            sb.append(hex);
        }
        
        return sb.toString();
    }
        
    /**
     * Extracts the hex value for a char from a hex string
     * @param hexString The hex string.
     * @param index The 0-based index of the hex value for a char.
     * @return The hex value for a char in a hex string
     */
    public static String getHexPairAt(String hexString, int index)
    {
    	String hex = "";
    	if (hexString.length() == 2) hex = hexString;
    	else
    		hex = hexString.substring(index, index+2);
        return hex;
    }
    
    public static byte[] xorByteArrays(byte[] one, byte[] two){
        int size = one.length;
        if (two.length < size)
        {
            size = two.length;
        }
    	byte[] res = new byte[size];
        for (int i=0;i<size;i++)
        {
            res[i] = (byte)(one[i] ^ two[i]);
        }
        
        return res;
    }

    /** Converts a hax string for a char to a binary string */
    public static String toBinString(String hexPair){
        assert(hexPair.length() == 2);
        String bin = Integer.toBinaryString(Integer.parseInt(hexPair,16));
        while (bin.length() < 8) {
            bin = "0" + bin;
        }
        return bin;
    }

    /**
     * XOR two hex strings to get an ASCII string
     * @param hexString1
     * @param hexString2
     * @return The hex XOR of two hex string.
     */
    public static String xorHexStringsToAscii(String hexString1, String hexString2)
    {
        int limit = hexString1.length() > hexString2.length()? hexString2.length(): hexString1.length();
        
        StringBuilder sb = new StringBuilder("");
        for (int n=0; n < limit; n+=2)
        {
            int k = xorHexPair(getHexPairAt(hexString1,n), getHexPairAt(hexString2,n));
            char c = (char)k;
            if (Util.isValidChar(c)){
                sb = sb.append(c);
            }
            else {
                sb.append('^');
            }
        }
        
        return sb.toString();
    }

    /**
     * XORs two hex strings
     * @param hexString1
     * @param hexString2
     * @return 
     */
    public static String xorHexStrings(String hexString1, String hexString2)
    {
        int limit = hexString1.length() > hexString2.length()? hexString2.length(): hexString1.length();
        
        StringBuilder sb = new StringBuilder("");
        for (int n=0; n < limit; n+=2)
        {
            int k = xorHexPair(getHexPairAt(hexString1,n), getHexPairAt(hexString2,n));
            String hex = Integer.toHexString(k);
            while (hex.length() < 2){
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        
        return sb.toString();
    }

    /**
     * XOR two hex values for chars
     * @param hexString1
     * @param hexString2
     * @return 
     */
    public static int xorHexPair(String hexString1, String hexString2)
    {
    	assert(hexString1.length() == 2);
    	assert(hexString2.length() == 2);
        int i = Integer.parseInt(hexString1, 16);
        int j = Integer.parseInt(hexString2, 16);
        int k = i ^ j;
        return k;
    }

    public static boolean isValidChar(char c)
    {
        return (c>='a' && c<='z') || (c>='A' && c<='Z') 
                || (c == ' ')
                || (c == ':')   
                || (c == ',');
    }

    public static boolean isAlpha(char c)
    {
        return (c>='a' && c<='z') || (c>='A' && c<='Z');
    }
}