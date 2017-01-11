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
 * An analyser for analysing index columns for tables in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: IndexAnalyser.java 3652 2007-10-26 01:31:14Z rakesh $
 * @see java.sql.DatabaseMetaData#getIndexInfo
 */
public class IndexAnalyser extends KeyAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public IndexAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link IndexMetaData} objects that
   * contain all information pertaining to the indices for the
   * specified table.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain two parameters which are the names of
   *   the <code>catalog/schema</code> and table from which index metadata
   *   are to be retrieved.
   */
  @Override
  public Collection<IndexMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    final TreeMap<String,IndexMetaData> md =
      new TreeMap<String,IndexMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );
      final TableMetaData tmd = (TableMetaData) parameters[1];

      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getIndexInfo(
          cs.getCatalogue(), cs.getSchema(), tmd.getName(), false, false );
      
      boolean first = true;
      while ( resultSet.next() )
      {
        if ( first )
        {
          checkTableColumns( tmd );
          first = false;
        }

        final String name = resultSet.getString( "index_name" );
        if ( name != null )
        {
          IndexMetaData imd = md.get( name );

          if ( imd == null )
          {
            imd = new IndexMetaData();
            imd.setName( name );
            imd.setUnique( ! resultSet.getBoolean( "non_unique" ) );
            imd.setType( processType( resultSet.getShort( "type" ) ) );
            imd.setPages( resultSet.getInt( "pages" ) );
            imd.setCardinality( resultSet.getInt( "cardinality" ) );
            imd.setSortSequence(
                processSortSequence( resultSet.getString( "asc_or_desc" ) ) );
            imd.setTable( tmd );

            md.put( name, imd );
          }

          imd.addColumn( tmd.getColumn( resultSet.getString( "column_name" ) ) );
        }
      }

      tmd.addIndices( md.values() );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return md.values();
  }

  /**
   * Process the <code>type</code> values and match
   * them with the appropriate {@link IndexMetaData.Type} value.
   *
   * @param value The the short value read from result set.
   * @return The appropriate type value.
   */
  protected IndexMetaData.Type processType( final short value )
  {
    IndexMetaData.Type rule = null;

    switch ( value )
    {
      case DatabaseMetaData.tableIndexStatistic :
        rule = IndexMetaData.Type.Statistic;
        break;
      case DatabaseMetaData.tableIndexClustered :
        rule = IndexMetaData.Type.Clustered;
        break;
      case DatabaseMetaData.tableIndexHashed :
        rule = IndexMetaData.Type.Hashed;
        break;
      case DatabaseMetaData.tableIndexOther :
        rule = IndexMetaData.Type.Other;
        break;
    };

    return rule;
  }

  /**
   * Process the <code>asc_or_desc</code> value and match them with the
   * appropriate {@link IndexMetaData.SortSequence} value.
   *
   * @param value The the short value read from result set.
   * @return The appropriate sort order value.
   */
  protected IndexMetaData.SortSequence processSortSequence(
      final String value )
  {
    IndexMetaData.SortSequence order = null;

    if ( "A".equalsIgnoreCase( value ) )
    {
      order = IndexMetaData.SortSequence.Ascending;
    }
    else if ( "D".equalsIgnoreCase( value ) )
    {
      order = IndexMetaData.SortSequence.Descending;
    }
    else
    {
      order = IndexMetaData.SortSequence.Unsorted;
    }

    return order;
  }
}
