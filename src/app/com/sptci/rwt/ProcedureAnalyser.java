package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing procedure type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: ProcedureAnalyser.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ProcedureAnalyser extends Analyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public ProcedureAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link ProcedureMetaData} objects that contain
   * the basic information pertaining to the procedures in the
   * catalog/schema.  You should invoke {@link #getAdditionalAttributes}
   * to fetch the additional attributes that are available in
   * <code>information_schema</code>.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain one parameter which is a {@link
   *   RootMetaData} that represents the <code>catalog</code> or 
   *   <code>schema</code> to restrict the analysis to.
   */
  @Override
  public Collection<ProcedureMetaData> analyse(
      final MetaData... parameters ) throws SQLException
  {
    final Collection<ProcedureMetaData> collection =
      new ArrayList<ProcedureMetaData>();

    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );

      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getProcedures( cs.getCatalogue(), cs.getSchema(), "%" );

      while ( resultSet.next() )
      {
        ProcedureMetaData pmd = new ProcedureMetaData();

        pmd.setName( resultSet.getString( "procedure_name" ) );
        pmd.setComment( resultSet.getString( "remarks" ) );

        if ( parameters[0] != null )
        {
          pmd.setRoot( (RootMetaData) parameters[0] );
        }

        collection.add( pmd );
      }

      if ( parameters[0] != null )
      {
        ( (RootMetaData) parameters[0] ).setProcedures( collection );
      }
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }

  /**
   * Fetch additional meta data available from the <code>information
   * schema</code> to the object.
   *
   * @since Version 1.1
   * @see AbstractProcedureAnalyser#getAdditionalAttributes
   * @param pmd The meta data object that is to be updated.
   */
  public void getAdditionalAttributes( final ProcedureMetaData pmd )
  {
    AbstractProcedureAnalyser analyser =
      new StandardProcedureAnalyser( manager );
    Connection connection = null;

    try
    {
      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      final String name = dmd.getDatabaseProductName().toLowerCase();

      if ( name.contains( "oracle" ) )
      {
        analyser = new OracleProcedureAnalyser( manager );
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

    analyser.getAdditionalAttributes( pmd );
  }
}
