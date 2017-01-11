package com.sptci.rwt;

/**
 * A value object that represents metadata for columns.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ColumnMetaData.java 3610 2007-10-13 02:17:00Z rakesh $
 * @see java.sql.DatabaseMetaData#getColumns
 */
public class ColumnMetaData extends ObjectMetaData
{
  /**
   * The data type for the column as values defined in {@link
   * java.sql.Types}.
   */
  private int type;

  /** The database specific name of the column type.  */
  private String typeName;

  /** The default value for this column.  */
  private String defaultValue;

  /** The size or precision of the column.  */
  private int size;

  /** Defines whether the column takes <code>null</code> values or not.  */
  private String nullable;

  /** A reference to the table/view in which this column belongs. */
  private TableTypeMetaData table;
  
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
   * Returns {@link #typeName}.
   *
   * @return The value/reference of/to typeName.
   */
  public String getTypeName()
  {
    return typeName;
  }
  
  /**
   * Set {@link #typeName}.
   *
   * @param typeName The value to set.
   */
  protected void setTypeName( final String typeName )
  {
    this.typeName = typeName;
  }
  
  /**
   * Returns {@link #size}.
   *
   * @return The value/reference of/to size.
   */
  public int getSize()
  {
    return size;
  }
  
  /**
   * Set {@link #size}.
   *
   * @param size The value to set.
   */
  protected void setSize( final int size )
  {
    this.size = size;
  }
  
  /**
   * Returns {@link #nullable}.
   *
   * @return The value/reference of/to nullable.
   */
  public String getNullable()
  {
    return nullable;
  }
  
  /**
   * Set {@link #nullable}.
   *
   * @param nullable The value to set.
   */
  protected void setNullable( final String nullable )
  {
    this.nullable = nullable;
  }
  
  /**
   * Returns {@link #defaultValue}.
   *
   * @return The value/reference of/to defaultValue.
   */
  public String getDefaultValue()
  {
    return defaultValue;
  }
  
  /**
   * Set {@link #defaultValue}.
   *
   * @param defaultValue The value to set.
   */
  protected void setDefaultValue( final String defaultValue )
  {
    this.defaultValue = defaultValue;
  }
  
  /**
   * Returns {@link #table}.
   *
   * @return The value/reference of/to table.
   */
  public TableTypeMetaData getTable()
  {
    return table;
  }
  
  /**
   * Set {@link #table}.
   *
   * @param table The value to set.
   */
  protected void setTable( final TableTypeMetaData table )
  {
    this.table = table;
  }
}
