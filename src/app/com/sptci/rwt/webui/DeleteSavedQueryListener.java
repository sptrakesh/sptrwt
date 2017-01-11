package com.sptci.rwt.webui;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Confirmation;
import com.sptci.echo2.Executor;
import com.sptci.echo2.Listener;

import com.sptci.rwt.Queries;
import com.sptci.rwt.Category;

/**
 * The component that is used to delete saved queries.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: DeleteSavedQueryListener.java 3694 2007-11-10 00:26:46Z rakesh $
 */
public class DeleteSavedQueryListener extends Listener<MainController>
{
  /** The category under which the query is saved. */
  private final String category;

  /** The name of the saved query. */
  private final String name;

  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param category The name of the database type under which the
   *   saved query is organised.
   * @param name The name of the saved query.
   * @param controller The controller to use to interact with the
   *   application
   */
  public DeleteSavedQueryListener( final String category,
      final String name, final MainController controller )
  {
    super( controller );
    this.category = category;
    this.name = name;
  }

  /**
   * The action listener implementation.  Launches a {@link
   * com.sptci.echo2.Confirmation} dialogue prompting user to confirm
   * the delete action.
   *
   * @param event The action event that was triggered.
   */
  public void actionPerformed( final ActionEvent event )
  {
    Executor<DeleteSavedQueryListener> executor =
      new Executor<DeleteSavedQueryListener>( this, "delete" );
    executor.addParameter( Component.class, (Component) event.getSource() );

    String message = Configuration.getString( this, "delete.message" );
    message = message.replaceAll( "\\$name\\$", name );

    Confirmation confirmation = new Confirmation(
        Configuration.getString( this, "delete.title" ), message, executor );
    controller.addPane( confirmation );
  }

  /**
   * Delete the saved query and update the {@link
   * ManageSavedQueriesView} view component.
   *
   * @see com.sptci.rwt.Queries#delete( String, String )
   * @see com.sptci.rwt.Queries#delete( String )
   * @see MainController#resetMenu
   * @param component The component that is to be removed from the view.
   */
  protected void delete( final Component component )
  {
    Queries queries = controller.getQueries();
    queries.delete( category, name );

    Component parent = component.getParent();
    int index = parent.indexOf( component );
    parent.remove( --index );
    parent.remove( index );

    Category cat = queries.getCategory( category );
    if ( cat.getQueries().isEmpty() )
    {
      queries.delete( category );
      Component p = parent.getParent().getParent();
      p.remove( parent.getParent() );
    }

    controller.resetMenu();
  }
}
