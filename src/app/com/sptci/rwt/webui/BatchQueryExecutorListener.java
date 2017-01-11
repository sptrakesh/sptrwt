package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;

/**
 * Action listener for executing a batch of SQL statements entered into
 * {@link BatchQueryExecutorView}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-05
 * @version $Id: BatchQueryExecutorListener.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class BatchQueryExecutorListener extends ExecutorListener
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public BatchQueryExecutorListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Executes the query and exports
   * the results.
   *
   * @param event The event that triggers the export process.
   */
  public void actionPerformed( ActionEvent event )
  {
    if ( checkConnection() )
    {
      controller.addPane( new BatchQueryExecutorView( controller ) );
    }
  }
}
