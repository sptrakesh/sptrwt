package com.sptci.rwt;

import java.sql.SQLException;

/**
 * An abstract analyser for analysing constraints and indices for tables.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: KeyAnalyser.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public abstract class KeyAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  protected KeyAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Check the specified table meta data object and ensure that the
   * {@link TableMetaData#columns} is initialised.
   *
   * @param tmd The table meta data object to initialise if necessary.
   * @throws SQLException If errors are encountered while initialising
   *   the meta data object.
   */
  protected void checkTableColumns( final TableMetaData tmd )
    throws SQLException
  {
    if ( tmd.getColumns().size() == 0 )
    {
      ColumnAnalyser analyser = new ColumnAnalyser( manager );
      analyser.analyse( tmd.getRoot(), tmd );
    }
  }
}
