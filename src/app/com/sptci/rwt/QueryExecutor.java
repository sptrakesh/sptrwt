package com.sptci.rwt;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sptci.util.CloseJDBCResources;

/**
 * A utility class to execute SQL statement (only one statement allowed).
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-01
 * @version $Id: QueryExecutor.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @see BatchQueryExecutor
 */
public class QueryExecutor extends AbstractQueryExecutor
{
  /**
   * Create a new instance using the specified manager to fetch connections.
   *
   * @param manager The connection manager to use.
   */
  public QueryExecutor( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Execute the specified statement and return a {@link Rows}
   * object that represent the data in the {@link java.sql.ResultSet}
   * obtained by executing the statement.
   *
   * @see #createStatement
   * @see #processResultSet
   * @see #processUpdateCount
   * @param sql The statement to be executed.
   * @param parameters Additional parameters to specify the following values
   *   in the order listed:
   *   <ul>
   *     <li><code>maxRows</code> The maximum number of rows to fetch from
   *     the database.</li>
   *     <li><code>page</code> The current page of data being viewed.  Page
   *     is specified using zero based indexing.</li>
   *     <li><code>rowsPerPage</code> The number of rows of data to display
   *     on a page</li>.
   *     <li><code>maxColumnLength</code> The maximum length for a column
   *     value.  This is used to truncate the value of <code>CLOB</code>
   *     type objects to avoid memory issues.</li>
   *   </ul>
   *   Note that specifying <code>page</code> should be combined with
   *   specifying <code>rowsPerPage</code> for proper behaviour.  A default
   *   value of <code>100</code> will be used otherwise.
   * @return The value object representing rows in the specified range in
   *   the {@link java.sql.ResultSet}.
   * @throws SQLException If errors are encountered while executing the
   *   statement.
   */
  public Rows execute( final String sql, final int... parameters )
    throws SQLException
  {
    int maxRows = 0;
    int page = 0;
    int rowsPerPage = Integer.MAX_VALUE;
    int maxColumnLength = 0;

    if ( parameters != null )
    {
      switch ( parameters.length )
      {
        case 1:
          maxRows = parameters[0];
          break;
        case 2:
          maxRows = parameters[0];
          page = parameters[1];
          rowsPerPage = 100;
          break;
        case 3:
          maxRows = parameters[0];
          page = parameters[1];
          rowsPerPage = parameters[2];
          break;
        case 4:
          maxRows = parameters[0];
          page = parameters[1];
          rowsPerPage = parameters[2];
          maxColumnLength = parameters[3];
          break;
      }
    }

    Connection connection = null;
    Statement statement = null;
    Rows rows = null;
    
    try
    {
      connection = manager.open();
      statement = createStatement( connection );
      statement.setMaxRows( maxRows );

      if ( rowsPerPage > 0 && rowsPerPage != Integer.MAX_VALUE )
      {
        statement.setFetchSize( rowsPerPage );
      }

      boolean status = statement.execute( sql );

      if ( status )
      {
        rows =
          processResultSet( statement, page, rowsPerPage, maxColumnLength );
      }
      else
      {
        rows = processUpdateCount( statement );
      }
    }
    finally
    {
      CloseJDBCResources.closeAll( statement );
      CloseJDBCResources.close( connection );
    }

    return rows;
  }

  /**
   * Return the total number of rows that can be retrieved by executing the
   * specified SQL statement.
   *
   * @param sql The statement to be executed.
   * @return The total number of rows that are available in the database.
   * @throws SQLException If errors are encountered while executing the
   *   statement.
   */
  public int getTotalRows( final String sql ) throws SQLException
  {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    int total = 0;
    
    try
    {
      connection = manager.open();
      statement = createStatement( connection );
      boolean status = statement.execute( sql );

      if ( status )
      {
        resultSet = statement.getResultSet();
        status = resultSet.last();
        if ( status ) total = resultSet.getRow();
      }
      else
      {
        if ( statement.getUpdateCount() != -1 )
        {
          total = 1;
        }
      }
    }
    finally
    {
      CloseJDBCResources.closeAll( resultSet );
      CloseJDBCResources.close( connection );
    }

    return total;
  }
}
