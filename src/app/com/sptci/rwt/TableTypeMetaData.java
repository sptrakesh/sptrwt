package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An abstract value object that represents metadata for tables, views,
 * synonyms and other similar objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: TableTypeMetaData.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @see java.sql.DatabaseMetaData#getTables
 */
public abstract class TableTypeMetaData extends ObjectMetaData
{
  /**
   * The collection of columns that belong to this table.
   */
  private Collection<ColumnMetaData> columns =
    new ArrayList<ColumnMetaData>();
  
  /**
   * The total number of records in the object.
   * 
   * @since Version 1.3
   */
  private int numberOfRows;

  /**
   * Return the metadata for the column with the specified name.
   *
   * @param column The column whose metadata is to be reetrieved.
   * @return The metadata for the column or <code>null</code> if no
   *   such column exists in the table.
   */
  public ColumnMetaData getColumn( final String column )
  {
    ColumnMetaData cmd = null;

    for ( ColumnMetaData md : columns )
    {
      if ( md.getName().equalsIgnoreCase( column ) )
      {
        cmd = md;
        break;
      }
    }

    return cmd;
  }

  /**
   * Returns {@link #columns}.
   *
   * @return The value/reference of/to columns.
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
   * Returns {@link #numberOfRows}.
   * 
   * @since Version 1.3
   * @return The value/reference of/to numberOfRows.
   */
  public int getNumberOfRows()
  {
    return numberOfRows;
  }
  
  /**
   * Set {@link #numberOfRows}.
   * 
   * @since Version 1.3
   * @param numberOfRows The value to set.
   */
  protected void setNumberOfRows( final int numberOfRows )
  {
    this.numberOfRows = numberOfRows;
  }
}
