//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class Hex
//{
//    private static final Encoder encoder = new HexEncoder();
//
//    public static String toHexString(
//            byte[] data)
//    {
//        return toHexString(data, 0, data.length);
//    }
//
//    public static String toHexString(
//            byte[] data,
//            int    off,
//            int    length)
//    {
//        byte[] encoded = encode(data, off, length);
//        return new String(asCharArray(encoded));
//    }
//
//    public static char[] asCharArray(byte[] bytes)
//    {
//        char[] chars = new char[bytes.length];
//
//        for (int i = 0; i != chars.length; i++)
//        {
//            chars[i] = (char)(bytes[i] & 0xff);
//        }
//
//        return chars;
//    }
//
//    /**
//     * encode the input data producing a Hex encoded byte array.
//     *
//     * @return a byte array containing the Hex encoded data.
//     */
//    public static byte[] encode(
//            byte[]    data)
//    {
//        return encode(data, 0, data.length);
//    }
//
//    /**
//     * encode the input data producing a Hex encoded byte array.
//     *
//     * @return a byte array containing the Hex encoded data.
//     */
//    public static byte[] encode(
//            byte[]    data,
//            int       off,
//            int       length)
//    {
//        ByteArrayOutputStream    bOut = new ByteArrayOutputStream();
//
//        try
//        {
//            encoder.encode(data, off, length, bOut);
//        }
//        catch (Exception e)
//        {
//            throw new IllegalArgumentException("exception encoding Hex string: " + e.getMessage(), e);
//        }
//
//        return bOut.toByteArray();
//    }
//
//    /**
//     * Hex encode the byte data writing it to the given output stream.
//     *
//     * @return the number of bytes produced.
//     */
//    public static int encode(
//            byte[]         data,
//            OutputStream   out)
//            throws IOException
//    {
//        return encoder.encode(data, 0, data.length, out);
//    }
//
//    /**
//     * Hex encode the byte data writing it to the given output stream.
//     *
//     * @return the number of bytes produced.
//     */
//    public static int encode(
//            byte[]         data,
//            int            off,
//            int            length,
//            OutputStream   out)
//            throws IOException
//    {
//        return encoder.encode(data, off, length, out);
//    }
//
//    /**
//     * decode the Hex encoded input data. It is assumed the input data is valid.
//     *
//     * @return a byte array representing the decoded data.
//     */
//    public static byte[] decode(
//            byte[]    data)
//    {
//        ByteArrayOutputStream    bOut = new ByteArrayOutputStream();
//
//        try
//        {
//            encoder.decode(data, 0, data.length, bOut);
//        }
//        catch (Exception e)
//        {
//            throw new IllegalArgumentException("exception decoding Hex data: " + e.getMessage(), e);
//        }
//
//        return bOut.toByteArray();
//    }
//
//    /**
//     * decode the Hex encoded String data - whitespace will be ignored.
//     *
//     * @return a byte array representing the decoded data.
//     */
//    public static byte[] decode(
//            String    data)
//    {
//        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//
//        try
//        {
//            encoder.decode(data, bOut);
//        }
//        catch (Exception e)
//        {
//            throw new IllegalArgumentException("exception decoding Hex string: " + e.getMessage(), e);
//        }
//
//        return bOut.toByteArray();
//    }
//
//    /**
//     * decode the Hex encoded String data writing it to the given output stream,
//     * whitespace characters will be ignored.
//     *
//     * @return the number of bytes produced.
//     */
//    public static int decode(
//            String          data,
//            OutputStream out)
//            throws IOException
//    {
//        return encoder.decode(data, out);
//    }
//}