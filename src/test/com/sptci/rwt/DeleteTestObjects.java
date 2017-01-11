package com.sptci.rwt;

import java.sql.Connection;
import java.sql.Statement;

import static junit.framework.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.sptci.util.CloseJDBCResources;

/**
 * Unit test for closing a JDBC {@link java.sql.Connection} through
 * {@link ConnectionFactory#close} method.
 * 
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * 
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: DeleteTestObjects.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class DeleteTestObjects extends TestCase
{
  public static Test suite()
  {
    return new TestSuite( DeleteTestObjects.class );
  }

  /**
   * Drop the test tables and objects created for testing.
   */
  public void testDrop() throws Exception
  {
    String ddl = "drop trigger $TRIGGERNAME$ on $REFTABLENAME$;\n" +
      "drop function $FUNCTIONNAME$();\n" +
      "drop view $VIEWNAME$;\n" +
      "drop index $INDEXNAME$;\n" +
      "drop table $REFTABLENAME$;\n" +
      "drop table $TABLENAME$;\n" +
      "drop sequence $SEQUENCENAME$;\n";
    ddl = ddl.replaceAll( "\\$TABLENAME\\$", CreateTestObjects.tableName );
    ddl = ddl.replaceAll( "\\$REFTABLENAME\\$", CreateTestObjects.refTableName );
    ddl = ddl.replaceAll( "\\$VIEWNAME\\$", CreateTestObjects.viewName );
    ddl = ddl.replaceAll( "\\$FUNCTIONNAME\\$", CreateTestObjects.functionName );
    ddl = ddl.replaceAll( "\\$TRIGGERNAME\\$", CreateTestObjects.triggerName );
    ddl = ddl.replaceAll( "\\$SEQUENCENAME\\$", CreateTestObjects.sequenceName );
    ddl = ddl.replaceAll( "\\$INDEXNAME\\$", CreateTestObjects.indexName );
    Connection connection = null;
    Statement statement = null;

    try
    {
      connection = CreateTestObjects.manager.open();
      statement = connection.createStatement();
      statement.execute( ddl );
    }
    finally
    {
      CloseJDBCResources.close( statement );
      CloseJDBCResources.close( connection );
    }

    System.out.format( "Dropped test tables: %s, %s and view: %s%n",
        CreateTestObjects.tableName, CreateTestObjects.refTableName,
        CreateTestObjects.viewName );
  }
}
