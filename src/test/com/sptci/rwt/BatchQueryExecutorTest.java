package com.sptci.rwt;

import java.util.List;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link BatchQueryExecutor} class.
 * 
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-02
 * @version $Id: BatchQueryExecutorTest.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class BatchQueryExecutorTest extends TestCase
{
  static final int total = 100;

  public static Test suite()
  {
    return new TestSuite( BatchQueryExecutorTest.class );
  }

  /** Test executing <code>insert</code> statements.  */
  public void testInsert() throws Exception
  {
    final List<Rows> rows = insert();

    try
    {
      assertEquals( "Ensuring rows are returned after insert",
          rows.size(), total );
      assertEquals( "Ensuring one column returned for row inserted",
          rows.get( 0 ).getRows().get( 0 ).getColumns().size(), 1 );
      assertEquals( "Ensuring update count after insert",
          rows.get( 0 ).getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
    }
    finally
    {
      delete();
    }
  }

  /** Test executing <code>select</code> statements. */
  public void testSelect() throws Exception
  {
    insert();

    try
    {
      final BatchQueryExecutor executor =
        new BatchQueryExecutor( CreateTestObjects.manager );

      final StringBuilder builder = new StringBuilder();
      final int count = 5;
      for ( int i = 0; i < count; ++i )
      {
        builder.append( "select * from " );
        builder.append( CreateTestObjects.tableName );
        builder.append( ";\n" );
      }
      final List<Rows> rows = executor.execute( builder.toString() );

      assertEquals( "Ensuring rows are returned for select",
          rows.size(), count );
      assertEquals( "Ensuring two columns returned for select",
          rows.get( 0 ).getRows().get( 0 ).getColumns().size(), 2 );
    }
    finally
    {
      delete();
    }
  }

  /** Test executing <code>update</code> statements. */
  public void testUpdate() throws Exception
  {
    insert();

    try
    {
      final BatchQueryExecutor executor =
        new BatchQueryExecutor( CreateTestObjects.manager );
      final StringBuilder builder = new StringBuilder();
      for ( int i = 0; i < total; ++i )
      {
        String statement = "update $TABLENAME$ " +
          "set description='test description modified' " +
          "where name = '$i$';\n";
        statement =
          statement.replaceAll( "\\$TABLENAME\\$", CreateTestObjects.tableName );
        statement =
          statement.replaceAll( "\\$i\\$", String.valueOf( i ) );
        builder.append( statement );
      }
      final List<Rows> rows = executor.execute( builder.toString() );

      assertEquals( "Ensuring rows are returned after update",
          rows.size(), total );
      assertEquals( "Ensuring one column returned for row update",
          rows.get( 0 ).getRows().get( 0 ).getColumns().size(), 1 );
      assertEquals( "Ensuring update count after update",
          rows.get( 0 ).getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
    }
    finally
    {
      delete();
    }
  }

  /** Test specifying <code>maxRows</code> to restrict result set size. */
  public void testMaxSize() throws Exception
  {
    insert();

    try
    {
      final BatchQueryExecutor executor =
        new BatchQueryExecutor( CreateTestObjects.manager );
      final int count = 10;
      final int results = 5;
      final StringBuilder builder = new StringBuilder();

      for ( int i = 0; i < results; ++i )
      {
        builder.append( "select * from " );
        builder.append( CreateTestObjects.tableName ).append( ";\n" );
      }
      final List<Rows> rows = executor.execute( builder.toString(), count );

      for ( int i = 0; i < results; ++i )
      {
        assertEquals( "Ensuring restricted result sets " + i,
            rows.get( i ).getRows().size(), count );
      }

      assertEquals( "Ensuring proper number of result sets",
          rows.size(), results );
      assertEquals( "Ensuring two columns returned for row select",
          rows.get( 0 ).getRows().get( 0 ).getColumns().size(), 2 );
    }
    finally
    {
      delete();
    }
  }

  /** Test the {@link TableTypeAnalyser#getNumberOfRows} method. */
  public void testNumberOfRows() throws Exception
  {
    insert();
    try
    {
      TableMetaData metadata = new TableMetaData();
      metadata.setName( CreateTestObjects.tableName );
      TableAnalyser analyser = new TableAnalyser( CreateTestObjects.manager );
      int rows = analyser.getNumberOfRows( metadata );
      assertTrue( "Ensuring non-zero table row count", rows > 0 );
    }
    finally
    {
      delete();
    }
  }

  /** Test executing a <code>delete</code> statement. */
  public void testZDelete() throws Exception
  {
    insert();
    final List<Rows> rows = delete();

    assertEquals( "Ensuring rows are returned after delete",
        rows.size(), total );
    assertEquals( "Ensuring one column returned for row delete",
        rows.get( 0 ).getRows().get( 0 ).getColumns().size(), 1 );
    assertEquals( "Ensuring update count after delete",
        rows.get( 0 ).getRows().get( 0 ).getColumns().get( 0 ).getContent(), 1 );
  }

  /** Insert test records into the table. */
  static List<Rows> insert() throws Exception
  {
    final BatchQueryExecutor executor =
      new BatchQueryExecutor( CreateTestObjects.manager );
    final StringBuilder builder = new StringBuilder( 512 );
    for ( int i = 0; i < total; ++i )
    {
      String statement = "insert into $TABLENAME$ " +
        "values ('$i$','test description');\n";
      statement =
        statement.replaceAll( "\\$TABLENAME\\$", CreateTestObjects.tableName );
      statement =
        statement.replaceAll( "\\$i\\$", String.valueOf( i ) );
      builder.append( statement ).append( "\n" );
    }

    return executor.execute( builder.toString() );
  }

  /** Delete the inserted rows from the table. */
  static List<Rows> delete() throws Exception
  {
    final BatchQueryExecutor executor =
      new BatchQueryExecutor( CreateTestObjects.manager );
    final StringBuilder builder = new StringBuilder();
    
    for ( int i = 0; i < total; ++i )
    {
      String statement = "delete from $TABLENAME$ " +
        "where name = '$i$';\n";
      statement =
        statement.replaceAll( "\\$TABLENAME\\$", CreateTestObjects.tableName );
      statement =
        statement.replaceAll( "\\$i\\$", String.valueOf( i ) );
      builder.append( statement );
    }

    return executor.execute( builder.toString() );
  }
}
