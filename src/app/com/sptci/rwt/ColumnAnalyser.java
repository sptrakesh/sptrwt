package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing column type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ColumnAnalyser.java 3652 2007-10-26 01:31:14Z rakesh $
 * @see java.sql.DatabaseMetaData#getColumns
 */
public class ColumnAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public ColumnAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link ColumnMetaData} objects that contain all
   * information pertaining to the columns in the specified table.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain two parameters which are the names of
   *   the <code>catalog/schema</code> and table type from which column
   *   metadata are to be retrieved.  Table types are table, view, etc.
   */
  @Override
  public Collection<ColumnMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    final Collection<ColumnMetaData> collection =
      new ArrayList<ColumnMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );
      final String table = parameters[1].getName();

      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getColumns(
          cs.getCatalogue(), cs.getSchema(), table, "%" );

      while ( resultSet.next() )
      {
        final ColumnMetaData cmd = new ColumnMetaData();

        cmd.setName( resultSet.getString( "column_name" ) );
        cmd.setComment( resultSet.getString( "remarks" ) );
        cmd.setDefaultValue( resultSet.getString( "column_def" ) );
        cmd.setTypeName( resultSet.getString( "type_name" ) );
        cmd.setNullable( resultSet.getString( "nullable" ) );

        cmd.setSize( resultSet.getInt( "column_size" ) );
        cmd.setType( resultSet.getInt( "data_type" ) );
        cmd.setTable( (TableTypeMetaData) parameters[1] );

        collection.add( cmd );
      }

      ( (TableTypeMetaData) parameters[1] ).setColumns( collection );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }
}
