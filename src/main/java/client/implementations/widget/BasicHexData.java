package client.implementations.widget;

import client.dataprocessing.BinaryData;
import client.dataprocessing.BinaryDataSlicer;
import client.dataprocessing.HexChunk;
import client.dataprocessing.HexChunkProcessor;
import client.factories.HexLineFactory;
import client.factories.HexPanelFactory;
import client.interfaces.HexData;
import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class BasicHexData implements HexData {
    private final HexLineFactory hexLineFactory;
    private final HexPanelFactory hexPanelFactory;
    private final int initialAddress;
    private final BinaryDataSlicer binaryDataSlicer;
    private final List<BinaryData> binaryDataList;
    private final HexChunkProcessor hexChunkProcessor;

    private boolean showAddresses = true;
    private int width = 16;

    @Inject
    public BasicHexData(HexLineFactory hexLineFactory, HexPanelFactory hexPanelFactory, BinaryDataSlicer binaryDataSlicer, HexChunkProcessor hexChunkProcessor,
                        @Assisted("initialAddress") int initialAddress,
                        @Assisted("binaryDataList") List<BinaryData> binaryDataList) {
        this.hexLineFactory = hexLineFactory;
        this.hexPanelFactory = hexPanelFactory;
        this.binaryDataSlicer = binaryDataSlicer;
        this.hexChunkProcessor = hexChunkProcessor;
        this.initialAddress = initialAddress;
        this.binaryDataList = binaryDataList;
    }

    @Override
    public HexPanel getHexPanel() {
        final List<HexLine> hexLines = new ArrayList<HexLine>();

        List<List<BinaryData>> slicedBinaryData = binaryDataSlicer.slice(binaryDataList, width);

        int address = initialAddress;

        final List<List<HexChunk>> hexChunkList = hexChunkProcessor.convert(slicedBinaryData);

        for (List<HexChunk> hexChunks : hexChunkList) {
            HexLine hexLine = hexLineFactory.create(address, hexChunks, width);

            hexLines.add(hexLine);
            address = address + width;
        }

        try {
            return hexPanelFactory.create(hexLines);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }
}
