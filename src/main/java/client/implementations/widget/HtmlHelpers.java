package client.implementations.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;

/**
 * Created by timmattison on 9/24/14.
 */
public class HtmlHelpers {
    private static final String COLOR = "color";
    private static final String BACKGROUND_COLOR = "backgroundColor";

    public static void setColor(HTML html, String color) {
        html.getElement().getStyle().setProperty(COLOR, color);
    }

    public static String getColor(HTML html) {
        return html.getElement().getStyle().getProperty(COLOR);
    }

    public static void setBackgroundColor(HTML html, String color) {
        html.getElement().getStyle().setProperty(BACKGROUND_COLOR, color);
    }

    public static void setNoWrap(HTML html) {
        //html.getElement().getStyle().setFloat(Style.Float.LEFT);
        html.getElement().getStyle().setWhiteSpace(Style.WhiteSpace.NOWRAP);
    }
}
