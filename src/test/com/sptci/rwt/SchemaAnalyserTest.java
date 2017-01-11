package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link SchemaAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: SchemaAnalyserTest.java 3559 2007-10-02 22:43:50Z rakesh $
 */
public class SchemaAnalyserTest extends TestCase
{
  static SchemaMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( SchemaAnalyserTest.class );
  }

  /**
   * Test fetching all schemas from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    SchemaAnalyser analyser = new SchemaAnalyser(
        CreateTestObjects.manager );
    Collection<SchemaMetaData> schemas = analyser.analyse(
        DBMSAnalyserTest.metadata );

    for ( SchemaMetaData smd : schemas )
    {
      if ( CreateTestObjects.parameters.userName.equalsIgnoreCase(
            smd.getName() ) )
      {
        metadata = smd;
        break;
      }
    }

    assertNotNull( "Ensuring user schema was found", metadata );
    assertTrue( "Checking non-zero schemas", schemas.size() > 0 );
  }

  /**
   * Test that the schema metadata were added to {@link #metadata}.
   */
  public void testMetaDataUpdated()
  {
    assertTrue( "Checking schemas added to DBMSMetaData",
        DBMSAnalyserTest.metadata.getSchemas().size() > 0 );
  }
}
