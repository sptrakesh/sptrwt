package com.sptci.rwt.webui;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.TextField;

import echopointng.PopUp;

import com.sptci.echo2.Utilities;
import com.sptci.echo2.View;

/**
 * A component that is used to display the components necessary to
 * prompt the user for entering information to save a JDBC connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: SaveConnectionComponent.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class SaveConnectionComponent extends Grid implements View
{
  /** The component used to enter the name for the connection. */
  private TextField name;

  /** The controller to use to interact with the rest of the application. */
  private final MainController controller;

  /**
   * Create a new instance with the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SaveConnectionComponent( final MainController controller )
  {
    this.controller = controller;
  }

  /**
   * Life-cycle method used to initialise the component.
   *
   * @see #createName
   */
  @Override
  public void init()
  {
    removeAll();
    createName();
  }

  /**
   * Initialise the {@link #name} component and its associated {@link
   * nextapp.echo2.app.Label}.
   */
  protected void createName()
  {
    add( Utilities.createLabel(
          getClass().getName(), "name", "Title.Label" ) );
    add( Utilities.createTextField(
        getClass().getName(), "name",
        new SaveConnectionListener( controller ), this ) );
  }

  /**
   * Return the value entered in {@link #name} field.
   *
   * @return The value entered or an empty string if no value was entered.
   */
  public String getName()
  {
    return name.getText();
  }
}
