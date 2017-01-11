package com.sptci.rwt.webui;

import com.sptci.echo2.table.SortableTableModel;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.QueryExecutor;
import com.sptci.rwt.QueryException;
import com.sptci.rwt.Row;
import com.sptci.rwt.Rows;

/**
 * A custom table model used to display {@link com.sptci.rwt.Row} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-13
 * @version $Id: SortableRowTableModel.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.3
 */
public class SortableRowTableModel extends RowTableModel
  implements SortableTableModel
{ 
  /** The name of the column to use in a SQL order by clause. */
  protected String orderBy;

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
   * @param maxColumnLength The maximum number of characters to display in
   *   a column.
   * @param manager The manager to use to fetch database connections.
   * @throws QueryException If errors are encountered while executing
   *   the {@link #query}.
   */
  public SortableRowTableModel( final String query, final int maxRows,
      final int maxColumnLength, final ConnectionManager manager )
    throws QueryException
  {
    super( query, maxRows, maxColumnLength, manager );
  }

  /**
   * Sort the data displayed in the table by the specified column.
   * Re-fetch the data from the {@link #query} ordered by the specified
   * column.
   * 
   * @see com.sptci.echo2.table.SortableTableModel#sort( int )
   * @see #getSortDirection
   * @see #sort( int, Direction )
   * @param column The index of the column in {@link #columns} by which
   *   to order the results.
   * @throws RuntimeException If errors are encountered while fetching
   *   the data.
   */
  public void sort( final int column ) throws RuntimeException
  {
    Direction direction = getSortDirection( column );
    sort( column, direction );
  }
  
  /**
   * Sort the data displayed in the table by the specified column.
   * Re-fetch the data from the {@link #query} ordered by the specified
   * column.  The {@link #page} is set to point to the first page of
   * data since the re-sorted data will usually have no reference to the
   * original data.
   * 
   * @param column The index of the column in {@link #columns} by which
   *   to order the results.
   * @param direction The direction in which to sort the data.
   * @throws RuntimeException If errors are encountered while fetching
   *   the data.
   */
  public void sort( final int column, final Direction direction )
    throws RuntimeException
  {
    page = 0;
    sortIndex = column;
    sortDirection = ( direction == null ) ? Direction.Ascending : direction;
    orderBy = null;
    String name = columns.get( column ).getName();
    
    switch ( sortDirection )
    {
      case Descending:
        name += " desc";
        break;
    }
    
    orderBy = name;
    
    try
    {
      fetchData();
    }
    catch ( Throwable t )
    {
      throw new RuntimeException( t );
    }
  }
  
  /**
   * Returns {@link #query}.  Over-ridden to tag on an order by clause
   * if {@link #orderBy} is not null.
   *
   * @return The value/reference of/to query.
   */
  @Override
  public String getQuery()
  {
    return ( orderBy == null ) ? query : query + " order by " + orderBy;
  }
}