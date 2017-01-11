package com.sptci.rwt;

import java.io.FileNotFoundException;
import java.io.Serializable;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;
import com.sptci.util.StringUtilities;

/**
 * A serializable wrapper used to represent saved JDBC connections for
 * the application.  This class will be serialised to the following file
 * and initialised from the same file during application load:
 *
 * <pre>&lt;sptrwt.data.directory&gt;/connections.xml</pre>
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-24
 * @version $Id: Connections.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class Connections implements Serializable
{
  /**
   * The encoding to use to serialise and deserialise instances of this
   * class.
   *
   * {@value}
   */
  public static final String ENCODING = "UTF-8";

  /**
   * The name of the file to which this class will be serialised.
   */
  public static final String FILE_NAME = "connections.xml";

  /**
   * The system property used to configure the location of the root directory
   * under which persistent state information for the application is stored.
   *
   * {@value}
   */
  public static final String DIRECTORY = "sptrwt.data.directory";

  /**
   * The {@link com.thoughtworks.xstream.XStream} instance used to serialise
   * and deserialise instances of this class.
   */
  protected static final XStream xstream;

  /**
   * Static initialiser for {@link #xstream}.
   */
  static
  {
    xstream = new XStream();
    xstream.alias( "connections", Connections.class );

    xstream.alias( "databaseType", DatabaseType.class );
    xstream.useAttributeFor( DatabaseType.class, "name" );
    xstream.useAttributeFor( DatabaseType.class, "driver" );
    xstream.useAttributeFor( DatabaseType.class, "urlPattern" );

    xstream.alias( "connectionData", ConnectionData.class );
  }

  /**
   * A map used to quickly look up {@link DatabaseType} instances by their
   * {@link DatabaseType#name}.
   */
  private final TreeMap<String,DatabaseType> databases;

  /**
   * The fully qualified file name to use to serialise this instance into.
   */
  private transient String fileName;

  /**
   * Default constructor.  Cannot be instantiated.
   */
  protected Connections()
  {
    databases = new TreeMap<String,DatabaseType>();
  }

  /**
   * Create a new instance of the class for the specified user.  Saved
   * files are stored under a directory named after the <code>user</code>
   * under {@link #DIRECTORY}.
   * 
   * @see #deserialise
   * @return The newly initialised instance of the class.
   * @throws RuntimeException If errors are encountered while deserialising
   *   the persistent state of this instance.
   */
  public static Connections getInstance( final String user )
  {
    final Connections connections = new Connections();
    connections.deserialise( user );
    return connections;
  }

  /**
   * Return a {@link java.sql.Connection} for the specified database type
   * and saved with the specified unique name.
   *
   * @see #getConnectionParameters
   * @see ConnectionFactory#open( ConnectionParameters )
   * @param databaseType The name of the database engine.
   * @param name The unique name used to identify the saved connection.
   * @return The connection object or <code>null</code> if no saved
   *   information can be found for the specified parameters.
   * @throws ConnectionException If errors are encountered while initiating
   *   the connection.
   */
  public Connection getConnection( final String databaseType,
      final String name ) throws ConnectionException
  {
    Connection connection = null;
    final ConnectionParameters parameters =
      getConnectionParameters( databaseType, name );
    if ( parameters != null )
    {
      connection = ConnectionFactory.open( parameters );
    }

    return connection;
  }

  /**
   * Return a value object that represents all the connection information
   * for the specified database type and saved with the specified unique
   * name.
   *
   * @param databaseType The name of the database engine.
   * @param name The unique name used to identify the saved connection.
   * @return The appropriate value object or <code>null</code> if no
   *   matching saved connection is found.
   */
  public ConnectionParameters getConnectionParameters(
      final String databaseType, final String name )
  {
    ConnectionParameters parameters = null;
    final DatabaseType type = databases.get( databaseType );

    if ( type != null )
    {
      final ConnectionData data = type.getConnectionData( name );
      if ( data != null )
      {
        parameters = new ConnectionParameters(
            data.getUserName(), data.getPassword(), data.getHost(),
            data.getPort(), data.getDatabase(), type.getName(),
            type.getUrlPattern(), type.getDriver() );
      }
    }

    return parameters;
  }

  /**
   * Add the specified connection parameters value object to the application
   * persistent state.
   *
   * @see #serialise
   * @param name The unique name to use to identify the saved connection.
   * @param parameters The parameters to be saved to {@link #FILE_NAME}.
   */
  public void add( final String name, final ConnectionParameters parameters )
  {
    DatabaseType type = databases.get( parameters.databaseType );
    if ( type == null )
    {
      type = new DatabaseType();
      type.setName( parameters.databaseType );
      type.setUrlPattern( parameters.urlPattern );
      type.setDriver( parameters.driver );
      databases.put( parameters.databaseType, type );
    }

    final ConnectionData data = new ConnectionData();
    data.setHost( parameters.host );
    data.setPort( parameters.port );
    data.setDatabase( parameters.database );
    data.setUserName( parameters.userName );
    data.setPassword( parameters.password );

    type.add( name, data );
    serialise();
  }

  /**
   * Remove the specified connection parameters from the application
   * persistent state.
   *
   * @see #serialise
   * @param databaseType The database type under which the connection was
   *   saved.
   * @param name The unique name to use to identify the saved connection.
   */
  public void delete( final String databaseType, final String name )
  {
    final DatabaseType type = databases.get( databaseType );
    if ( type != null ) type.delete( name );
    serialise();
  }

  /**
   * Remove the specified database from persistent state.  This removes
   * all saved connections for that database engine.
   *
   * @see #serialise
   * @param name The name of the database engine to remove from saved state.
   */
  public void delete( final String name )
  {
    databases.remove( name );
    serialise();
  }

  /**
   * Deserialise the contents of {@link #FILE_NAME} into this instance.
   *
   * @param user The name of the user to use to construct the full filename.
   * @throws RuntimeException If errors are encountered while deserialising
   *   the persistent state.  No exceptions are thrown if the file does
   *   not exist.
   */
  protected void deserialise( final String user ) throws RuntimeException
  {
    final String separator =
      System.getProperties().getProperty( "file.separator" );

    final StringBuilder builder = new StringBuilder( 64 );
    builder.append( System.getProperty( DIRECTORY ) );
    builder.append( separator );
    builder.append( user );
    builder.append( separator );
    builder.append( FILE_NAME );
    fileName = builder.toString();

    try
    {
      final String xml = StringUtilities.fromFile( fileName, ENCODING );
      xstream.fromXML( xml, this );
    }
    catch ( FileNotFoundException fnfe ) {}
    catch ( Throwable t )
    {
      throw new RuntimeException( "Error deserialising saved instance", t );
    }
  }

  /**
   * Serialise this instance to the {@link #FILE_NAME}.
   *
   * @throws RuntimeException If errors are encountered while serialising
   *   the instance.
   */
  protected void serialise() throws RuntimeException
  {
    try
    {
      final String xml = xstream.toXML( this );
      StringUtilities.toFile( xml, fileName, ENCODING );
    }
    catch ( Throwable t )
    {
      throw new RuntimeException( t );
    }
  }
  
  /**
   * Returns the {@link DatabaseType} identified by the {@link DatabaseType#name}
   * parameter specified.
   * 
   * @param name The name to use to fetch the database type.
   * @return The database type associated with the <code>name</code> or
   *   <code>null</code> if no such database type exists.
   */
  public DatabaseType getDatabaseType( final String name )
  {
    return databases.get( name );
  }
  
  /**
   * Returns {@link #databases}.
   *
   * @return A read only view of the collection.
   */
  public Collection<DatabaseType> getDatabaseTypes()
  {
    return Collections.unmodifiableCollection( databases.values() );
  }
  
  /**
   * Returns the collection of {@link DatabaseType#name} values stored as
   * <code>key</code> in {@link #databases}.
   * 
   * @return The collection of names.
   */
  public Collection<String> getDatabaseTypeNames()
  {
    return Collections.unmodifiableCollection( databases.keySet() );
  }
}
