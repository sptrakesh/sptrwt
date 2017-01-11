package com.sptci.rwt.webui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.sptci.echo2.WindowPane;

/**
 * A view component used to display the {@link ExecutorView#history}
 * information.  History is displayed as {@link nextapp.echo2.app.Button}
 * components which will reset the {@link ExecutorView#query} component.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-13
 * @version $Id: HistoryView.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class HistoryView extends WindowPane
{
  /** The executor view to which this component is bound. */
  private final ExecutorView view;
  
  /** Parent Controller. */
  private final MainController controller;

  /**
   * Create instance of the pane using the specified controller.
   *
   * @param view The {@link #view} to which this view is bound.
   * @param controller The controller to use to interact with the rest of
   *   the application.
   */
  public HistoryView( final ExecutorView view,
      final MainController controller )
  {
    this.view = view;
    this.controller = controller;
  }

  /** Life-cycle method invoked when component is added to the UI. */
  @Override
  public void init()
  {
    removeAll();
    final Column column = new Column();

    for ( String statement : view.getHistory().keySet() )
    {
      final Button button = new Button( statement );
      button.setStyleName( "Link.Button" );
      button.addActionListener( new HistoryViewListener() );
      column.add( button );
    }

    add( column );
  }

  /**
   * The action listener used to update the {@link ExecutorView#query}
   * field with the previous statement.
   */
  protected class HistoryViewListener implements ActionListener
  {
    /**
     * The action listener implementation.  Invokes {@link
     * ExecutorView#setQuery} with the statement value.
     *
     * @param event The action event that was triggered.
     */
    public void actionPerformed( final ActionEvent event )
    {
      Button button = (Button) event.getSource();
      view.setQueryFromHistory( button.getText() );
      HistoryView.this.userClose();
    }
  }
}
