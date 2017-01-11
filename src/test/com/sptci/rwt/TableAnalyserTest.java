package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link TableAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: TableAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class TableAnalyserTest extends TestCase
{
  static TableMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( TableAnalyserTest.class );
  }

  /**
   * Test fetching all tables from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    TableAnalyser analyser = new TableAnalyser(
        CreateTestObjects.manager );
    Collection<TableMetaData> tables =
      analyser.analyse( SchemaAnalyserTest.metadata );

    for ( TableMetaData tmd : tables )
    {
      if ( CreateTestObjects.refTableName.equalsIgnoreCase( tmd.getName() ) )
      {
        metadata = tmd;
        break;
      }
    }

    assertNotNull( "Ensuring test table was found", metadata );
    assertTrue( "Checking non-zero tables", tables.size() > 0 );
  }

  /**
   * Test that the {@link TableMetaData} were added to {@link
   * SchemaMetaData#tables}.
   */
  public void testSchemaUpdated()
  {
    assertTrue( "Checking tables added to SchemaMetaData",
        SchemaAnalyserTest.metadata.getTables().size() > 0 );
  }
}
