package com.sptci.rwt.webui;

import java.sql.SQLException;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Listener;
import com.sptci.rwt.BatchQueryExecutor;
import com.sptci.rwt.Rows;

/**
 * The listener for the {@link QueryExecutorView} used to interact with
 * the {@link com.sptci.rwt.QueryExecutor} class.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: ExecuteBatchQueryListener.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class ExecuteBatchQueryListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified {@link
   * com.sptci.echo2.Controller} to interact with the application.
   *
   * @param controller The controller to use.
   */
  public ExecuteBatchQueryListener( MainController controller )
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
      final BatchQueryExecutorView view =
        (BatchQueryExecutorView) controller.getParentView( button );
      view.reset();
      processQuery( view );
    }
    catch ( Throwable t )
    {
      controller.processFatalException( Configuration.getString(
            this, "error" ), t );
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
  private void processQuery( final BatchQueryExecutorView view )
    throws SQLException
  {
    final String query = view.getQuery();
    final int maxResults = view.getMaxResults();
    final int maxColumnLength = view.getMaxColumnLength();

    if ( query.length() > 0 )
    {
      view.addToHistory( query );
      view.reset();

      BatchQueryExecutor executor =
        new BatchQueryExecutor( view.getConnectionManager() );
      int count = 0;
      for ( Rows rows : executor.execute(
          query, maxResults, maxColumnLength ) )
      {
        RowsTableModel model = new RowsTableModel( rows );
        view.addResults( "Results " + ++count, model );
      }
    }
  }
}
