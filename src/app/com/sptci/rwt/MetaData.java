package com.sptci.rwt;

import java.io.Serializable;

/**
 * An abstract base class for all value objects that represent metadata for
 * database objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-25
 * @version $Id: MetaData.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public abstract class MetaData implements Serializable
{
  /**
   * The name of the object.
   */
  private String name;

  /**
   * Return a string representation of this instance.  By default return
   * {@link #getName}.
   *
   * @return The string representation of this object.
   */
  @Override
  public String toString()
  {
    return getName();
  }

  /**
   * Default implementation of the equality comparison method.  Compares
   * the class types and {@link #name} values.
   *
   * @param object The object that is to be compared with this for equality.
   * @return Returns <code>true</code> if the object is of the same type
   *   and has the same {@link #name}.
   */
  @Override
  public boolean equals( Object object )
  {
    if ( this == object ) return true;
    boolean result = false;

    if ( getClass() == object.getClass() )
    {
      MetaData md = (MetaData) object;
      result = ( ( getName() == md.getName() ) ||
          ( ( getName() != null ) && getName().equals( md.getName() ) ) );
    }

    return result;
  }

  /**
   * Return a hash code for this object.
   *
   * @return The hash code value computed out of the class of this object
   *   and {@link #name}.
   */
  @Override
  public int hashCode()
  {
    int hash = 7;
    hash += ( 31 * 7 ) + getClass().getName().hashCode();
    hash += ( 31 * 7 ) + ( ( getName() == null ) ? 0 : getName().hashCode() );
    return hash;
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
}
