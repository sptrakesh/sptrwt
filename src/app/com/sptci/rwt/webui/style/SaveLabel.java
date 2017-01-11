package com.sptci.rwt.webui.style;

import nextapp.echo2.app.HttpImageReference;
import nextapp.echo2.app.ImageReference;

import com.sptci.echo2.style.Label;
import com.sptci.echo2.style.Extent;

/**
 * The {@link nextapp.echo2.app.Style} for the primary {@link
 * nextapp.echo2.app.Label} component used to anchor the
 * {@link echopointng.PopUp} component used to trigger a
 * save query action.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: SaveLabel.java 3610 2007-10-13 02:17:00Z rakesh $
 */
public class SaveLabel extends Label
{
  /** The image to use as the icon for this label. */
  private static final ImageReference ICON = new HttpImageReference(
      "icons/save.png", Extent.getInstance( 20 ), Extent.getInstance( 20 ) );

  /** Over-ridden to add icon information. */
  @Override
  protected void init()
  {
    super.init();
    
    setProperty( nextapp.echo2.app.Label.PROPERTY_ICON, ICON );
    setProperty( nextapp.echo2.app.Label.PROPERTY_ICON_TEXT_MARGIN,
        Extent.getInstance( 5 ) );
  }
}
