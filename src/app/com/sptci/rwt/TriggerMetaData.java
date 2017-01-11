package com.sptci.rwt;

/**
 * A metadata object that represents trigger objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: TriggerMetaData.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class TriggerMetaData extends MetaData
{
  /** An enumeration of event types that cause a trigger to fire. */
  public enum Event { Insert, Update, Delete, InsertOrUpdate,
    InsertOrDelete, UpdateOrDelete, InsertOrUpdateOrDelete };

  /** An enumeration of <code>action_orientation</code> values. */
  public enum Orientation { Row, Statement };

  /** An enumeration of timing values for trigger execution. */
  public enum Timing { Before, After };

  /** The table on which the trigger operates. */
  private String table;

  /**
   * The catalogue to which the table on which the tigger operates belongs.
   * This may be different from the catalogue in which the trigger exists.
   */
  private String tableCatalogue;

  /**
   * The schema to which the table on which the tigger operates belongs.
   * This may be different from the schema in which the trigger exists.
   */
  private String tableSchema;

  /** The event that fires the trigger. */
  private Event event;

  /** The statement that is executed by the trigger. */
  private String statement;

  /**
   * Identifies whether the trigger fires once for each processed row or
   * once for each statement.
   */
  private Orientation orientation;

  /** The time the trigger fires. */
  private Timing timing;

  /** A reference to the catalogue/schema in which this trigger exists. */
  private RootMetaData root;

  /**
   * A reference to the table on which this trigger executes.  This will
   * be populated only if the {@link #table} on which the trigger executes
   * belongs to the same schema.
   */
  private TableMetaData tableMetaData;
  
  /**
   * Returns {@link #table}.
   *
   * @return The value/reference of/to table.
   */
  public String getTable()
  {
    return table;
  }
  
  /**
   * Set {@link #table}.
   *
   * @param table The value to set.
   */
  protected void setTable( final String table )
  {
    this.table = table;
  }
  
  /**
   * Returns {@link #tableCatalogue}.
   *
   * @return The value/reference of/to tableCatalogue.
   */
  public String getTableCatalogue()
  {
    return tableCatalogue;
  }
  
  /**
   * Set {@link #tableCatalogue}.
   *
   * @param tableCatalogue The value to set.
   */
  protected void setTableCatalogue( final String tableCatalogue )
  {
    this.tableCatalogue = tableCatalogue;
  }
  
  /**
   * Returns {@link #tableSchema}.
   *
   * @return The value/reference of/to tableSchema.
   */
  public String getTableSchema()
  {
    return tableSchema;
  }
  
  /**
   * Set {@link #tableSchema}.
   *
   * @param tableSchema The value to set.
   */
  protected void setTableSchema( final String tableSchema )
  {
    this.tableSchema = tableSchema;
  }
  
  /**
   * Returns {@link #event}.
   *
   * @return The value/reference of/to event.
   */
  public String getEvent()
  {
    return ( ( event != null ) ? event.toString() : "" );
  }
  
  /**
   * Set {@link #event}.
   *
   * @param event The value to set.
   */
  protected void setEvent( final Event event )
  {
    this.event = event;
  }
  
  /**
   * Returns {@link #statement}.
   *
   * @return The value/reference of/to statement.
   */
  public String getStatement()
  {
    return statement;
  }
  
  /**
   * Set {@link #statement}.
   *
   * @param statement The value to set.
   */
  protected void setStatement( final String statement )
  {
    this.statement = statement;
  }
  
  /**
   * Returns {@link #orientation}.
   *
   * @return The value/reference of/to orientation.
   */
  public String getOrientation()
  {
    return ( ( orientation != null ) ? orientation.toString() : "" );
  }
  
  /**
   * Set {@link #orientation}.
   *
   * @param orientation The value to set.
   */
  protected void setOrientation( final Orientation orientation )
  {
    this.orientation = orientation;
  }
  
  /**
   * Returns {@link #timing}.
   *
   * @return The value/reference of/to timing.
   */
  public String getTiming()
  {
    return ( ( timing != null ) ? timing.toString() : "" );
  }
  
  /**
   * Set {@link #timing}.
   *
   * @param timing The value to set.
   */
  protected void setTiming( final Timing timing )
  {
    this.timing = timing;
  }
  
  /**
   * Returns {@link #root}.
   *
   * @return The value/reference of/to root.
   */
  public RootMetaData getRoot()
  {
    return root;
  }
  
  /**
   * Set {@link #root}.
   *
   * @param root The value to set.
   */
  protected void setRoot( final RootMetaData root )
  {
    this.root = root;
  }
  
  /**
   * Returns {@link #tableMetaData}.
   *
   * @return The value/reference of/to tableMetaData.
   */
  public TableMetaData getTableMetaData()
  {
    return tableMetaData;
  }
  
  /**
   * Set {@link #tableMetaData}.
   *
   * @param tableMetaData The value to set.
   */
  protected void setTableMetaData( final TableMetaData tableMetaData )
  {
    this.tableMetaData = tableMetaData;
  }
}
