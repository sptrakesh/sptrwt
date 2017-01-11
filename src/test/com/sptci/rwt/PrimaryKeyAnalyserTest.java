package com.sptci.rwt;

import java.sql.Connection;
import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link PrimaryKeyAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: PrimaryKeyAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class PrimaryKeyAnalyserTest extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( PrimaryKeyAnalyserTest.class );
  }

  /**
   * Test fetching all columns from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    PrimaryKeyAnalyser analyser = new PrimaryKeyAnalyser(
        CreateTestObjects.manager );
    Collection<PrimaryKeyMetaData> metadata = analyser.analyse(
        SchemaAnalyserTest.metadata, TableAnalyserTest.metadata );

    assertTrue( "Checking single primary key found", metadata.size() == 1 );
  }

  /**
   * Test to ensure that the {@link TableMetaData#primaryKey} field was
   * updated with {@link PrimaryKeyMetaData} information.
   */
  public void testTableUpdated()
  {
    assertNotNull( "Checking primary keys added to table metadata",
        TableAnalyserTest.metadata.getPrimaryKey() );
    assertTrue( "Checking primary key has multiple columns",
        TableAnalyserTest.metadata.getPrimaryKey().getColumns().size() > 1 );
  }
}
