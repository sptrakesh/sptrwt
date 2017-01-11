package com.sptci.rwt.webui;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.list.DefaultListModel;

import echopointng.ComboBox;
import echopointng.PopUp;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;
import com.sptci.echo2.View;
import com.sptci.rwt.Category;

/**
 * A component that is used to display the components necessary to
 * prompt the user for entering information to save a SQL statement.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: SaveQueryComponent.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class SaveQueryComponent extends Grid implements View
{
  /** The component used to display available categories. */
  private ComboBox category;

  /** The component used to enter the name for the query. */
  private TextField name;

  /** The controller to use to interact with the rest of the application. */
  private final MainController controller;

  /**
   * Create a new instance with the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SaveQueryComponent( final MainController controller )
  {
    this.controller = controller;
  }

  /**
   * Life-cycle method used to initialise the component.
   *
   * @see #createCategory
   * @see #createName
   */
  @Override
  public void init()
  {
    removeAll();
    createCategory();
    createName();
  }

  /**
   * Initialise the {@link #category} component with data from the {@link
   * com.sptci.rwt.Queries} data object.
   */
  protected void createCategory()
  {
    add( Utilities.createLabel(
          getClass().getName(), "category", "Title.Label" ) );

    category = new ComboBox();
    category.setToolTipText(
        Configuration.getString( this, "category.tooltip" ) );
    DefaultListModel model = new DefaultListModel();

    for ( Category category : controller.getCategories() )
    {
      model.add( category.getName() );
    }

    category.setListModel( model );
    add( category );
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
        new SaveQueryListener( controller ), this ) );
  }

  /**
   * Return the value entered/selected in {@link #category} field.
   *
   * @return The value entered/selected or an empty string if no value
   *   was selected or entered.
   */
  public String getCategory()
  {
    return category.getText();
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
