package client.dataprocessing.interfaces;

import client.dataprocessing.values.BinaryData;

import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public interface BinaryDataSlicer {
    /**
     * Takes a list of binary data objects and splits them into "lines"
     *
     * @param binaryDataList
     * @param width
     * @return
     */
    public List<List<BinaryData>> slice(List<BinaryData> binaryDataList, int width);
}
