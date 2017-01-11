package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Label;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.View;
import com.sptci.echo2.table.TableNavigation;

import com.sptci.rwt.Row;
import com.sptci.rwt.TableTypeMetaData;
import com.sptci.rwt.webui.SortableRowTable;
import com.sptci.rwt.webui.SortableRowTableModel;

/**
 * A view {@link nextapp.echo2.app.Component} for displaying
 * the data contained in a table.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-13
 * @version $Id: TableTypeDataView.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.3
 */
public class TableTypeDataView extends Column implements View
{
  /** The meta data object whose data is being displayed. */
  private final TableTypeMetaData metaData;

  /**
   * The {@link nextapp.echo2.app.table.TableModel} used to build the
   * {@link nextapp.echo2.app.Table} in which the data is displayed.
   */
  private SortableRowTableModel model;

  /**
   * Create a new instance for the specified meta data.
   * 
   * @param metaData The {@link #metaData} to use.
   */
  public TableTypeDataView( final TableTypeMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Create a new instance for the specified meta data.
   * 
   * @param metaData The {@link #metaData} to use.
   */
  public TableTypeDataView( final TableTypeMetaData metaData,
      final SortableRowTableModel model )
  {
    this( metaData );
    this.model = model;
  }

  /**
   * Builds the display.  Assumes that {@link #model} has been
   * initialised.
   * 
   * @see #setModel
   * @see #display
   */
  @Override
  public void init()
  {
    display();
  }
  
  /**
   * Add the appropriate components to this view to display the data
   * encapsulated in {@link #model}.
   * 
   * @see #createTitle
   * @see #createNavigation
   */
  protected void display()
  {
    removeAll();
    createTitle();
    createNavigation();
    add( new SortableRowTable( model ) );
  }
  
  /**
   * Create a title for the component.
   */
  protected void createTitle()
  {
    String text = Configuration.getString( this, "title" );
    text = text.replaceAll( "\\$table\\$", metaData.getName() );
    Label label = new Label( text );
    label.setStyleName( "Bold.Label" );
    add( label );
  }
  
  /**
   * Create a {@link com.sptci.echo2.table.TableNavigation} for the specified
   * {@link com.sptci.rwt.webui.SortableRowTable} if necessary.
   */
  protected void createNavigation()
  {
    if ( model.getRowCount() > TableNavigation.MINIMUM_PAGE_SIZE )
    {
      TableNavigation<Row> navigation =
        new TableNavigation<Row>( model );
      add( navigation );
    }
  }
  
  /**
   * Set the model for the table displayed in this view.  Use to update
   * the contents of the table.
   */
  public void setModel( final SortableRowTableModel model )
  {
    this.model = model;
    display();
  }
}