package com.sptci.rwt;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple bean used to represent different types of database engines
 * that the application can connect to.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-24
 * @version $Id: DatabaseType.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class DatabaseType implements Serializable
{
  /**
   * A map that stores saved {@link ConnectionData} identified by a
   * unique name.
   */
  private Map<String,ConnectionData> savedConnections =
    new TreeMap<String,ConnectionData>();

  /**
   * The name of the database engine represented by this instance.
   */
  private String name;

  /**
   * The encoded JDBC url pattern to use to build a connection url to the
   * database.
   */
  private String urlPattern;

  /**
   * The fully qualified name of the JDBC driver class.
   */
  private String driver;

  /**
   * Default constructor.  Not publicly instantiable.
   */
  protected DatabaseType() {}

  /**
   * Check to see if a saved connection exists with the specified name.
   *
   * @param name The name to check for uniqueness.
   */
  public boolean hasName( final String name )
  {
    return savedConnections.containsKey( name );
  }

  /**
   * Return a collection of all the names used to store connections.
   *
   * @return The collection of unique names for saved connections.
   */
  public Collection<String> getNames()
  {
    return Collections.unmodifiableCollection( savedConnections.keySet() );
  }

  /**
   * Return the connection data uniquely identified by the specified name.
   *
   * @param name The unique name to use to fetch the connection data.
   * @return The matching connection data object or <code>null</code> if
   *   no such mapping exists.
   */
  public ConnectionData getConnectionData( final String name )
  {
    return savedConnections.get( name );
  }

  /**
   * Return a collection of all the saved connection data objects.
   *
   * @return The map of all saved name-connection combinations.
   */
  public Map<String,ConnectionData> getConnectionData()
  {
    return Collections.unmodifiableMap( savedConnections );
  }

  /**
   * Add the specified connection data to the {@link #savedConnections} map.
   * If such a mapping already exists, the existing mapping is updated.
   *
   * @see #hasName
   * @param name The unique name to use to identify this connection.
   * @param data The connection data to be saved.
   */
  void add( final String name, final ConnectionData data )
  {
    savedConnections.put( name, data );
  }

  /**
   * Remove the saved connection identified by the specified name.
   *
   * @param name The unique name to use to identify the connection to remove.
   */
  void delete( final String name )
  {
    savedConnections.remove( name );
  }
  
  /**
   * Returns {@link #name}.
   *
   * @return The value/reference of/to name.
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Set {@link #name}.
   *
   * @param name The value to set.
   */
  protected void setName( final String name )
  {
    this.name = name;
  }
  
  /**
   * Returns {@link #urlPattern}.
   *
   * @return The value/reference of/to urlPattern.
   */
  public String getUrlPattern()
  {
    return urlPattern;
  }
  
  /**
   * Set {@link #urlPattern}.
   *
   * @param urlPattern The value to set.
   */
  protected void setUrlPattern( final String urlPattern )
  {
    this.urlPattern = urlPattern;
  }
  
  /**
   * Returns {@link #driver}.
   *
   * @return The value/reference of/to driver.
   */
  public String getDriver()
  {
    return driver;
  }
  
  /**
   * Set {@link #driver}.
   *
   * @param driver The value to set.
   */
  protected void setDriver( final String driver )
  {
    this.driver = driver;
  }
}
