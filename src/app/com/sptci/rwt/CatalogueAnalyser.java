package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing catalogues in a database.
 *
 * <pre>
 *   ConnectionManager manager = new ConnectionManager( "rwt" );
 *   DBMSMetaData dmd = new DBMSAnalyser( manager ).analyse();
 *   CatalogueAnalyser analyser = new CatalogueAnalyser( manager );
 *   Collection<CatalogueMetaData> catalogues = analyser.analyse( dmd );
 * </pre>
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-23
 * @version $Id: CatalogueAnalyser.java 3649 2007-10-25 00:15:24Z rakesh $
 * @since Version 1.1
 */
public class CatalogueAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public CatalogueAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link CatalogueMetaData} objects that contain
   * the basic information pertaining to the catalogues in the database.
   *
   * @see Analyser#analyse
   * @param parameters Must contain one parameter which is a {@link
   *   DBMSMetaData} that represents the database connected to.
   */
  @Override
  public Collection<CatalogueMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Collection<CatalogueMetaData> collection =
      new ArrayList<CatalogueMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getCatalogs();

      while ( resultSet.next() )
      {
        CatalogueMetaData cmd = new CatalogueMetaData();
        cmd.setName( resultSet.getString( "table_cat" ) );
        cmd.setDbmsMetaData( (DBMSMetaData) parameters[0] );
        collection.add( cmd );
      }

      ( (DBMSMetaData) parameters[0] ).setCatalogues( collection );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }
}
