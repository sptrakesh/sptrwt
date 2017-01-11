package com.sptci.rwt;

/**
 * A custom exception that is used to wrap any exceptions raised when
 * executing SQL statements.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-05
 * @version $Id: QueryException.java 3580 2007-10-08 04:07:39Z rakesh $
 */
public class QueryException extends RuntimeException
{
  /**
   * Default constructor.  Cannot be instantiated.
   */
  protected QueryException() {}

  /**
   * Create a new exception with the specified message.
   *
   * @param message The message that describes the problem.
   */
  public QueryException( final String message )
  {
    super( message );
  }

  /**
   * Create a new exception with the instance of {@link Throwable} that
   * caused the problem.
   *
   * @param cause The exception that caused this instance of the
   *   exception to be thrown.
   */
  public QueryException( final Throwable cause )
  {
    super( cause );
  }

  /**
   * Create a new exception with the specified message and instance of
   * {@link Throwable} caused the problem.
   *
   * @param message The message that describes the problem.
   * @param cause The exception that caused this instance of the
   *   exception to be thrown.
   */
  public QueryException( final String message, final Throwable cause )
  {
    super( message, cause );
  }
}
