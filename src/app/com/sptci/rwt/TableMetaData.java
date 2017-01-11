package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents metadata for tables.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: TableMetaData.java 3610 2007-10-13 02:17:00Z rakesh $
 * @see java.sql.DatabaseMetaData#getTables
 */
public class TableMetaData extends TableTypeMetaData
{
  /**
   * The collection of primary key columns for this table.
   */
  private PrimaryKeyMetaData primaryKey;

  /**
   * The collection of foreign key columns for this table.
   */
  private Collection<ForeignKeyMetaData> foreignKeys =
    new ArrayList<ForeignKeyMetaData>();

  /**
   * The collection of foreign key columns for this table.
   */
  private Collection<IndexMetaData> indices = new ArrayList<IndexMetaData>();

  /**
   * The collection of triggers for this table.
   */
  private Collection<TriggerMetaData> triggers =
    new ArrayList<TriggerMetaData>();
  
  /**
   * Returns {@link #primaryKey}.
   *
   * @return The value/reference of/to primaryKey.
   */
  public PrimaryKeyMetaData getPrimaryKey()
  {
    return primaryKey;
  }
  
  /**
   * Set {@link #primaryKey}.
   *
   * @param primaryKey The value to set.
   */
  protected void setPrimaryKey( final PrimaryKeyMetaData primaryKey )
  {
    this.primaryKey = primaryKey;
  }
  
  /**
   * Returns {@link #foreignKeys}.
   *
   * @return The value/reference of/to foreignKeys.
   */
  public Collection<ForeignKeyMetaData> getForeignKeys()
  {
    return Collections.unmodifiableCollection( foreignKeys );
  }
  
  /**
   * Set {@link #foreignKeys}.
   *
   * @param foreignKeys The value to set.
   */
  protected void setForeignKeys(
      final Collection<ForeignKeyMetaData> foreignKeys )
  {
    this.foreignKeys.clear();
    this.foreignKeys.addAll( foreignKeys );
  }
  
  /**
   * Adds the specified foreign key to {@link #foreignKeys}.
   *
   * @param foreignKey The value to add.
   */
  protected void addForeignKey( final ForeignKeyMetaData foreignKey )
  {
    this.foreignKeys.add( foreignKey );
  }
  
  /**
   * Adds the specified collection of foreign keys to {@link #foreignKeys}.
   *
   * @param foreignKeys The value to add.
   */
  protected void addForeignKeys(
      final Collection<ForeignKeyMetaData> foreignKeys )
  {
    this.foreignKeys.addAll( foreignKeys );
  }
  
  /**
   * Returns {@link #indices}.
   *
   * @return The value/reference of/to indices.
   */
  public Collection<IndexMetaData> getIndices()
  {
    return Collections.unmodifiableCollection( indices );
  }
  
  /**
   * Set {@link #indices}.
   *
   * @param indices The value to set.
   */
  protected void setIndices( final Collection<IndexMetaData> indices )
  {
    this.indices.clear();
    this.indices.addAll( indices );
  }

  /**
   * Add the specified index to {@link #indices}.
   *
   * @param index The index that is to be added.
   */
  protected void addIndex( final IndexMetaData index )
  {
    this.indices.add( index );
  }

  /**
   * Add the specified collection of indices to {@link #indices}.
   *
   * @param indices The indices that are to be added.
   */
  protected void addIndices( final Collection<IndexMetaData> indices )
  {
    this.indices.addAll( indices );
  }
  
  /**
   * Returns {@link #triggers}.
   *
   * @return The value/reference of/to triggers.
   */
  public Collection<TriggerMetaData> getTriggers()
  {
    return Collections.unmodifiableCollection( triggers );
  }
  
  /**
   * Set {@link #triggers}.
   *
   * @param triggers The value to set.
   */
  protected void setTriggers( final Collection<TriggerMetaData> triggers )
  {
    this.triggers.clear();
    this.triggers.addAll( triggers );
  }
}
