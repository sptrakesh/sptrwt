package com.sptci.rwt.webui.model;

import java.util.Collection;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.DirectHtml;
import echopointng.GroupBox;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Utilities;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.MetaData;
import com.sptci.rwt.ViewAnalyser;
import com.sptci.rwt.ViewMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.ViewMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ViewView.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class ViewView extends SourceView
{
  /** The meta data object whose details are to be displayed. */
  private final ViewMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @see #initMetaData
   * @param metaData The {@link #metaData} model object to use.
   */
  public ViewView( final ViewMetaData metaData )
  {
    this.metaData = metaData;
    initMetaData();
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createDefinition
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createDefinition() );
  }

  /**
   * Create the component used to display the default limits enforced by
   * the database engine.
   *
   * @see #createLabels
   * @see #createNumberOfRows
   * @return The component that displays the limits information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "columns", metaData, grid );
    createLabels( "checkOption", metaData, grid );
    createLabels( "updatable", metaData, grid );
    createLabels( "insertable", metaData, grid );
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
      ViewAnalyser analyser = new ViewAnalyser( manager );
      numberOfRows = analyser.getNumberOfRows( metaData );
    }
    catch ( Throwable t )
    {
      Application.getApplication().processFatalException(
          Configuration.getString( this, "error.numberOfRows" ), t );
    }
    
    Button button = new Button( String.valueOf( numberOfRows ) );
    button.setStyleName( "Link.Button" );
    button.addActionListener( new TableTypeDataListener(
        MainController.getController(), metaData ) );
    parent.add( button );
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
   * @param component The container component to which the labels are to
   *   be added.
   */
  @Override
  protected void createLabels( final String name, final MetaData metaData,
      final Component component )
  {
    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    try
    {
      component.add( Utilities.createLabel(
            getClass().getName(), name, "Title.Label" ) );

      Object object = ReflectionUtility.execute( metaData, method );
      if ( object instanceof Collection )
      {
        Collection collection = (Collection) object;
        int size = collection.size();
        component.add( new Label(
               ( ( size == 0 ) ? "Not loaded or 0" : String.valueOf( size ) ) ) );
      }
      else
      {
        component.add( new Label( "Unknown" ) );
      }
    }
    catch ( Throwable t )
    {
      processFatalException( method, metaData.getClass().getName(), t );
    }
  }

  /**
   * Create the component used to display the SQL statement that was
   * executed to create the view.
   *
   * @see #toHtml
   * @see #syntaxHighlight
   * @return The component that displays the SQL statement
   */
  protected Component createDefinition()
  {
    String body = metaData.getDefinition();
    if ( body == null ) return new Label( "" );

    body = syntaxHighlight( toHtml( body ) );

    GroupBox box = new GroupBox(
        Configuration.getString( this, "definition.title" ) );
    box.add( new DirectHtml( body ) );
    return box;
  }
  
  /**
   * Perform additional initialisation of the {@link #metaData} object.
   * 
   * @since Version 1.1
   * @see com.sptci.rwt.ViewAnalyser#getAdditionalAttributes
   */
  protected void initMetaData()
  {
    ConnectionManager manager = (ConnectionManager) 
    Application.getApplication().getProperty(
        MainController.CONNECTION_MANAGER );
    ViewAnalyser analyser = new ViewAnalyser( manager );
    analyser.getAdditionalAttributes( metaData );
  }
}
