package client.implementations.widget;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public class FancyColorPicker implements ColorPicker {
    // From http://ethanschoonover.com/solarized
    private final List<String> backgroundColors = new ArrayList<String>() {{
        add("#000000");
        add("#000000");
        add("#000000");
        add("#000000");
        add("#000000");
        add("#000000");
        add("#000000");
    }};

    private final List<String> textColors = new ArrayList<String>() {{
        add("#b58900");
        add("#cb4b16");
        add("#657b83");
        add("#93a1a1");
        add("#6c71c4");
        add("#d33682");
        add("#268bd2");
    }};

    private int index = 0;

    @Inject
    public FancyColorPicker() {

    }

    @Override
    public String getTextColor() {
        return textColors.get(index);
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColors.get(index);
    }

    @Override
    public void nextColor() {
        index++;
        index %= backgroundColors.size();
    }
}
