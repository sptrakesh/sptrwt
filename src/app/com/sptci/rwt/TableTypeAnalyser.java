package com.sptci.rwt;

import java.sql.SQLException;

/**
 * An analyser for analysing table type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-12
 * @version $Id: TableTypeAnalyser.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.3
 */
public abstract class TableTypeAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  protected TableTypeAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Return the total number of rows in the specified table.  The
   * {@link TableTypeMetaData#numberOfRows} field is also updated.
   * 
   * @see QueryExecutor#execute
   * @param table The table for which the row count is to be retrieved.
   * @return The total number of rows in the table.
   * @throws SQLException If errors are encountered while fetching the
   *   number of rows.
   */
  public int getNumberOfRows( final TableTypeMetaData table )
    throws SQLException
  {
    final StringBuilder query = new StringBuilder();
    query.append( "select count(*) as count from " );
    if ( table.getRoot() instanceof SchemaMetaData )
    {
      query.append( table.getRoot().getName() ).append( "." );
    }
    query.append( table.getName() );

    final QueryExecutor executor = new QueryExecutor( manager );
    final Rows rows = executor.execute( query.toString() );
    final Row row = rows.getRows().get( 0 );
    final Column column = row.getColumns().get( 0 );
    final int count = Integer.parseInt( (String) column.getContent() );
    table.setNumberOfRows( count );
    
    return count;
  }
}
