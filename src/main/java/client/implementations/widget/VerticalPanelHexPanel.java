package client.implementations.widget;

import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class VerticalPanelHexPanel implements HexPanel {
    private final List<HexLine> hexLines;
    private VerticalPanel panel;

    @Inject
    public VerticalPanelHexPanel(@Assisted("hexLines") List<HexLine> hexLines) {
        this.hexLines = hexLines;
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            panel = new VerticalPanel();

            for (HexLine hexLine : hexLines) {
                panel.add(hexLine.asWidget());
            }

            panel.addStyleName("hexpile");
            panel.addStyleName("highlight");
        }

        return panel;
    }
}
