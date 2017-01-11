package com.sptci.rwt.webui;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.ErrorPane;
import com.sptci.echo2.Listener;

/**
 * An abstract {@link nextapp.echo2.app.event.ActionListener} used to
 * listen to events triggered to open {@link ExecutorView} components.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: ExecutorListener.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public abstract class ExecutorListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified {@link
   * com.sptci.echo2.Controller} to interact with the application.
   *
   * @param controller The controller to use.
   */
  public ExecutorListener( MainController controller )
  {
    super( controller );
  }

  /**
   * Display an error messages notifying user of lack of a {@link
   * com.sptci.rwt.ConnectionManager} for the session.
   */
  protected boolean checkConnection()
  {
    boolean result = true;

    if ( controller.getConnectionManager() == null )
    {
      ErrorPane pane = new ErrorPane(
          Configuration.getString( ExecutorListener.class,
            "noConnectionManager.title" ),
          Configuration.getString( ExecutorListener.class,
            "noConnectionManager.message" ) );
      controller.addPane( pane );
      result = false;
    }

    return result;
  }
}
