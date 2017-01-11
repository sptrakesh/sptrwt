package com.sptci.rwt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing sequence type objects in the database.  Used
 * for databases that support the SQL92 standard information schema.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-04
 * @version $Id: StandardSequenceAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
class StandardSequenceAnalyser extends AbstractSequenceAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  StandardSequenceAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link SequenceMetaData} objects that contain
   * all information pertaining to the sequences in the specified schema.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain the <code>catalog/schema</code> from
   *   which sequence metadata are to be retrieved.
   */
  @Override
  public Collection<SequenceMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    final Collection<SequenceMetaData> collection =
      new ArrayList<SequenceMetaData>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );

      connection = manager.open();
      if ( ( cs.getCatalogue() == null ) && ( cs.getSchema() == null ) )
      {
        final String query = "select * from information_schema.sequences";
        statement = connection.prepareStatement( query );
      }
      else if ( cs.getCatalogue() == null )
      {
        final String query = "select * from information_schema.sequences where sequence_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
      }
      else if ( cs.getSchema() == null )
      {
        final String query = "select * from information_schema.sequences where sequence_catalog = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
      }
      else
      {
        final String query = "select * from information_schema.sequences where sequence_catalog = ? and sequence_schema = ?";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, cs.getSchema() );
      }

      resultSet = statement.executeQuery();

      while ( resultSet.next() )
      {
        final SequenceMetaData smd = new SequenceMetaData();

        smd.setName( resultSet.getString( "sequence_name" ) );
        smd.setDataType( resultSet.getString( "data_type" ) );
        smd.setMaximum( resultSet.getString( "maximum_value" ) );
        smd.setMinimum( resultSet.getString( "minimum_value" ) );
        smd.setCyclePolicy( resultSet.getString( "cycle_option" ) );

        smd.setRoot( (RootMetaData) parameters[0] );
        collection.add( smd );
      }

      ( (RootMetaData) parameters[0] ).setSequences( collection );
    }
    catch ( SQLException sex )
    {
      logger.log( Level.FINE,
          "Error fetching sequence details from information_schema", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }
}
