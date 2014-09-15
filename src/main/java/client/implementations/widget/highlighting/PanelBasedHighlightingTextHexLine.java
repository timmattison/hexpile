package client.implementations.widget.highlighting;

import client.dataprocessing.HexChunk;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class PanelBasedHighlightingTextHexLine extends AbstractHighlightingTextHexLine {
    private HorizontalPanel horizontalPanel;

    @Inject
    public PanelBasedHighlightingTextHexLine(@Assisted("address") int address,
                                             @Assisted("hexChunks") List<HexChunk> hexChunks,
                                             @Assisted("width") int width,
                                             @Assisted("grouping") int grouping) {
        super(address, hexChunks, width, grouping);
    }

    private Panel getPanel() {
        if (horizontalPanel == null) {
            horizontalPanel = new HorizontalPanel();
            populatePanel();
        }

        return horizontalPanel;
    }

    @Override
    protected void addAddress(String addressText) {
        HTML html = new HTML(addressText);

        horizontalPanel.add(html);
    }

    @Override
    protected void addTextList(List<HTML> textList) {
        // Add the text to the horizontal panel
        for (HTML html : textList) {
            horizontalPanel.add(html);
        }
    }

    @Override
    protected void addHexList(List<HTML> hexList) {
        // Add the HTML to the horizontal panel
        for (HTML html : hexList) {
            horizontalPanel.add(html);
        }
    }

    @Override
    protected Widget finalizeWidget() {
        return getPanel();
    }
}
