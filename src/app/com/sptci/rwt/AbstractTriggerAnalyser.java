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
 * An abstract analyser for analysing trigger type objects in the database.
 * Serves as the base class for implementation specific trigger analysers.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-02
 * @version $Id: AbstractTriggerAnalyser.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.2
 */
public abstract class AbstractTriggerAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  protected AbstractTriggerAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link TriggerMetaData} objects that contain
   * the basic information pertaining to the triggers in the schema and
   * table (optional) specified.
   *
   * @see Analyser#analyse
   * @param parameters Must contain at least one parameter which is a {@link
   *   SchemaMetaData} that represents the <code>schema</code> to restrict
   *   the analysis to.  An optional additional {@link TableMetaData}
   *   parameter may be specified to restrict the analysis to a specified
   *   table.
   */
  @Override
  public Collection<TriggerMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Collection<TriggerMetaData> collection = new ArrayList<TriggerMetaData>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try
    {
      connection = manager.open();
      statement = createStatement( connection, parameters );
      resultSet = statement.executeQuery();
      createMetaData( resultSet, collection, parameters );

      if ( parameters.length == 1 )
      {
        ( (RootMetaData) parameters[0] ).setTriggers( collection );
      }
      else if ( parameters.length > 1 )
      {
        ( (TableMetaData) parameters[1] ).setTriggers( collection );
      }
    }
    catch ( SQLException sex )
    {
      logger.log( Level.FINE,
          "Error fetching trigger details from information_schema", sex );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }

  /**
   * Create a {@link java.sql.PreparedStatement} based on the
   * <code>parameters</code> passed in.
   *
   * @see #getNames
   * @param connection The database connection to use.
   * @param parameters The array of {@link SchemaMetaData} and {@link
   *   TableMetaData} objects.
   * @return PreparedStatement The initialised statement that can be executed.
   * @throws SQLException If errors are encountered while creating the
   *   statement.
   */
  protected abstract PreparedStatement createStatement(
      final Connection connection, final MetaData... parameters )
    throws SQLException;

  /**
   * Create a new metadata object out of the data in the specified result
   * set.
   *
   * @param resultSet The result set from which to create the metadata.
   * @param collection The collection to which the metadata objects are
   *   to be added.
   * @param parameters The array of {@link RootMetaData} and {@link
   *   TableMetaData} objects.
   * @throws SQLException If errors are encountered while fetching the
   *   data from the result set.
   */
  protected abstract void createMetaData( final ResultSet resultSet,
      final Collection<TriggerMetaData> collection,
      final MetaData... parameters ) throws SQLException;
}
