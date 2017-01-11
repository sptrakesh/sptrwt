package com.sptci.rwt;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple value object that represents a row in a {@link
 * java.sql.ResultSet}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-02
 * @version $Id: Row.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class Row implements Serializable
{
  /** The collection of {@link Column} instances contained in this row. */
  private List<Column> columns = new ArrayList<Column>();
  
  /**
   * Returns {@link #columns}.
   *
   * @return The value/reference of/to columns.
   */
  public List<Column> getColumns()
  {
    return Collections.unmodifiableList( columns );
  }
  
  /**
   * Set {@link #columns}.
   *
   * @param columns The value to set.
   */
  protected void setColumns( final List<Column> columns )
  {
    this.columns.clear();
    this.columns.addAll( columns );
  }

  /**
   * Add the specified column to {@link #columns} collection.
   *
   * @param column The column to be added.
   */
  protected void addColumn( final Column column )
  {
    columns.add( column );
  }
}
