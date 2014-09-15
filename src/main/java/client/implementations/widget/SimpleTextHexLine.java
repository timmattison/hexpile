package client.implementations.widget;

import client.dataprocessing.HexChunk;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class SimpleTextHexLine extends AbstractHexLine {
    private static final String SECTION_SEPARATOR = "&nbsp;|&nbsp;";
    private final StringBuilder stringBuilder = new StringBuilder();
    private final List<HexChunk> hexChunks;
    private FlowPanel flowPanel;

    @Inject
    public SimpleTextHexLine(@Assisted("address") int address,
                             @Assisted("hexChunks") List<HexChunk> hexChunks,
                             @Assisted("width") int width,
                             @Assisted("grouping") int grouping) {
        super(address, width, grouping);
        this.hexChunks = hexChunks;
    }

    private Panel getPanel() {
        if (flowPanel == null) {
            flowPanel = new FlowPanel();
            populatePanel();
        }

        return flowPanel;
    }

    private void addAddress() {
        String addressText = "0x" + HexHelpers.toHexNumberWithLeadingZeroes(address, ADDRESS_LENGTH);
        add(addressText);
    }

    private void populatePanel() {
        addAddress();
        addSeparator();
        addHexBytes();
        addSeparator();
        addText();
    }

    private void addSeparator() {
        add(SECTION_SEPARATOR);
    }

    private void addHexBytes() {
        // TODO - Implement grouping support

        String separator = null;

        int totalSize = 0;

        // XXX - This is a bit messed up since the refactor
        for (HexChunk hexChunk : hexChunks) {
            totalSize = totalSize + hexChunk.hexData.length();

            if (separator != null) {
                add(separator);
            }

            add(hexChunk.hexData);

            separator = VALUE_SEPARATOR;
        }

        for (int loop = totalSize; loop < width; loop++) {
            add(VALUE_PLACEHOLDER);
        }
    }

    private void addText() {
        int totalSize = 0;

        // XXX - This is a bit messed up since the refactor
        for (HexChunk hexChunk : hexChunks) {
            totalSize = totalSize + hexChunk.text.length();
            add(hexChunk.text);
        }

        for (int loop = totalSize; loop < width; loop++) {
            add(VALUE_SEPARATOR);
        }
    }

    protected void add(String text) {
        stringBuilder.append(text);
    }

    @Override
    protected Widget finalizeWidget() {
        getPanel().clear();
        HTML html = new HTML(stringBuilder.toString());
        getPanel().add(html);
        return getPanel();
    }
}
