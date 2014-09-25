package client.dataprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public class BasicBinaryDataSlicer implements BinaryDataSlicer {
    public List<List<BinaryData>> slice(List<BinaryData> binaryDataList, int width) {
        // Create an output object that is a list of the binary data lists.  One list per line.
        List<List<BinaryData>> output = new ArrayList<List<BinaryData>>();

        // Create a current list object that is a list of the binary data for one line
        List<BinaryData> currentList = new ArrayList<BinaryData>();

        // Track our current position
        int position = 0;

        // Iterate through the binary data list
        for (BinaryData binaryData : binaryDataList) {
            // Copy the actual byte data to a temporary list
            List<Byte> tempData = new ArrayList<Byte>(binaryData.data);

            // Get the name of this chunk
            String name = binaryData.name;

            // Loop until there is no data left
            while (tempData.size() != 0) {
                // Calculate how many bytes are left in this line
                int bytesLeft = width - position;

                // Track where our array copy should end
                int copyEnd = bytesLeft;

                // Are there more bytes to copy than there is space left on this line?
                if (bytesLeft > tempData.size()) {
                    // Yes, reduce the amount we're data of copying so it fits on this line
                    copyEnd = tempData.size();
                }

                // Copy the data
                List<Byte> currentBytes = new ArrayList(tempData.subList(0, copyEnd));

                // Create a new binary data object for this chunk
                BinaryData newBinaryData = new BinaryData();

                // Set the data and the name
                newBinaryData.data = currentBytes;
                newBinaryData.name = name;

                // Add the binary data to the list of binary data for this line
                currentList.add(newBinaryData);

                // Remove the data we just copied from the temporary data
                tempData = new ArrayList<Byte>(tempData.subList(copyEnd, tempData.size()));

                // Update our position variable
                position = position + copyEnd;

                // Are we at the end of a line?
                if (position == width) {
                    // Yes, add this entire list to the master list
                    output.add(currentList);

                    // Create a new list for the next line
                    currentList = new ArrayList<BinaryData>();

                    // Reset the position variable
                    position = 0;
                }
            }
        }

        // Was there partial data left over from the last line?
        if (position != 0) {
            // Yes, add it to the master list
            output.add(currentList);
        }

        return output;
    }
}
