package com.sptci.rwt;

/**
 * An abstract value object that is used to represent database objects such
 * as tables, views, columns etc that support having comments.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: ObjectMetaData.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public abstract class ObjectMetaData extends MetaData
{
  /**
   * The comments associated with the table.
   */
  private String comment;

  /**
   * A reference to the parent catalogue to which this object belongs.
   */
  private CatalogueMetaData catalogue;

  /**
   * A reference to the parent catalogue/schema to which this object belongs.
   */
  private RootMetaData root;
  
  /**
   * Returns {@link #comment}.
   *
   * @return The value/reference of/to comment.
   */
  public String getComment()
  {
    return comment;
  }
  
  /**
   * Set {@link #comment}.
   *
   * @param comment The value to set.
   */
  protected void setComment( final String comment )
  {
    this.comment = comment;
  }
  
  /**
   * Returns {@link #catalogue}.
   *
   * @return The value/reference of/to catalogue.
   */
  public CatalogueMetaData getCatalogue()
  {
    return catalogue;
  }
  
  /**
   * Set {@link #catalogue}.
   *
   * @param catalogue The value to set.
   */
  protected void setCatalogue( final CatalogueMetaData catalogue )
  {
    this.catalogue = catalogue;
  }
  
  /**
   * Returns {@link #root}.
   *
   * @return The value/reference of/to root.
   */
  public RootMetaData getRoot()
  {
    return root;
  }
  
  /**
   * Set {@link #root}.
   *
   * @param root The value to set.
   */
  protected void setRoot( final RootMetaData root )
  {
    this.root = root;
  }
}
