package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;
import com.sptci.rwt.ColumnMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.ColumnMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ColumnView.java 3626 2007-10-15 15:05:53Z rakesh $
 */
public class ColumnView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final ColumnMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public ColumnView( final ColumnMetaData metaData )
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
   * Create the component used to display basic information about the
   * Column driver.
   *
   * @see #createLabels
   * @return The component that displays the basic information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "type", metaData, grid );
    createLabels( "typeName", metaData, grid );
    createLabels( "defaultValue", metaData, grid );
    createLabels( "size", metaData, grid );
    createLabels( "nullable", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }
}
