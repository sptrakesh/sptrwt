package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents metadata for views.  Metadata for views
 * are more complete in the <code>Information Schema</code> and hence
 * additional details (over and above those in {@link TableMetaData} are
 * fetched from it.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: ViewMetaData.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class ViewMetaData extends TableTypeMetaData
{
  /**
   * The query expression defining the view (null if the view is not owned
   * by a currently enabled role)
   */
  private String definition;

  /**
   * The <code>check_option</code> column in the
   * <code>information_schema.views</code> view.
   */
  private String checkOption;

  /**
   * A flag used to indicate whether the view supports <code>UPDATE</code>
   * and <code>DELETE</code> statements.
   */
  private boolean updatable;

  /**
   * A flag used to indicate whether the view supports <code>INSERT</code>
   * statements.
   */
  private boolean insertable;
  
  /**
   * Returns {@link #definition}.
   *
   * @return The value/reference of/to definition.
   */
  public String getDefinition()
  {
    return definition;
  }
  
  /**
   * Set {@link #definition}.
   *
   * @param definition The value to set.
   */
  protected void setDefinition( final String definition )
  {
    this.definition = definition;
  }
  
  /**
   * Returns {@link #checkOption}.
   *
   * @return The value/reference of/to checkOption.
   */
  public String getCheckOption()
  {
    return checkOption;
  }
  
  /**
   * Set {@link #checkOption}.
   *
   * @param checkOption The value to set.
   */
  protected void setCheckOption( final String checkOption )
  {
    this.checkOption = checkOption;
  }
  
  /**
   * Returns {@link #updatable}.
   *
   * @return The value/reference of/to updatable.
   */
  public boolean getUpdatable()
  {
    return updatable;
  }
  
  /**
   * Set {@link #updatable}.
   *
   * @param updatable The value to set.
   */
  protected void setUpdatable( final boolean updatable )
  {
    this.updatable = updatable;
  }
  
  /**
   * Returns {@link #insertable}.
   *
   * @return The value/reference of/to insertable.
   */
  public boolean getInsertable()
  {
    return insertable;
  }
  
  /**
   * Set {@link #insertable}.
   *
   * @param insertable The value to set.
   */
  protected void setInsertable( final boolean insertable )
  {
    this.insertable = insertable;
  }
}
