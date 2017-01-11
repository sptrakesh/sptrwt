package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing schemas in a database.
 *
 * <pre>
 *   ConnectionManager manager = new ConnectionManager( "rwt" );
 *   DBMSMetaData dmd = new DBMSAnalyser( manager ).analyse();
 *   SchemaAnalyser analyser = new SchemaAnalyser( manager );
 *   Collection<SchemaMetaData> schemas = analyser.analyse( dmd );
 * </pre>
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: SchemaAnalyser.java 3649 2007-10-25 00:15:24Z rakesh $
 */
public class SchemaAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public SchemaAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link SchemaMetaData} objects that contain the
   * basic information pertaining to the schemas in the database.
   *
   * @see Analyser#analyse
   * @param parameters Must contain one parameter which is a {@link
   *   DBMSMetaData} that represents the database connected to.
   */
  @Override
  public Collection<SchemaMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Collection<SchemaMetaData> collection = new ArrayList<SchemaMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      DBMSMetaData dbmsmd = (DBMSMetaData) parameters[0];
      Collection<CatalogueMetaData> catalogues = dbmsmd.getCatalogues();

      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getSchemas();
      ResultSetMetaData rsmd = resultSet.getMetaData();

      while ( resultSet.next() )
      {
        SchemaMetaData smd = new SchemaMetaData();
        smd.setName( resultSet.getString( "table_schem" ) );
        smd.setDbmsMetaData( (DBMSMetaData) parameters[0] );

        // Handling for JDBC2 drivers
        if ( parameters.length > 1 )
        {
          smd.setCatalogue( (CatalogueMetaData) parameters[1] );
        }
        else if ( rsmd.getColumnCount() > 1 )
        {
          // JDBC3 drivers
          CatalogueMetaData cmd =
            ( (DBMSMetaData) parameters[0] ).getCatalogue(
                resultSet.getString( "table_catalog" ) );
          smd.setCatalogue( cmd );
          if ( cmd != null ) cmd.addSchema( smd );
        }
        else if ( catalogues.size() == 1 )
        {
          for ( CatalogueMetaData cmd : dbmsmd.getCatalogues() )
          {
            smd.setCatalogue( cmd );
            cmd.addSchema( smd );
          }
        }

        if ( smd.getCatalogue() == null ) collection.add( smd );
      }

      dbmsmd.setSchemas( collection );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }
}
