package client.implementations.widget;

/**
 * Created by timmattison on 9/22/14.
 */
public class HexHelpers {
    public static String toHexNumberWithLeadingZeroes(int value, int length) {
        String output = Long.toHexString(0x100000000L | value);

        output = output.substring(output.length() - length);

        return output;
    }
}
