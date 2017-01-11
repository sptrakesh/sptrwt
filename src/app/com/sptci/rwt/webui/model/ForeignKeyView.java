package com.sptci.rwt.webui.model;

import java.util.Collection;
import java.util.Map;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.GroupBox;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;

import com.sptci.rwt.ColumnMetaData;
import com.sptci.rwt.KeyMetaData;
import com.sptci.rwt.ForeignKeyMetaData;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.ForeignKeyMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ForeignKeyView.java 3577 2007-10-08 04:02:38Z rakesh $
 */
public class ForeignKeyView extends KeyView
{
  /** The meta data object whose details are to be displayed. */
  private final ForeignKeyMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public ForeignKeyView( final ForeignKeyMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createColumnDetails
   * @see #createColumnMappings
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createColumnDetails() );
    add( createColumnMappings() );
  }

  /**
   * Create the component used to display the details of the foreign key.
   *
   * @see #createLabels
   * @return The component that displays the foreign key information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "referencedSchema", metaData, grid );
    createLabels( "referencedTable", metaData, grid );
    createLabels( "updateRule", metaData, grid );
    createLabels( "deleteRule", metaData, grid );
    createLabels( "deferrability", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create the component used to display the mapping of the table column(s)
   * to the referenced table column(s).
   *
   * @return The component that displays the mappings.
   */
  protected Component createColumnMappings()
  {
    Map<ColumnMetaData,String> map = metaData.getColumnMappings();
    Grid grid = new Grid( map.size() + 1 );

    for ( ColumnMetaData cmd : map.keySet() )
    {
      grid.add( Utilities.createLabel(
            getClass().getName(), "columnLabel", "Title.Label" ) );
      grid.add( new Label( cmd.getName() ) );
    }

    for ( String column : map.values() )
    {
      grid.add( Utilities.createLabel(
            getClass().getName(), "referencedColumn", "Title.Label" ) );
      grid.add( new Label( column ) );
    }

    GroupBox box = new GroupBox(
        Configuration.getString( this, "columnMappings.title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Method to return the model object for this view.
   *
   * @return The meta data object.
   */
  @Override
  protected KeyMetaData getMetaData()
  {
    return metaData;
  }
}
