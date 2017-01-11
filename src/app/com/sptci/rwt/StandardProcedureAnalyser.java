package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing procedure type objects in the database.  This
 * is used to database engines that support the SQL92 standard information
 * schema.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-03
 * @version $Id: StandardProcedureAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
class StandardProcedureAnalyser extends AbstractProcedureAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  StandardProcedureAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Fetch additional meta data available from the <code>information
   * schema</code> to the object.
   *
   * @see #getNames
   * @param pmd The meta data object that is to be updated.
   */
  @Override
  protected void getAdditionalAttributes( final ProcedureMetaData pmd )
  {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try
    {
      CatalogueSchema cs = getNames( pmd.getRoot() );
      connection = manager.open();

      if ( cs.getCatalogue() == null )
      {
        final String query = "select data_type, type_udt_name, routine_body, routine_definition from information_schema.routines where routine_schema = ? and routine_name = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
        statement.setString( 2, pmd.getName() );
      }
      else if ( cs.getSchema() == null )
      {
        final String query = "select data_type, type_udt_name, routine_body, routine_definition from information_schema.routines where routine_catalog = ? and routine_name = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, pmd.getName() );
      }
      else
      {
        final String query = "select data_type, type_udt_name, routine_body, routine_definition from information_schema.routines where routine_catalog = ? and routine_schema = ? and routine_name = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, cs.getSchema() );
        statement.setString( 3, pmd.getName() );
      }

      resultSet = statement.executeQuery();
      if ( resultSet.next() )
      {
        pmd.setDataType( resultSet.getString( "data_type" ) );
        pmd.setUserDefinedType( resultSet.getString( "type_udt_name" ) );
        pmd.setBody( resultSet.getString( "routine_body" ) );
        pmd.setDefinition( resultSet.getString( "routine_definition" ) );
      }
    }
    catch ( SQLException sex )
    {
      logger.log( Level.FINE,
          "Error fetching procedure details from information_schema", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }
  }
}
