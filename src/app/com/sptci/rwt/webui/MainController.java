package com.sptci.rwt.webui;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.FloatingPane;

import com.sptci.echo2.Application;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.Controller;
import com.sptci.echo2.View;

import com.sptci.rwt.Category;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.ConnectionParameters;
import com.sptci.rwt.Connections;
import com.sptci.rwt.DBMSAnalyser;
import com.sptci.rwt.DBMSMetaData;
import com.sptci.rwt.DatabaseType;
import com.sptci.rwt.Queries;
import com.sptci.rwt.Query;

/**
 * The primary <code>ContentPane</code> for the application.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-29
 * @version $Id: MainController.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class MainController extends Controller<MainView>
{
  /**
   * The property name used to identify the connection manager for the
   * session.
   *
   * {@value}
   */
  public static final String CONNECTION_MANAGER = "connectionManager";

  /**
   * The property name used to identify the instance of this class stored
   * for the session.
   *
   * {@value}
   */
  public static final String CONTROLLER = "controller";

  /** The object that represents saved connections. */
  private Connections connections;

  /** The object that represents saved queries. */
  private Queries queries;

  /**
   * Create a new instance of the controller for the specified view.
   *
   * @param view The view this controller controls.
   */
  public MainController( final MainView view )
  {
    super( view );
    Application.getApplication().setProperty( CONTROLLER, this );

    Principal principal = Application.getApplication().getPrincipal();
    String user = ( (principal == null ) ? "default" : principal.getName() );
    connections = Connections.getInstance( user );
    queries = Queries.getInstance( user );
  }

  /**
   * Return the instance stored for the session.
   *
   * <p><b>Note:</b> This method should be invoked only after being
   * instantiated.</p>
   *
   * @return The controller for the session.
   */
  public static MainController getController()
  {
    return (MainController)
      Application.getApplication().getProperty( CONTROLLER );
  }

  /**
   * Get the connection manager in use by the session.
   *
   * @return The manager in use.
   */
  public ConnectionManager getConnectionManager()
  {
    return (ConnectionManager) application.getProperty( CONNECTION_MANAGER );
  }

  /**
   * Set the connection manager to use for the session.
   *
   * @param manager The manager to use.
   */
  public void setConnectionManager( final ConnectionManager manager )
  {
    application.setProperty( CONNECTION_MANAGER, manager );
    getView().refresh();
  }

  /**
   * Retrieve all the available datasources configured under the
   * <code>java:/comp/env</code> context.
   *
   * @return The collection of configured datasource names.
   */
  public Collection<String> getDataSources()
  {
    Collection<String> collection = new ArrayList<String>();

    try
    {
      Context context = new InitialContext();
      addBinding( "java:/comp/env", context, collection );
    }
    catch ( Throwable t )
    {
      processFatalException( Configuration.getString(
            this, "dataSourceError" ), t );
    }

    return collection;
  }

  private void addBinding( final String name, final Context environment,
      final Collection<String> collection ) throws javax.naming.NamingException
  {
    for ( NamingEnumeration<Binding> enumeration =
        environment.listBindings( name ); enumeration.hasMore(); )
    {
      Binding binding = enumeration.nextElement();
      final String resource = name + "/" + binding.getName();

      if ( environment.lookup( resource ) instanceof javax.sql.DataSource )
      {
        collection.add( resource );
      }
      else addBinding( resource, environment, collection );
    }
  }

  /**
   * Retrieve all the saved database connections configured by the user.
   *
   * @return A {@link java.util.Map} containing the database name as the
   *   <code>key</code> and a {@link java.util.Collection} of saved
   *   connection names as the <code>value</code>.
   */
  public Map<String,Collection<String>> getSavedConnections()
  {
    Map<String,Collection<String>> map = new TreeMap<String,Collection<String>>();
    
    for ( DatabaseType dt : connections.getDatabaseTypes() )
    {
      ArrayList<String> list = new ArrayList<String>();
      list.addAll( dt.getNames() );
      map.put( dt.getName(), list );
    }

    return map;
  }

  /**
   * Create a connection parameters bean using the values in the {@link
   * ConnectionDialogue}.
   *
   * @return The new connection parameters bean.
   */
  public ConnectionParameters getParameters(
      final ConnectionDialogue dialogue )
  {
    final String userName = dialogue.getUserName();
    final String password = dialogue.getPassword();
    final String host = dialogue.getHost();
    final int port = dialogue.getPort();
    final String database = dialogue.getDatabase();
    final String databaseType = dialogue.getDatabaseType();
    final String urlPattern = Configuration.getString(
        dialogue, databaseType + ".urlPattern" );
    final String driver = Configuration.getString(
        dialogue, databaseType + ".driver" );

    return new ConnectionParameters( userName, password, host, port,
        database, databaseType, urlPattern, driver );
  }

  /**
   * Retrieve the metadata about the database pointed to by the current
   * active {@link com.sptci.rwt.ConnectionManager}.
   *
   * @return The meta data value object for the database.  Returns
   *   <code>null</code> if no active connection manager exists for the
   *   application.
   */
  public DBMSMetaData getDBMSMetaData()
  {
    DBMSMetaData metadata = null;
    ConnectionManager manager = getConnectionManager();
    if ( manager == null )
    {
      return null;
    }

    try
    {
      DBMSAnalyser analyser = new DBMSAnalyser( manager );
      metadata = analyser.analyse();
    }
    catch ( Throwable t )
    {
      processFatalException(
          Configuration.getString( this, "dbmsMetaData" ), t );
    }

    return metadata;
  }

  /**
   * Method used to update the content of {@link MainView#right} content
   * area.
   *
   * @param component The component that is to be displayed.
   */
  public void setContent( final Component component )
  {
    getView().setContent( component );
  }

  /**
   * Convenient method for {@link com.sptci.echo2.Application#addPane}.
   */
  public void addPane( final FloatingPane pane )
  {
    application.addPane( pane );
  }
  
  /**
   * Returns {@link #connections}.
   *
   * @return The value/reference of/to connections.
   */
  public Connections getConnections()
  {
    return connections;
  }
  
  /**
   * Returns {@link #queries}.
   *
   * @return The value/reference of/to queries.
   */
  public Queries getQueries()
  {
    return queries;
  }
  
  /**
   * Returns {@link com.sptci.rwt.Queries#getCategories}.
   *
   * @return The value/reference of/to queries.
   */
  public Collection<Category> getCategories()
  {
    return queries.getCategories();
  }

  /**
   * Rebuild the {@link MainView#menuComponent} to reflect modifications
   * made to the application persistent state.  Persistent state is
   * modified when new connections/queries are added to the persistent
   * state.
   */
  public void resetMenu()
  {
    view.rebuildMenu();
  }

  /**
   * Convenience method that invokes {@link
   * com.sptci.echo2.Application#getParentView}
   *
   * @return The parent view of the specified component.
   */
  public View getParentView( Component component )
  {
    return application.getParentView( component );
  }

  /**
   * Sets the focussed component for the application.
   *
   * @see nextapp.echo2.app.ApplicationInstance#setFocusedComponent
   * @param component The component that is to gain the focus.
   */
  public void setFocused( Component component )
  {
    application.setFocusedComponent( component );
  }
}
