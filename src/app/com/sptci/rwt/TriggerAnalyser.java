package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing trigger type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @see StandardTriggerAnalyser
 * @see OracleTriggerAnalyser
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: TriggerAnalyser.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class TriggerAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public TriggerAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link TriggerMetaData} objects that contain
   * the basic information pertaining to the triggers in the schema and
   * table (optional) specified.
   *
   * @see Analyser#analyse
   * @see AbstractTriggerAnalyser#analyse
   * @param parameters Must contain at least one parameter which is a {@link
   *   SchemaMetaData} that represents the <code>schema</code> to restrict
   *   the analysis to.  An optional additional {@link TableMetaData}
   *   parameter may be specified to restrict the analysis to a specified
   *   table.
   */
  @Override
  public Collection<TriggerMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    AbstractTriggerAnalyser analyser = new StandardTriggerAnalyser( manager );
    Connection connection = null;

    try
    {
      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      final String name = dmd.getDatabaseProductName().toLowerCase();

      if ( name.contains( "oracle" ) )
      {
        analyser = new OracleTriggerAnalyser( manager );
      }
    }
    finally
    {
      CloseJDBCResources.close( connection );
    }

    return analyser.analyse( parameters );
  }
}
