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
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private int grouping = 1;

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
    public boolean isShowingAddresses() {
        return showAddresses;
    }

    @Override
    public void setShowAddresses(boolean showAddresses) {
        this.showAddresses = showAddresses;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getGrouping() {
        return grouping;
    }

    @Override
    public void setGrouping(int grouping) {
        this.grouping = grouping;
    }

    @Override
    public HexPanel getHexPanel() {
        final List<HexLine> hexLines = new ArrayList<HexLine>();

        List<List<BinaryData>> slicedBinaryData = binaryDataSlicer.slice(binaryDataList, width);

        int address = initialAddress;

        final List<List<HexChunk>> hexChunkList = hexChunkProcessor.convertAll(slicedBinaryData);

        for (List<HexChunk> hexChunks : hexChunkList) {
            HexLine hexLine = hexLineFactory.create(address, hexChunks, width, grouping);

            hexLines.add(hexLine);
            address = address + width;
        }

        try {
            HexPanel hexPanel = hexPanelFactory.create(hexLines);

            return hexPanel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }
}
