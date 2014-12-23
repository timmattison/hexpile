package client;

import client.dataprocessing.values.BinaryData;
import client.factories.HexDataFactory;
import client.injectors.HexpileGinjector;
import client.interfaces.HexData;
import client.interfaces.HexPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 9/15/14.
 */
class Hexpile implements EntryPoint {
    private static final String OUTPUT = "_output";
    private static final String HEXPILE = "hexpile_";
    private final HexpileGinjector injector = GWT.create(HexpileGinjector.class);
    private static HexpileGinjector staticInjector;

    public void onModuleLoad() {
        exportMyFunction();
        staticInjector = injector;

        reformatAll();
    }

    private void reformatAll() {
        NodeList<Element> divs = RootPanel.getBodyElement().getElementsByTagName("div");

        for (int loop = 0; loop < divs.getLength(); loop++) {
            Element div = divs.getItem(loop);
            String divId = div.getId();

            if (divId.startsWith(HEXPILE) && (!divId.endsWith(OUTPUT))) {
                reformat(divId, divId + OUTPUT);
            }
        }
    }

    public static native void exportMyFunction()/*-{
        $wnd.reformat = @client.Hexpile::reformat(Ljava/lang/String;Ljava/lang/String;);
    }-*/;

    private static void displayHighlightedBinary(String divId, String data) {
        String[] lines = data.split("\\n");
        List<String> dataList = new ArrayList<String>();

        for (String line : lines) {
            if (line.length() == 0) {
                continue;
            }

            String[] elements = line.split(":");

            if ((elements.length % 2) != 0) {
                throw new UnsupportedOperationException("Badly formatted data");
            }

            String description = elements[0].trim();
            String hex = elements[1].trim().replaceAll("^0x", "");

            dataList.add(description);
            dataList.add(hex);
        }

        try {
            List<BinaryData> binaryDataList = dataListToBinaryData(dataList.toArray(new String[dataList.size()]));
            HexDataFactory hexDataFactory = staticInjector.getHexDataFactory();
            HexData hexData = hexDataFactory.create(0, binaryDataList);
            HexPanel hexPanel = hexData.getHexPanel();
            RootPanel div = RootPanel.get(divId);
            div.clear();
            div.add(hexPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void reformat(String inputDivId, String outputDivId) {
        RootPanel inputDiv = RootPanel.get(inputDivId);

        String data = inputDiv.getElement().getInnerText();
        displayHighlightedBinary(outputDivId, data);

        inputDiv.getElement().getStyle().setDisplay(Style.Display.NONE);
    }

    private static List<BinaryData> dataListToBinaryData(String[] dataList) {
        List<BinaryData> output = new ArrayList<BinaryData>();
        for (int loop = 0; loop < (dataList.length / 2); loop++) {
            int position = loop * 2;
            List<Byte> bytes = convertDataToBytes(dataList[position + 1]);
            BinaryData binaryData = new BinaryData();
            binaryData.name = dataList[position];
            binaryData.data = bytes;
            output.add(binaryData);
        }
        return output;
    }

    private static List<Byte> convertDataToBytes(String data) {
        if ((data.length() % 2) != 0) {
            throw new UnsupportedOperationException("Data must be hex and its length must be divisible by 2");
        }

        int byteCount = data.length() / 2;

        List<Byte> output = new ArrayList<Byte>();

        for (int loop = 0; loop < byteCount; loop++) {
            String currentHexByte = data.substring(loop * 2, (loop + 1) * 2);

            Byte currentByte = (byte) ((Character.digit(currentHexByte.charAt(0), 16) << 4) + Character.digit(currentHexByte.charAt(1), 16));

            output.add(currentByte);
        }

        return output;
    }
}
