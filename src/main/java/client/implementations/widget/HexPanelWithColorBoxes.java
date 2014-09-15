package client.implementations.widget;

import client.implementations.widget.colorbox.ColorBox;
import client.implementations.widget.colorbox.ColorBoxFactory;
import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by timmattison on 9/15/14.
 */
public class HexPanelWithColorBoxes implements HexPanel {
    private final ColorBoxFactory colorBoxFactory;
    private final List<HexLine> hexLines;
    private HorizontalPanel panel;
    private VerticalPanel leftPanel;
    private VerticalPanel rightPanel;

    @Inject
    public HexPanelWithColorBoxes(ColorBoxFactory colorBoxFactory,
                                  @Assisted("hexLines") List<HexLine> hexLines) {
        this.colorBoxFactory = colorBoxFactory;
        this.hexLines = hexLines;
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            panel = new HorizontalPanel();

            buildLeftPanel();
            buildRightPanel();

            panel.add(leftPanel);
            panel.add(rightPanel);

            panel.addStyleName("hexpile");
            panel.addStyleName("highlight");

            HexPanelHelper.addAllMouseovers(hexLines);
        }

        return panel;
    }

    private void buildRightPanel() {
        rightPanel = new VerticalPanel();

        Set<String> alreadyProcessed = new HashSet<String>();

        for (HexLine hexLine : hexLines) {
            Map<String, List<HTML>> associatedHtml = hexLine.getAssociatedHtml();
            List<String> elementOrderingList = hexLine.getElementOrderingList();

            if (associatedHtml == null) {
                continue;
            }

            for (String name : elementOrderingList) {
                if (alreadyProcessed.contains(name)) {
                    continue;
                }

                List<HTML> htmlList = hexLine.getAssociatedHtml().get(name);

                if ((htmlList == null) || (htmlList.size() == 0)) {
                    continue;
                }

                HTML html = htmlList.get(0);
                String color = HtmlHelpers.getColor(html);

                if (color == null) {
                    continue;
                }

                ColorBox colorBox = colorBoxFactory.create(color, name);
                rightPanel.add(colorBox);
                htmlList.add(colorBox.getTextHtml());
                alreadyProcessed.add(name);
            }
        }
    }

    private void buildLeftPanel() {
        leftPanel = new VerticalPanel();

        for (HexLine hexLine : hexLines) {
            leftPanel.add(hexLine.asWidget());
        }
    }
}
