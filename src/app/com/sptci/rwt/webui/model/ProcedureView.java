package com.sptci.rwt.webui.model;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;

import echopointng.DirectHtml;
import echopointng.GroupBox;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;

import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.ProcedureAnalyser;
import com.sptci.rwt.ProcedureMetaData;
import com.sptci.rwt.webui.MainController;

/**
 * A view component used to display the information contained in
 * {@link com.sptci.rwt.ProcedureMetaData}.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-07
 * @version $Id: ProcedureView.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ProcedureView extends SourceView
{
  /** The meta data object whose details are to be displayed. */
  private final ProcedureMetaData metaData;

  /**
   * Create a new instance of the view using the specified model object.
   *
   * @see #initMetaData
   * @param metaData The {@link #metaData} model object to use.
   */
  public ProcedureView( final ProcedureMetaData metaData )
  {
    this.metaData = metaData;
    initMetaData();
  }

  /**
   * Lifecycle method used to initialise component when added to a
   * container hierarchy.
   *
   * @see #createDetails
   * @see #createSource
   */
  @Override
  public void init()
  {
    removeAll();
    add( createDetails() );
    add( createSource( "body" ) );
    add( createSource( "definition" ) );
  }

  /**
   * Create the component used to display the details of the index.
   *
   * @see #createLabels
   * @return The component that displays the index information.
   */
  protected Component createDetails()
  {
    Grid grid = new Grid();

    createLabels( "name", metaData, grid );
    createLabels( "dataType", metaData, grid );
    createLabels( "userDefinedType", metaData, grid );

    GroupBox box = new GroupBox( Configuration.getString( this, "title" ) );
    box.add( grid );
    return box;
  }

  /**
   * Create the component used to display the SQL statement that is executed
   * by the trigger.
   *
   * @see #toHtml
   * @see #syntaxHighlight
   * @param name The name of the field to fetch.
   * @return The component that displays the SQL statement
   */
  protected Component createSource( final String name )
  {
    final String method = "get" + name.substring( 0, 1 ).toUpperCase() +
      name.substring( 1 );

    try
    {
      String body = (String) ReflectionUtility.execute( metaData, method );
      if ( body == null ) return new Label( "" );

      body = syntaxHighlight( toHtml( body ) );

      GroupBox box = new GroupBox(
          Configuration.getString( this, name + ".title" ) );
      box.add( new DirectHtml( body ) );
      return box;
    }
    catch ( Throwable t )
    {
      processFatalException( method, metaData.getClass().getName(), t );
    }

    return new Label( "" );
  }

  /**
   * Perform additional initialisation of the {@link #metaData} object.
   * 
   * @since Version 1.1
   * @see com.sptci.rwt.ProcedureAnalyser#getAdditionalAttributes
   */
  protected void initMetaData()
  {
    ConnectionManager manager = (ConnectionManager) 
      Application.getApplication().getProperty(
        MainController.CONNECTION_MANAGER );
    ProcedureAnalyser analyser = new ProcedureAnalyser( manager );
    analyser.getAdditionalAttributes( metaData );
  }
}
