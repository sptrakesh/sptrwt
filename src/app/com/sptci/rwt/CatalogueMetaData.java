package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents database catalogues.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-23
 * @version $Id: CatalogueMetaData.java 3649 2007-10-25 00:15:24Z rakesh $
 * @since Version 1.1
 */
public class CatalogueMetaData extends RootMetaData
{
  /**
   * The collection of schema objects that belong to this catalogue.
   */
  private Collection<SchemaMetaData> schemas = new ArrayList<SchemaMetaData>();
  
  /**
   * Returns {@link #schemas}.
   *
   * @return The value/reference of/to schemas.
   */
  public Collection<SchemaMetaData> getSchemas()
  {
    return Collections.unmodifiableCollection( schemas );
  }
  
  /**
   * Set {@link #schemas}.
   *
   * @param schemas The value to set.
   */
  protected void setSchemas( final Collection<SchemaMetaData> schemas )
  {
    this.schemas.clear();
    this.schemas.addAll( schemas );
  }

  /**
   * Add the specified schema to {@link #schemas}.
   *
   * @param schema The meta data object to add.
   */
  protected void addSchema( final SchemaMetaData schema )
  {
    boolean exists = false;
    for ( SchemaMetaData smd : schemas )
    {
      if ( schema.getName().equals( smd.getName() ) )
      {
        exists = true;
        break;
      }
    }

    if ( ! exists ) schemas.add( schema );
  }
}
