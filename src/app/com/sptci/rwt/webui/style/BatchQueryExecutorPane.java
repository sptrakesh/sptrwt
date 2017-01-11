package com.sptci.rwt.webui.style;

import nextapp.echo2.app.SplitPane;

import com.sptci.echo2.Dimensions;
import com.sptci.echo2.style.Extent;

import com.sptci.rwt.webui.BatchQueryExecutorView;

/**
 * The {@link nextapp.echo2.app.Style} for the primary {@link
 * nextapp.echo2.app.SplitPane} used to layout the {@link
 * com.sptci.rwt.webui.BatchQueryExecutorView}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: BatchQueryExecutorPane.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class BatchQueryExecutorPane extends ExecutorPane
{
  /**
   * Initialise the style properties.
   */
  @Override
  protected void init()
  {
    super.init();
    setProperty( SplitPane.PROPERTY_SEPARATOR_POSITION,
        Extent.getInstance( Dimensions.getInt(
        BatchQueryExecutorView.class.getName() + ".splitpane.height" ) ) );
  }
}