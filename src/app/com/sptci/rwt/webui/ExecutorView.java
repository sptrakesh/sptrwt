package com.sptci.rwt.webui;

import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionListener;

import consultas.echo2consultas.LiveTextField;

import echopointng.PopUp;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Dimensions;
import com.sptci.echo2.Utilities;
import com.sptci.echo2.WindowPane;
import com.sptci.echo2.style.Extent;
import com.sptci.rwt.ConnectionManager;

/**
 * An abstract base class for query executor view components used to
 * interact with the {@link com.sptci.rwt.AbstractQueryExecutor} class.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-12
 * @version $Id: ExecutorView.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public abstract class ExecutorView extends WindowPane
{
  /** The component used to enter the SQL statement to execute. */
  protected TextArea query;
  
  /**
   * The component used to specify the maximum number of rows of data
   * to retrieve for <code>select</code> statements.
   */
  protected LiveTextField maxResults;
  
  /**
   * The component used to specify the maximum number of characters to
   * display in a table column.
   * 
   * @since Version 1.3
   */
  protected LiveTextField maxColumnLength;

  /**
   * The component used to display the results after executing a statement.
   */
  protected Column results;
  
  /** Parent Controller. */
  protected final MainController controller;

  /** The connection manager to use to interact with the database. */
  protected final ConnectionManager connectionManager;

  /** The cache of previously executed statements in this view. */
  protected final Map<String,String> history =
    new LinkedHashMap<String,String>();

  /**
   * Create instance of the pane using the specified controller.
   *
   * @param controller The controller to use to interact with the rest of the
   *   application.
   */
  protected ExecutorView( final MainController controller )
  {
    this.controller = controller;
    this.connectionManager = controller.getConnectionManager();
    setTitle( getTitle() + " : " + connectionManager.getTitle() );
  }

  /**
   * Create the UI components that are necessary for the pane.
   *
   * @see #createQuery
   * @see #createControls
   */
  @Override
  public void init()
  {
    removeAll();
    SplitPane pane = new SplitPane();
    pane.setStyleName( getClass().getName() + ".splitPane" );

    Column column = new Column();

    column.add( createQuery() );
    column.add( createControls() );
    pane.add( column );

    results = new Column();
    pane.add( results );

    add( pane );
  }

  /**
   * Initialise the {@link #query} component.
   *
   * @return The properly initialised component.
   */
  protected Component createQuery()
  {
    query = new TextArea();
    query.setHeight( Extent.getInstance(
          Dimensions.getInt( this, "query.height" ) ) );
    query.setStyleName( "Query.TextComponent" );
    controller.setFocused( query );
    return query;
  }

  /**
   * Initialise the {@link #maxResults} component.
   *
   * @see #createLiveTextField
   * @param row The component to which the {@link #maxResults} component
   *   and its associated label is to be added.
   */
  protected void createMaxResults( final Component row )
  {
    maxResults = createLiveTextField( "maxResults", row );
  }
  
  /**
   * Initialise the {@link #maxColumnLength} component.
   *
   * @since Version 1.3
   * @see #createLiveTextField
   * @param row The component to which the {@link #maxColumnLength} component
   *   and its associated label is to be added.
   */
  protected void createMaxColumnLength( final Component row )
  {
    maxColumnLength = createLiveTextField( "maxColumnLength", row );
    maxColumnLength.setText( String.valueOf(
        Dimensions.getInt( this, "maxColumnLength" ) ) );
  }
  
  /**
   * Create a numeric text field with the specified name.
   * 
   * @since Version 1.3
   * @param name The name of the text field.  Used to look up localised
   *   information.
   * @param row The parent component to which the text field is to be added.
   * @return The properly initialised text field.
   */
  protected LiveTextField createLiveTextField( final String name,
      final Component row )
  {
    row.add( Utilities.createLabel(
          getClass().getName(), name, "General.Label" ) );

    LiveTextField field = new LiveTextField();
    field.setRegexp( "^\\d*$" );
    field.setWidth( Extent.getInstance(
        Dimensions.getInt( this, name + ".width" ) ) );
    field.setMaximumLength(
        Dimensions.getInt( this, name + ".maxlength" ) );

    row.add( field );
    return field;
  }

  /**
   * Create the {@link nextapp.echo2.app.Button} used to trigger execution
   * of the user entered SQL statement.
   *
   * @see #getListenerName
   * @return The button component.
   */
  protected Component createExecute()
  {
    final String className = getListenerName();
    ActionListener listener = null;

    try
    {
      listener = (ActionListener)
        ReflectionUtility.newInstance( className, controller );
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( ExecutorView.class, "error" );
      message = message.replaceAll( "\\$class\\$", className );
      controller.processFatalException( message, t );
    }

    return Utilities.createButton( getClass().getName(), "execute",
          "General.Button", listener );
  }

  /**
   * Create the {@link nextapp.echo2.app.Button} used to trigger export
   * of the results of the query to Excel.
   *
   * @return The component used to trigger the export action.
   */
  protected Component createExport()
  {
    return Utilities.createButton( getClass().getName(), "export",
          "General.Button", new ExportListener( controller ) );
  }

  /**
   * Create the {@link echopointng.PopUp} component used to display the
   * component used to capture user input on the category and name to
   * assign to a saved SQL statement.
   *
   * @return The component used to capture user input.
   */
  protected Component createSave()
  {
    PopUp popup = new PopUp();
    popup.setTarget( Utilities.createLabel( getClass().getName(),
          "saveQuery", "Save.Label" ) );
    popup.setPopUp( new SaveQueryComponent( controller ) );

    return popup;
  }

  /**
   * Create the {@link nextapp.echo2.app.Button} used to display the
   * history of statements executed in the current view.
   *
   * @return The button component.
   */
  protected Component createHistory()
  {
    return Utilities.createButton( getClass().getName(), "history",
        "General.Button", new HistoryListener( controller ) );
  }

  /**
   * Get the fully qualified class name of the action listener used to
   * execute the user entered statement.
   *
   * @return The fully qualified class name of the listener.
   */
  protected String getListenerName()
  {
    final String name = getClass().getName();
    String cls = name.substring( 0, name.lastIndexOf( "." ) );
    cls += ".Execute";
    cls += name.substring( name.lastIndexOf( "." ) + 1 );
    cls = cls.substring( 0, cls.lastIndexOf( "ExecutorView" ) );
    cls += "Listener";

    return cls;
  }

  /**
   * Return the SQL statement that was entered into {@link #query}.
   *
   * @return The sql statement that was entered.
   */
  public String getQuery()
  {
    return query.getText();
  }

  /**
   * Sets the text displayed in {@link #query} to the specified value.
   * Re-initialises {@link #query} to get around the client not getting 
   * updated if client state has been modified.
   *
   * @param text The query value to set.
   */
  public void setQuery( final String text )
  {
    final Component parent = query.getParent();
    final int index = parent.indexOf( query );
    parent.remove( index );
    parent.add( createQuery(), index );
    query.setText( text );
  }

  /**
   * Return the value entered in {@link #maxResults}.
   */
  public int getMaxResults()
  {
    return ( ( maxResults.getText().length() == 0 ) ? 0 :
        Integer.parseInt( maxResults.getText() ) );
  }
  
  /**
   * Return the value entered in {@link #maxColumnLength}.
   */
  public int getMaxColumnLength()
  {
    return ( ( maxColumnLength.getText().length() == 0 ) ? 0 :
        Integer.parseInt( maxColumnLength.getText() ) );
  }

  /**
   * Removes the results of a previous query execution.  Removes all the
   * child components of {@link #results}.
   */
  public void reset()
  {
    results.removeAll();
  }

  /**
   * Create the layout component to use to display the {@link #maxResults}
   * component and the other controls used to execute the statement.
   *
   * @return The layout component.
   */
  protected abstract Component createControls();
  
  /**
   * Returns {@link #history}.
   *
   * @return The value/reference of/to history.
   */
  public Map<String,String> getHistory()
  {
    return Collections.unmodifiableMap( history );
  }

  /**
   * Add the specified statement to {@link #history}.  Removes and re-adds
   * duplicate statements to maintain execution order.
   *
   * @param statement The statement that is to be added.
   */
  public void addToHistory( final String statement )
  {
    final String text = ( ( statement.length() > 100 ) ?
        statement.substring( 0, 100 ) + "..." : statement );

    if ( history.containsKey( text ) )
    {
      history.remove( text );
    }

    history.put( text, statement );
  }

  /**
   * Sets the text displayed in {@link #query} to the specified value
   * from {@link #history}.  The value specified is the <code>key</code>
   * in {@link #history}.  The value set will be <code>value</code>.
   *
   * @see #setQuery
   * @param key The key to use to set the statement.
   */
  public void setQueryFromHistory( final String key )
  {
    final String value = history.get( key );
    if ( value != null ) setQuery( value );
  }
  
  /**
   * Returns {@link #connectionManager}.
   *
   * @return The value/reference of/to connectionManager.
   */
  public ConnectionManager getConnectionManager()
  {
    return connectionManager;
  }
}
