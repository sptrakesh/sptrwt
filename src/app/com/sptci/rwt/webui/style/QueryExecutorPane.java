package com.sptci.rwt.webui.style;

import nextapp.echo2.app.SplitPane;

import com.sptci.echo2.Dimensions;

import com.sptci.rwt.webui.QueryExecutorView;

/**
 * The {@link nextapp.echo2.app.Style} for the primary {@link
 * nextapp.echo2.app.SplitPane} used to layout the {@link
 * com.sptci.rwt.webui.QueryExecutorView}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-04
 * @version $Id: QueryExecutorPane.java 3637 2007-10-21 02:11:18Z rakesh $
 */
public class QueryExecutorPane extends ExecutorPane
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    setProperty( SplitPane.PROPERTY_SEPARATOR_POSITION, Dimensions.getExtent(
        QueryExecutorView.class.getName() + ".splitpane.height" ) );
  }
}