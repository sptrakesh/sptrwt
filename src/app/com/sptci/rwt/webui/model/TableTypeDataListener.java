package com.sptci.rwt.webui.model;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Dimensions;
import com.sptci.echo2.Listener;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.SchemaMetaData;
import com.sptci.rwt.TableTypeMetaData;
import com.sptci.rwt.webui.MainController;
import com.sptci.rwt.webui.SortableRowTableModel;

/**
 * The {@link nextapp.echo2.app.event.ActionListener} for displaying
 * the data contained in a table.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-13
 * @version $Id: TableTypeDataListener.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.3
 */
public class TableTypeDataListener extends Listener<MainController>
{
  /** The metadata object for the table whose data is to be displayed. */
  private final TableTypeMetaData metaData;

  /**
   * Create a new instance using the specified controller.
   * 
   * @param controller The {@link #controller} to use.
   */
  public TableTypeDataListener( final MainController controller,
      final TableTypeMetaData metaData )
  {
    super( controller );
    this.metaData = metaData;
  }
  
  /**
   * The action listener implementation.  Display a {@link
   * com.sptci.echo2.table.DatabaseTable} that contains all the records
   * in {@link #metaData}.
   * 
   * @param event The action event that was triggered.
   */
  public void actionPerformed( final ActionEvent event )
  {
    final StringBuilder query = new StringBuilder();
    query.append( "select * from " );
    if ( metaData.getRoot() instanceof SchemaMetaData )
    {
      query.append( metaData.getRoot().getName() ).append( "." );
    }
    query.append( metaData.getName() );

    try
    {
      SortableRowTableModel model = new SortableRowTableModel(
          query.toString(), 0,
          Dimensions.getInt( TableTypeDataView.class, "maxColumnLength" ),
          controller.getConnectionManager() );
      TableTypeDataView view = new TableTypeDataView( metaData, model );
      controller.setContent( view );
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( this, "error" );
      message = message.replaceAll( "\\$table\\$", metaData.getName() );
      controller.processFatalException( message, t );
    }
  }
}