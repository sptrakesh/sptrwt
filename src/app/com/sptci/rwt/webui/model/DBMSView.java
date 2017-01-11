package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;
import com.sptci.rwt.DBMSMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.DBMSMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: DBMSView.java 3581 2007-10-08 11:32:54Z rakesh $
 */
public class DBMSView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final DBMSMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public DBMSView( final DBMSMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createJDBCView
   * @see #createLimitsView
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createJDBCView() );
    add( createLimitsView() );
  }

  /**
   * Create the component used to display basic information about the
   * database engine.
   *
   * @see #createLabels
   * @return The component that displays the basic information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    grid.add( Utilities.createLabel(
          getClass().getName(), "product", "Title.Label" ) );
    grid.add( new Label( metaData.getName() ) );

    createLabels( "version", metaData, grid );
    createLabels( "defaultTransaction", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create the component that is used to display the information 
   * pertaining to the JDBC driver in use.
   *
   * @return The component that displays the JDBC driver information.
   */
  protected Component createJDBCView()
  {
    return new JDBCView( metaData.getJdbcMetaData() );
  }

  /**
   * Create the component that is used to display the information 
   * pertaining to the limits enforced by the database engine.
   *
   * @return The component that displays the limits for the database.
   */
  protected Component createLimitsView()
  {
    return new LimitsView( metaData.getLimitsMetaData() );
  }
}
