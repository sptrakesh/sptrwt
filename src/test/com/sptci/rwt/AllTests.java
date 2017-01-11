package com.sptci.rwt;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Execute all the tests for the project.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: AllTests.java 3607 2007-10-13 01:17:06Z rakesh $
 */
public class AllTests
{
  public static Test suite () 
  {
    TestSuite suite= new TestSuite( "All JUnit Tests for sptrwt project" );
    suite.addTest( CreateTestObjects.suite() );
    suite.addTest( ConnectionsTest.suite() );
    suite.addTest( QueriesTest.suite() );
    suite.addTest( DBMSAnalyserTest.suite() );
    suite.addTest( SchemaAnalyserTest.suite() );
    suite.addTest( TableAnalyserTest.suite() );
    suite.addTest( ViewAnalyserTest.suite() );
    suite.addTest( ColumnAnalyserTest.suite() );
    suite.addTest( PrimaryKeyAnalyserTest.suite() );
    suite.addTest( ForeignKeyAnalyserTest.suite() );
    suite.addTest( IndexAnalyserTest.suite() );
    suite.addTest( TriggerAnalyserTest.suite() );
    suite.addTest( ProcedureAnalyserTest.suite() );
    suite.addTest( SequenceAnalyserTest.suite() );
    suite.addTest( QueryExecutorTest.suite() );
    suite.addTest( BatchQueryExecutorTest.suite() );

    suite.addTest( DeleteTestObjects.suite() );

    return suite;
  }

  public static void main (String[] args) 
  {
    junit.textui.TestRunner.run (suite());
    System.exit( 0 );
  }
}
