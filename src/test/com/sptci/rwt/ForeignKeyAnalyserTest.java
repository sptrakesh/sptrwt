package com.sptci.rwt;

import java.sql.Connection;
import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link ForeignKeyAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ForeignKeyAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class ForeignKeyAnalyserTest extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( ForeignKeyAnalyserTest.class );
  }

  /**
   * Test fetching all columns from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    ForeignKeyAnalyser analyser = new ForeignKeyAnalyser(
        CreateTestObjects.manager );
    Collection<ForeignKeyMetaData> metadata = analyser.analyse(
        SchemaAnalyserTest.metadata, TableAnalyserTest.metadata );

    assertTrue( "Checking non-zero foreign keys", metadata.size() > 0 );
  }

  /**
   * Test to ensure that the {@link TableMetaData#foreignKeys} field was
   * updated with {@link ForeignKeyMetaData} information.
   */
  public void testTableUpdated()
  {
    assertTrue( "Checking foreign keys added to table metadata",
        TableAnalyserTest.metadata.getForeignKeys().size() > 0 );
  }
}
