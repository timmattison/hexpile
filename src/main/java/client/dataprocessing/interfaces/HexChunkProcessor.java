package client.dataprocessing.interfaces;

import client.dataprocessing.values.BinaryData;
import client.dataprocessing.values.HexChunk;

import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public interface HexChunkProcessor {
    /**
     * Takes a list of binary data objects and converts them into hex chunks
     *
     * @param binaryDataList
     * @return
     */
    public List<List<HexChunk>> convert(List<List<BinaryData>> binaryDataList);
}
