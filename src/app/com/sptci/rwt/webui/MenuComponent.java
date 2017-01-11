package com.sptci.rwt.webui;

import java.util.Collection;
import java.util.Map;

import echopointng.Menu;
import echopointng.MenuBar;
import echopointng.MenuItem;

import com.sptci.echo2.Configuration;
import com.sptci.epng.Logout;
import com.sptci.rwt.Category;
import com.sptci.rwt.DatabaseType;
import com.sptci.rwt.Query;

/**
 * The component that displays the application menu.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: MenuComponent.java 3620 2007-10-15 01:50:04Z rakesh $
 */
public class MenuComponent extends MenuBar
{
  /** The main application controller to use for this component. */
  private final MainController controller;

  /** The menu used to store saved JDBC connections. */
  private Menu savedConnections;

  /** The menu used to store saved SQL statements. */
  private Menu savedQueries;

  /**
   * Default constructor.  Initialises the menu bar.
   *
   * @see #createConnections
   * @see #createQueries
   */
  public MenuComponent( final MainController controller )
  {
    this.controller = controller;
    createConnectionsMenu();
    createQueries();
  }

  /**
   * Create the menu that displays the connection options.
   *
   * @see #createDataSources
   * @see #createConnections
   * @see #createConnection
   */
  private void createConnectionsMenu()
  {
    Menu menu = new Menu(
        Configuration.getString( this, "connectionMenu.text" ) );

    menu.add( createDataSources() );
    menu.add( createConnections() );
    menu.add( createConnection() );
    menu.add( new Logout( Configuration.getString( this, "logout" ) ) );

    add( menu );
  }

  /**
   * Create the menu used to display all available {@link
   * javax.sql.DataSource}s configured for the application.
   *
   * @return The menu with all available datasources as menu items.
   */
  private Menu createDataSources()
  {
    final Menu menu = new Menu( Configuration.getString(
          this, "connectionMenu.dataSources.text" ) );

    final DataSourceListener listener = new DataSourceListener( controller );
    for ( String name : controller.getDataSources() )
    {
      final MenuItem item = new MenuItem( name );
      item.setActionCommand( name );
      item.addActionListener( listener );
      menu.add( item );
    }

    return menu;
  }

  /**
   * Create the menu used to display all the saved JDBC {@link
   * java.sql.Connection}s for the application user.
   *
   * @return The menu with all available saved connections.
   */
  private Menu createConnections()
  {
    savedConnections = new Menu( Configuration.getString(
          this, "connectionMenu.connections.text" ) );

    final MenuItem manage = new MenuItem( Configuration.getString(
          this, "manageConnections" ) );
    manage.addActionListener(
        new ManageSavedConnectionsListener( controller ) );
    savedConnections.add( manage );

    for ( Map.Entry<String,Collection<String>> entry :
        controller.getSavedConnections().entrySet() )
    {
      final Menu sub = new Menu( entry.getKey() );

      for ( String name : entry.getValue() )
      {
        final MenuItem item = new MenuItem( name );
        final DatabaseType databaseType =
          controller.getConnections().getDatabaseType( entry.getKey() );
        item.addActionListener(
            new SavedConnectionListener( databaseType, name, controller ) );
        sub.add( item );
      }

      savedConnections.add( sub );
    }

    return savedConnections;
  }

  /**
   * Create the menu item used to display the connection dialogue.
   *
   * @return The menu item to launch the connection dialogue.
   */
  private MenuItem createConnection()
  {
    MenuItem item = new MenuItem( Configuration.getString(
          this, "connectionMenu.createConnection.text" ) );
    item.setToolTipText( Configuration.getString(
          this, "connectionMenu.createConnection.tooltip" ) );
    item.addActionListener( new ConnectionDialogueListener( controller ) );
    return item;
  }

  /**
   * Create the menu that displays query windows and saved queries.
   *
   * @see #createQueryExecutor
   * @see #createBatchQueryExecutor
   * @see #createSavedQueries()
   */
  private void createQueries()
  {
    Menu menu = new Menu(
        Configuration.getString( this, "queryMenu.text" ) );

    menu.add( createQueryExecutor() );
    menu.add( createBatchQueryExecutor() );
    menu.add( createSavedQueries() );

    add( menu );
  }

  /**
   * Create the menu item that is used to launch the {@link
   * QueryExecutorView} component.
   *
   * @return The menu item to launch the query executor component.
   */
  private MenuItem createQueryExecutor()
  {
    MenuItem item = new MenuItem( Configuration.getString(
          this, "queryMenu.queryExecutor.text" ) );
    item.setToolTipText( Configuration.getString(
          this, "queryMenu.queryExecutor.tooltip" ) );
    item.addActionListener( new QueryExecutorListener( controller ) );
    return item;
  }

  /**
   * Create the menu item that is used to launch the {@link
   * BatchQueryExecutorView} component.
   *
   * @return The menu item to launch the batch query executor component.
   */
  private MenuItem createBatchQueryExecutor()
  {
    MenuItem item = new MenuItem( Configuration.getString(
          this, "queryMenu.batchQueryExecutor.text" ) );
    item.setToolTipText( Configuration.getString(
          this, "queryMenu.batchQueryExecutor.tooltip" ) );
    item.addActionListener( new BatchQueryExecutorListener( controller ) );
    return item;
  }

  /**
   * Create the menu used to display all the saved queries for the user
   * and application.
   *
   * @see #createSavedQueries( Category )
   * @return The menu used to organise the saved queries.
   */
  private Menu createSavedQueries()
  {
    savedQueries = new Menu(
        Configuration.getString( this, "savedQueriesMenu.text" ) );

    final MenuItem item =
      new MenuItem( Configuration.getString( this, "manageQueries" ) );
    item.addActionListener( new ManageSavedQueriesListener( controller ) );
    savedQueries.add( item );

    for ( Category category : controller.getCategories() )
    {
      savedQueries.add( createSavedQueries( category ) );
    }

    return savedQueries;
  }

  /**
   * Create the necessary menu's to display the saved queries under the
   * specified category.
   *
   * @param category The category under which saved queries are stored.
   */
  private Menu createSavedQueries( final Category category )
  {
    final Menu menu = new Menu( category.getName() );
    for ( String name : category.getNames() )
    {
      final MenuItem item = new MenuItem( name );
      item.addActionListener( new SavedQueryExecutorListener(
            category.getQuery( name ).getValue(), controller ) );
      menu.add( item );
    }

    return menu;
  }
}
