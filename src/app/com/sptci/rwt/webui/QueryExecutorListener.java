package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

/**
 * The listener used by {@link MenuComponent} to display a new {@link
 * QueryExecutorView} component.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-06
 * @version $Id: QueryExecutorListener.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class QueryExecutorListener extends ExecutorListener
{
  /**
   * Create a new instance of the listener using the specified {@link
   * com.sptci.echo2.Controller} to interact with the application.
   *
   * @param controller The controller to use.
   */
  public QueryExecutorListener( MainController controller )
  {
    super( controller );
  }

  /**
   * The {@link nextapp.echo2.app.event.ActionListener} implementation.
   * Display a new instance of {@link QueryExecutorView}.
   *
   * @param event The action event that was triggered by the user.
   */
  public void actionPerformed( ActionEvent event )
  {
    if ( checkConnection() )
    {
      controller.addPane( new QueryExecutorView( controller ) );
    }
  }
}
