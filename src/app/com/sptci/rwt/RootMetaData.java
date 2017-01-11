package com.sptci.rwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An asbtract base class that represents objects that serve as the root
 * of the database.  Database engines generally support two kinds of root
 * objects:
 *
 * <ol>
 *   <li>Catalog</li>
 *   <li>Schema</li>
 * </ol>
 *
 * <p>Schema objects may belong to a catalog or exist on their own.</p>
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-23
 * @version $Id: RootMetaData.java 3649 2007-10-25 00:15:24Z rakesh $
 * @since Version 1.1
 */
public abstract class RootMetaData extends MetaData
{
  /** A reference to the meta data object for the database. */
  private DBMSMetaData dbmsMetaData;

  /**
   * The collection of {@link TableMetaData} objects that represent all the
   * tables available in this schema.
   */
  private Collection<TableMetaData> tables = new ArrayList<TableMetaData>();
  
  /**
   * The collection of {@link ViewMetaData} objects that represent all the
   * views available in this schema.
   */
  private Collection<ViewMetaData> views = new ArrayList<ViewMetaData>();
  
  /**
   * The collection of {@link TriggerMetaData} objects that represent all
   * the triggers available in this schema.
   */
  private Collection<TriggerMetaData> triggers =
    new ArrayList<TriggerMetaData>();
  
  /**
   * The collection of {@link ProcedureMetaData} objects that represent all
   * the procedures available in this schema.
   */
  private Collection<ProcedureMetaData> procedures =
    new ArrayList<ProcedureMetaData>();
  
  /**
   * The collection of {@link SequenceMetaData} objects that represent all
   * the sequences available in this schema.
   */
  private Collection<SequenceMetaData> sequences =
    new ArrayList<SequenceMetaData>();
  
  /**
   * Returns {@link #dbmsMetaData}.
   *
   * @return The value/reference of/to dbmsMetaData.
   */
  public DBMSMetaData getDbmsMetaData()
  {
    return dbmsMetaData;
  }
  
  /**
   * Set {@link #dbmsMetaData}.
   *
   * @param dbmsMetaData The value to set.
   */
  protected void setDbmsMetaData( final DBMSMetaData dbmsMetaData )
  {
    this.dbmsMetaData = dbmsMetaData;
  }
  
  /**
   * Returns {@link #tables}.
   *
   * @return The value/reference of/to tables.
   */
  public Collection<TableMetaData> getTables()
  {
    return Collections.unmodifiableCollection( tables );
  }
  
  /**
   * Set {@link #tables}.
   *
   * @param tables The value to set.
   */
  protected void setTables( final Collection tables )
  {
    this.tables.clear();
    this.tables.addAll( tables );
  }
  
  /**
   * Returns {@link #views}.
   *
   * @return The value/reference of/to views.
   */
  public Collection<ViewMetaData> getViews()
  {
    return Collections.unmodifiableCollection( views );
  }
  
  /**
   * Set {@link #views}.
   *
   * @param views The value to set.
   */
  protected void setViews( final Collection views )
  {
    this.views.clear();
    this.views.addAll( views );
  }
  
  /**
   * Returns {@link #triggers}.
   *
   * @return The value/reference of/to triggers.
   */
  public Collection<TriggerMetaData> getTriggers()
  {
    return Collections.unmodifiableCollection( triggers );
  }
  
  /**
   * Set {@link #triggers}.
   *
   * @param triggers The value to set.
   */
  protected void setTriggers( final Collection triggers )
  {
    this.triggers.clear();
    this.triggers.addAll( triggers );
  }
  
  /**
   * Returns {@link #procedures}.
   *
   * @return The value/reference of/to procedures.
   */
  public Collection<ProcedureMetaData> getProcedures()
  {
    return Collections.unmodifiableCollection( procedures );
  }
  
  /**
   * Set {@link #procedures}.
   *
   * @param procedures The value to set.
   */
  protected void setProcedures( final Collection procedures )
  {
    this.procedures.clear();
    this.procedures.addAll( procedures );
  }
  
  /**
   * Returns {@link #sequences}.
   *
   * @return The value/reference of/to sequences.
   */
  public Collection getSequences()
  {
    return Collections.unmodifiableCollection( sequences );
  }
  
  /**
   * Set {@link #sequences}.
   *
   * @param sequences The value to set.
   */
  protected void setSequences( final Collection sequences )
  {
    this.sequences.clear();
    this.sequences.addAll( sequences );
  }
}
