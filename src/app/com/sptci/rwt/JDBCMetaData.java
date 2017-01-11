package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents metadata for the JDBC driver.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: JDBCMetaData.java 3610 2007-10-13 02:17:00Z rakesh $
 * @see java.sql.DatabaseMetaData
 */
public class JDBCMetaData extends MetaData
{
  /** The version number of the driver. */
  private String version;

  /**
   * Returns {@link #version}.
   *
   * @return The value/reference of/to version.
   */
  public String getVersion()
  {
    return version;
  }

  /**
   * Set {@link #version}.
   *
   * @param version The value to set.
   */
  protected void setVersion( final String version )
  {
    this.version = version;
  }
}
