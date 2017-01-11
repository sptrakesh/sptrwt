package com.sptci.rwt;

/**
 * A value object that represents database schemas.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-26
 * @version $Id: SchemaMetaData.java 3648 2007-10-24 23:52:17Z rakesh $
 */
public class SchemaMetaData extends RootMetaData
{
  /**
   * The {@link CatalogueMetaData} that represents the <code>catalog</code>
   * in which the schema exists.
   */
  private CatalogueMetaData catalogue;
  
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
}
