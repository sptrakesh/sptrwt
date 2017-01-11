package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link SequenceAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: SequenceAnalyserTest.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class SequenceAnalyserTest extends TestCase
{
  static SequenceMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( SequenceAnalyserTest.class );
  }

  /**
   * Test fetching all sequences from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    SequenceAnalyser analyser = new SequenceAnalyser(
        CreateTestObjects.manager );
    Collection<SequenceMetaData> sequences =
      analyser.analyse( SchemaAnalyserTest.metadata );

    for ( SequenceMetaData smd : sequences )
    {
      if ( CreateTestObjects.sequenceName.equalsIgnoreCase( smd.getName() ) )
      {
        metadata = smd;
        assertTrue( "Checking SchemaMetaData applied to Sequence",
            SchemaAnalyserTest.metadata.equals( smd.getRoot() ) );
        break;
      }
    }

    assertNotNull( "Ensuring test sequence was found", metadata );
    assertTrue( "Checking non-zero sequences", sequences.size() > 0 );
  }

  /**
   * Test to ensure that the {@link SchemaMetaData} was updated by the
   * {@link SequenceAnalyser}.
   */
  public void testSchemaMetaDataUpdated()
  {
    assertTrue( "Checking sequences added to SchemaMetaData",
        SchemaAnalyserTest.metadata.getSequences().size() > 0 );
  }
}
