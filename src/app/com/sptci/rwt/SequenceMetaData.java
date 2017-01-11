package com.sptci.rwt;

/**
 * A value object that represents metadata for sequences.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-28
 * @version $Id: SequenceMetaData.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class SequenceMetaData extends ObjectMetaData
{
  /** The data type for the sequence.  */
  private String dataType;

  /** The minimum value of the sequence. */
  private String minimum;

  /** The maximum value of the sequence.  */
  private String maximum;

  /** The value by which the sequence is incremented. */
  private int increment = 1;

  /** The cycle policy for the sequence after hitting maximum value */
  private String cyclePolicy;

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
   * Returns {@link #minimum}.
   *
   * @return The value/reference of/to minimum.
   */
  public String getMinimum()
  {
    return minimum;
  }

  /**
   * Set {@link #minimum}.
   *
   * @param minimum The value to set.
   */
  protected void setMinimum( final String minimum )
  {
    this.minimum = minimum;
  }

  /**
   * Returns {@link #maximum}.
   *
   * @return The value/reference of/to maximum.
   */
  public String getMaximum()
  {
    return maximum;
  }

  /**
   * Set {@link #maximum}.
   *
   * @param maximum The value to set.
   */
  protected void setMaximum( final String maximum )
  {
    this.maximum = maximum;
  }

  /**
   * Returns {@link #increment}.
   *
   * @return The value/reference of/to increment.
   */
  public int getIncrement()
  {
    return increment;
  }
  
  /**
   * Set {@link #increment}.
   *
   * @param increment The value to set.
   */
  protected void setIncrement( final int increment )
  {
    this.increment = increment;
  }
  
  /**
   * Returns {@link #cyclePolicy}.
   *
   * @return The value/reference of/to cyclePolicy.
   */
  public String getCyclePolicy()
  {
    return cyclePolicy;
  }
  
  /**
   * Set {@link #cyclePolicy}.
   *
   * @param cyclePolicy The value to set.
   */
  protected void setCyclePolicy( final String cyclePolicy )
  {
    this.cyclePolicy = cyclePolicy;
  }
}
