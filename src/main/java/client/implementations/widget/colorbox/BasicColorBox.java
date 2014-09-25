package client.implementations.widget.colorbox;

import client.implementations.widget.HtmlHelpers;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Created by timmattison on 9/24/14.
 */
public class BasicColorBox implements ColorBox {
    private final String color;
    private final String text;
    private HorizontalPanel horizontalPanel;
    private final InlineHTML box = new InlineHTML();
    private final InlineHTML description = new InlineHTML();

    @Inject
    public BasicColorBox(@Assisted("color") String color, @Assisted("text") String text) {
        this.color = color;
        this.text = text;
    }

    private Panel getPanel() {
        if (horizontalPanel == null) {
            horizontalPanel = new HorizontalPanel();
            populatePanel();
        }

        return horizontalPanel;
    }

    private void populatePanel() {
        box.setStyleName("hexpileColorBox");
        description.setHTML(text);

        HtmlHelpers.setBackgroundColor(box, color);
        HtmlHelpers.setColor(description, color);
        HtmlHelpers.setNoWrap(description);
        HtmlHelpers.setNoWrap(box);

        horizontalPanel.add(box);
        horizontalPanel.add(description);
    }

    @Override
    public Widget asWidget() {
        return getPanel();
    }

    @Override
    public HTML getTextHtml() {
        return description;
    }
}
