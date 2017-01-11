package com.sptci.rwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * A metadata object that represents foreign key constraint types.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: ForeignKeyMetaData.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class ForeignKeyMetaData extends KeyMetaData
{
  /**
   * The enumeration of <code>UPDATE_RULE</code> and <code>DELETE_RULE</code>
   * values for keys.
   */
  public enum Rule { NoAction, Cascade, SetNull, SetDefault, Restrict };

  /**
   * The enumeration of <code>deferrability</code> for keys.
   */
  public enum Deferrability
    { InitiallyDeferred, InitiallyImmediate, NotDeferrable };

  /**
   * The schema in which the table whose column this foreign key references
   * exists.
   */
  private String referencedSchema;

  /**
   * The table whose column this foreign key references.
   */
  private String referencedTable;

  /**
   * The columns that comprise this foreign key and the referenced columns.
   * The map is defined as &lt;ColumnMetaData,String&gt; with the 
   * <code>key</code> indicating the column on the table and the 
   * <code>value</code> indicating the referenced column on {@link
   * #referencedTable}.
   */
  private Map<ColumnMetaData,String> columnMappings =
    new LinkedHashMap<ColumnMetaData,String>();

  /**
   * The <code>UPDATE_RULE</code> for this key.
   */
  private Rule updateRule;

  /**
   * The <code>DELETE_RULE</code> for this key.
   */
  private Rule deleteRule;

  /**
   * The <code>DEFERRABILITY</code> of this key.
   */
  private Deferrability deferrability;
  
  /**
   * Returns {@link #referencedTable}.
   *
   * @return The value/reference of/to referencedTable.
   */
  public String getReferencedTable()
  {
    return referencedTable;
  }
  
  /**
   * Returns {@link #referencedSchema}.
   *
   * @return The value/reference of/to referencedSchema.
   */
  public String getReferencedSchema()
  {
    return referencedSchema;
  }
  
  /**
   * Set {@link #referencedSchema}.
   *
   * @param referencedSchema The value to set.
   */
  protected void setReferencedSchema( final String referencedSchema )
  {
    this.referencedSchema = referencedSchema;
  }
  
  /**
   * Set {@link #referencedTable}.
   *
   * @param referencedTable The value to set.
   */
  protected void setReferencedTable( final String referencedTable )
  {
    this.referencedTable = referencedTable;
  }
  
  /**
   * Returns {@link #columns}.
   *
   * @return The value/reference of/to columns.
   */
  public Map<ColumnMetaData,String> getColumnMappings()
  {
    return Collections.unmodifiableMap( columnMappings );
  }
  
  /**
   * Set {@link #columns}.
   *
   * @see #setColumns
   * @param columns The value to set.
   */
  protected void setColumnMappings( final Map<ColumnMetaData,String> columns )
  {
    this.columnMappings.clear();
    this.columnMappings.putAll( columns );
    setColumns( this.columnMappings.keySet() );
  }

  /**
   * Add the specified values to the {@link #columns} map.
   *
   * @see #setColumns
   * @param cmd The column meta data key to add.
   * @param column The referenced column name to add.
   */
  protected void addColumn( final ColumnMetaData cmd, final String column )
  {
    columnMappings.put( cmd, column );
    setColumns( columnMappings.keySet() );
  }
  
  /**
   * Returns {@link #updateRule}.
   *
   * @return The value/reference of/to updateRule.
   */
  public String getUpdateRule()
  {
    return updateRule.toString();
  }
  
  /**
   * Set {@link #updateRule}.
   *
   * @param updateRule The value to set.
   */
  protected void setUpdateRule( final Rule updateRule )
  {
    this.updateRule = updateRule;
  }
  
  /**
   * Returns {@link #deleteRule}.
   *
   * @return The value/reference of/to deleteRule.
   */
  public String getDeleteRule()
  {
    return deleteRule.toString();
  }
  
  /**
   * Set {@link #deleteRule}.
   *
   * @param deleteRule The value to set.
   */
  protected void setDeleteRule( final Rule deleteRule )
  {
    this.deleteRule = deleteRule;
  }
  
  /**
   * Returns {@link #deferrability}.
   *
   * @return The value/reference of/to deferrability.
   */
  public String getDeferrability()
  {
    return deferrability.toString();
  }
  
  /**
   * Set {@link #deferrability}.
   *
   * @param deferrability The value to set.
   */
  protected void setDeferrability( final Deferrability deferrability )
  {
    this.deferrability = deferrability;
  }
}
