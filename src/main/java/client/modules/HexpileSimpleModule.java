package client.modules;

import client.dataprocessing.BasicBinaryDataSlicer;
import client.dataprocessing.BasicHexChunkProcessor;
import client.dataprocessing.BinaryDataSlicer;
import client.dataprocessing.HexChunkProcessor;
import client.factories.HexDataFactory;
import client.factories.HexLineFactory;
import client.factories.HexPanelFactory;
import client.implementations.widget.*;
import client.interfaces.HexData;
import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

/**
 * Created by timmattison on 9/17/14.
 */
public class HexpileSimpleModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().implement(HexData.class, BasicHexData.class).build(HexDataFactory.class));
        install(new GinFactoryModuleBuilder().implement(HexLine.class, SimpleTextHexLine.class).build(HexLineFactory.class));
        install(new GinFactoryModuleBuilder().implement(HexPanel.class, VerticalPanelHexPanel.class).build(HexPanelFactory.class));

        bind(HexChunkProcessor.class).to(BasicHexChunkProcessor.class);
        bind(ColorPicker.class).to(FancyColorPicker.class).in(Singleton.class);
        bind(BinaryDataSlicer.class).to(BasicBinaryDataSlicer.class);
    }
}
