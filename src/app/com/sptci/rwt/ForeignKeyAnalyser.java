package com.sptci.rwt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.sptci.util.CloseJDBCResources;

/**
 * An analyser for analysing foreign key columns for tables in the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ForeignKeyAnalyser.java 3652 2007-10-26 01:31:14Z rakesh $
 * @see java.sql.DatabaseMetaData#getImportedKeys
 */
public class ForeignKeyAnalyser extends KeyAnalyser
{
  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The manager for obtaining database connections.
   */
  public ForeignKeyAnalyser( final ConnectionManager manager )
  {
    super( manager );
  }

  /**
   * Returns a collection of {@link ForeignKeyMetaData} objects that
   * contain all information pertaining to the foreign keys for the
   * specified table.
   *
   * @see Analyser#analyse
   * @see #getNames
   * @param parameters Must contain two parameters which are the names of
   *   the <code>catalog/schema</code> and table from which foreign key
   *   metadata are to be retrieved.
   */
  @Override
  public Collection<ForeignKeyMetaData> analyse(
      final MetaData... parameters ) throws SQLException
  {
    Connection connection = null;
    ResultSet resultSet = null;
    final Map<String,ForeignKeyMetaData> md =
      new TreeMap<String,ForeignKeyMetaData>();

    try
    {
      final CatalogueSchema cs = getNames( parameters[0] );
      final TableMetaData tmd = (TableMetaData) parameters[1];

      connection = manager.open();
      final DatabaseMetaData dmd = connection.getMetaData();
      resultSet = dmd.getImportedKeys(
          cs.getCatalogue(), cs.getSchema(), tmd.getName() );
      
      boolean first = true;
      while ( resultSet.next() )
      {
        if ( first )
        {
          checkTableColumns( tmd );
          first = false;
        }

        final String name = resultSet.getString( "fk_name" );

        ForeignKeyMetaData fkmd = md.get( name );
        if ( fkmd == null )
        {
          fkmd = new ForeignKeyMetaData();

          fkmd.setName( name );
          fkmd.setReferencedSchema( resultSet.getString( "pktable_schem" ) );
          fkmd.setReferencedTable( resultSet.getString( "pktable_name" ) );

          fkmd.setUpdateRule(
              processRule( resultSet.getShort( "update_rule" ) ) );
          fkmd.setDeleteRule(
              processRule( resultSet.getShort( "delete_rule" ) ) );
          fkmd.setDeferrability(
              processDeferrability( resultSet.getShort( "deferrability" ) ) );

          fkmd.setTable( tmd );
          md.put( name, fkmd );
        }

        fkmd.addColumn(
            tmd.getColumn( resultSet.getString( "fkcolumn_name" ) ),
            resultSet.getString( "pkcolumn_name" ) );
      }

      tmd.addForeignKeys( md.values() );
    }
    finally
    {
      CloseJDBCResources.close( resultSet );
      CloseJDBCResources.close( connection );
    }

    return md.values();
  }

  /**
   * Process the <code>update_rule/delete_rule</code> values and match
   * them with the appropriate {@link ForeignKeyMetaData.Rule} value.
   *
   * @param value The the short value read from result set.
   * @return The appropriate rule value.
   */
  protected ForeignKeyMetaData.Rule processRule( final short value )
  {
    ForeignKeyMetaData.Rule rule = null;

    switch ( value )
    {
      case DatabaseMetaData.importedKeyNoAction :
        rule = ForeignKeyMetaData.Rule.NoAction;
        break;
      case DatabaseMetaData.importedKeyCascade :
        rule = ForeignKeyMetaData.Rule.Cascade;
        break;
      case DatabaseMetaData.importedKeySetNull :
        rule = ForeignKeyMetaData.Rule.SetNull;
        break;
      case DatabaseMetaData.importedKeySetDefault :
        rule = ForeignKeyMetaData.Rule.SetDefault;
        break;
      case DatabaseMetaData.importedKeyRestrict :
        rule = ForeignKeyMetaData.Rule.Restrict;
        break;
    };

    return rule;
  }

  /**
   * Process the <code>deferrability</code> value and match them with the
   * appropriate {@link ForeignKeyMetaData.Deferrability} value.
   *
   * @param value The the short value read from result set.
   * @return The appropriate deferrability value.
   */
  protected ForeignKeyMetaData.Deferrability processDeferrability(
      final short value )
  {
    ForeignKeyMetaData.Deferrability deferrability = null;

    switch ( value )
    {
      case DatabaseMetaData.importedKeyInitiallyDeferred :
        deferrability = ForeignKeyMetaData.Deferrability.InitiallyDeferred;
        break;
      case DatabaseMetaData.importedKeyInitiallyImmediate :
        deferrability = ForeignKeyMetaData.Deferrability.InitiallyImmediate;
        break;
      case DatabaseMetaData.importedKeyNotDeferrable :
        deferrability = ForeignKeyMetaData.Deferrability.NotDeferrable;
        break;
    };

    return deferrability;
  }
}
