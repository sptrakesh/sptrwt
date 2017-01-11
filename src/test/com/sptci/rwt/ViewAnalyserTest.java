package com.sptci.rwt;

import java.util.Collection;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the {@link ViewAnalyser} object.
 *
 * <p>Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ViewAnalyserTest.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class ViewAnalyserTest extends TestCase
{
  static ViewMetaData metadata;

  public static Test suite()
  {
    return new TestSuite( ViewAnalyserTest.class );
  }

  /**
   * Test fetching all views from the database connection.
   */
  public void testAnalyse() throws Exception
  {
    ViewAnalyser analyser = new ViewAnalyser(
        CreateTestObjects.manager );
    Collection<ViewMetaData> views =
      analyser.analyse( SchemaAnalyserTest.metadata );

    for ( ViewMetaData vmd : views )
    {
      if ( CreateTestObjects.viewName.equalsIgnoreCase( vmd.getName() ) )
      {
        metadata = vmd;
        break;
      }
    }

    assertNotNull( "Ensuring test view was found", metadata );
    assertTrue( "Checking non-zero views", views.size() > 0 );
  }

  /**
   * Test that the {@link ViewMetaData} were added to {@link 
   * SchemaMetaData#views}.
   */
  public void testSchemaUpdated()
  {
    assertTrue( "Checking views added to SchemaMetaData",
        SchemaAnalyserTest.metadata.getViews().size() > 0 );
  }
}
