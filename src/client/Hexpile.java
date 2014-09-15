package client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by timmattison on 9/15/14.
 */
public class Hexpile implements EntryPoint {
    public void onModuleLoad() {
        exportMyFunction();
    }

    public static native void exportMyFunction()/*-{
        $wnd.handleAnchorClick = @client.Hexpile::handleAnchorClick(Lcom/google/gwt/core/client/JsArrayMixed;);
    }-*/;

    public static void handleAnchorClick(JsArrayMixed args) {
        Window.alert("Current row and Column is " +
                args.getNumber(0) + "  " + args.getNumber(1));
    }
}
