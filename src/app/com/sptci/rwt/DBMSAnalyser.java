package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing metadata about a database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: DBMSAnalyser.java 3579 2007-10-08 04:06:37Z rakesh $
 */
public class DBMSAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public DBMSAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link DBMSMetaData} objects that contain the
   * basic information pertaining to the database.
   *
   * <pre>
   *   ConnectionManager manager = new ConnectionManager( "rwt" );
   *   Collection<DBMSMetaData> dmd = new DBMSAnalyser( manager ).analyse();
   * </pre>
   *
   * @see Analyser#analyse
   * @see #analyse()
   * @see #processTransaction
   * @see #processJdbcMetaData
   * @see #processLimits
   * @param parameters No values needed.
   * @return Returns a collection with just one entry since there is only
   *   database about which metadata is retrieved.
   */
  @Override
  public Collection<DBMSMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Collection<DBMSMetaData> collection = new ArrayList<DBMSMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      DBMSMetaData dbmd = new DBMSMetaData();
      dbmd.setName( dmd.getDatabaseProductName() );
      dbmd.setVersion( dmd.getDatabaseProductVersion() );
      dbmd.setDefaultTransaction(
          processTransaction( dmd.getDefaultTransactionIsolation() ) );
      dbmd.setJdbcMetaData( processJdbcMetaData( dmd ) );
      dbmd.setLimitsMetaData( processLimits( dmd ) );
      collection.add( dbmd );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }

  /**
   * Return a {@link DBMSMetaData} object that contains the basic
   * information pertaining to the database connected to.
   *
   * @see #analyse( MetaData... )
   * @return The metadata about the database.
   * @throws SQLException If errors are encountered while analysing the
   *   database.
   */
  public DBMSMetaData analyse() throws SQLException
  {
    return ( (List<DBMSMetaData>) analyse( new MetaData[0] ) ).get( 0 );
  }

  /**
   * Map the constants defined in {@link java.sql.Connection} for
   * transaction isolation levels to the {@link DBMSMetaData.Transaction}
   * enumeration.
   *
   * @param value The constant value that indicates the transaction level.
   * @return The enumerated transaction value.
   */
  protected DBMSMetaData.Transaction processTransaction( final int value )
  {
    DBMSMetaData.Transaction tx = null;

    switch ( value )
    {
      case Connection.TRANSACTION_NONE :
        tx = DBMSMetaData.Transaction.None;
        break;
      case Connection.TRANSACTION_READ_COMMITTED :
        tx = DBMSMetaData.Transaction.ReadCommitted;
        break;
      case Connection.TRANSACTION_READ_UNCOMMITTED :
        tx = DBMSMetaData.Transaction.ReadUncommitted;
        break;
      case Connection.TRANSACTION_REPEATABLE_READ :
        tx = DBMSMetaData.Transaction.RepeatableRead;
        break;
      case Connection.TRANSACTION_SERIALIZABLE :
        tx = DBMSMetaData.Transaction.Serialisable;
        break;
    };

    return tx;
  }

  /**
   * Fetch the JDBC driver metadata from the specified database meta data.
   *
   * @param dmd The database metadata from which the JDBC driver metadata
   *   is to be retrieved.
   * @throws SQLException If errors are encountered while fetching the
   *   information.
   */
  protected JDBCMetaData processJdbcMetaData( DatabaseMetaData dmd )
    throws SQLException
  {
    JDBCMetaData md = new JDBCMetaData();
    md.setName( dmd.getDriverName() );
    md.setVersion( dmd.getDriverVersion() );
    return md;
  }

  /**
   * Fetch the limits enforced by the database.
   *
   * @param dmd The database metadata from which the database engine
   *   limits are to be retrieved.
   * @throws SQLException If errors are encountered while fetching the
   *   information.
   */
  protected LimitsMetaData processLimits( DatabaseMetaData dmd )
    throws SQLException
  {
    LimitsMetaData lmd = new LimitsMetaData();
    lmd.setCharacterLength( dmd.getMaxCharLiteralLength() );
    lmd.setColumnNameLength( dmd.getMaxColumnNameLength() );
    lmd.setColumnsInGroupBy( dmd.getMaxColumnsInGroupBy() );
    lmd.setColumnsInIndex( dmd.getMaxColumnsInIndex() );
    lmd.setColumnsInOrderBy( dmd.getMaxColumnsInOrderBy() );
    lmd.setColumnsInSelect( dmd.getMaxColumnsInSelect() );
    lmd.setColumnsInTable( dmd.getMaxColumnsInTable() );
    lmd.setConnections( dmd.getMaxConnections() );
    lmd.setCursorNameLength( dmd.getMaxCursorNameLength() );
    lmd.setIndexLength( dmd.getMaxIndexLength() );
    lmd.setProcedureNameLength( dmd.getMaxProcedureNameLength() );
    lmd.setRowSize( dmd.getMaxRowSize() );
    lmd.setSchemaNameLength( dmd.getMaxSchemaNameLength() );
    lmd.setStatements( dmd.getMaxStatements() );
    lmd.setTableNameLength( dmd.getMaxTableNameLength() );
    lmd.setTablesInSelect( dmd.getMaxTablesInSelect() );
    lmd.setUserNameLength( dmd.getMaxUserNameLength() );

    return lmd;
  }
}
