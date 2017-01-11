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
 * information is retrieved from the <code>Information Schema</code>.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-02
 * @version $Id: StandardViewAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
class StandardViewAnalyser extends AbstractViewAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  StandardViewAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Fetch the views directly from <code>information_schema</code> if no
   * information is available through {@link java.sql.DatabaseMetaData}.
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
      if ( ( cs.getCatalogue() == null || "".equals( cs.getCatalogue() ) )
          && ( cs.getSchema() == null || "".equals( cs.getSchema() ) ) )
      {
        final String query = "select * from information_schema.views";
        statement = connection.prepareStatement( query );
      }
      else if ( cs.getCatalogue() == null || "".equals( cs.getCatalogue() ) )
      {
        final String query = "select * from information_schema.views where table_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
      }
      else if ( cs.getSchema() == null || "".equals( cs.getSchema() ) )
      {
        final String query = "select * from information_schema.views where table_catalog = ?";
        statement = connection.prepareStatement( query );
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
      }
      else
      {
        final String query = "select * from information_schema.views where table_catalog = ? and table_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, cs.getSchema() );
      }

      resultSet = statement.executeQuery();

      while ( resultSet.next() )
      {
        ViewMetaData vmd = new ViewMetaData();

        vmd.setName( resultSet.getString( "table_name" ) );
        vmd.setDefinition( resultSet.getString( "view_definition" ) );
        vmd.setCheckOption( resultSet.getString( "check_option" ) );
        vmd.setUpdatable( resultSet.getBoolean( "is_updatable" ) );
        vmd.setInsertable( resultSet.getBoolean( "is_insertable_into" ) );
        vmd.setRoot( root );

        collection.add( vmd );
      }

      if ( root != null ) root.setViews( collection );
    }
    catch ( SQLException sex )
    {
      logger.log( Level.FINE,
          "Error fetching view details from information_schema", sex );
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
   * @see #getNames
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

      if ( ( cs.getCatalogue() == null  ) && ( cs.getSchema() == null ) )
      {
        final String query = "select * from information_schema.views where table_name = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
      }
      else if ( cs.getCatalogue() == null )
      {
        final String query = "select * from information_schema.views where table_name = ? and table_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
        statement.setString( 2, cs.getSchema() );
      }
      else if ( cs.getSchema() == null )
      {
        final String query = "select * from information_schema.views where table_name = ? and table_catalog = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
        statement.setString( 2, cs.getCatalogue() );
      }
      else
      {
        final String query = "select * from information_schema.views where table_name = ? and table_catalog = ? and table_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, vmd.getName() );
        statement.setString( 2, cs.getCatalogue() );
        statement.setString( 3, cs.getSchema() );
      }

      resultSet = statement.executeQuery();

      if ( resultSet.next() )
      {
        vmd.setDefinition( resultSet.getString( "view_definition" ) );
        vmd.setCheckOption( resultSet.getString( "check_option" ) );
        vmd.setUpdatable( resultSet.getBoolean( "is_updatable" ) );
        vmd.setInsertable( resultSet.getBoolean( "is_insertable_into" ) );
      }
    }
    catch ( SQLException sex )
    {
      logger.log( Level.FINE,
          "Error fetching details of view: " + vmd.getName() +
          " from information_schema", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }
  }
}
