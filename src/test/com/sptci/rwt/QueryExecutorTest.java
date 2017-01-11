package com.sptci.rwt;

import java.util.List;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link QueryExecutor} class.
 * 
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-02
 * @version $Id: QueryExecutorTest.java 3579 2007-10-08 04:06:37Z rakesh $
 */
public class QueryExecutorTest extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( QueryExecutorTest.class );
  }

  /** Test executing an <code>insert</code> statement.  */
  public void testInsert() throws Exception
  {
    final Rows rows = insert();

    try
    {
      assertEquals( "Ensuring one row is returned after insert",
          rows.getRows().size(), 1 );
      assertEquals( "Ensuring one column returned for row inserted",
          rows.getRows().get( 0 ).getColumns().size(), 1 );
      assertEquals( "Ensuring update count after insert",
          rows.getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
    }
    finally
    {
      delete();
    }
  }

  /** Test executing a <code>select</code> statement. */
  public void testSelect() throws Exception
  {
    insert();

    try
    {
      final QueryExecutor executor =
        new QueryExecutor( CreateTestObjects.manager );
      final String statement = "select * from " + CreateTestObjects.tableName;
      final Rows rows = executor.execute( statement );

      assertEquals( "Ensuring one row is returned for select",
          rows.getRows().size(), 1 );
      assertEquals( "Ensuring two columns returned for select",
          rows.getRows().get( 0 ).getColumns().size(), 2 );
    }
    finally
    {
      delete();
    }
  }

  /** Test executing an <code>update</code> statement. */
  public void testUpdate() throws Exception
  {
    insert();

    try
    {
      final QueryExecutor executor =
        new QueryExecutor( CreateTestObjects.manager );
      final String statement = "update " + CreateTestObjects.tableName +
        " set description='test description modified'";
      final Rows rows = executor.execute( statement );

      assertEquals( "Ensuring one row is returned after update",
          rows.getRows().size(), 1 );
      assertEquals( "Ensuring one column returned for row update",
          rows.getRows().get( 0 ).getColumns().size(), 1 );
      assertEquals( "Ensuring update count after update",
          rows.getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
    }
    finally
    {
      delete();
    }
  }

  /** Test paging of result set */
  public void testPaging() throws Exception
  {
    BatchQueryExecutorTest.insert();

    try
    {
      final int pageSize = 10;
      final int maxRows = 0;

      final QueryExecutor executor =
        new QueryExecutor( CreateTestObjects.manager );

      for ( int page = 0; page < 10; ++page )
      {
        final String statement = "select * from " + CreateTestObjects.tableName;
        final Rows rows = executor.execute( statement, maxRows, page, pageSize );

        assertEquals( "Ensuring " + pageSize + " rows returned after select",
            rows.getRows().size(), pageSize );
        assertEquals( "Ensuring two columns returned for select",
            rows.getRows().get( 0 ).getColumns().size(), 2 );
        assertEquals(
            "Ensuring proper value in name column for page " + page,
          rows.getRows().get( 0 ).getColumns().get( 0 ).getContent(),
          String.valueOf( ( page * pageSize ) ) );
      }
    }
    finally
    {
      BatchQueryExecutorTest.delete();
    }
  }

  /** Test restricting rows of result set */
  public void testMaxRows() throws Exception
  {
    BatchQueryExecutorTest.insert();

    try
    {
      final int maxRows = 25;

      final QueryExecutor executor =
        new QueryExecutor( CreateTestObjects.manager );

      final String statement = "select * from " + CreateTestObjects.tableName;
      final Rows rows = executor.execute( statement, maxRows );

      assertEquals( "Ensuring " + maxRows + " rows returned after select",
          rows.getRows().size(), maxRows );
      assertEquals( "Ensuring two columns returned for select",
          rows.getRows().get( 0 ).getColumns().size(), 2 );
    }
    finally
    {
      BatchQueryExecutorTest.delete();
    }
  }

  /** Test fetching the total rows in a result set. */
  public void testTotalRows() throws Exception
  {
    BatchQueryExecutorTest.insert();

    try
    {
      final QueryExecutor executor =
        new QueryExecutor( CreateTestObjects.manager );

      final String statement = "select * from " + CreateTestObjects.tableName;
      final int total = executor.getTotalRows( statement );

      assertEquals( "Ensuring " + BatchQueryExecutorTest.total +
          " rows returned after select", total, BatchQueryExecutorTest.total );
    }
    finally
    {
      BatchQueryExecutorTest.delete();
    }
  }

  /** Test executing a <code>delete</code> statement. */
  public void testDelete() throws Exception
  {
    insert();
    final Rows rows = delete();

    assertEquals( "Ensuring one row is returned after delete",
        rows.getRows().size(), 1 );
    assertEquals( "Ensuring one column returned for row delete",
        rows.getRows().get( 0 ).getColumns().size(), 1 );
    assertEquals( "Ensuring update count after delete",
        rows.getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
  }

  /** Insert the test record to table. */
  private Rows insert() throws Exception
  {
    final QueryExecutor executor =
      new QueryExecutor( CreateTestObjects.manager );
    final String statement = "insert into " + CreateTestObjects.tableName +
      " values ('test name','test description')";
    return executor.execute( statement );
  }

  /** Delete the test record added to table. */
  private Rows delete() throws Exception
  {
    final QueryExecutor executor =
      new QueryExecutor( CreateTestObjects.manager );
    final String statement = "delete from " + CreateTestObjects.tableName;
    return executor.execute( statement );
  }
}
