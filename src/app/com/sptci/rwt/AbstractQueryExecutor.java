package com.sptci.rwt;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import com.sptci.util.CloseJDBCResources;
import com.sptci.util.StringUtilities;

/**
 * A utility class to execute SQL statement (only one statement allowed).
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-01
 * @version $Id: AbstractQueryExecutor.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @see BatchQueryExecutor
 */
public abstract class AbstractQueryExecutor
{
  /** The manager to use to fetch database connections. */
  protected final ConnectionManager manager;

  /**
   * Create a new instance using the specified manager to fetch connections.
   *
   * @param manager The connection manager to use.
   */
  protected AbstractQueryExecutor( final ConnectionManager manager )
  {
    this.manager = manager;
  }

  /**
   * Create a {@link java.sql.Statement} for the specified
   * SQL statement(s) and return it.
   *
   * @param connection The database connection to use.
   * @throws SQLException If errors are encountered while creating the
   *   statement.
   */
  protected Statement createStatement( final Connection connection )
    throws SQLException
  {
    return connection.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
  }

  /**
   * Create a {@link Rows} object that represent the rows in the result
   * set within the specified row range.
   *
   * @see #getClobValue
   * @param statement The statement whose result set to process.
   * @param page The page number being retrieved.  Page number is specified
   *   using zero based indexing as opposed to 1 based.
   * @param rowsPerPage The number of rows to display per page.
   * @param maxColumnLength The maximum number of characters to retrieve
   *   from <code>CLOB</code> type columns to avoid memory issues.
   * @throws SQLException If errors are encountered while executing the
   *   statement(s) and fetching the results.
   */
  protected Rows processResultSet( final Statement statement,
      final int page, final int rowsPerPage, final int maxColumnLength )
    throws SQLException
  {
    final int start = ( page * rowsPerPage ) + 1;
    final int end = start + rowsPerPage - 1;
    final int columnLength =
      ( maxColumnLength > 0 ) ? maxColumnLength : Integer.MAX_VALUE;
    final ResultSet resultSet = statement.getResultSet();
    final Rows rows = new Rows();

    resultSet.last();
    rows.setTotalRows( resultSet.getRow() );

    if ( start == 1 ) resultSet.beforeFirst();
    else resultSet.absolute( start - 1 );

    ResultSetMetaData rsmd = resultSet.getMetaData();
    final int columnCount = rsmd.getColumnCount();

    if ( rows.getTotalRows() < start )
    {
      final Row row = new Row();

      for ( int i = 1; i <= columnCount; ++i )
      {
        final Column column = new Column();
        column.setName( rsmd.getColumnLabel( i ) );
        column.setType( rsmd.getColumnType( i ) );
        row.addColumn( column );
      }

      rows.addRow( row );
    }
    else
    {
      while ( resultSet.next() && resultSet.getRow() <= end )
      {
        final Row row = new Row();

        for ( int i = 1; i <= columnCount; ++i )
        {
          Object value = null;

          switch ( rsmd.getColumnType( i ) )
          {
            case Types.BINARY:
            case Types.BLOB:
            case Types.DATALINK:
            case Types.JAVA_OBJECT:
            case Types.LONGVARBINARY:
            case Types.OTHER:
            case Types.REF:
            case Types.VARBINARY:
              value = "Undisplayable";
              break;
            case Types.CLOB:
              value = getClobValue( resultSet.getClob( i ), columnLength );
              break;
            case Types.DATE:
              if ( resultSet.getDate( i ) != null )
              {
                value = new Date( resultSet.getDate( i ).getTime() );
              }
              break;
            case Types.TIME:
              if ( resultSet.getTime( i ) != null )
              {
                value = new Date( resultSet.getTime( i ).getTime() );
              }
              break;
            case Types.TIMESTAMP:
              if ( resultSet.getTimestamp( i ) != null )
              {
                value = new Date( resultSet.getTimestamp( i ).getTime() );
              }
              break;
            default:
              value = processText( resultSet.getString( i ), columnLength );
              break;
          }

          final Column column = new Column();
          column.setName( rsmd.getColumnLabel( i ) );
          column.setType( rsmd.getColumnType( i ) );
          column.setContent( value );
          row.addColumn( column );
        }

        rows.addRow( row );
      }
    }

    CloseJDBCResources.close( resultSet );
    return rows;
  }

  /**
   * Return a {@link Rows} object that represents the
   * <code>updateCount</code> obtained by executing a {@link
   * java.sql.Statement}.
   *
   * @param statement The statement from which update count is to be
   *   retrieved.
   * @return The rows object representing the update count.
   * @throws SQLException If errors are encountered while fetching the
   *   update count from <code>statement</code>.
   */
  protected Rows processUpdateCount( final Statement statement )
    throws SQLException
  {
    final Rows rows = new Rows();
    final Row row = new Row();
    final Column column = new Column();
    column.setName( "Update Count" );
    column.setType( Types.INTEGER );
    column.setContent( statement.getUpdateCount() );

    row.addColumn( column );
    rows.addRow( row );

    return rows;
  }
  
  /**
   * Return the value that is to be displayed for a {@link java.sql.Clob}
   * type.
   * 
   * @since Version 1.3
   * @param clob The CLOB whose content is to be retrieved.
   * @param length The maximum number of characters to display.
   * @throws SQLException If errors are encountered while fetching the
   *   content of the CLOB.
   */
  protected String getClobValue( final Clob clob, final int length )
    throws SQLException
  {
    String value = ( clob.length() > length ) ?
      clob.getSubString( 0, length ) + "..." :
      clob.getSubString( 0, (int) clob.length() );
    value = StringUtilities.stripInvalidXMLCharacters( value );
    return value;
  }
  
  /**
   * Return the value that is to be displayed for a text value.  Strips
   * any invalid XML characters and truncates if necessary.
   * 
   * @since Version 1.3
   * @param text The text content that is to be processed.
   * @param length The maximum number of characters to display
   */
  protected String processText( final String text, final int length )
  {
    if ( text == null ) return null;

    String value = ( text.length() > length ) ?
      text.substring( 0, length ) + "..." : text;
    value = StringUtilities.stripInvalidXMLCharacters( value );
    return value;
  }
}
