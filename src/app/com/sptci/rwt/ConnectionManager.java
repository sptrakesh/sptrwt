package com.sptci.rwt;

import java.sql.Connection;

/**
 * A manager used to globally maintain a single means of connecting to a
 * database.  Connections are obtained through {@link javax.sql.DataSource}
 * or {@link java.sql.DriverManager}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: ConnectionManager.java 3618 2007-10-15 00:58:30Z rakesh $
 */
public class ConnectionManager
{
  /**
   * The name of the data source to use to obtain connections.
   */
  private String dataSource;

  /**
   * The connection parameters to use to obtain connections.
   */
  private ConnectionParameters parameters;

  /**
   * Default constructor.  Cannot be instantiated.
   */
  private ConnectionManager() {}

  /**
   * Create a new instance of the class configured to use the specifiedj
   * {@link #dataSource}.
   *
   * @param dataSource The {@link #dataSource} value to use.
   */
  public ConnectionManager( final String dataSource )
  {
    setDataSource( dataSource );
  }

  /**
   * Create a new instance of the class configured to use the specified
   * {@link #parameters}.
   *
   * @param parameters The {@link #parameters} to use.
   */
  public ConnectionManager( final ConnectionParameters parameters )
  {
    setParameters( parameters );
  }

  /**
   * Open a connection to the database using the currently active {@link
   * #dataSource} or {@link #parameters}.
   *
   * @see ConnectionFactory#open( String )
   * @see ConnectionFactory#open( ConnectionParameters )
   * @return The connection to the database.
   * @throws ConnectionException If errors are encountered while fetching 
   *   the connection to the database.  Can also be thrown if this instance
   *   has not been initialised through {@link #setDataSource} or {@link
   *   #setParameters}.
   */
  public Connection open() throws ConnectionException
  {
    return ( ( dataSource == null ) ? 
        ConnectionFactory.open( parameters ) :
        ConnectionFactory.open( dataSource ) );
  }

  /**
   * Close the specified connection.
   *
   * @see ConnectionFactory#close
   */
  public void close( final Connection connection )
  {
    ConnectionFactory.close( connection );
  }
  
  /**
   * Returns {@link #dataSource}.
   *
   * @return The value/reference of/to dataSource.
   */
  public String getDataSource()
  {
    return dataSource;
  }
  
  /**
   * Set {@link #dataSource}.  If a valid string was specified, sets {@link
   * #parameters} to <code>null</code>.
   *
   * @param dataSource The value to set.
   */
  public void setDataSource( final String dataSource )
  {
    this.dataSource = dataSource;
    if ( dataSource != null ) parameters = null;
  }
  
  /**
   * Returns {@link #parameters}.
   *
   * @return The value/reference of/to parameters.
   */
  public ConnectionParameters getParameters()
  {
    return parameters;
  }
  
  /**
   * Set {@link #parameters}.  If valid parameters were specified, sets
   * {@link #dataSource} to <code>null</code>.
   *
   * @param parameters The value to set.
   */
  public void setParameters( final ConnectionParameters parameters )
  {
    this.parameters = parameters;
    if ( parameters != null ) dataSource = null;
  }
  
  /**
   * Return a name indicating the current connection source.
   * 
   * @return The name inidicating the connection source in use.
   */
  public String getTitle()
  {
    String result = "";
    if ( dataSource != null )
    {
      result = dataSource;
    }
    else
    {
      result = parameters.databaseType + "(" + parameters.database + ":" +
          parameters.userName + ")";
    }
    
    return result;
  }
}
