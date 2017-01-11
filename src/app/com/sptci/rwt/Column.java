package com.sptci.rwt;

import java.io.Serializable;

/**
 * A simple value object that represents a column in a result set row.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-02
 * @version $Id: Column.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class Column implements Serializable
{
  /** The name of the column. */
  private String name;

  /** The {@link java.sql.Types} value for the data type of the column. */
  private int type;

  /** The object that represents the contents of the column. */
  private Object content;
  
  /**
   * Returns {@link #name}.
   *
   * @return The value/reference of/to name.
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Set {@link #name}.
   *
   * @param name The value to set.
   */
  protected void setName( final String name )
  {
    this.name = name;
  }
  
  /**
   * Returns {@link #type}.
   *
   * @return The value/reference of/to type.
   */
  public int getType()
  {
    return type;
  }
  
  /**
   * Set {@link #type}.
   *
   * @param type The value to set.
   */
  protected void setType( final int type )
  {
    this.type = type;
  }
  
  /**
   * Returns {@link #content}.
   *
   * @return The value/reference of/to content.
   */
  public Object getContent()
  {
    return content;
  }
  
  /**
   * Set {@link #content}.
   *
   * @param content The value to set.
   */
  protected void setContent( final Object content )
  {
    this.content = content;
  }
}
