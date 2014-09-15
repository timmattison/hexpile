package client.implementations.widget;

import client.interfaces.HexLine;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HTML;

import java.util.List;
import java.util.Map;

/**
 * Created by timmattison on 9/24/14.
 */
public class HexPanelHelper {
    public static void addAllMouseovers(List<HexLine> hexLines) {
        Map<String, List<HTML>> combinedAssociatedHtml = HexLineHelper.getCombinedAssociatedHtml(hexLines);

        for (String name : combinedAssociatedHtml.keySet()) {
            List<HTML> associatedHtml = combinedAssociatedHtml.get(name);
            if ((associatedHtml == null) || (associatedHtml.size() == 0)) {
                continue;
            }

            System.out.println("Name: " + name + ", " + associatedHtml.size());
            addIndividualMouseover(associatedHtml);
        }
    }

    private static void addIndividualMouseover(final List<HTML> associatedHtml) {
        for (HTML html : associatedHtml) {
            html.addMouseOverHandler(new MouseOverHandler() {
                @Override
                public void onMouseOver(MouseOverEvent event) {
                    for (HTML relatedChunk : associatedHtml) {
                        relatedChunk.addStyleName("hexpileHighlighted");
                    }
                }
            });

            html.addMouseOutHandler(new MouseOutHandler() {
                @Override
                public void onMouseOut(MouseOutEvent event) {
                    for (HTML relatedChunk : associatedHtml) {
                        relatedChunk.removeStyleName("hexpileHighlighted");
                    }
                }
            });
        }
    }
}
