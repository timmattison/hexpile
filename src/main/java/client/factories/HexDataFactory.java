package client.factories;

import client.dataprocessing.BinaryData;
import client.interfaces.HexData;
import com.google.inject.assistedinject.Assisted;

import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public interface HexDataFactory {
    public HexData create(@Assisted("initialAddress") int initialAddress,
                          @Assisted("binaryDataList") List<BinaryData> binaryDataList);
}
