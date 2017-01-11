package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An abstract metadata object that represents constraint types (primary
 * and foreign keys).
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: KeyMetaData.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class KeyMetaData extends MetaData
{
  /**
   * The name of the column(s) on which the key is defined.
   *
   * @see TableMetaData#getColumn
   */
  private Collection<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

  /** A sequence number that indicates the sequence number with a key */
  private int keySequence;

  /** A reference to the table in which this key belongs. */
  private TableMetaData table;
  
  /**
   * Returns {@link #columns}.
   *
   * @return The value/reference of/to column.
   */
  public Collection<ColumnMetaData> getColumns()
  {
    return Collections.unmodifiableCollection( columns );
  }
  
  /**
   * Set {@link #columns}.
   *
   * @param columns The value to set.
   */
  protected void setColumns( final Collection<ColumnMetaData> columns )
  {
    this.columns.clear();
    this.columns.addAll( columns );
  }

  /**
   * Add the specified column to {@link #columns}.
   *
   * @param column The column to add.
   */
  protected void addColumn( final ColumnMetaData column )
  {
    this.columns.add( column );
  }
  
  /**
   * Returns {@link #keySequence}.
   *
   * @return The value/reference of/to keySequence.
   */
  public int getKeySequence()
  {
    return keySequence;
  }
  
  /**
   * Set {@link #keySequence}.
   *
   * @param keySequence The value to set.
   */
  protected void setKeySequence( final int keySequence )
  {
    this.keySequence = keySequence;
  }
  
  /**
   * Returns {@link #table}.
   *
   * @return The value/reference of/to table.
   */
  public TableMetaData getTable()
  {
    return table;
  }
  
  /**
   * Set {@link #table}.
   *
   * @param table The value to set.
   */
  protected void setTable( final TableMetaData table )
  {
    this.table = table;
  }
}
