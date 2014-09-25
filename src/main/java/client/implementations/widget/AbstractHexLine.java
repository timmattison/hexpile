package client.implementations.widget;

import client.interfaces.HexLine;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timmattison on 9/15/14.
 */
public abstract class AbstractHexLine implements HexLine {
    protected static final String VALUE_SEPARATOR = "&nbsp;";
    protected static final String VALUE_PLACEHOLDER = "&nbsp;&nbsp;&nbsp;";
    protected static final int ADDRESS_LENGTH = 8;
    static final int VALUE_LENGTH = 8;
    protected final int address;
    protected final int width;
    private Widget widget;
    private Map<String, List<HTML>> associatedHtml;
    private List<String> elementOrderingList;

    @Inject
    public AbstractHexLine(@Assisted("address") int address,
                           @Assisted("width") int width) {
        this.address = address;
        this.width = width;
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            widget = finalizeWidget();
        }

        return widget;
    }

    protected abstract Widget finalizeWidget();

    HTML createLabelWithText(String text) {
        HTML html = new HTML();
        html.setHTML(text);
        return html;
    }

    @Override
    public Map<String, List<HTML>> getAssociatedHtml() {
        if (widget == null) {
            // Make sure the widget has been created
            asWidget();
        }

        return associatedHtml;
    }

    @Override
    public List<String> getElementOrderingList() {
        if (widget == null) {
            // Make sure the widget has been created
            asWidget();
        }

        return elementOrderingList;
    }

    protected Map<String, List<HTML>> innerGetAssociatedHtml() {
        if (associatedHtml == null) {
            associatedHtml = new HashMap<String, List<HTML>>();
        }

        return associatedHtml;
    }

    protected List<String> innerGetElementOrderingList() {
        if (elementOrderingList == null) {
            elementOrderingList = new ArrayList<String>();
        }

        return elementOrderingList;
    }
}
