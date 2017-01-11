package com.sptci.rwt;

import java.io.FileNotFoundException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;

import com.sptci.KeyValue;
import com.sptci.util.StringUtilities;

/**
 * A serializable wrapper used to represent saved SQL queries for
 * the application.  This class will be serialised to the following file
 * and initialised from the same file during application load:
 *
 * <pre>&lt;sptrwt.data.directory&gt;/queries.xml</pre>
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: Queries.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class Queries implements Serializable
{
  /**
   * The encoding to use to serialise and deserialise instances of this
   * class.
   *
   * {@value}
   */
  public static final String ENCODING = "UTF-8";

  /**
   * The name of the file to which this class will be serialised.
   */
  public static final String FILE_NAME = "queries.xml";

  /**
   * The system property used to configure the location of the root directory
   * under which persistent state information for the application is stored.
   *
   * {@value}
   */
  public static final String DIRECTORY = "sptrwt.data.directory";

  /**
   * The {@link com.thoughtworks.xstream.XStream} instance used to serialise
   * and deserialise instances of this class.
   */
  protected static final XStream xstream;

  /**
   * Static initialiser for {@link #xstream}.
   */
  static
  {
    xstream = new XStream();
    xstream.alias( "queries", Queries.class );
    xstream.alias( "query", Query.class );
    xstream.alias( "keyValue", KeyValue.class );
    xstream.alias( "category", Category.class );

    xstream.useAttributeFor( KeyValue.class, "key" );
    xstream.useAttributeFor( KeyValue.class, "value" );
  }

  /**
   * A map used to quickly look up {@link Query} instances by their
   * {@link Category#name}.
   */
  private final TreeMap<String,Category> categories;

  /**
   * The fully qualified file name to use to serialise this instance into.
   */
  private transient String fileName;

  /**
   * Default constructor.  Cannot be instantiated.
   */
  protected Queries()
  {
    categories = new TreeMap<String,Category>();
  }

  /**
   * Create a new instance of the class for the specified user.  Saved
   * files are stored under a directory named after the <code>user</code>
   * under {@link #DIRECTORY}.
   * 
   * @see #deserialise
   * @return The newly initialised instance of the class.
   * @throws RuntimeException If errors are encountered while deserialising
   *   the persistent state of this instance.
   */
  public static Queries getInstance( final String user )
  {
    final Queries queries = new Queries();
    queries.deserialise( user );
    return queries;
  }

  /**
   * Return a {@link java.sql.Query} for the specified category and saved
   * with the specified unique name.
   *
   * @param category The category under which the query is saved.
   * @param name The unique name used to identify the saved query.
   * @return The SQL statement associated with the named query, or
   *   <code>null</code> if no such mapping exists.
   */
  public Query getQuery( final String category, final String name )
  {
    Query query = null;
    Category cat = categories.get( category );
    if ( cat != null )
    {
      query = cat.getQuery( name );
    }

    return query;
  }

  /**
   * Add the specified query parameters value object to the application
   * persistent state.
   *
   * @see #serialise
   * @param category The category to associate the query with.
   * @param query The query to be saved.
   */
  public void add( final String category, final Query query )
  {
    Category cat = categories.get( category );

    if ( cat == null )
    {
      cat = new Category( category );
      categories.put( category, cat );
    }

    cat.addQuery( query );
    serialise();
  }

  /**
   * Remove the specified query parameters from the application
   * persistent state.
   *
   * @see #serialise
   * @param category The category under which the query was saved.
   * @param name The unique name to use to identify the saved query.
   */
  public void delete( final String category, final String name )
  {
    final Category cat = categories.get( category );
    if ( cat != null )
    {
      cat.deleteQuery( name );
      serialise();
    }
  }

  /**
   * Remove the specified category from persistent state.  This removes
   * all saved queries for that category.
   *
   * @see #serialise
   * @param name The name of the category to remove from saved state.
   */
  public void delete( final String name )
  {
    categories.remove( name );
    serialise();
  }

  /**
   * Deserialise the contents of {@link #FILE_NAME} into this instance.
   *
   * @param user The name of the user to use to construct the full filename.
   * @throws RuntimeException If errors are encountered while deserialising
   *   the persistent state.  No exceptions are thrown if the file does
   *   not exist.
   */
  protected void deserialise( final String user ) throws RuntimeException
  {
    final String separator =
      System.getProperties().getProperty( "file.separator" );

    final StringBuilder builder = new StringBuilder( 64 );
    builder.append( System.getProperty( DIRECTORY ) );
    builder.append( separator );
    builder.append( user );
    builder.append( separator );
    builder.append( FILE_NAME );
    fileName = builder.toString();

    try
    {
      final String xml = StringUtilities.fromFile( fileName, ENCODING );
      xstream.fromXML( xml, this );
    }
    catch ( FileNotFoundException fnfe ) {}
    catch ( Throwable t )
    {
      throw new RuntimeException( "Error deserialising saved instance", t );
    }
  }

  /**
   * Serialise this instance to the {@link #FILE_NAME}.
   *
   * @throws RuntimeException If errors are encountered while serialising
   *   the instance.
   */
  protected void serialise() throws RuntimeException
  {
    try
    {
      final String xml = xstream.toXML( this );
      StringUtilities.toFile( xml, fileName, ENCODING );
    }
    catch ( Throwable t )
    {
      throw new RuntimeException( t );
    }
  }

  /**
   * Returns the category identified by the name specified.
   *
   * @param name The name of the category.
   * @return The category identified by the name specified, or
   *   <code>null</code> if no such category exists.
   */
  public Category getCategory( final String name )
  {
    return categories.get( name );
  }
  
  /**
   * Returns the names of the categories under which queries have been
   * saved.
   *
   * @return A read only collection of category names.
   */
  public Collection<Category> getCategories()
  {
    return Collections.unmodifiableCollection( categories.values() );
  }
}
