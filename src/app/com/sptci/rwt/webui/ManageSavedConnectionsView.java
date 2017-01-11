package com.sptci.rwt.webui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.ExpandableSection;

import com.sptci.echo2.Utilities;
import com.sptci.echo2.WindowPane;
import com.sptci.rwt.DatabaseType;

/**
 * The component that is used to delete saved connections.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: ManageSavedConnectionsView.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class ManageSavedConnectionsView extends WindowPane
{
  /** The controller used to interact with the application. */
  private final MainController controller;

  /**
   * Create a new instance using the specified controller.
   *
   * @param controller The {@link #controller} to use to interact with the
   *   application.
   */
  public ManageSavedConnectionsView( final MainController controller )
  {
    this.controller = controller;
  }

  /**
   * Life-cycle method invoked when the component is added to the component
   * hierarchy.
   *
   * @see #createDatabaseType
   */
  @Override
  public void init()
  {
    removeAll();
    Column column = new Column();

    for ( DatabaseType type : controller.getConnections().getDatabaseTypes() )
    {
      column.add( createDatabaseType( type ) );
    }

    add( column );
  }

  /**
   * Create the component that displays the saved connections in a specified
   * database type.
   *
   * @param type The database type for which saved connections are available.
   */
  private Component createDatabaseType( final DatabaseType type )
  {
    ExpandableSection section = new ExpandableSection( type.getName() );
    Grid grid = new Grid();

    for ( String name : type.getNames() )
    {
      Label label = new Label( name );
      label.setStyleName( "Title.Label" );
      grid.add( label );
      grid.add( Utilities.createButton( getClass().getName(), "delete",
          new DeleteSavedConnectionListener(
            type.getName(), name, controller ) ) );
    }

    section.add( grid );
    return section;
  }
}
