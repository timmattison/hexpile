package client.implementations.widget.highlighting;

import client.dataprocessing.HexChunk;
import client.implementations.widget.AbstractHexLine;
import client.implementations.widget.HexHelpers;
import client.implementations.widget.HtmlHelpers;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
public abstract class AbstractHighlightingTextHexLine extends AbstractHexLine {
    private final List<HexChunk> hexChunks;

    @Inject
    public AbstractHighlightingTextHexLine(@Assisted("address") int address,
                                           @Assisted("hexChunks") List<HexChunk> hexChunks,
                                           @Assisted("width") int width) {
        super(address, width);
        this.hexChunks = hexChunks;
    }

    protected void addAddress() {
        String addressText = "0x" + HexHelpers.toHexNumberWithLeadingZeroes(address, ADDRESS_LENGTH);
        addAddress(addressText);
    }

    protected abstract void addAddress(String addressText);

    protected void populatePanel() {
        addAddress();
        addHexBytesAndText();
    }

    private void addHexBytesAndText() {
        // Build the list of all hex data and text data we want to display
        List<HTML> hexList = new ArrayList<HTML>();
        List<HTML> textList = new ArrayList<HTML>();

        // Track the total size so we know how much padding to do at the end
        int totalSize = 0;

        HTML separatorHTML = createNewHtmlContainer();
        separatorHTML.setHTML(VALUE_SEPARATOR);
        textList.add(separatorHTML);

        // Loop through all of the chunks
        for (HexChunk hexChunk : hexChunks) {
            HTML hexBytesHtml = getNewHtmlObject();
            HTML textHtml = getNewHtmlObject();

            hexBytesHtml.setHTML(hexChunk.hexData);
            textHtml.setHTML(hexChunk.text);

            // Does this data have a name?
            if ((hexChunk.name != null) && (!hexChunk.name.equals(""))) {
                // Yes, it needs to be highlighted.  Get the current color.
                setColors(hexChunk, hexBytesHtml, textHtml);

                List<HTML> associatedHtml;

                if (!innerGetAssociatedHtml().containsKey(hexChunk.name)) {
                    innerGetElementOrderingList().add(hexChunk.name);
                    associatedHtml = new ArrayList<HTML>();

                    innerGetAssociatedHtml().put(hexChunk.name, associatedHtml);
                } else {
                    associatedHtml = innerGetAssociatedHtml().get(hexChunk.name);
                }

                associatedHtml.add(hexBytesHtml);
                associatedHtml.add(textHtml);
            }

            // Add a whitespace separator to the hex list
            separatorHTML = createNewHtmlContainer();
            separatorHTML.setHTML(VALUE_SEPARATOR);
            hexList.add(separatorHTML);

            // Add the length of the text to the total size
            totalSize = totalSize + textHtml.getText().length();

            // Add this hex data and text data to the list of HTML containers we need to add to the panel
            hexList.add(hexBytesHtml);
            textList.add(textHtml);
        }

        // Pad any left over space if necessary
        padLeftOverSpace(hexList, textList, totalSize);

        addHexList(hexList);
        addTextList(textList);
    }

    protected abstract void addTextList(List<HTML> textList);

    protected abstract void addHexList(List<HTML> hexList);

    private void padLeftOverSpace(List<HTML> hexList, List<HTML> textList, int totalSize) {
        // Is there space left over?
        if (totalSize != width) {
            // Yes, create some placeholders
            HTML hexBytesPlaceholder = getNewHtmlObject();
            HTML textPlaceholder = getNewHtmlObject();

            // Create string builders so we can build up the padding data
            StringBuilder hexStringBuilder = new StringBuilder();
            StringBuilder textStringBuilder = new StringBuilder();

            // Pad as much as we need to
            for (int loop = totalSize; loop < width; loop++) {
                // Use the value placeholder for hex data and the value separator for text
                hexStringBuilder.append(VALUE_PLACEHOLDER);
                textStringBuilder.append(VALUE_SEPARATOR);
            }

            hexBytesPlaceholder.setHTML(hexStringBuilder.toString());
            textPlaceholder.setHTML(textStringBuilder.toString());

            hexList.add(hexBytesPlaceholder);
            textList.add(textPlaceholder);
        }
    }

    protected HTML getNewHtmlObject() {
        return new HTML();
    }

    private void setColors(HexChunk hexChunk, HTML hexBytesHtml, HTML textHtml) {
        // Set the color as the background color of the hex data and the text
        //setBackgroundColor(hexBytesHtml, hexChunk.backgroundColor);
        //setBackgroundColor(textHtml, hexChunk.backgroundColor);

        HtmlHelpers.setColor(hexBytesHtml, hexChunk.textColor);
        HtmlHelpers.setColor(textHtml, hexChunk.textColor);
        HtmlHelpers.setNoWrap(hexBytesHtml);
        HtmlHelpers.setNoWrap(textHtml);
    }

    private HTML createNewHtmlContainer() {
        HTML html = getNewHtmlObject();
        return html;
    }
}
