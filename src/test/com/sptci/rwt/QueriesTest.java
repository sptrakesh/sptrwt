package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link Queries} object.  Test initialising,
 * adding and deleting saved queries.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: QueriesTest.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class QueriesTest extends TestCase
{
  static Queries queries;
  static final String userName = ConnectionsTest.userName;
  static final String categoryName = "category" + System.currentTimeMillis();
  static final String queryName = "query" + System.currentTimeMillis();
  static final Query query = new Query( queryName, "select * from activities where pub_date < ?" );;

  public static Test suite()
  {
    return new TestSuite( QueriesTest.class );
  }

  /**
   * Test instantiating a new instance through the {@link
   * Queries#getInstance} method when no saved state is available.
   */
  public void testNewInstantiation() throws Exception
  {
    queries = Queries.getInstance( userName );
    assertTrue( "Checking no saved data",
        queries.getCategories().size() == 0 );
  }

  /**
   * Test adding a new {@link Query} to the saved state using
   * the {@link Queries#add} method.
   */
  public void testAdd()
  {
    queries.add( categoryName, query );
    assertTrue( "Checking category added",
        queries.getCategories().size() > 0 );
    for ( Category c : queries.getCategories() )
    {
      assertTrue( "Checking saved query added",
          c.getQueries().size() > 0 );
    }
  }

  /**
   * Test fetching a {@link Query} instance using the {@link
   * Queries#getQuery} method.
   */
  public void testQuery()
  {
    testDelete();
    testAdd();
    Query q = queries.getQuery( categoryName, queryName );
    assertNotNull( "Checking query found", q );
    assertEquals( "Ensuring query found is same as inserted", q, query );
    testDelete();
  }

  /**
   * Test adding a duplicate query with same saved name.  Exception
   * test case.
   */
  public void testDuplicateName()
  {
    try
    {
      queries.add( categoryName, query );
      fail( "Duplicate query saved!" );
    }
    catch ( Throwable t ) {}
  }

  /**
   * Test initialisation of {@link Queries} from persistent state.
   */
  public void testInitialisation()
  {
    Queries q = Queries.getInstance( userName );
    assertTrue( "Ensuring saved data loaded", q.getCategories().size() > 0 );
  }

  /**
   * Test removing a saved query from the persistent state using the
   * {@link Queries#delete( String, String )} method.
   */
  public void testDeleteQuery()
  {
    testAdd();
    queries.delete( categoryName, queryName );
    for ( Category cat : queries.getCategories() )
    {
      assertEquals( "Checking saved query removed",
          cat.getQueries().size(), 0 );
    }
  }

  /**
   * Test removing a category from the persistent state using the
   * {@link Queries#delete( String )} method.
   */
  public void testDelete()
  {
    queries.delete( categoryName );
    assertEquals( "Checking saved category removed",
        queries.getCategories().size(), 0 );
  }
}
