package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.sptci.util.CloseJDBCResources;

/**
 * An abstractanalyser for analysing view type objects in the database.
 * Sub-classes perform database engine specific analysis since not all
 * database engines support the SQL92 standard information schema.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-02
 * @version $Id: AbstractViewAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 * @since Version 1.2
 */
public abstract class AbstractViewAnalyser extends Analyser
{
  /**
   * The <code>types</code> parameters to specify while invoking the
   * {@link java.sql.DatabaseMetaData#getTables} method.
   */
  protected static final String[] TYPE = new String[] { "VIEW" };

  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  protected AbstractViewAnalyser( final ConnectionManager manager )
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
    Collection<ViewMetaData> collection = new ArrayList<ViewMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );

      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getTables( cs.getCatalogue(), cs.getSchema(), "%", TYPE );

      while ( resultSet.next() )
      {
        ViewMetaData vmd = new ViewMetaData();
        vmd.setName( resultSet.getString( "table_name" ) );
        vmd.setComment( resultSet.getString( "remarks" ) );
        vmd.setRoot( (RootMetaData) parameters[0] );

        collection.add( vmd );
      }

      if ( parameters[0] != null )
      {
        ( (RootMetaData) parameters[0] ).setViews( collection );
      }
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    if ( collection.isEmpty() )
    {
      getViews( (RootMetaData) parameters[0], collection );
    }

    return collection;
  }

  /**
   * Fetch the views directly from <code>information_schema</code> if no
   * information is available through {@link java.sql.DatabaseMetaData}.
   *
   * @see #getNames
   * @param root The metadata object that represents the catalog/schema for
   *   which the views are to be retrieved.
   * @param collection The collection to which the {@link ViewMetaData}
   *   objects are to be added.
   * @throws SQLException If errors are encountered while fetching the views.
   */
  protected abstract void getViews( final RootMetaData root,
      final Collection<ViewMetaData> collection ) throws SQLException;

  /**
   * Fetch additional meta data about the specified view from the
   * <code>information_schema</code>.
   *
   * @since Version 1.1
   * @param vmd The meta data object that is to have additional attributes
   *   populated.
   */
  protected abstract void getAdditionalAttributes( final ViewMetaData vmd );
}
