package client.interfaces;

/**
 * Created by timmattison on 9/15/14.
 */
public interface HexData {
    public boolean isShowingAddresses();

    public void setShowAddresses(boolean showAddresses);

    public int getWidth();

    public void setWidth(int width);

    public HexPanel getHexPanel();
}
