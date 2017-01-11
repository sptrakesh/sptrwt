package com.sptci.rwt;

/**
 * A metadata object that represents table indices.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: IndexMetaData.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class IndexMetaData extends KeyMetaData
{
  /**
   * An enumeration for the index types.
   */
  public enum Type { Statistic, Clustered, Hashed, Other };

  /**
   * An enumerationg for the sort sequence of indices.
   */
  public enum SortSequence { Ascending, Descending, Unsorted };

  /**
   * A flag indicating whether this index represents unique values or not.
   */
  private boolean unique;
  
  /**
   * A field indicating the type of the index.
   */
  private Type type;

  /**
   * A field indicating the sort order used by the index.
   */
  private SortSequence sortSequence;

  /**
   * The cardinality for the index.  When {@link #type} is {@link
   * Type#Statistic}, then this is the number of rows in the table;
   * otherwise, it is the number of unique values in the index.
   */
  private int cardinality;

  /**
   * The number of pages used for the index.  When {@link #type} is {@link
   * Type#Statistic}, then this is the number of pages used for the table;
   * otherwise, it is the number of pages used for the index.
   */
  private int pages;

  /**
   * Returns {@link #unique}.
   *
   * @return The value/reference of/to unique.
   */
  public boolean isUnique()
  {
    return unique;
  }

  /**
   * Returns {@link #unique}.
   *
   * @return The value/reference of/to unique.
   */
  public boolean getUnique()
  {
    return isUnique();
  }

  /**
   * Set {@link #unique}.
   *
   * @param unique The value to set.
   */
  protected void setUnique( final boolean unique )
  {
    this.unique = unique;
  }

  /**
   * Returns {@link #type}.
   *
   * @return The value/reference of/to type.
   */
  public String getType()
  {
    return type.toString();
  }

  /**
   * Set {@link #type}.
   *
   * @param type The value to set.
   */
  protected void setType( final Type type )
  {
    this.type = type;
  }

  /**
   * Returns {@link #sortSequence}.
   *
   * @return The value/reference of/to sortSequence.
   */
  public String getSortSequence()
  {
    return sortSequence.toString();
  }

  /**
   * Set {@link #sortSequence}.
   *
   * @param sortSequence The value to set.
   */
  protected void setSortSequence( final SortSequence sortSequence )
  {
    this.sortSequence = sortSequence;
  }

  /**
   * Returns {@link #cardinality}.
   *
   * @return The value/reference of/to cardinality.
   */
  public int getCardinality()
  {
    return cardinality;
  }

  /**
   * Set {@link #cardinality}.
   *
   * @param cardinality The value to set.
   */
  protected void setCardinality( final int cardinality )
  {
    this.cardinality = cardinality;
  }

  /**
   * Returns {@link #pages}.
   *
   * @return The value/reference of/to pages.
   */
  public int getPages()
  {
    return pages;
  }

  /**
   * Set {@link #pages}.
   *
   * @param pages The value to set.
   */
  protected void setPages( final int pages )
  {
    this.pages = pages;
  }
}
