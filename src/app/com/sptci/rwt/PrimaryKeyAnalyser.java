package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing primary key columns for tables in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: PrimaryKeyAnalyser.java 3652 2007-10-26 01:31:14Z rakesh $
 * @see java.sql.DatabaseMetaData#getPrimaryKeys
 */
public class PrimaryKeyAnalyser extends KeyAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public PrimaryKeyAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link PrimaryKeyMetaData} objects that
   * contain all information pertaining to the primary key columns for the
   * specified table.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @see #checkTableColumns
   * @see #processColumns
   * @param parameters Must contain two parameters which are the names of
   *   the <code>schema</code> and table from which primary key metadata are
   *   to be retrieved.
   * @return A collection representing the primary key for the table.  This
   *   method returns a collection purely to keep compatibility with the
   *   super-class abstract method declaration.  A table can have only
   *   one primary key.
   */
  @Override
  public Collection<PrimaryKeyMetaData> analyse(
      final MetaData... parameters ) throws SQLException
  {
    final ArrayList<PrimaryKeyMetaData> collection =
      new ArrayList<PrimaryKeyMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );
      final TableMetaData tmd = (TableMetaData) parameters[1];
      final String table = tmd.getName();

      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getPrimaryKeys(
          cs.getCatalogue(), cs.getSchema(), table );

      if ( resultSet.next() )
      {
        final PrimaryKeyMetaData pkmd = new PrimaryKeyMetaData();
        pkmd.setName( resultSet.getString( "pk_name" ) );
        pkmd.setTable( (TableMetaData) parameters[1] );

        checkTableColumns( tmd );
        pkmd.setColumns( processColumns( tmd, resultSet ) );

        collection.add( pkmd );
      }

      // There should be only one entry in the collection.
      tmd.setPrimaryKey( collection.get( 0 ) );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }

  /**
   * Read the column(s) that comprise the primary key and crate a collection
   * of {@link ColumnMetaData} objects ordered by <code>key_seq</code>.
   *
   * @param tmd The meta data object representing the table on which the
   *   primary key is defined.
   * @param resultSet The result set object to process.
   * @return The collection of columns.
   * @throws SQLException If errors are encountered while processing the
   *   result set.
   */
  protected Collection<ColumnMetaData> processColumns(
      final TableMetaData tmd, final ResultSet resultSet )
    throws SQLException
  {
    final TreeMap<Short,ColumnMetaData> columns =
      new TreeMap<Short,ColumnMetaData>();
    
    ColumnMetaData cmd =
      tmd.getColumn( resultSet.getString( "column_name" ) ); 
    columns.put( resultSet.getShort( "key_seq" ), cmd );

    while ( resultSet.next() )
    {
      cmd = tmd.getColumn( resultSet.getString( "column_name" ) ); 
      columns.put( resultSet.getShort( "key_seq" ), cmd );
    }

    return columns.values();
  }
}
