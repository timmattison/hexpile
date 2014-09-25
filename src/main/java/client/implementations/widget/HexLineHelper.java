package client.implementations.widget;

import client.interfaces.HexLine;
import com.google.gwt.user.client.ui.HTML;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by timmattison on 9/24/14.
 */
class HexLineHelper {
    public static Map<String, List<HTML>> getCombinedAssociatedHtml(List<HexLine> hexLines) {
        Map<String, List<HTML>> combinedAssociatedHtml = new HashMap<String, List<HTML>>();

        for (HexLine hexLine : hexLines) {
            Map<String, List<HTML>> associatedHtml = hexLine.getAssociatedHtml();

            if (associatedHtml == null) {
                continue;
            }

            combineData(combinedAssociatedHtml, associatedHtml);
        }

        return combinedAssociatedHtml;
    }

    private static void combineData(Map<String, List<HTML>> combinedAssociatedHtml, Map<String, List<HTML>> associatedHtml) {
        Set<Map.Entry<String, List<HTML>>> entries = associatedHtml.entrySet();
        for (Map.Entry<String, List<HTML>> entry : entries) {
            List<HTML> secondMapValue = combinedAssociatedHtml.get(entry.getKey());
            if (secondMapValue == null) {
                combinedAssociatedHtml.put(entry.getKey(), entry.getValue());
            } else {
                secondMapValue.addAll(entry.getValue());
            }
        }
    }
}
