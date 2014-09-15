package client.implementations.widget;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public class BasicColorPicker implements ColorPicker {
    private final List<String> colors = new ArrayList<String>() {{
        add("red");
        add("green");
        add("blue");
    }};

    private int index = 0;

    @Inject
    public BasicColorPicker() {

    }

    @Override
    public String getTextColor() {
        return "#000000";
    }

    @Override
    public String getBackgroundColor() {
        return colors.get(index);
    }

    @Override
    public void nextColor() {
        index++;
        index %= colors.size();
    }
}
