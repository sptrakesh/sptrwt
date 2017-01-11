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
 * An analyser for analysing trigger type objects in the database.  This
 * works with databases that support the SQL92 standard information
 * schema.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-02
 * @version $Id: StandardTriggerAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
class StandardTriggerAnalyser extends AbstractTriggerAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  StandardTriggerAnalyser( final ConnectionManager manager )
  {
    super( manager );
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
  @Override
  protected PreparedStatement createStatement( final Connection connection,
      final MetaData... parameters ) throws SQLException
  {
    final CatalogueSchema cs = getNames( parameters[0] );

    String table = null;
    if ( parameters.length > 1 )
    {
      table = ( parameters[1] == null ) ? null : parameters[1].getName();
    }

    PreparedStatement statement = null;

    if ( ( cs.getCatalogue() == null ) && ( cs.getSchema() == null ) )
    {
      if ( table == null )
      {
        final String query = "select * from information_schema.triggers order by trigger_name";
        statement = connection.prepareStatement( query );
      }
      else
      {
        final String query = "select * from information_schema.triggers where event_object_table = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, table );
      }
    }
    else if ( cs.getCatalogue() == null )
    {
      if ( table == null )
      {
        final String query = "select * from information_schema.triggers where trigger_schema = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
      }
      else
      {
        final String query = "select * from information_schema.triggers where trigger_schema = ? and event_object_table = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getSchema() );
        statement.setString( 2, table );
      }
    }
    else if ( cs.getSchema() == null )
    {
      if ( table == null )
      {
        final String query = "select * from information_schema.triggers where trigger_catalog = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
      }
      else
      {
        final String query = "select * from information_schema.triggers where trigger_catalog = ? and event_object_table = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, table );
      }
    }
    else
    {
      if ( table == null )
      {
        final String query = "select * from information_schema.triggers where trigger_catalog = ? and trigger_schema = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, cs.getSchema() );
      }
      else
      {
        final String query = "select * from information_schema.triggers where trigger_catalog = ? and trigger_schema = ? and event_object_table = ? order by trigger_name";
        statement = connection.prepareStatement( query );
        statement.setString( 1, cs.getCatalogue() );
        statement.setString( 2, cs.getSchema() );
        statement.setString( 3, table );
      }
    }

    return statement;
  }

  /**
   * Create a new metadata object out of the data in the specified result
   * set.
   *
   * @see #getEvent
   * @see #getOrientation
   * @param resultSet The result set from which to create the metadata.
   * @param collection The collection to which the metadata objects are
   *   to be added.
   * @param parameters The array of {@link RootMetaData} and {@link
   *   TableMetaData} objects.
   * @throws SQLException If errors are encountered while fetching the
   *   data from the result set.
   */
  @Override
  protected void createMetaData( final ResultSet resultSet,
      final Collection<TriggerMetaData> collection,
      final MetaData... parameters ) throws SQLException
  {
    while ( resultSet.next() )
    {
      TriggerMetaData tmd = new TriggerMetaData();

      tmd.setName( resultSet.getString( "trigger_name" ) );
      tmd.setTable( resultSet.getString( "event_object_table" ) );
      tmd.setTableSchema( resultSet.getString( "event_object_schema" ) );
      tmd.setStatement( resultSet.getString( "action_statement" ) );

      tmd.setEvent(
          getEvent( resultSet.getString( "event_manipulation" ) ) );
      tmd.setOrientation( getOrientation(
            resultSet.getString( "action_orientation" ) ) );
      tmd.setTiming( getTiming(
            resultSet.getString( "condition_timing" ) ) );

      if ( parameters[0] != null )
      {
        tmd.setRoot( (RootMetaData) parameters[0] );
      }
      if ( ( parameters.length > 1 ) && ( parameters[1] != null ) )
      {
        tmd.setTableMetaData( (TableMetaData) parameters[1] );
      }

      collection.add( tmd );
    }
  }

  /**
   * Map the <code>event_manipulation</code> field value to an appropriate
   * {@link TriggerMetaData.Event} type.
   *
   * @param manipulation The value to be mapped.
   * @return The appropriate enumerated type.
   */
  protected TriggerMetaData.Event getEvent( final String manipulation )
  {
    TriggerMetaData.Event event = null;

    if ( "insert".equalsIgnoreCase( manipulation ) )
    {
      event = TriggerMetaData.Event.Insert;
    }
    else if ( "update".equalsIgnoreCase( manipulation ) )
    {
      event = TriggerMetaData.Event.Update;
    }
    else if ( "delete".equalsIgnoreCase( manipulation ) )
    {
      event = TriggerMetaData.Event.Delete;
    }

    return event;
  }

  /**
   * Map the <code>action_orientation</code> field value to an appropriate
   * {@link TriggerMetaData.Orientation} type.
   *
   * @param action The value to be mapped.
   * @return The appropriate enumerated type.
   */
  protected TriggerMetaData.Orientation getOrientation( final String action )
  {
    TriggerMetaData.Orientation orientation = null;

    if ( "row".equalsIgnoreCase( action ) )
    {
      orientation = TriggerMetaData.Orientation.Row;
    }
    else if ( "statement".equalsIgnoreCase( action ) )
    {
      orientation = TriggerMetaData.Orientation.Statement;
    }

    return orientation;
  }

  /**
   * Map the <code>condition_timing</code> field value to an appropriate
   * {@link TriggerMetaData.Timing} type.
   *
   * @param condition The value to be mapped.
   * @return The appropriate enumerated type.
   */
  protected TriggerMetaData.Timing getTiming( final String condition )
  {
    TriggerMetaData.Timing timing = null;

    if ( "before".equalsIgnoreCase( condition ) )
    {
      timing = TriggerMetaData.Timing.Before;
    }
    else if ( "after".equalsIgnoreCase( condition ) )
    {
      timing = TriggerMetaData.Timing.After;
    }

    return timing;
  }
}
