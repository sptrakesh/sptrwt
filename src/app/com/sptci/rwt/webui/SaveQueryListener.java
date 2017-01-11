package com.sptci.rwt.webui;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.event.ActionEvent;

import echopointng.PopUp;

import com.sptci.echo2.Listener;
import com.sptci.rwt.Query;

/**
 * Action listener for saving the query in {@link QueryExecutorView} to
 * the application persistent storage area.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-05
 * @version $Id: SaveQueryListener.java 3667 2007-10-31 00:36:36Z rakesh $
 */
public class SaveQueryListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SaveQueryListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Saves the query to application
   * persistent storage.
   *
   * @see com.sptci.rwt.Queries#add
   * @see MainController#resetMenu
   * @param event The event that triggers the save process.
   */
  public void actionPerformed( ActionEvent event )
  {
    SaveQueryComponent dest = (SaveQueryComponent)
      controller.getParentView( (Component) event.getSource() );
    PopUp popup = (PopUp) dest.getParent();
    ExecutorView view = (ExecutorView) controller.getParentView( popup );

    Query query = new Query( dest.getName(), view.getQuery() );
    controller.getQueries().add( dest.getCategory(), query );
    controller.resetMenu();
    popup.setExpanded( false );
  }
}
