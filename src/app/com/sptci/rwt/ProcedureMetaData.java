package com.sptci.rwt;

/**
 * A metadata object that represents procedure/function objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: ProcedureMetaData.java 3558 2007-10-02 22:42:51Z rakesh $
 */
public class ProcedureMetaData extends ObjectMetaData
{
  /** The data type returned by the procedure. */
  private String dataType;

  /**
   * The name of the user defined type if {@link #dataType} is a user
   * defined type.
   */
  private String userDefinedType;

  /** The SQL content of the procedure. */
  private String body;

  /**
   * The definition of the procedure.  This usually includes {@link
   * #body} within it
   */
  private String definition;
  
  /**
   * Returns {@link #dataType}.
   *
   * @return The value/reference of/to dataType.
   */
  public String getDataType()
  {
    return dataType;
  }
  
  /**
   * Set {@link #dataType}.
   *
   * @param dataType The value to set.
   */
  protected void setDataType( final String dataType )
  {
    this.dataType = dataType;
  }
  
  /**
   * Returns {@link #userDefinedType}.
   *
   * @return The value/reference of/to userDefinedType.
   */
  public String getUserDefinedType()
  {
    return userDefinedType;
  }
  
  /**
   * Set {@link #userDefinedType}.
   *
   * @param userDefinedType The value to set.
   */
  protected void setUserDefinedType( final String userDefinedType )
  {
    this.userDefinedType = userDefinedType;
  }
  
  /**
   * Returns {@link #body}.
   *
   * @return The value/reference of/to body.
   */
  public String getBody()
  {
    return body;
  }
  
  /**
   * Set {@link #body}.
   *
   * @param body The value to set.
   */
  protected void setBody( final String body )
  {
    this.body = body;
  }
  
  /**
   * Returns {@link #definition}.
   *
   * @return The value/reference of/to definition.
   */
  public String getDefinition()
  {
    return definition;
  }
  
  /**
   * Set {@link #definition}.
   *
   * @param definition The value to set.
   */
  protected void setDefinition( final String definition )
  {
    this.definition = definition;
  }
}
