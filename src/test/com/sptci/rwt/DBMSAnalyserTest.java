package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link DBMSAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: DBMSAnalyserTest.java 3579 2007-10-08 04:06:37Z rakesh $
 */
public class DBMSAnalyserTest extends TestCase
{
  static DBMSMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( DBMSAnalyserTest.class );
  }

  /**
   * Test fetching all schemas from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    DBMSAnalyser analyser = new DBMSAnalyser(
        CreateTestObjects.manager );
    metadata = analyser.analyse();

    assertNotNull( "Checking non-null metadata", metadata );
    assertNotNull( "Checking non-null JDBCMetaData",
        metadata.getJdbcMetaData() );
    assertNotNull( "Checking non-null LimitsMetaData", metadata.getLimitsMetaData() );
  }
}
