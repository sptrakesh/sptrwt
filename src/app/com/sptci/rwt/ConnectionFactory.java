package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import com.sptci.util.CloseJDBCResources;

/**
 * A factory used to obtain database connections.  Database connections can
 * be obtained through {@link javax.sql.DataSource} or {@link
 * java.sql.DriverManager}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-24
 * @version $Id: ConnectionFactory.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class ConnectionFactory
{
  /**
   * A map used to maintain {@link javax.sql.DataSource} references to
   * avoid having to look it up each time.
   */
  private static final Map<String,DataSource> cache =
    new ConcurrentHashMap<String,DataSource>();

  /**
   * Fetch a connection to the database defined by the specified parameters
   * object.
   *
   * @param parameters The value object with the connection parameters.
   * @return The connection to the database.
   * @throws ConnectionException If errors are encountered while fetching
   *   the connection.
   */
  public static Connection open(
      final ConnectionParameters parameters ) throws ConnectionException
  {
    Connection connection = null;

    try
    {
      Class.forName( parameters.driver );
      String url = parameters.getUrl();

      connection = DriverManager.getConnection(
          url, parameters.userName, parameters.password );
    }
    catch ( Throwable t )
    {
      throw new ConnectionException( t );
    }

    return connection;
  }

  /**
   * Fetch a connection to the database via a relative {@link
   * javax.sql.DataSource} name.
   *
   * @param name The name of the datasource from which to fetch a connection.
   * @return The connection to the database.
   * @throws ConnectionException If errors are encountered while fetching
   *   the connection.
   */
  public static Connection open( final String name )
    throws ConnectionException
  {
    Connection connection = null;

    try
    {
      DataSource ds = cache.get( name );
      if ( ds == null )
      {
        Context context = new InitialContext();
        ds = (DataSource) context.lookup( name );
        cache.put( name, ds );
      }

      connection = ds.getConnection();
    }
    catch ( Throwable t )
    {
      throw new ConnectionException( t );
    }

    return connection;
  }

  /**
   * Close the specified connection.  Fail-safe method that only logs errors.
   *
   * @see com.sptci.util.CloseJDBCResources#close( Connection )
   * @param connection The connection that is to be closed.
   */
  public static final void close( final Connection connection )
  {
    CloseJDBCResources.close( connection );
  }
}
