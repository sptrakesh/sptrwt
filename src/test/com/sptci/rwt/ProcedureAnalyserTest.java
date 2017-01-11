package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link ProcedureAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ProcedureAnalyserTest.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ProcedureAnalyserTest extends TestCase
{
  static ProcedureMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( ProcedureAnalyserTest.class );
  }

  /**
   * Test fetching all procedures from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    ProcedureAnalyser analyser = new ProcedureAnalyser(
        CreateTestObjects.manager );
    Collection<ProcedureMetaData> procedures =
      analyser.analyse( SchemaAnalyserTest.metadata );

    for ( ProcedureMetaData tmd : procedures )
    {
      if ( CreateTestObjects.functionName.equalsIgnoreCase( tmd.getName() ) )
      {
        metadata = tmd;
        assertTrue( "Checking SchemaMetaData applied to Procedure",
            SchemaAnalyserTest.metadata.equals( tmd.getRoot() ) );
        break;
      }
    }

    assertNotNull( "Ensuring test procedure was found", metadata );
    assertTrue( "Checking non-zero procedures", procedures.size() > 0 );
  }

  /**
   * Test to ensure that the {@link SchemaMetaData} was updated by the
   * {@link ProcedureAnalyser}.
   */
  public void testSchemaMetaDataUpdated()
  {
    assertTrue( "Checking procedures added to SchemaMetaData",
        SchemaAnalyserTest.metadata.getProcedures().size() > 0 );
  }
}
