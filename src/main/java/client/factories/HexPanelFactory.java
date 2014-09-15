package client.factories;

import client.interfaces.HexLine;
import client.interfaces.HexPanel;
import com.google.inject.assistedinject.Assisted;

import java.util.List;

/**
 * Created by timmattison on 9/16/14.
 */
public interface HexPanelFactory {
    public HexPanel create(@Assisted("hexLines") List<HexLine> hexLines);
}
