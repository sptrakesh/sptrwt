package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents metadata about limits enforced by
 * the database.  All limits are expressed in terms of the maximum values
 * allowed.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: LimitsMetaData.java 3610 2007-10-13 02:17:00Z rakesh $
 * @see java.sql.DatabaseMetaData
 */
public class LimitsMetaData extends MetaData
{
  /** 
   * The maximum number of characters this database allows for a character
   * literal.
   */
  private int characterLength;

  /**
   * The maximum number of characters this database allows for a column
   * name.
   */
  private int columnNameLength;

  /**
   * The maximum number of columns this database allows in a <code>GROUP
   * BY</code> clause.
   */
  private int columnsInGroupBy;

  /** The maximum number of columns this database allows in an index. */
  private int columnsInIndex;

  /**
   * The maximum number of columns this database allows in an <code>ORDER
   * BY</code> clause.
   */
  private int columnsInOrderBy;

  /**
   * The maximum number of columns this database allows in a <code>SELECT
   * </code> list.
   */
  private int columnsInSelect;

  /** The maximum number of columns this database allows in a table. */
  private int columnsInTable;

  /**
   * The maximum number of concurrent connections to this database that
   * are possible.
   */
  private int connections;

  /**
   * The maximum number of characters that this database allows in a
   * <code>cursor</code> name.
   */
  private int cursorNameLength;

  /**
   * The maximum number of bytes this database allows for an index,
   * including all of the parts of the index.
   */
  private int indexLength;

  /**
   * The maximum number of characters that this database allows in a
   * procedure name.
   */
  private int procedureNameLength;

  /** The maximum number of bytes this database allows in a single row. */
  private int rowSize;

  /**
   * The maximum number of characters that this database allows in a
   * schema name.
   */
  private int schemaNameLength;

  /**
   * The maximum number of characters this database allows in an <code>SQL
   * </code> statement.
   */
  private int statementLength;

  /**
   * The maximum number of active statements to this database that can be
   * open at the same time.
   */
  private int statements;

  /**
   * The maximum number of characters this database allows in a table name.
   */
  private int tableNameLength;

  /**
   * The maximum number of tables this database allows in a <code>SELECT
   * </code> statement.
   */
  private int tablesInSelect;

  /**
   * The maximum number of characters this database allows in a user name.
   */
  private int userNameLength;
  
  /**
   * Returns {@link #characterLength}.
   *
   * @return The value/reference of/to characterLength.
   */
  public int getCharacterLength()
  {
    return characterLength;
  }
  
  /**
   * Set {@link #characterLength}.
   *
   * @param characterLength The value to set.
   */
  protected void setCharacterLength( final int characterLength )
  {
    this.characterLength = characterLength;
  }
  
  /**
   * Returns {@link #columnNameLength}.
   *
   * @return The value/reference of/to columnNameLength.
   */
  public int getColumnNameLength()
  {
    return columnNameLength;
  }
  
  /**
   * Set {@link #columnNameLength}.
   *
   * @param columnNameLength The value to set.
   */
  protected void setColumnNameLength( final int columnNameLength )
  {
    this.columnNameLength = columnNameLength;
  }
  
  /**
   * Returns {@link #columnsInGroupBy}.
   *
   * @return The value/reference of/to columnsInGroupBy.
   */
  public int getColumnsInGroupBy()
  {
    return columnsInGroupBy;
  }
  
  /**
   * Set {@link #columnsInGroupBy}.
   *
   * @param columnsInGroupBy The value to set.
   */
  protected void setColumnsInGroupBy( final int columnsInGroupBy )
  {
    this.columnsInGroupBy = columnsInGroupBy;
  }
  
  /**
   * Returns {@link #columnsInIndex}.
   *
   * @return The value/reference of/to columnsInIndex.
   */
  public int getColumnsInIndex()
  {
    return columnsInIndex;
  }
  
  /**
   * Set {@link #columnsInIndex}.
   *
   * @param columnsInIndex The value to set.
   */
  protected void setColumnsInIndex( final int columnsInIndex )
  {
    this.columnsInIndex = columnsInIndex;
  }
  
  /**
   * Returns {@link #columnsInOrderBy}.
   *
   * @return The value/reference of/to columnsInOrderBy.
   */
  public int getColumnsInOrderBy()
  {
    return columnsInOrderBy;
  }
  
  /**
   * Set {@link #columnsInOrderBy}.
   *
   * @param columnsInOrderBy The value to set.
   */
  protected void setColumnsInOrderBy( final int columnsInOrderBy )
  {
    this.columnsInOrderBy = columnsInOrderBy;
  }
  
  /**
   * Returns {@link #columnsInSelect}.
   *
   * @return The value/reference of/to columnsInSelect.
   */
  public int getColumnsInSelect()
  {
    return columnsInSelect;
  }
  
  /**
   * Set {@link #columnsInSelect}.
   *
   * @param columnsInSelect The value to set.
   */
  protected void setColumnsInSelect( final int columnsInSelect )
  {
    this.columnsInSelect = columnsInSelect;
  }
  
  /**
   * Returns {@link #columnsInTable}.
   *
   * @return The value/reference of/to columnsInTable.
   */
  public int getColumnsInTable()
  {
    return columnsInTable;
  }
  
  /**
   * Set {@link #columnsInTable}.
   *
   * @param columnsInTable The value to set.
   */
  protected void setColumnsInTable( final int columnsInTable )
  {
    this.columnsInTable = columnsInTable;
  }
  
  /**
   * Returns {@link #connections}.
   *
   * @return The value/reference of/to connections.
   */
  public int getConnections()
  {
    return connections;
  }
  
  /**
   * Set {@link #connections}.
   *
   * @param connections The value to set.
   */
  protected void setConnections( final int connections )
  {
    this.connections = connections;
  }
  
  /**
   * Returns {@link #cursorNameLength}.
   *
   * @return The value/reference of/to cursorNameLength.
   */
  public int getCursorNameLength()
  {
    return cursorNameLength;
  }
  
  /**
   * Set {@link #cursorNameLength}.
   *
   * @param cursorNameLength The value to set.
   */
  protected void setCursorNameLength( final int cursorNameLength )
  {
    this.cursorNameLength = cursorNameLength;
  }
  
  /**
   * Returns {@link #indexLength}.
   *
   * @return The value/reference of/to indexLength.
   */
  public int getIndexLength()
  {
    return indexLength;
  }
  
  /**
   * Set {@link #indexLength}.
   *
   * @param indexLength The value to set.
   */
  protected void setIndexLength( final int indexLength )
  {
    this.indexLength = indexLength;
  }
  
  /**
   * Returns {@link #procedureNameLength}.
   *
   * @return The value/reference of/to procedureNameLength.
   */
  public int getProcedureNameLength()
  {
    return procedureNameLength;
  }
  
  /**
   * Set {@link #procedureNameLength}.
   *
   * @param procedureNameLength The value to set.
   */
  protected void setProcedureNameLength( final int procedureNameLength )
  {
    this.procedureNameLength = procedureNameLength;
  }
  
  /**
   * Returns {@link #rowSize}.
   *
   * @return The value/reference of/to rowSize.
   */
  public int getRowSize()
  {
    return rowSize;
  }
  
  /**
   * Set {@link #rowSize}.
   *
   * @param rowSize The value to set.
   */
  protected void setRowSize( final int rowSize )
  {
    this.rowSize = rowSize;
  }
  
  /**
   * Returns {@link #schemaNameLength}.
   *
   * @return The value/reference of/to schemaNameLength.
   */
  public int getSchemaNameLength()
  {
    return schemaNameLength;
  }
  
  /**
   * Set {@link #schemaNameLength}.
   *
   * @param schemaNameLength The value to set.
   */
  protected void setSchemaNameLength( final int schemaNameLength )
  {
    this.schemaNameLength = schemaNameLength;
  }
  
  /**
   * Returns {@link #statementLength}.
   *
   * @return The value/reference of/to statementLength.
   */
  public int getStatementLength()
  {
    return statementLength;
  }
  
  /**
   * Set {@link #statementLength}.
   *
   * @param statementLength The value to set.
   */
  protected void setStatementLength( final int statementLength )
  {
    this.statementLength = statementLength;
  }
  
  /**
   * Returns {@link #statements}.
   *
   * @return The value/reference of/to statements.
   */
  public int getStatements()
  {
    return statements;
  }
  
  /**
   * Set {@link #statements}.
   *
   * @param statements The value to set.
   */
  protected void setStatements( final int statements )
  {
    this.statements = statements;
  }
  
  /**
   * Returns {@link #tableNameLength}.
   *
   * @return The value/reference of/to tableNameLength.
   */
  public int getTableNameLength()
  {
    return tableNameLength;
  }
  
  /**
   * Set {@link #tableNameLength}.
   *
   * @param tableNameLength The value to set.
   */
  protected void setTableNameLength( final int tableNameLength )
  {
    this.tableNameLength = tableNameLength;
  }
  
  /**
   * Returns {@link #tablesInSelect}.
   *
   * @return The value/reference of/to tablesInSelect.
   */
  public int getTablesInSelect()
  {
    return tablesInSelect;
  }
  
  /**
   * Set {@link #tablesInSelect}.
   *
   * @param tablesInSelect The value to set.
   */
  protected void setTablesInSelect( final int tablesInSelect )
  {
    this.tablesInSelect = tablesInSelect;
  }
  
  /**
   * Returns {@link #userNameLength}.
   *
   * @return The value/reference of/to userNameLength.
   */
  public int getUserNameLength()
  {
    return userNameLength;
  }
  
  /**
   * Set {@link #userNameLength}.
   *
   * @param userNameLength The value to set.
   */
  protected void setUserNameLength( final int userNameLength )
  {
    this.userNameLength = userNameLength;
  }
}
