package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing sequence type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: SequenceAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class SequenceAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public SequenceAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link SequenceMetaData} objects that contain
   * all information pertaining to the sequences in the specified schema.
   *
   * @see Analyser#analyse
   * @see AbstractSequenceAnalyser#analyse
   * @param parameters Must contain the <code>catalog/schema</code> from
   *   which sequence metadata are to be retrieved.
   */
  @Override
  public Collection<SequenceMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Connection connection = null;
    AbstractSequenceAnalyser analyser = new StandardSequenceAnalyser( manager );

    try
    {
      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      final String name = dmd.getDatabaseProductName().toLowerCase();

      if ( name.contains( "oracle" ) )
      {
        analyser = new OracleSequenceAnalyser( manager );
      }
    }
    finally
    {
      CloseJDBCResources.close( connection );
    }

    return analyser.analyse( parameters );
  }
}
