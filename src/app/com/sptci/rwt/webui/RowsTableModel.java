package com.sptci.rwt.webui;

import java.util.List;

import com.sptci.echo2.Dimensions;
import com.sptci.rwt.Row;
import com.sptci.rwt.Rows;

/**
 * A custom table model used to display {@link com.sptci.rwt.Rows} object.
 * This is used by the {@link BatchQueryExecutorView} to display each result
 * obtained by executing a statement in the batch specified.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: RowsTableModel.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class RowsTableModel extends RowTableModel
{
  /**
   * The list used as the backing store for this model.
   * 
   * @since Version 1.2
   */
  private final List<Row> rows;

  /**
   * Create a new instance of the table model using the specified model
   * object.
   *
   * @see #setPageSize
   * @see #processColumns
   * @param rows The data backing this table model.
   */
  public RowsTableModel( final Rows rows )
  {
    super();
    this.rows = rows.getRows();
    totalRows = rows.getTotalRows();
    setPageSize( Dimensions.getInt( this, "pageSize" ) );
    processColumns();
  }
  
  /**
   * Update the {@link #data} with the current page of data from {@link
   * #rows}.
   *
   * @since Version 1.2
   * @see nextapp.echo2.app.table.AbstractTableModel#fireTableDataChanged
   * @see com.sptci.rwt.QueryExecutor
   */
  @Override
  protected void fetchData()
  {
    final int start = ( page * pageSize );
    int end = start + pageSize;
    if ( end > totalRows ) end = totalRows;
    data.clear();

    for ( int i = start; i < end; ++i )
    {
      data.add( rows.get( i ) );
    }
    
    fireTableDataChanged();
  }
}
