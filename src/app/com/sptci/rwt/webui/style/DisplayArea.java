package com.sptci.rwt.webui.style;

import nextapp.echo2.app.SplitPane;

import com.sptci.echo2.Dimensions;
import com.sptci.echo2.style.Background;
import com.sptci.echo2.style.Extent;
import com.sptci.echo2.style.General;

import com.sptci.rwt.webui.MainView;

/**
 * The {@link nextapp.echo2.app.StyleSheet} for the application.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-30
 * @version $Id: DisplayArea.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class DisplayArea extends General
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    setProperty( SplitPane.PROPERTY_RESIZABLE, true );
    setProperty( SplitPane.PROPERTY_SEPARATOR_POSITION,
        Extent.getInstance( Dimensions.getInt(
        MainView.class.getName() + ".displayArea.width" ) ) );
  }
}