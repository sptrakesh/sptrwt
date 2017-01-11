package com.sptci.rwt;

import java.io.Serializable;

import com.sptci.util.AESEncrypt;

/**
 * A simple bean used to represent different types of database engines
 * that the application can connect to.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-24
 * @version $Id: ConnectionData.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class ConnectionData implements Serializable
{
  /**
   * The key used to encrypt {@link #password}.
   */
  private static final String PASSWORD_KEY = ")4m&s#R4s0%c*c(m";

  /**
   * The fully qualified hostname of the database server to connect to.
   */
  private String host;

  /**
   * The name of the database to connect to.
   */
  private String database;

  /**
   * The port to connect to.
   */
  private int port;

  /**
   * The database user to connect as.
   */
  private String userName;

  /**
   * The password for the database user to connect as.
   */
  private byte[] password;

  /**
   * Default constructor. Not publicly instantiable.
   */
  protected ConnectionData() {}
  
  /**
   * Returns {@link #host}.
   *
   * @return The value/reference of/to host.
   */
  public String getHost()
  {
    return host;
  }
  
  /**
   * Set {@link #host}.
   *
   * @param host The value to set.
   */
  protected void setHost( final String host )
  {
    this.host = host;
  }
  
  /**
   * Returns {@link #database}.
   *
   * @return The value/reference of/to database.
   */
  public String getDatabase()
  {
    return database;
  }
  
  /**
   * Set {@link #database}.
   *
   * @param database The value to set.
   */
  protected void setDatabase( final String database )
  {
    this.database = database;
  }
  
  /**
   * Returns {@link #port}.
   *
   * @return The value/reference of/to port.
   */
  public int getPort()
  {
    return port;
  }
  
  /**
   * Set {@link #port}.
   *
   * @param port The value to set.
   */
  protected void setPort( final int port )
  {
    this.port = port;
  }
  
  /**
   * Returns {@link #userName}.
   *
   * @return The value/reference of/to userName.
   */
  public String getUserName()
  {
    return userName;
  }
  
  /**
   * Set {@link #userName}.
   *
   * @param userName The value to set.
   */
  protected void setUserName( final String userName )
  {
    this.userName = userName;
  }
 
 /**
  * Returns {@link #password}.
  *
  * @return The value/reference of/to password.
  * @throws RuntimeException If the password value cannot be decrypted.
  */
 public String getPassword() throws RuntimeException
 {
   try
   {
     return new AESEncrypt().decrypt( PASSWORD_KEY, password );
   }
   catch ( Throwable t )
   {
     throw new RuntimeException( "Error decrypting password.", t );
   }
 }
 
 /**
  * Set {@link #password}.
  *
  * @param password The value to set.
  * @throws RuntimeException If errors are encountered while encrypting the
  *   <code>password</code> value.
  */
 protected void setPassword( final String password ) throws RuntimeException
 {
   try
   {
     this.password = new AESEncrypt().encrypt( PASSWORD_KEY, password );
   }
   catch ( Throwable t )
   {
     throw new RuntimeException( "Error encrypting password.", t );
   }
 }
}
