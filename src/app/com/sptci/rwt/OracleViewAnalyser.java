package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing view type objects in the database. View 
 * information is retrieved from the Oracle data dictionary as it does
 * not support the information schema.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-02
 * @version $Id: OracleViewAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
class OracleViewAnalyser extends AbstractViewAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  OracleViewAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Fetch the views from the data diectionary.
   *
   * @see #getNames
   * @param root The metadata object that represents the catalog/schema for
   *   which the views are to be retrieved.
   * @param collection The collection to which the {@link ViewMetaData}
   *   objects are to be added.
   * @throws SQLException If errors are encountered while fetching the views.
   */
  @Override
  protected void getViews( final RootMetaData root,
      final Collection<ViewMetaData> collection ) throws SQLException
  {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( root );

      connection = manager.open();
      if ( ( cs.getSchema() == null ) || "".equals( cs.getSchema() ) )
      {
        final String query = "select * from all_views";
        statement = connection.prepareStatement( query );
      }
      else
      {
        final String query = "select * from all_views where owner = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
      }

      resultSet = statement.executeQuery();

      while ( resultSet.next() )
      {
        ViewMetaData vmd = new ViewMetaData();

        vmd.setName( resultSet.getString( "view_name" ) );
        vmd.setDefinition( resultSet.getString( "text" ) );
        vmd.setRoot( root );

        collection.add( vmd );
      }

      if ( root != null ) root.setViews( collection );
    }
    catch ( SQLException sex )
    {
      logger.log( Level.INFO,
          "Error fetching view details from all_views", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }
  }

  /**
   * Fetch additional meta data about the specified view from the
   * <code>information_schema</code>.
   *
   * @param vmd The meta data object that is to have additional attributes
   *   populated.
   */
  @Override
  protected void getAdditionalAttributes( final ViewMetaData vmd )
  {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try
    {
      CatalogueSchema cs = getNames( vmd.getRoot() );
      connection = manager.open();

      if ( cs.getSchema() == null )
      {
        final String query = "select text from all_views where view_name = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
      }
      else
      {
        final String query = "select text from all_views where view_name = ? and owner = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
        statement.setString( 2, cs.getSchema() );
      }

      resultSet = statement.executeQuery();

      if ( resultSet.next() )
      {
        vmd.setDefinition( resultSet.getString( "text" ) );
      }
    }
    catch ( SQLException sex )
    {
      logger.log( Level.INFO,
          "Error fetching details of view: " + vmd.getName() +
          " from all_views", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }
  }
}
