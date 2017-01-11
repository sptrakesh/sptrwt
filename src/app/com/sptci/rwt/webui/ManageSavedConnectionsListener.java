package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;
import com.sptci.echo2.Listener;

/**
 * The listener used to display a {@link ManageSavedConnectionsView} 
 * component.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: ManageSavedConnectionsListener.java 3617 2007-10-15 00:13:55Z rakesh $
 */
public class ManageSavedConnectionsListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public ManageSavedConnectionsListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Displays a {@link
   * ManageSavedConnectionsView} component.
   *
   * @param event The action event that was triggered.
   */
  public void actionPerformed( ActionEvent event )
  {
    controller.addPane( new ManageSavedConnectionsView( controller ) );
  }
}
