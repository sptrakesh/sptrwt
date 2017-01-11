package com.sptci.rwt.webui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Listener;

/**
 * The action listener used to display {@link HistoryView} instances for
 * the {@link ExecutorView} specified.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-13
 * @version $Id: HistoryListener.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class HistoryListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener for the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public HistoryListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Displays a {@link HistoryView}
   * component that displays the statement history for the view from
   * which the history was launched.
   *
   * @param event The action event that was triggered.
   */
  public void actionPerformed( ActionEvent event )
  {
    ExecutorView view = (ExecutorView)
      controller.getParentView( (Button) event.getSource() );
    controller.addPane( new HistoryView( view, controller ) );
  }
}
