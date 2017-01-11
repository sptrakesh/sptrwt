package com.sptci.rwt.webui;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.event.ActionEvent;

import echopointng.PopUp;

import com.sptci.echo2.Listener;
import com.sptci.rwt.ConnectionParameters;

/**
 * Action listener for saving the connection in {@link ConnectionDialogue}
 * to the application persistent storage area.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: SaveConnectionListener.java 3623 2007-10-15 10:52:13Z rakesh $
 */
public class SaveConnectionListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SaveConnectionListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Saves the connection to
   * application persistent storage.
   *
   * @see MainController#getParameters
   * @see com.sptci.rwt.Connections#add
   * @see MainController#resetMenu
   * @param event The event that triggers the save process.
   */
  public void actionPerformed( ActionEvent event )
  {
    SaveConnectionComponent dest = (SaveConnectionComponent)
      controller.getParentView( (Component) event.getSource() );
    PopUp popup = (PopUp) dest.getParent();

    ConnectionDialogue view =
      (ConnectionDialogue) controller.getParentView( popup );
    ConnectionParameters parameters = controller.getParameters( view );

    controller.getConnections().add( dest.getName(), parameters );
    controller.resetMenu();
    popup.setExpanded( false );
  }
}
