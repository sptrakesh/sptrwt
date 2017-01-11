package com.sptci.rwt.webui;

import java.util.ArrayList;
import java.util.List;

import com.sptci.echo2.Dimensions;
import com.sptci.echo2.table.ColumnMetaData;
import com.sptci.echo2.table.DefaultPageableTableModel;

import com.sptci.rwt.Column;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.QueryExecutor;
import com.sptci.rwt.QueryException;
import com.sptci.rwt.Row;
import com.sptci.rwt.Rows;

/**
 * A custom table model used to display {@link com.sptci.rwt.Row} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-04
 * @version $Id: RowTableModel.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class RowTableModel extends DefaultPageableTableModel<Row>
{
  /** The SQL statement that is to be executed to fetch the results. */
  protected final String query;

  /** The maximum number of records to fetch from the database. */
  protected final int maxRows;
  
  /**
   * The maximum number of characters to display in a column.
   * 
   * @since Version 1.3
   */
  protected final int maxColumnLength;

  /** The connection manager to use to fetch database connections. */
  protected final ConnectionManager manager;
  
  /**
   * Default constructor.  No special actions required.
   * 
   * @since Version 1.2
   */
  protected RowTableModel()
  {
    query = null;
    manager = null;
    maxRows = 0;
    maxColumnLength = 0;
  }

  /**
   * Create a new instance of the table model using the specified collection
   * of {@link com.sptci.rwt.Row} objects.
   *
   * @see #fetchData
   * @see #processColumns
   * @param query The sql statement that is to be executed to fetch any
   *   additional pages of data.
   * @param maxRows The maximum number of rows to fetch from the
   *   result set.
   * @param manager The manager to use to fetch database connections.
   * @throws QueryException If errors are encountered while executing
   *   the {@link #query}.
   */
  public RowTableModel( final String query, final int maxRows,
      final int maxColumnLength, final ConnectionManager manager )
    throws QueryException
  {
    super();
    this.query = query;
    this.maxRows = maxRows;
    this.maxColumnLength = maxColumnLength;
    this.manager = manager;
    setPageSize( Dimensions.getInt( this, "pageSize" ) );
    processColumns();
  }

  /**
   * Fetch the data from the database by executing {@link #query}.
   *
   * @see nextapp.echo2.app.table.AbstractTableModel#fireTableDataChanged
   * @see com.sptci.rwt.QueryExecutor
   * @throws QueryException If errors are encountered while executing
   *   the {@link #query}.
   */
  protected void fetchData() throws QueryException
  {
    try
    {
      QueryExecutor executor = new QueryExecutor( manager );

      final Rows rows = executor.execute(
          getQuery(), maxRows, page, pageSize, maxColumnLength );
      totalRows = rows.getTotalRows();
      if ( ( maxRows > 0 ) && ( totalRows > maxRows ) )
      {
        totalRows = maxRows;
      }

      data.clear();
      data.addAll( rows.getRows() );
      fireTableDataChanged();
    }
    catch ( Throwable t )
    {
      throw new QueryException( "Error fetching data for query", t );
    }
  }

  /**
   * Over-ridden to set the proper column meta data using the column
   * information from the row object.
   */
  @Override
  protected void processColumns()
  {
    if ( data.size() == 0 ) return;
    final Row row = data.get( 0 );

    final List<Column> columns = row.getColumns();
    if ( columns.size() == 0 ) return;

    for ( Column column : columns )
    {
      final ColumnMetaData cmd =
        new ColumnMetaData( column.getName(), String.class );
      this.columns.add( cmd );
    }
  }

  /**
   * Return the column name to display at the specified index.
   *
   * @return The name of the column.
   */
  @Override
  public String getColumnName( final int column )
  {
    return columns.get( column ).getName();
  }

  /**
   * Return the value to display at the coordinates specified.
   *
   * @param column The column index.
   * @param row The row index.
   * @return The value for the coordinates.
   * @throws RuntimeException If the indices are invalid.
   */
  @Override
  public Object getValueAt( final int column, final int row )
  {
    if ( column < 0 || column >= getColumnCount() )
    {
      throw new IllegalArgumentException( "Illegal column index: " + column );
    }
    if ( row < 0 || row >= getRowCount() )
    {
      throw new IllegalArgumentException( "Illegal row index: " + row );
    }

    Row r = data.get( row );
    Object value = r.getColumns().get( column ).getContent();

    return value;
  }
  
  /**
   * Returns {@link #query}.
   *
   * @return The value/reference of/to query.
   */
  public String getQuery()
  {
    return query;
  }
  
  /**
   * Returns {@link #maxRows}.
   *
   * @return The value/reference of/to maxRows.
   */
  public int getMaxRows()
  {
    return maxRows;
  }
  /**
   * Returns {@link #maxColumnLength}.
   *
   * @since Version 1.3
   * @return The value/reference of/to maxColumnLength.
   */
  public int getMaxColumnLength()
  {
    return maxColumnLength;
  }
  
  /**
   * Returns {@link #manager}.
   *
   * @return The value/reference of/to manager.
   */
  public ConnectionManager getManager()
  {
    return manager;
  }
  
  /**
   * Returns {@link #totalRows}.
   *
   * @return The value/reference of/to totalRows.
   */
  public int getTotalRows()
  {
    return totalRows;
  }
  
  /**
   * Set {@link #page}.
   *
   * @see #fetchData
   * @param page The value to set.
   */
  public void setPage( final int page )
  {
    this.page = page;
    fetchData();
  }
  
  /**
   * Set {@link #pageSize}.
   *
   * @see #fetchData
   * @param pageSize The value to set.
   */
  public void setPageSize( final int pageSize )
  {
    this.pageSize = pageSize;
    fetchData();
  }
}
