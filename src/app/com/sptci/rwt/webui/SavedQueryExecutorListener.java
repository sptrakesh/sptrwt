package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;

/**
 * Action listener for executing a saved SQL statement (which may be a
 * batch of statements) using {@link BatchQueryExecutorView}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-13
 * @version $Id: SavedQueryExecutorListener.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class SavedQueryExecutorListener extends ExecutorListener
{
  /** The query that is to be executed. */
  private final String statement;

  /**
   * Create a new instance of the listener using the specified query and
   * controller.
   *
   * @param statement The statement that is to be executed.
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SavedQueryExecutorListener( final String statement,
      final MainController controller )
  {
    super( controller );
    this.statement = statement;
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
      final BatchQueryExecutorView view =
        new BatchQueryExecutorView( controller, statement );
      controller.addPane( view );
    }
  }
}
