package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing table type objects in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: TableAnalyser.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class TableAnalyser extends TableTypeAnalyser
{
  /**
   * The <code>types</code> parameters to specify while invoking the
   * {@link java.sql.DatabaseMetaData#getTables} method.
   */
  private static final String[] TYPE = new String[] { "TABLE" };

  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public TableAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link TableMetaData} objects that contain the
   * basic information pertaining to the tables in the schema.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain one parameter which is a {@link
   *   RootMetaData} that represents the <code>catalog</code> or
   *   <code>schema</code> to restrict the analysis to.  The name of the
   *   <code>catalog</code> or <code>schema</code> may be <code>null</code>
   *   or an empty string.  See {@link java.sql.DatabaseMetaData#getTables}
   *   for a description of the behaviour of specifying the schema.
   */
  @Override
  public Collection<TableMetaData> analyse( final MetaData... parameters )
    throws SQLException
  {
    Collection<TableMetaData> collection = new ArrayList<TableMetaData>();
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
        TableMetaData tmd = new TableMetaData();

        tmd.setName( resultSet.getString( "table_name" ) );
        tmd.setComment( resultSet.getString( "remarks" ) );
        if ( parameters[0] != null )
        {
          tmd.setRoot( (RootMetaData) parameters[0] );
        }

        collection.add( tmd );
      }

      if ( parameters[0] != null )
      {
        ( (RootMetaData) parameters[0] ).setTables( collection );
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
   * Retrieve the meta data about the foreign key references to this
   * table.
   *
   * @since Version 1.1
   * @see #getNames
   * @param metaData The meta data object whose foreign key references
   *   are to be retrieved.
   * @return The collection of {@link ForeignKeyMetaData} objects that
   *   represent the columns that reference the specified table columns.
   * @throws SQLException If errors are encountered while fetching the
   *   details.
   */
  public Collection<ForeignKeyMetaData> getExportedKeys(
      final TableMetaData metaData ) throws SQLException
  {
    Collection<ForeignKeyMetaData> collection =
      new ArrayList<ForeignKeyMetaData>();
    Connection connection = null;
    ResultSet resultSet = null;

    try
    {
      final CatalogueSchema cs = getNames( metaData.getRoot() );

      connection = manager.open();
      DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getExportedKeys(
          cs.getCatalogue(), cs.getSchema(), metaData.getName() );

      while ( resultSet.next() )
      {
        ForeignKeyMetaData fkmd = new ForeignKeyMetaData();
        fkmd.setName( resultSet.getString( "fk_name" ) );

        CatalogueMetaData cmd = new CatalogueMetaData();
        cmd.setName( resultSet.getString( "fktable_cat" ) );
        SchemaMetaData smd = new SchemaMetaData();
        smd.setName( resultSet.getString( "fktable_schem" ) );
        smd.setCatalogue( cmd );

        TableMetaData tmd = new TableMetaData();
        tmd.setName( resultSet.getString( "fktable_name" ) );
        tmd.setRoot( smd );
        fkmd.setTable( tmd );

        ColumnMetaData comd = new ColumnMetaData();
        comd.setName( resultSet.getString( "fkcolumn_name" ) );
        fkmd.addColumn( comd );

        ForeignKeyAnalyser fka = new ForeignKeyAnalyser( manager );
        fkmd.setUpdateRule( fka.processRule(
              resultSet.getShort( "update_rule" ) ) );
        fkmd.setDeleteRule( fka.processRule(
              resultSet.getShort( "delete_rule" ) ) );
        fkmd.setDeferrability( fka.processDeferrability(
              resultSet.getShort( "deferrability" ) ) );
        // Hack for convenience.
        fkmd.setReferencedTable( resultSet.getString( "pkcolumn_name" ) );

        collection.add( fkmd );
      }
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return collection;
  }
}
