package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A value object that represents metadata for the database.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: DBMSMetaData.java 3648 2007-10-24 23:52:17Z rakesh $
 * @see java.sql.DatabaseMetaData
 */
public class DBMSMetaData extends MetaData
{
  /** 
   * An enumeration for the various types of transaction isolation levels
   * supported by the database.
   */
  public enum Transaction { None, ReadCommitted, ReadUncommitted,
    RepeatableRead, Serialisable };

  /** The product version as reported by the vendor.  */
  private String version;

  /** The default transaction isolation level used by the database. */
  private Transaction defaultTransaction;

  /** Metadata about the JDBC driver used to access the database */
  private JDBCMetaData jdbcMetaData;

  /**
   * Metadata about maximum limitsMetaData enforced by the database.
   */
  private LimitsMetaData limitsMetaData;

  /** Collection of catalogues available in the database. */
  private Collection<CatalogueMetaData> catalogues =
    new ArrayList<CatalogueMetaData>();

  /** Collection of schemas available in the database. */
  private Collection<SchemaMetaData> schemas = new ArrayList<SchemaMetaData>();

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

  /**
   * Returns {@link #jdbcMetaData}.
   *
   * @return The value/reference of/to jdbcMetaData.
   */
  public JDBCMetaData getJdbcMetaData()
  {
    return jdbcMetaData;
  }

  /**
   * Set {@link #jdbcMetaData}.
   *
   * @param jdbcMetaData The value to set.
   */
  protected void setJdbcMetaData( final JDBCMetaData jdbcMetaData )
  {
    this.jdbcMetaData = jdbcMetaData;
  }

  /**
   * Returns {@link #defaultTransaction}.
   *
   * @return The value/reference of/to defaultTransaction.
   */
  public String getDefaultTransaction()
  {
    return defaultTransaction.toString();
  }

  /**
   * Set {@link #defaultTransaction}.
   *
   * @param transaction The value to set.
   */
  protected void setDefaultTransaction( final Transaction transaction )
  {
    this.defaultTransaction = transaction;
  }

  /**
   * Returns {@link #limitsMetaData}.
   * 
   * @return The value/reference of/to limitsMetaData.
   */
  public LimitsMetaData getLimitsMetaData()
  {
    return limitsMetaData;
  }

  /**
   * Set {@link #limitsMetaData}.
   * 
   * @param limitsMetaData The value to set.
   */
  protected void setLimitsMetaData( final LimitsMetaData limitsMetaData )
  {
    this.limitsMetaData = limitsMetaData;
  }

  /**
   * Return the catalogue identified by the name specified.
   *
   * @param name The name of the catalogue.
   * @return The meta data object associated with the catalogue.
   */
  public CatalogueMetaData getCatalogue( final String name )
  {
    CatalogueMetaData cmd = null;
    if ( name == null ) return cmd;

    for ( CatalogueMetaData md : catalogues )
    {
      if ( name.equals( md.getName() ) )
      {
        cmd = md;
      }
    }

    return cmd;
  }
  
  /**
   * Returns {@link #catalogues}.
   *
   * @return The value/reference of/to catalogues.
   */
  public Collection<CatalogueMetaData> getCatalogues()
  {
    return Collections.unmodifiableCollection( catalogues );
  }
  
  /**
   * Set {@link #catalogues}.
   *
   * @param catalogues The value to set.
   */
  protected void setCatalogues(
      final Collection<CatalogueMetaData> catalogues )
  {
    this.catalogues.clear();
    this.catalogues.addAll( catalogues );
  }

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
}
