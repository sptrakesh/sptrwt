package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;
import com.sptci.rwt.JDBCMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.JDBCMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: JDBCView.java 3577 2007-10-08 04:02:38Z rakesh $
 */
public class JDBCView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final JDBCMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public JDBCView( final JDBCMetaData metaData )
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
   * JDBC driver.
   *
   * @see #createLabels
   * @return The component that displays the basic information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "version", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }
}
