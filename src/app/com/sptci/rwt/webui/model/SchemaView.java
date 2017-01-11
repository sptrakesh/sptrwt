package com.sptci.rwt.webui.model;

import java.util.Collection;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.GroupBox;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;

import com.sptci.rwt.MetaData;
import com.sptci.rwt.SchemaMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.SchemaMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: SchemaView.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class SchemaView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final SchemaMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public SchemaView( final SchemaMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
  }

  /**
   * Create the component used to display the default limits enforced by
   * the database engine.
   *
   * @see #createLabels
   * @return The component that displays the limits information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "tables", metaData, grid );
    createLabels( "views", metaData, grid );
    createLabels( "triggers", metaData, grid );
    createLabels( "procedures", metaData, grid );
    createLabels( "sequences", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create standard {@link nextapp.echo2.app.Label} components that
   * represent the name of the specified field and the value in the
   * specified model.  Over-ridden to invoke the {@link
   * java.util.Collection#size} method instead of just the accessor in
   * the model.
   *
   * @param name The name of the field.
   * @param metaData The model object.
   * @param component The container component to which the labels are to
   *   be added.
   */
  @Override
  protected void createLabels( final String name, final MetaData metaData,
      final Component component )
  {
    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    try
    {
      component.add( Utilities.createLabel(
            getClass().getName(), name, "Title.Label" ) );

      Collection collection =
        (Collection) ReflectionUtility.execute( metaData, method );
      int size = collection.size();
      component.add( new Label(
            ( ( size == 0 ) ? "Not loaded or 0" : String.valueOf( size ) ) ) );
    }
    catch ( Throwable t )
    {
      processFatalException( method, metaData.getClass().getName(), t );
    }
  }
}
