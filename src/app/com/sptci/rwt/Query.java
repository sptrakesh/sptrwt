package com.sptci.rwt;

import java.io.Serializable;

import com.sptci.KeyValue;

/**
 * A simple sub-class of <code>KeyValue</code> used to represent a saved
 * query.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: Query.java 3610 2007-10-13 02:17:00Z rakesh $
 */
public class Query extends KeyValue<String,String> implements Serializable
{
  /** Default constructor.  */
  public Query() {}

  /**
   * Create a new instance using the specified values.
   *
   * @param name The name to identify the query by.
   * @param sql The SQL statement to save.
   */
  public Query( final String name, final String sql )
  {
    super( name, sql );
  }

  /**
   * Compares this object with the specified object for order. Over-ridden
   * to compare only the value of <code>key</code>.
   *
   * @param query The object that is to be compared with this object.
   * @return int A negative integer, zero, or a positive integer as 
   *   this object is less than, equal to, or greater than the 
   *   specified object.
   */
  @Override
  public int compareTo( KeyValue<String,String> query )
  {
    if ( query == null ) return 1;
    return getKey().compareTo( query.getKey() );
  }
}
