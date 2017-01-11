package com.sptci.rwt.webui.model;

import java.util.Collection;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.GroupBox;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;

import com.sptci.rwt.ColumnMetaData;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.MetaData;
import com.sptci.rwt.ForeignKeyMetaData;
import com.sptci.rwt.PrimaryKeyMetaData;
import com.sptci.rwt.SchemaMetaData;
import com.sptci.rwt.TableAnalyser;
import com.sptci.rwt.TableMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.TableMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: TableView.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class TableView extends AbstractView
{
  /** The meta data object whose details are to be displayed. */
  private final TableMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @param metaData The {@link #metaData} model object to use.
   */
  public TableView( final TableMetaData metaData )
  {
    this.metaData = metaData;
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createReferences
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createReferences() );
  }

  /**
   * Create the component used to display the details for the table.
   * 
   * @see #createLabels
   * @see #createNumberOfRows
   * @return The component that displays the table information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "columns", metaData, grid );
    createLabels( "primaryKey", metaData, grid );
    createLabels( "foreignKeys", metaData, grid );
    createLabels( "indices", metaData, grid );
    createLabels( "triggers", metaData, grid );
    createLabels( "comment", metaData, grid );
    createNumberOfRows( grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }
  
  /**
   * Create the component used to display the total number of rows in
   * the table.
   * 
   * @since Version 1.3
   * @param parent The container component to which the components are
   *   to be added.
   */
  protected void createNumberOfRows( final Component parent )
  {
    parent.add( Utilities.createLabel(
        getClass().getName(), "numberOfRows", "Title.Label" ) );
    int numberOfRows = metaData.getNumberOfRows();
    
    try
    {
      ConnectionManager manager = (ConnectionManager) 
        Application.getApplication().getProperty(
            MainController.CONNECTION_MANAGER );
      TableAnalyser analyser = new TableAnalyser( manager );
      numberOfRows = analyser.getNumberOfRows( metaData );
    }
    catch ( Throwable t )
    {
      Application.getApplication().processFatalException(
          Configuration.getString( this, "error.numberOfRows" ), t );
    }

    Button button = new Button( String.valueOf( numberOfRows ) );
    button.setToolTipText(
        Configuration.getString( this, "numberOfRows.tooltip" ) );
    button.setStyleName( "Link.Button" );
    button.addActionListener( new TableTypeDataListener(
        MainController.getController(), metaData ) );
    parent.add( button );
  }
  
  /**
   * Create the component used to display the foreign key references to
   * this table.
   * 
   * @since Version 1.1
   * @see #addColumn
   * @see #addCatalogue
   * @see #addSchema
   * @see #addTable
   * @see #addReferencingColumn
   * @see #addForeignKeyName
   * @see #addUpdateRule
   * @see #addDeleteRule
   * @see #addDeferrability
   * @return The component that displays the references information.
   */
  protected Component createReferences()
  {
    Component box = new Label();
    
    try
    {
      ConnectionManager manager = (ConnectionManager) 
        Application.getApplication().getProperty(
          MainController.CONNECTION_MANAGER );
      TableAnalyser analyser = new TableAnalyser( manager );
      Collection<ForeignKeyMetaData> collection =
        analyser.getExportedKeys( metaData );
      
      if ( collection.size() > 0 )
      {
        box =
          new GroupBox( Configuration.getString( this, "references" ) );
        Grid grid = new Grid( collection.size() + 1 );
        addColumn( collection, grid );
        addCatalogue( collection, grid );
        addSchema( collection, grid );
        addTable( collection, grid );
        addReferencingColumn( collection, grid );
        addForeignKeyName( collection, grid );
        addUpdateRule( collection, grid );
        addDeleteRule( collection, grid );
        addDeferrability( collection, grid );
  
        box.add( grid );
      }
    }
    catch ( Throwable t )
    {
      Application.getApplication().processFatalException(
          Configuration.getString( this, "error.references" ), t );
    }

    return box;
  }

  /**
   * Create standard {@link nextapp.echo2.app.Label} components that
   * represent the name of the specified field and the value in the
   * specified model.  Over-ridden to invoke the {@link
   * java.util.Collection#size} method instead of just the accessor in
   * the model.
   *
   * @param name The name of the field.
   * @param metaData The model object.
   * @param parent The container component to which the labels are to
   *   be added.
   */
  @Override
  protected void createLabels( final String name, final MetaData metaData,
      final Component parent )
  {
    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    try
    {
      parent.add( Utilities.createLabel(
            getClass().getName(), name, "Title.Label" ) );

      Object object = ReflectionUtility.execute( metaData, method );
      if ( object instanceof Collection )
      {
        Collection collection = (Collection) object;
        int size = collection.size();
        parent.add( new Label(
               ( ( size == 0 ) ? "Not loaded or 0" : String.valueOf( size ) ) ) );
      }
      else if ( object instanceof PrimaryKeyMetaData )
      {
        PrimaryKeyMetaData pkmd = (PrimaryKeyMetaData) object;
        if ( object != null )
        {
          int size = pkmd.getColumns().size();
          parent.add( new Label( ( ( size == 0 ) ?
                  "Not loaded or 0" : String.valueOf( size ) ) ) );
        }
      }
      else
      {
        parent.add( new Label( "Unknown" ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( method, metaData.getClass().getName(), t );
    }
  }
  
  /**
   * Add the column names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addColumn(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.column", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getReferencedTable() ) );
    }
  }

  /**
   * Add the catalogue names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addCatalogue(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.catalogue", "Title.Label" ) );
        first = false;
      }

      SchemaMetaData smd = (SchemaMetaData) fkmd.getTable().getRoot();
      parent.add( new Label( smd.getCatalogue().getName() ) );
    }
  }
  
  /**
   * Add the schema names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addSchema(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.schema", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getTable().getRoot().getName() ) );
    }
  }
  
  /**
   * Add the table names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addTable(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.table", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getTable().getName() ) );
    }
  }
  
  /**
   * Add the referencing column names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addReferencingColumn(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.refcolumn", "Title.Label" ) );
        first = false;
      }
      
      for ( ColumnMetaData cmd : fkmd.getColumns() )
      {
        parent.add( new Label( cmd.getName() ) );
      }
    }
  }
  
  /**
   * Add the foreign key names in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addForeignKeyName(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.fkname", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getName() ) );
    }
  }
  
  /**
   * Add the update rules in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addUpdateRule(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.updateRule", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getUpdateRule() ) );
    }
  }
  
  /**
   * Add the update rules in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addDeleteRule(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.deleteRule", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getDeleteRule() ) );
    }
  }
  
  /**
   * Add the update rules in the collection to the component
   * 
   * @since Version 1.1
   * @param collection The collection of {@link 
   *   com.sptci.rwt.ForeignKeyMetaData} values.
   * @param parent The parent component to which the details are to be
   *   added.
   */
  protected void addDeferrability(
      final Collection<ForeignKeyMetaData> collection,
      final Component parent )
  {
    boolean first = true;
    for ( ForeignKeyMetaData fkmd : collection )
    {
      if ( first )
      {
        parent.add( Utilities.createLabel( getClass().getName(),
            "reference.deferrability", "Title.Label" ) );
        first = false;
      }
      
      parent.add( new Label( fkmd.getDeferrability() ) );
    }
  }
}
