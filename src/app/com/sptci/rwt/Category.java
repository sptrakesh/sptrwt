package com.sptci.rwt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple data object used to represent a category under which named
 * queries are stored.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: Category.java 3610 2007-10-13 02:17:00Z rakesh $
 */
public class Category implements Serializable
{
  /** The name of the category. */
  private String name;

  /** The named queries associated with this category. */
  private Map<String,Query> queries = new TreeMap<String,Query>();

  /** Default constructor. */
  public Category() {}

  /**
   * Create a new instance using the specified name.
   *
   * @param name The {@link #name} to use.
   */
  public Category( final String name )
  {
    setName( name );
  }
  
  /**
   * Returns {@link #name}.
   *
   * @return The value/reference of/to name.
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Set {@link #name}.
   *
   * @param name The value to set.
   */
  protected void setName( final String name )
  {
    this.name = name;
  }

  /**
   * Add the specified query as a saved instance.
   *
   * @param query The query to add to the saved state.
   * @throws IllegalArgumentException If the specified name already exists.
   */
  protected void addQuery( final Query query ) throws IllegalArgumentException
  {
    if ( queries.containsKey( query.getKey() ) )
    {
      throw new IllegalArgumentException( "A query with name: " +
          query.getKey() + " already exists!" );
    }

    queries.put( query.getKey(), query );
  }

  /**
   * Remove the query specified from persistent state.
   *
   * @see #deleteQuery( String )
   * @param query The query that is to be removed.
   */
  protected void deleteQuery( final Query query )
  {
    deleteQuery( query.getKey() );
  }

  /**
   * Remove the query identified by the specified unique name from
   * persistent state.
   *
   * @param name The unique name assigned to the query.
   */
  protected void deleteQuery( final String name )
  {
    queries.remove( name );
  }

  /**
   * Return the query associated with the specified name.
   *
   * @param name The unique name used to identify the saved query.
   * @return The saved query instance or <code>null</code> if no such
   *   saved query exists.
   */
  public Query getQuery( final String name )
  {
    return queries.get( name );
  }
  
  /**
   * Returns a collection of {@link Query} objects that are stored under
   * this category.
   *
   * @return The collection of query instances.
   */
  public Collection<Query> getQueries()
  {
    return Collections.unmodifiableCollection( queries.values() );
  }
  
  /**
   * Set {@link #queries}.
   *
   * @param queries The value to set.
   */
  protected void setQueries( final Map<String,Query> queries )
  {
    this.queries.clear();
    this.queries.putAll( queries ); 
  }

  /**
   * Returns the unique names that have been used to save queries.
   *
   * @return The collection of unique names assigned to queries.
   */
  public Collection<String> getNames()
  {
    return Collections.unmodifiableCollection( queries.keySet() );
  }
}
