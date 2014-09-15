package client.interfaces;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;
import java.util.Map;

/**
 * Created by timmattison on 9/15/14.
 */
public interface HexLine extends IsWidget {
    public Map<String, List<HTML>> getAssociatedHtml();

    public List<String> getElementOrderingList();
}
