package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;
import com.sptci.rwt.Connections;
import com.sptci.rwt.ConnectionParameters;
import com.sptci.rwt.ConnectionManager;

/**
 * The listener for trigger a new connection to a database through a
 * configured {@link java.sql.Connection}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-30
 * @version $Id: ConnectionDialogueListener.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class ConnectionDialogueListener extends Listener<MainController>
{
  /**
   * Create a new instance using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public ConnectionDialogueListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Display the {@link
   * ConnectionDialogue} in {@link MainView}.
   */
  public void actionPerformed( final ActionEvent event )
  {
    controller.addPane( new ConnectionDialogue( controller ) );
  }
}
