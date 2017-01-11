package com.sptci.rwt;

import java.sql.Connection;
import java.util.Map;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link Connections} object.  Test initialising,
 * adding and deleting saved connections.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ConnectionsTest.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class ConnectionsTest extends TestCase
{
  static Connections connections;
  static final String userName = "user" + System.currentTimeMillis();
  static final String connectionName = "connection" + System.currentTimeMillis();

  public static Test suite()
  {
    return new TestSuite( ConnectionsTest.class );
  }

  /**
   * Test instantiating a new instance through the {@link
   * Connections#getInstance} method when no saved state is available.
   */
  public void testNewInstantiation() throws Exception
  {
    connections = Connections.getInstance( userName );
    assertTrue( "Checking no saved data",
        connections.getDatabaseTypes().size() == 0 );
  }

  /**
   * Test adding a new {@link ConnectionParameters} to the saved state using
   * the {@link Connections#add} method.
   */
  public void testAdd()
  {
    connections.add( connectionName, CreateTestObjects.parameters );
    assertTrue( "Checking saved connection added",
        connections.getDatabaseTypes().size() > 0 );
  }

  /**
   * Test fetching a {@link java.sql.Connection} using the {@link
   * Connections#getConnection} method.
   *
   * @see #connect
   */
  public void testConnection()
  {
    connect( CreateTestObjects.parameters.databaseType,
        connectionName, connections );
  }

  /**
   * Test fetching a {@link java.sql.Connection} using the {@link
   * ConnectionFactory#open( ConnectionParameters )} method after fetching
   * the parameters from {@link Connections#getConnectionParameters}.
   *
   * @see #connectFromParameters
   */
  public void testConnectionFromParameters()
  {
    connectFromParameters( connections );
  }

  /**
   * Test adding a duplicate connection with same saved name.  Exception
   * test case.
   */
  public void testDuplicateName()
  {
    connections.add( connectionName, CreateTestObjects.parameters );
    assertTrue( "Checking saved connection added",
        connections.getDatabaseTypes().size() > 0 );
  }

  /**
   * Test initialisation of {@link Connections} from persistent state.
   *
   * @see #connect
   * @see #connectFromParameters
   */
  public void testInitialisation()
  {
    Connections c = Connections.getInstance( userName );
    assertTrue( "Ensuring saved data loaded", c.getDatabaseTypes().size() > 0 );
    for ( DatabaseType type : c.getDatabaseTypes() )
    {
      for ( Map.Entry<String,ConnectionData> entry :
          type.getConnectionData().entrySet() )
      {
        connect( type.getName(), entry.getKey(), c );
      }
    }

    connectFromParameters( c );
  }

  /**
   * Test removing a saved connection from the persistent state using the
   * {@link Connections#delete( String, String )} method.
   */
  public void testDeleteConnection()
  {
    connections.delete(
        CreateTestObjects.parameters.databaseType, connectionName );
    for ( DatabaseType type : connections.getDatabaseTypes() )
    {
      assertTrue( "Checking saved connection removed",
          type.getConnectionData().size() == 0 );
    }
  }

  /**
   * Test removing a database from the persistent state using the
   * {@link Connections#delete( String )} method.
   */
  public void testDelete()
  {
    testAdd();
    connections.delete( CreateTestObjects.parameters.databaseType );
    assertTrue( "Checking saved database removed",
        connections.getDatabaseTypes().size() == 0 );
  }

  /**
   * Open a connection and close it to test.
   *
   * @param databaseType The database engine to connect to.
   * @param name The name of the saved connection.
   * @param connections The connections object to use.
   */
  protected void connect( final String databaseType, final String name,
      final Connections connections )
  {
    Connection connection =
      connections.getConnection( databaseType, name );
    assertNotNull( "Checking valid connection", connection );
    ConnectionFactory.close( connection );
  }

  /**
   * Open a connection using {@link ConnectionParameters} and close it.
   *
   * @param connections The connections object to use.
   */
  protected void connectFromParameters( final Connections connections )
  {
    ConnectionParameters parms = connections.getConnectionParameters(
        CreateTestObjects.parameters.databaseType, connectionName );
    assertNotNull( "Checking valid ConnectionParameters", parms );

    Connection connection = ConnectionFactory.open( parms );
    assertNotNull( "Checking valid connection", connection );
    ConnectionFactory.close( connection );
  }
}
