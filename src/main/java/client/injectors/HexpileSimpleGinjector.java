package client.injectors;

import client.factories.HexDataFactory;
import client.modules.HexpileSimpleModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * Created by timmattison on 9/16/14.
 */
@GinModules(HexpileSimpleModule.class)
public interface HexpileSimpleGinjector extends Ginjector {
    HexDataFactory getHexDataFactory();
}
