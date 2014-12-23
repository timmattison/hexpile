package client.modules;

import client.dataprocessing.BasicBinaryDataSlicer;
import client.dataprocessing.BasicHexChunkProcessor;
import client.dataprocessing.interfaces.BinaryDataSlicer;
import client.dataprocessing.interfaces.HexChunkProcessor;
import client.factories.HexDataFactory;
import client.factories.HexLineFactory;
import client.factories.HexPanelFactory;
import client.implementations.widget.BasicHexData;
import client.implementations.widget.ColorPicker;
import client.implementations.widget.FancyColorPicker;
import client.implementations.widget.HexPanelWithColorBoxes;
import client.implementations.widget.colorbox.BasicColorBox;
import client.implementations.widget.colorbox.ColorBox;
import client.implementations.widget.colorbox.ColorBoxFactory;
import client.implementations.widget.highlighting.SpanBasedHighlightingTextHexLine;
import client.interfaces.HexData;
import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

/**
 * Created by timmattison on 9/16/14.
 */
public class HexpileModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().implement(HexData.class, BasicHexData.class).build(HexDataFactory.class));
        install(new GinFactoryModuleBuilder().implement(HexLine.class, SpanBasedHighlightingTextHexLine.class).build(HexLineFactory.class));
        //install(new GinFactoryModuleBuilder().implement(HexPanel.class, VerticalPanelHexPanel.class).build(HexPanelFactory.class));
        install(new GinFactoryModuleBuilder().implement(HexPanel.class, HexPanelWithColorBoxes.class).build(HexPanelFactory.class));
        install(new GinFactoryModuleBuilder().implement(ColorBox.class, BasicColorBox.class).build(ColorBoxFactory.class));

        bind(HexChunkProcessor.class).to(BasicHexChunkProcessor.class);
        bind(ColorPicker.class).to(FancyColorPicker.class).in(Singleton.class);
        bind(BinaryDataSlicer.class).to(BasicBinaryDataSlicer.class);
    }
}
