package com.sptci.rwt.webui;

import java.sql.SQLException;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;

/**
 * The listener for the {@link QueryExecutorView} used to interact with
 * the {@link com.sptci.rwt.QueryExecutor} class.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-04
 * @version $Id: ExecuteQueryListener.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class ExecuteQueryListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified {@link
   * com.sptci.echo2.Controller} to interact with the application.
   *
   * @param controller The controller to use.
   */
  public ExecuteQueryListener( MainController controller )
  {
    super( controller );
  }

  /**
   * The {@link nextapp.echo2.app.event.ActionListener} implementation.
   * Submit the entered query to the database server for execution.  Handle
   * query execution in the background to enable cancellation of the
   * query.
   *
   * @param event The action event that was triggered by the user.
   */
  public void actionPerformed( ActionEvent event )
  {
    final Button button = (Button) event.getSource();
    button.setEnabled( false );

    try
    {
      final QueryExecutorView view =
        (QueryExecutorView) controller.getParentView( button );
      view.reset();
      processQuery( view );
    }
    finally
    {
      button.setEnabled( true );
    }
  }

  /**
   * Process the query entered in {@link QueryExecutorView#query} field.
   *
   * @param view The view from which this action was triggered.
   * @throws SQLException If errors are encountered while executing
   *   the query.
   */
  private void processQuery( final QueryExecutorView view )
  {
    final String query = view.getQuery();
    final int maxResults = view.getMaxResults();
    final int maxColumnLength = view.getMaxColumnLength();

    if ( query.length() > 0 )
    {
      view.addToHistory( query );
      final RowTableModel model = new RowTableModel(
          query, maxResults, maxColumnLength, view.getConnectionManager() );
      view.setResults( new RowTable( model ) );
    }
  }
}
