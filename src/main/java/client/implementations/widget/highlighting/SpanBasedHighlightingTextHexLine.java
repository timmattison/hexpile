package client.implementations.widget.highlighting;

import client.dataprocessing.HexChunk;
import com.google.gwt.user.client.ui.*;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class SpanBasedHighlightingTextHexLine extends AbstractHighlightingTextHexLine {
    private FlowPanel flowPanel;

    @Inject
    public SpanBasedHighlightingTextHexLine(@Assisted("address") int address,
                                            @Assisted("hexChunks") List<HexChunk> hexChunks,
                                            @Assisted("width") int width,
                                            @Assisted("grouping") int grouping) {
        super(address, hexChunks, width, grouping);
    }

    private Panel getPanel() {
        if (flowPanel == null) {
            flowPanel = new FlowPanel();
            populatePanel();
        }

        return flowPanel;
    }

    @Override
    protected void addAddress(String addressText) {
        InlineHTML html = new InlineHTML(addressText);

        flowPanel.add(html);
    }

    @Override
    protected void addTextList(List<HTML> textList) {
        for (HTML html : textList) {
            flowPanel.add(html);
        }
    }

    @Override
    protected void addHexList(List<HTML> hexList) {
        for (HTML html : hexList) {
            flowPanel.add(html);
        }
    }

    @Override
    protected Widget finalizeWidget() {
        return getPanel();
    }

    @Override
    protected HTML getNewHtmlObject() {
        return new InlineHTML();
    }
}
