package com.sptci.rwt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;
import static com.sptci.io.FileUtilities.END_OF_LINE;

/**
 * An analyser for analysing procedure type objects in the database.  This
 * is used to query the Oracle data dictionary.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-03
 * @version $Id: OracleProcedureAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 */
class OracleProcedureAnalyser extends AbstractProcedureAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  OracleProcedureAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Fetch additional meta data available from the <code>information
   * schema</code> to the object.
   *
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

      if ( cs.getSchema() != null )
      {
        final String query = "select text from all_source where name = ? and type = 'PROCEDURE' and owner = ? order by line";
        statement = connection.prepareStatement( query );
        statement.setString( 1, pmd.getName() );
        statement.setString( 2, cs.getSchema() );
      }
      else
      {
        final String query = "select text from all_source where name = ? and type = 'PROCEDURE' order by line";
        statement = connection.prepareStatement( query );
        statement.setString( 1, pmd.getName() );
      }

      resultSet = statement.executeQuery();
      StringBuilder builder = new StringBuilder();
      while ( resultSet.next() )
      {
        builder.append( resultSet.getString( "text" ) );
        builder.append( END_OF_LINE );
      }

      if ( builder.length() > 0 ) pmd.setBody( builder.toString() );
    }
    catch ( SQLException sex )
    {
      logger.log( Level.INFO,
          "Error fetching procedure details from all_source", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }
  }
}
