package client.dataprocessing;

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
    public static final String VALUE_SEPARATOR = "&nbsp;";
    public static final int VALUE_LENGTH = 2;
    private final ColorPicker colorPicker;

    @Inject
    public BasicHexChunkProcessor(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public List<List<HexChunk>> convertAll(List<List<BinaryData>> binaryDataList) {
        Map<String, String> backgroundColors = new HashMap<String, String>();
        Map<String, String> textColors = new HashMap<String, String>();
        List<List<HexChunk>> output = new ArrayList<List<HexChunk>>();

        for (List<BinaryData> binaryDataLine : binaryDataList) {
            List<HexChunk> hexChunkList = convertLine(backgroundColors, textColors, binaryDataLine);

            output.add(hexChunkList);
        }

        return output;
    }

    public List<HexChunk> convertLine(List<BinaryData> binaryDataLine) {
        return convertLine(null, null, binaryDataLine);
    }

    private List<HexChunk> convertLine(Map<String, String> backgroundColors, Map<String, String> textColors, List<BinaryData> binaryDataLine) {
        if (backgroundColors == null) {
            backgroundColors = new HashMap<String, String>();
        }

        if (textColors == null) {
            textColors = new HashMap<String, String>();
        }

        List<HexChunk> output = new ArrayList<HexChunk>();

        for (BinaryData binaryData : binaryDataLine) {
            StringBuilder textStringBuilder = new StringBuilder();
            StringBuilder hexDataStringBuilder = new StringBuilder();

            HexChunk hexChunk = new HexChunk();

            innerConvert(binaryData, textStringBuilder, hexDataStringBuilder);

            hexChunk.hexData = hexDataStringBuilder.toString();
            hexChunk.text = textStringBuilder.toString();
            hexChunk.name = binaryData.name;

            if ((hexChunk.name != null) && (hexChunk.name.length() != 0)) {
                boolean moveToNextColor = false;

                if (backgroundColors.containsKey(hexChunk.name)) {
                    hexChunk.backgroundColor = backgroundColors.get(hexChunk.name);
                } else {
                    hexChunk.backgroundColor = colorPicker.getBackgroundColor();
                    backgroundColors.put(hexChunk.name, hexChunk.backgroundColor);
                    moveToNextColor = true;
                }

                if (textColors.containsKey(hexChunk.name)) {
                    hexChunk.textColor = textColors.get(hexChunk.name);
                } else {
                    hexChunk.textColor = colorPicker.getTextColor();
                    textColors.put(hexChunk.name, hexChunk.textColor);
                    moveToNextColor = true;
                }

                if (moveToNextColor) {
                    colorPicker.nextColor();
                }
            }

            output.add(hexChunk);
        }

        return output;
    }

    private void innerConvert(BinaryData binaryData, StringBuilder textStringBuilder, StringBuilder hexDataStringBuilder) {
        String separator = null;

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
