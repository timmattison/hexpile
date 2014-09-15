package client.dataprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public class BasicBinaryDataSlicer implements BinaryDataSlicer {
    public List<List<BinaryData>> slice(List<BinaryData> binaryDataList, int width) {
        List<List<BinaryData>> output = new ArrayList<List<BinaryData>>();
        List<BinaryData> currentList = new ArrayList<BinaryData>();

        int position = 0;

        for (BinaryData binaryData : binaryDataList) {
            List<Byte> tempData = new ArrayList<Byte>(binaryData.data);
            String name = binaryData.name;

            while (tempData.size() != 0) {
                int bytesLeft = width - position;
                int copyEnd = bytesLeft;

                if (bytesLeft > tempData.size()) {
                    copyEnd = tempData.size();
                }

                List<Byte> currentBytes = new ArrayList(tempData.subList(0, copyEnd));

                BinaryData newBinaryData = new BinaryData();
                newBinaryData.data = currentBytes;
                newBinaryData.name = name;

                tempData = new ArrayList<Byte>(tempData.subList(copyEnd, tempData.size()));

                currentList.add(newBinaryData);

                position = position + copyEnd;

                if (position == width) {
                    output.add(currentList);
                    currentList = new ArrayList<BinaryData>();
                    position = 0;
                }
            }
        }

        if (position != 0) {
            output.add(currentList);
        }

        return output;
    }
}
