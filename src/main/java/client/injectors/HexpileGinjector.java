package client.injectors;

import client.factories.HexDataFactory;
import client.modules.HexpileModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * Created by timmattison on 9/16/14.
 */
@GinModules(HexpileModule.class)
public interface HexpileGinjector extends Ginjector {
    HexDataFactory getHexDataFactory();
}
