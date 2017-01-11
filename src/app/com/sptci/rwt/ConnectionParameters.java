package com.sptci.rwt;

import java.io.Serializable;

/**
 * A simple value object used to represent the data required to initiate
 * a connection to a database.  T
 * be obtained through {@link javax.sql.DataSource} or {@link
 * java.sql.DriverManager}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-24
 * @version $Id: ConnectionParameters.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class ConnectionParameters implements Serializable
{
  /**
   * The encoded value used to represent the <code>host</code> part of a
   * JDBC connection url.
   *
   * {@value}
   */
  public static final String HOST = "$HOST$";

  /**
   * The encoded value used to represent the <code>port</code> part of a
   * JDBC connection url.
   *
   * {@value}
   */
  public static final String PORT = "$PORT$";

  /**
   * The encoded value used to represent the <code>database</code> part of a
   * JDBC connection url.
   *
   * {@value}
   */
  public static final String DATABASE = "$DATABASE$";

  /**
   * The fully qualified hostname of the database server to connect to.
   */
  public final String host;

  /**
   * The name of the database to connect to.
   */
  public final String database;

  /**
   * The port to connect to.
   */
  public final int port;

  /**
   * The database user to connect as.
   */
  public final String userName;

  /**
   * The password for the database user to connect as.
   */
  public transient final String password;

  /**
   * The name to use to identify the database engine.
   */
  public final String databaseType;

  /**
   * The JDBC connection url pattern to use.  The URL pattern contains
   * encoded values for variables such as <code>$HOST$</code> etc.
   */
  public final String urlPattern;
  
  /**
   * The fully qualified name of the JDBC driver class.
   */
  public final String driver;

  /**
   * Designated constructor.  Create a new instance with the specified
   * values.
   *
   * @param userName The {@link #userName} value to use.
   * @param password The {@link #password} value to use.
   * @param host The {@link #host} value to use.
   * @param port The {@link #port} value to use.
   * @param database The {@link #database} value to use.
   * @param urlPattern The {@link #urlPattern} value to use.
   */
  public ConnectionParameters( final String userName, final String password,
      final String host, final int port, final String database,
      final String databaseType, final String urlPattern,
      final String driver )
  {
    this.userName = userName;
    this.password = password;
    this.host = host;
    this.port = port;
    this.database = database;
    this.databaseType = databaseType;
    this.urlPattern = urlPattern;
    this.driver = driver;
  }

  /**
   * Return the proper JDBC connection url after replacing place holders
   * in {@link #urlPattern}.
   *
   * @return The proper connection url.
   */
  public String getUrl()
  {
    String url = urlPattern.replace( HOST, host );
    url = url.replace( DATABASE, database );
    url = url.replace( PORT, String.valueOf( port ) );
    return url;
  }
}
