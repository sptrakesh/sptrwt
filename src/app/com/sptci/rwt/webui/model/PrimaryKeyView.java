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

import com.sptci.rwt.ColumnMetaData;
import com.sptci.rwt.MetaData;
import com.sptci.rwt.PrimaryKeyMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.PrimaryKeyMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: PrimaryKeyView.java 3612 2007-10-13 23:59:53Z rakesh $
 */
public class PrimaryKeyView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final PrimaryKeyMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public PrimaryKeyView( final PrimaryKeyMetaData metaData )
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
    PrimaryKeyMetaData pkmd = (PrimaryKeyMetaData) metaData;
    int size = pkmd.getColumns().size();

    Grid grid = new Grid( size + 1 );

    createLabels( "name", metaData, grid );
    createLabels( "type", metaData, grid );
    createLabels( "defaultValue", metaData, grid );
    createLabels( "size", metaData, grid );
    createLabels( "nullable", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create standard {@link nextapp.echo2.app.Label} components that
   * represent the name of the specified field and the value in the
   * specified model.  Over-ridden to process the
   * java.util.Collection}&lt;ColumnMetaData&gt; and create a
   * multi-dimensional grid.
   *
   * @param name The name of the field.
   * @param metaData The model object.
   * @param component The container component to which the labels are to
   *   be added.
   */
  @Override
  protected void createLabels( final String name,
      final MetaData metaData, final Component component )
  {
    PrimaryKeyMetaData pkmd = (PrimaryKeyMetaData) metaData;
    int size = pkmd.getColumns().size();

    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    component.add( Utilities.createLabel(
          getClass().getName(), name, "Title.Label" ) );

    for ( ColumnMetaData cmd : pkmd.getColumns() )
    {
      try
      {
        Object object = ReflectionUtility.execute( cmd, method );
        component.add( new Label( String.valueOf( object ) ) );
      }
      catch ( Throwable t )
      {
        processFatalException( method, ColumnMetaData.class.getName(), t );
      }
    }
  }
}
