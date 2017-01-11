package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing view type objects in the database. View 
 * information is retrieved from the <code>Information Schema</code>.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: ViewAnalyser.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class ViewAnalyser extends TableTypeAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public ViewAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link ViewMetaData} objects that contain the
   * basic information pertaining to the views in the schema.  You must
   * invoke {@link #getAdditionalAttributes} to fetch information from
   * the <code>information_schema</code>.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain one parameter which is a {@link
   *   RootMetaData} that represents the <code>catalog</code> or 
   *   <code>schema</code> to restrict the analysis to.  The name of the
   *   catalogue or schema may be <code>null</code> or an empty string.
   */
  @Override
  public Collection<ViewMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    return getAnalyser().analyse( parameters );
  }

  /**
   * Fetch additional meta data about the specified view from the
   * <code>information_schema</code>.
   *
   * @since Version 1.1
   * @see AbstractViewAnalyser#getAdditionalAttributes
   * @param vmd The meta data object that is to have additional attributes
   *   populated.
   */
  public void getAdditionalAttributes( final ViewMetaData vmd )
  {
    getAnalyser().getAdditionalAttributes( vmd );
  }

  /**
   * Return the appropriate implementation class depending upon the
   * database engine being analysed.
   *
   * @return The appropriate implementation class for the database.
   * @throws SQLException If errors are encountered while determining
   *   the analyser class to use.
   */
  protected AbstractViewAnalyser getAnalyser()
  {
    Connection connection = null;
    AbstractViewAnalyser analyser = new StandardViewAnalyser( manager );

    try
    {
      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      final String name = dmd.getDatabaseProductName().toLowerCase();

      if ( name.contains( "oracle" ) )
      {
        analyser = new OracleViewAnalyser( manager );
      }
    }
    catch ( SQLException sex )
    {
      logger.log( Level.INFO, "Error introspecting database engine name", sex );
    }
    finally
    {
      CloseJDBCResources.close( connection );
    }

    return analyser;
  }
}
