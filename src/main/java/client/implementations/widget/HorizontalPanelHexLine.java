package client.implementations.widget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public class HorizontalPanelHexLine extends AbstractHexLine {
    private final List<Byte> data;
    private HorizontalPanel horizontalPanel;

    @Inject
    public HorizontalPanelHexLine(@Assisted("address") int address,
                                  @Assisted("data") List<Byte> data,
                                  @Assisted("width") int width) {
        super(address, width);
        this.data = data;
    }

    private Panel getPanel() {
        if (horizontalPanel == null) {
            horizontalPanel = new HorizontalPanel();
            horizontalPanel.setBorderWidth(0);
            horizontalPanel.setSpacing(0);
        }

        return horizontalPanel;
    }

    void add(Label label) {
        getPanel().add(label);
    }

    @Override
    protected Widget finalizeWidget() {
        if (horizontalPanel == null) {
            addAddress();
            addHexBytes();
            addText();
        }

        return horizontalPanel;
    }

    private void addText() {
        for (Byte currentByte : data) {
            char realValue = (char) currentByte.intValue();
            Label currentCharacter = getTextLabel(String.valueOf(realValue));
            add(currentCharacter);
        }

        for (int loop = data.size(); loop < width; loop++) {
            Label currentCharacter = getTextLabel(VALUE_SEPARATOR);
            add(currentCharacter);
        }
    }

    private void addAddress() {
        Label addressLabel = createLabelWithText("0x" + HexHelpers.toHexNumberWithLeadingZeroes(address, ADDRESS_LENGTH));
        add(addressLabel);
    }

    private void addHexBytes() {
        String separator = null;

        for (Byte currentByte : data) {
            int realValue = ((int) currentByte) & 0x0FF;

            if (separator != null) {
                Label byteSeparator = getTextLabel(separator);
                add(byteSeparator);
            }

            Label currentByteLabel = getValueLabel(realValue);
            add(currentByteLabel);

            separator = VALUE_SEPARATOR;
        }

        for (int loop = data.size(); loop < width; loop++) {
            Label currentCharacter = getTextLabel(VALUE_PLACEHOLDER);
            add(currentCharacter);
        }
    }

    private Label getTextLabel(String realValue) {
        return createLabelWithText(String.valueOf(realValue));
    }

    private Label getValueLabel(int realValue) {
        return createLabelWithText(HexHelpers.toHexNumberWithLeadingZeroes(realValue, VALUE_LENGTH));
    }
}
