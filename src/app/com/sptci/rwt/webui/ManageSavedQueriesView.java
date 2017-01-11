package com.sptci.rwt.webui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.ExpandableSection;

import com.sptci.echo2.Utilities;
import com.sptci.echo2.WindowPane;
import com.sptci.rwt.Category;

/**
 * The component that is used to delete saved queries.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: ManageSavedQueriesView.java 3617 2007-10-15 00:13:55Z rakesh $
 */
public class ManageSavedQueriesView extends WindowPane
{
  /** The controller used to interact with the application. */
  private final MainController controller;

  /**
   * Create a new instance using the specified controller.
   *
   * @param controller The {@link #controller} to use to interact with the
   *   application.
   */
  public ManageSavedQueriesView( final MainController controller )
  {
    this.controller = controller;
  }

  /**
   * Life-cycle method invoked when the component is added to the component
   * hierarchy.
   *
   * @see #createCategory
   */
  @Override
  public void init()
  {
    removeAll();
    Column column = new Column();

    for ( Category category : controller.getQueries().getCategories() )
    {
      column.add( createCategory( category ) );
    }

    add( column );
  }

  /**
   * Create the component that displays the saved queries in a specified
   * database category.
   *
   * @param category The database category for which saved queries are available.
   */
  private Component createCategory( final Category category )
  {
    ExpandableSection section = new ExpandableSection( category.getName() );
    Grid grid = new Grid();

    for ( String name : category.getNames() )
    {
      Label label = new Label( name );
      label.setStyleName( "Title.Label" );
      grid.add( label );
      grid.add( Utilities.createButton( getClass().getName(), "delete",
          new DeleteSavedQueryListener(
            category.getName(), name, controller ) ) );
    }

    section.add( grid );
    return section;
  }
}
