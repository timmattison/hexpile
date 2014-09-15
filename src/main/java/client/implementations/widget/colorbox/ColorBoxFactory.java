package client.implementations.widget.colorbox;

import com.google.inject.assistedinject.Assisted;

/**
 * Created by timmattison on 9/24/14.
 */
public interface ColorBoxFactory {
    ColorBox create(@Assisted("color") String color, @Assisted("text") String text);
}
