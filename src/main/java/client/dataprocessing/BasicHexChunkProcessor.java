package client.dataprocessing;

import client.dataprocessing.interfaces.HexChunkProcessor;
import client.dataprocessing.values.BinaryData;
import client.dataprocessing.values.HexChunk;
import client.implementations.widget.ColorPicker;
import client.implementations.widget.HexHelpers;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timmattison on 9/16/14.
 */
public class BasicHexChunkProcessor implements HexChunkProcessor {
    private static final String VALUE_SEPARATOR = "&nbsp;";
    private static final int VALUE_LENGTH = 2;
    private final ColorPicker colorPicker;

    @Inject
    public BasicHexChunkProcessor(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public List<List<HexChunk>> convert(List<List<BinaryData>> binaryDataList) {
        // Create maps of the text colors and background colors that is keyed by chunk name
        Map<String, String> backgroundColors = new HashMap<String, String>();
        Map<String, String> textColors = new HashMap<String, String>();

        // Create a list of hex chunk lists (one list per line)
        List<List<HexChunk>> output = new ArrayList<List<HexChunk>>();

        // Loop through the list of binary data (one binary data list per line)
        for (List<BinaryData> binaryDataLine : binaryDataList) {
            // Convert this line to a hex chunk list
            List<HexChunk> hexChunkList = convertLine(backgroundColors, textColors, binaryDataLine);

            // Add the hex chunk list to the output
            output.add(hexChunkList);
        }

        return output;
    }

    private List<HexChunk> convertLine(Map<String, String> backgroundColors, Map<String, String> textColors, List<BinaryData> binaryDataLine) {
        // Create a list of the output hex chunks
        List<HexChunk> output = new ArrayList<HexChunk>();

        // Loop through the binary data for this line
        for (BinaryData binaryData : binaryDataLine) {
            // Create two string builders.  One for the text and one for the hex data.
            StringBuilder textStringBuilder = new StringBuilder();
            StringBuilder hexDataStringBuilder = new StringBuilder();

            // Create a new hex chunk
            HexChunk hexChunk = new HexChunk();

            // Put the binary data into the string builders
            innerConvert(binaryData, textStringBuilder, hexDataStringBuilder);

            // Populate the hex chunk object
            hexChunk.hexData = hexDataStringBuilder.toString();
            hexChunk.text = textStringBuilder.toString();
            hexChunk.name = binaryData.name;

            // Does this hex chunk have a name?
            if ((hexChunk.name != null) && (hexChunk.name.length() != 0)) {
                // Yes, we need to color it
                boolean moveToNextColor = false;

                // Was a color already selected?
                if (backgroundColors.containsKey(hexChunk.name)) {
                    // Yes, use the color we selected already
                    hexChunk.backgroundColor = backgroundColors.get(hexChunk.name);
                    hexChunk.textColor = textColors.get(hexChunk.name);
                } else {
                    // No, select new colors
                    hexChunk.backgroundColor = colorPicker.getBackgroundColor();
                    hexChunk.textColor = colorPicker.getTextColor();

                    // Add the colors to the map
                    backgroundColors.put(hexChunk.name, hexChunk.backgroundColor);
                    textColors.put(hexChunk.name, hexChunk.textColor);

                    // Move onto the next color
                    colorPicker.nextColor();
                }
            }

            // Add the hex chunk to the output
            output.add(hexChunk);
        }

        return output;
    }

    private void innerConvert(BinaryData binaryData, StringBuilder textStringBuilder, StringBuilder hexDataStringBuilder) {
        // Start with no separator
        String separator = null;

        // Loop through each byte of this data
        for (Byte currentByte : binaryData.data) {
            // Get the "real" value of this byte (unsigned)
            int realValue = ((int) currentByte) & 0x0FF;

            // Is there a separator already?
            if (separator != null) {
                // Yes, add it to the hex HTML
                hexDataStringBuilder.append(separator);
            } else {
                // Set the separator for next time
                separator = VALUE_SEPARATOR;
            }

            // Convert the value to a hex string and add it to the hex bytes data
            String valueText = HexHelpers.toHexNumberWithLeadingZeroes(realValue, VALUE_LENGTH);
            hexDataStringBuilder.append(valueText);

            // Get the current byte value as an integer
            int currentIntValue = currentByte.intValue();

            // Is this a weird character that we can't print easily?
            if ((currentIntValue < 32) || (currentIntValue > 127)) {
                // Yes, replace it with a period
                textStringBuilder.append(".");
            } else {
                // Convert the value to a character and add it to the text data
                char realValueChar = (char) currentIntValue;
                textStringBuilder.append(String.valueOf(realValueChar));
            }
        }
    }
}
