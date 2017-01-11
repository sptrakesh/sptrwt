package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;
import com.sptci.rwt.ConnectionManager;

/**
 * The listener for trigger a new connection to a database through a
 * configured {@link javax.sql.DataSource}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: DataSourceListener.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class DataSourceListener extends Listener<MainController>
{
  /**
   * Create a new instance using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public DataSourceListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Update the application's
   * connection source and reset the view.
   */
  public void actionPerformed( final ActionEvent event )
  {
    ConnectionManager manager =
      new ConnectionManager( event.getActionCommand() );
    controller.setConnectionManager( manager );
  }
}
