package client.dataprocessing;

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
    public List<List<HexChunk>> convertAll(List<List<BinaryData>> binaryDataList);

    /**
     * Takes a list of binary data objects and converts them into hex chunks
     *
     * @param binaryDataLine
     * @return
     */
    public List<HexChunk> convertLine(List<BinaryData> binaryDataLine);
}
