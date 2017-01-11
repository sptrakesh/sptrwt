package com.sptci.rwt;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple value object that represents a {@link java.sql.ResultSet}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-02
 * @version $Id: Rows.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class Rows implements Serializable
{
  /**
   * The total number of rows available in the {@link java.sql.ResultSet}
   * out of which this object was generated.  Note that the {@link
   * #rows} collection will usually not contain the same number (usually
   * less) of rows as the total number available.
   * 
   * @since Version 1.2
   */
  private int totalRows;

  /** The collection of {@link Row} instances contained in result set. */
  private List<Row> rows = new ArrayList<Row>();
  
  /**
   * Returns {@link #totalRows}.
   *
   * @since Version 1.2
   * @return The value/reference of/to totalRows.
   */
  public int getTotalRows()
  {
    return totalRows;
  }
  
  /**
   * Set {@link #totalRows}.
   *
   * @since Version 1.2
   * @param totalRows The value to set.
   */
  protected void setTotalRows( final int totalRows )
  {
    this.totalRows = totalRows;
  }

  /**
   * Returns {@link #rows}.
   *
   * @return The value/reference of/to rows.
   */
  public List<Row> getRows()
  {
    return Collections.unmodifiableList( rows );
  }

  /**
   * Set {@link #rows}.
   *
   * @param rows The value to set.
   */
  protected void setRows( final List<Row> rows )
  {
    this.rows.clear();
    this.rows.addAll( rows );
  }

  /**
   * Add the specified row to {@link #rows} collection.
   *
   * @param row The row to be added.
   */
  protected void addRow( final Row row )
  {
    rows.add( row );
  }
}
