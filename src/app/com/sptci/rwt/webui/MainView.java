package com.sptci.rwt.webui;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;

import echopointng.Menu;
import echopointng.MenuBar;
import echopointng.MenuItem;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Header;
import com.sptci.echo2.View;
import com.sptci.echo2.style.Extent;

import com.sptci.epng.Tree;

/**
 * The primary {@link nextapp.echo2.app.ContentPane} for the application.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: MainView.java 3617 2007-10-15 00:13:55Z rakesh $
 */
public class MainView extends ContentPane implements View
{
  /** The controller for this view component. */
  private final MainController controller;

  /** The component used to display the menu for the application. */
  private MenuComponent menuComponent;

  /** The component used to display the database object hierarchy. */
  private MetaDataTree tree;

  /**  container used to hold the metadata objects (tree). */
  private Component left;

  /**
   * The container used to hold all the information displayed in the
   * detail view area (right side).
   */
  private Component right;

  /**
   * Create a new instance of the pane.
   *
   * @see #layout
   * @see #createLeft
   * @see #createRight
   */
  public MainView()
  {
    controller = new MainController( this );
    layout();
  }

  /**
   * Refreshes the view.  Usually used to initiate a new connection.
   */
  void refresh()
  {
    left.remove( tree );
    left.add( createTree() );
    right.removeAll();
  }

  /**
   * Set the content of the {@link #right} using the specified component.
   *
   * @param component The component that is to be displayed in the right
   *   container component.
   */
  void setContent( final Component component )
  {
    right.removeAll();
    right.add( component );
  }

  /**
   * Layout the content pane to accomodate top menu, database object
   * navigation (left) and detail content (right).
   *
   * @see #createTop
   * @see #createLeft
   * @see #createRight
   */
  private void layout()
  {
    SplitPane main = new SplitPane( SplitPane.ORIENTATION_VERTICAL );
    main.setStyleName( getClass().getName() + ".mainSplitPane" );
    main.add( createTop() );

    SplitPane displayArea = new SplitPane();
    displayArea.setStyleName( getClass().getName() + ".displayArea" );
    displayArea.add( createLeft() );
    displayArea.add( createRight() );

    main.add( displayArea );
    add( main );
  }

  /**
   * Create the component that displays header and menu.
   *
   * @see com.sptci.echo2.Header
   * @see MenuComponent
   */
  private Component createTop()
  {
    Column column = new Column();
    column.setCellSpacing( Extent.getInstance( 0 ) );
    column.add( new Header() );

    menuComponent = new MenuComponent( controller );
    column.add( menuComponent );

    return column;
  }

  /**
   * Create the left navigation area.
   */
  private Component createLeft()
  {
    left = new Column();
    return left;
  }

  /**
   * Create the right area that holds the main content.
   */
  private Component createRight()
  {
    right = new Column();
    return right;
  }

  /**
   * Create the component that displays the database metadata objects in
   * a tree.
   *
   * @return The tree component.
   */
  private MetaDataTree createTree()
  {
    tree = new MetaDataTree( controller );
    return tree;
  }
  
  /**
   * Returns {@link #controller}.
   *
   * @return The value/reference of/to controller.
   */
  public MainController getController()
  {
    return controller;
  }
  
  /**
   * Returns {@link #menuComponent}.
   *
   * @return The value/reference of/to menuComponent.
   */
  public MenuComponent getMenuComponent()
  {
    return menuComponent;
  }

  /**
   * Rebuilds the {@link #menuComponent}.
   */
  public void rebuildMenu()
  {
    final Component parent = menuComponent.getParent();
    final int index = parent.indexOf( menuComponent );
    parent.remove( index );
    menuComponent = new MenuComponent( controller );
    parent.add( menuComponent, index );
  }
}
