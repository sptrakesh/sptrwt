package com.sptci.rwt;

import java.sql.Connection;
import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link ColumnAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ColumnAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class ColumnAnalyserTest extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( ColumnAnalyserTest.class );
  }

  /**
   * Test fetching all columns from the database connection.
   */
  public void testTableAnalyse() throws Exception
  {
    ColumnAnalyser analyser = new ColumnAnalyser(
        CreateTestObjects.manager );
    Collection<ColumnMetaData> columns = analyser.analyse(
        SchemaAnalyserTest.metadata, TableAnalyserTest.metadata );

    assertTrue( "Checking non-zero columns", columns.size() > 0 );
  }

  /**
   * Test to ensure that the {@link TableMetaData} field {@link
   * TableMetaData#columns} was updated with {@link ColumnMetaData} information.
   */
  public void testTableUpdated()
  {
    assertTrue( "Checking columns added to table metadata",
        TableAnalyserTest.metadata.getColumns().size() > 0 );
  }

  /**
   * Test fetching all columns from the database connection.
   */
  public void testViewAnalyse() throws Exception
  {
    ColumnAnalyser analyser = new ColumnAnalyser(
        CreateTestObjects.manager );
    Collection<ColumnMetaData> columns = analyser.analyse(
        SchemaAnalyserTest.metadata, ViewAnalyserTest.metadata );

    assertTrue( "Checking non-zero columns", columns.size() > 0 );
  }

  /**
   * Test to ensure that the {@link ViewMetaData} field {@link
   * ViewMetaData#columns} was updated with {@link ColumnMetaData} information.
   */
  public void testViewUpdated()
  {
    assertTrue( "Checking columns added to view metadata",
        ViewAnalyserTest.metadata.getColumns().size() > 0 );
  }
}
