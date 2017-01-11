package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link TriggerAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: TriggerAnalyserTest.java 3559 2007-10-02 22:43:50Z rakesh $
 */
public class TriggerAnalyserTest extends TestCase
{
  static TriggerMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( TriggerAnalyserTest.class );
  }

  /**
   * Test fetching all triggers from the database connection.
   */
  public void testAnalyseOnSchema() throws Exception
  {
    TriggerAnalyser analyser = new TriggerAnalyser(
        CreateTestObjects.manager );
    Collection<TriggerMetaData> triggers =
      analyser.analyse( SchemaAnalyserTest.metadata );

    for ( TriggerMetaData tmd : triggers )
    {
      if ( CreateTestObjects.triggerName.equalsIgnoreCase( tmd.getName() ) )
      {
        metadata = tmd;
        break;
      }
    }

    assertNotNull( "Ensuring test trigger was found", metadata );
    assertTrue( "Checking non-zero triggers", triggers.size() > 0 );
    assertTrue( "Checking triggers added to SchemaMetaData",
        SchemaAnalyserTest.metadata.getTriggers().size() > 0 );
  }

  /**
   * Test fetching all triggers for a specified table.
   */
  public void testAnalyseOnTable() throws Exception
  {
    TriggerAnalyser analyser = new TriggerAnalyser(
        CreateTestObjects.manager );
    Collection<TriggerMetaData> triggers = analyser.analyse(
        SchemaAnalyserTest.metadata, TableAnalyserTest.metadata );

    for ( TriggerMetaData tmd : triggers )
    {
      if ( CreateTestObjects.triggerName.equalsIgnoreCase( tmd.getName() ) )
      {
        metadata = tmd;
        break;
      }
    }

    assertNotNull( "Ensuring test trigger was found", metadata );
    assertTrue( "Checking non-zero triggers", triggers.size() > 0 );
    assertTrue( "Checking triggers added to TableMetaData",
        TableAnalyserTest.metadata.getTriggers().size() > 0 );
  }
}
