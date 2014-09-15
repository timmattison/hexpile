package client.factories;

import client.dataprocessing.BinaryData;
import client.dataprocessing.HexChunk;
import client.interfaces.HexLine;
import com.google.inject.assistedinject.Assisted;

import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public interface HexLineFactory {
    public HexLine create(@Assisted("address") int address,
                          @Assisted("hexChunks") List<HexChunk> hexChunks,
                          @Assisted("width") int width,
                          @Assisted("grouping") int grouping);
}
