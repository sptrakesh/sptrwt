package com.sptci.rwt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import com.sptci.KeyValue;

/**
 * An abstract base class analyser for the various database objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: Analyser.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public abstract class Analyser
{
  /** The logger to use to log errors/messages to. */
  protected static final Logger logger = Logger.getAnonymousLogger();

  /**
   * The connection manager to use to fetch connections.
   */
  protected final ConnectionManager manager;

  /**
   * Create a new instance of the class using the specified connection
   * manager.
   *
   * @param manager The connection manager to use.
   */
  protected Analyser( final ConnectionManager manager )
  {
    this.manager = manager;
  }

  /**
   * Analyse the database connected to and return the appropriate metadata
   * objects.  Returns a collection of metadata objects that represent all
   * the objects of this type available to the user.
   *
   * @param parameters Optional parameters that are required to analyse
   *   objects of this type.
   * @return The collection of metadata objects representing all objects of
   *   this type.
   * @throws SQLException If errors are encountered while analysisng the
   */
  public abstract Collection<? extends MetaData> analyse(
      final MetaData... parameters ) throws SQLException;

  /**
   * Return a value object that represents the names of the catalogue and/or
   * schema specified.
   *
   * @since Version 1.1
   * @param metaData The meta data object out of which the names are
   *   to be retrieved.
   * @return The value object that contains the names.
   */
  protected CatalogueSchema getNames( final MetaData metaData )
  {
    String catalogue = null;
    String schema = null;
    if ( metaData != null )
    {
      if ( metaData instanceof CatalogueMetaData )
      {
        catalogue = metaData.getName();
      }
      else
      {
        SchemaMetaData smd = (SchemaMetaData) metaData;
        if ( smd.getCatalogue() != null )
        {
          catalogue = smd.getCatalogue().getName();
        }
        schema = smd.getName();
      }
    }

    return new CatalogueSchema( catalogue, schema );
  }

  /**
   * A value object used to represent the names of the catalogue and
   * schema.
   */
  protected class CatalogueSchema extends KeyValue<String,String>
  {
    /**
     * Create a new instance of the value object with the specified
     * values.
     *
     * @param catalogue The name of the catalogue.  May be null.
     * @param schema The name of the schema.  May be null.
     */
    public CatalogueSchema( final String catalogue, final String schema )
    {
      super( catalogue, schema );
    }

    /** Return the name of the catalogue. */
    public String getCatalogue() { return getKey(); }

    /** Return the name of the schema. */
    public String getSchema() { return getValue(); }
  }
}
