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
     * Extract a hex value for a char in a hex string
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

    public static String toBinString(String hexPair){
        int i1 = Integer.parseInt(hexPair.substring(0,1),16);
        int j1 = Integer.parseInt(hexPair.substring(1),16);
        return Util.toBinaryString(i1,4) + Util.toBinaryString(j1,4);
    }

    /**
     * XOR two hex strings.
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
            String bin1 = Util.toBinString(getHexPairAt(hexString1,n));
            String bin2 = Util.toBinString(getHexPairAt(hexString2,n));
            String xorBin = Util.xorBinaryStrings(bin1, bin2);
            int k = Integer.parseInt(xorBin, 2);
            char c = (char)k;
            //sb.append(c);
            if (Util.isValidChar(c)){
                sb = sb.append(c);
            }
            else {
                sb.append('^');
            }
        }
        
        return sb.toString();
    }

    public static String xorHexStrings(String hexString1, String hexString2)
    {
        int limit = hexString1.length() > hexString2.length()? hexString2.length(): hexString1.length();
        
        StringBuilder sb = new StringBuilder("");
        for (int n=0; n < limit; n+=2)
        {
            String bin1 = Util.toBinString(getHexPairAt(hexString1,n));
            String bin2 = Util.toBinString(getHexPairAt(hexString2,n));
            String xorBin = Util.xorBinaryStrings(bin1, bin2);
            int x = Integer.parseInt(xorBin, 2);
            String hex = Integer.toHexString(x);
            while (hex.length() < 2){
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        
        return sb.toString();
    }
    
    public static String toBinaryString(int i, int size) {
        String s = Integer.toBinaryString(i);
        while (s.length() < size) {
            s = "0" + s;
        }
        return s;
    }

    public static String xorBinaryStrings(String bin1, String bin2){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<8;i++){
            char c1 = bin1.charAt(i);
            char c2 = bin2.charAt(i);
            if (c1 != c2) {
                sb.append("1");
            }
            else {
                sb.append("0");
            }
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

    /**
     * Creates a integer string from a binary string
     * @param binary The binary string
     * @return The integer string.
     */
    public static char binaryStringToIntegerString(String binary)
    {
        char c = (char)Integer.parseInt(binary,2);
        return c;
    }
    
    public static String hexPairTo8BitBinary(String string)
    {
        int i = Integer.parseInt(string, 16);
        String bin = Integer.toBinaryString(i);
        int size = bin.length();
        while (size < 8){
            bin = '0' + bin;
            size++;
        }
        return bin;
    }
    
    /** 
     * Converts plain text to bytes
     * @param plainText
     * @return 
     */
    public static byte[] getBytesForPlainText(String plainText)
    {
        return plainText.getBytes();
//        String string = plainTextToHex(plainText);
//        return getBytesForHexString(string);
    }

    public static byte[] getBytesForHexString(String hexString)
    {//            
        int size = hexString.length();
        byte[] bytes = new byte[size/2];        
        
        for (int n = 0; n<size; n=n+2)
        {
            bytes[n/2] = (byte)((Character.digit(hexString.charAt(n),16) << 4) +
            Character.digit(hexString.charAt(n+1),16));
        }
        return bytes;
    }
    
    public static String getHexStringFromBytes(byte[] bytes)
    {
        
        int size = bytes.length;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<size;i++)
        {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2)
            {
                hex = "0" + hex;
            }
//            System.out.print(hex);
            sb.append(hex);
        }
        return sb.toString();
        //return hexToPlainText(sb.toString());
    }

    public static boolean isValidChar(char c)
    {
        if (c == ',') System.out.println("Found ,");
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