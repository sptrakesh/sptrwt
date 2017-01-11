package com.sptci.rwt.webui.style;

import nextapp.echo2.app.SplitPane;

import com.sptci.echo2.style.General;

/**
 * The {@link nextapp.echo2.app.Style} for the primary {@link
 * nextapp.echo2.app.SplitPane} used to layout the query executor views.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: ExecutorPane.java 3637 2007-10-21 02:11:18Z rakesh $
 */
public abstract class ExecutorPane extends General
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    setProperty( SplitPane.PROPERTY_RESIZABLE, true );
    setProperty( SplitPane.PROPERTY_ORIENTATION,
        SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM );
  }
}