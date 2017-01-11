package com.sptci.rwt.webui.style;

import nextapp.echo2.app.SplitPane;

import com.sptci.echo2.Dimensions;
import com.sptci.echo2.style.Background;
import com.sptci.echo2.style.Extent;
import com.sptci.echo2.style.General;

import com.sptci.rwt.webui.MainView;

/**
 * The {@link nextapp.echo2.app.Style} for the primary {@link
 * nextapp.echo2.app.SplitPane} used to layout the application {@link
 * nextapp.echo2.app.ContentPane}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-30
 * @version $Id: MainSplitPane.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class MainSplitPane extends General
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    setProperty( SplitPane.PROPERTY_BACKGROUND, Background.getInstance() );
    setProperty( SplitPane.PROPERTY_SEPARATOR_POSITION,
        Dimensions.getExtent( MainView.class, "mainSplitPane.height" ) );
    setProperty( SplitPane.PROPERTY_SEPARATOR_WIDTH,
        Extent.getInstance( 1, Extent.PX ) );
  }
}
