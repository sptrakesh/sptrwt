package com.sptci.rwt;

import java.sql.Connection;
import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link IndexAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: IndexAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class IndexAnalyserTest extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( IndexAnalyserTest.class );
  }

  /**
   * Test fetching all columns from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    IndexAnalyser analyser = new IndexAnalyser(
        CreateTestObjects.manager );
    Collection<IndexMetaData> metadata = analyser.analyse(
        SchemaAnalyserTest.metadata, TableAnalyserTest.metadata );

    assertTrue( "Checking non-zero indices", metadata.size() > 0 );
  }

  /**
   * Test to ensure that the {@link TableMetaData#indices} field was updated
   * with {@link IndexMetaData} information.
   */
  public void testTableUpdated()
  {
    assertTrue( "Checking indices added to table metadata",
        TableAnalyserTest.metadata.getIndices().size() > 0 );
  }

  /**
   * Test to ensure that the {@link IndexMetaData} created for testing
   * has multiple columns.
   */
  public void testMultipleColumns()
  {
    for ( IndexMetaData imd : TableAnalyserTest.metadata.getIndices() )
    {
      if ( CreateTestObjects.indexName.equalsIgnoreCase( imd.getName() ) )
      {
        assertTrue( "Checking index has multiple columns",
            imd.getColumns().size() > 1 );
      }
    }
  }
}
