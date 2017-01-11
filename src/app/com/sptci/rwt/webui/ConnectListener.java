package com.sptci.rwt.webui;

import java.util.ArrayList;
import java.util.Collection;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.event.ActionEvent;

import com.sptci.ReflectionUtility;
import com.sptci.echo2.Configuration;
import com.sptci.echo2.ErrorPane;
import com.sptci.echo2.Listener;
import com.sptci.rwt.ConnectionManager;
import com.sptci.rwt.ConnectionParameters;

/**
 * The dialogue used to initiate a new JDBC {@link java.sql.Connection}.
 * Also used to save/edit pre-configured connections.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-08
 * @version $Id: ConnectListener.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ConnectListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public ConnectListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Initiates a connection to the
   * database using the specified parameters.
   *
   * @see #checkView
   * @see #displayMessages
   * @see MainController#getParameters
   * @param event The event that was triggered.
   */
  public void actionPerformed( ActionEvent event )
  {
    final ConnectionDialogue dialogue = (ConnectionDialogue)
      controller.getParentView( (Component) event.getSource() );

    Collection<String> messages = checkView( dialogue );
    if ( messages.isEmpty() )
    {
      final ConnectionParameters parameters =
        controller.getParameters( dialogue );
      
      final ConnectionManager manager = new ConnectionManager( parameters );
      controller.setConnectionManager( manager );
      dialogue.userClose();
    }
    else
    {
      displayMessages( messages );
    }
  }

  /**
   * Check the {@link ConnectionDialogue} to ensure that all the required
   * information has been entered to fetch a connection.
   *
   * @see #checkString
   * @return Collection<String> Return a collection of error messages.
   *   Returns an empty collection if no errors were found.
   */
  protected Collection<String> checkView( final ConnectionDialogue dialogue )
  {
    Collection<String> messages = new ArrayList<String>();

    if ( dialogue.getDatabaseType() == null )
    {
      messages.add( Configuration.getString( this, "noDatabaseType" ) );
    }

    checkString( "Host", dialogue, messages );

    if ( dialogue.getPort() == 0 )
    {
      messages.add( Configuration.getString( this, "noPort" ) );
    }

    checkString( "Database", dialogue, messages );
    checkString( "UserName", dialogue, messages );

    return messages;
  }

  /**
   * Check the field represented by the specified name.
   *
   * @param name The name to use to find the appropriate field to test.
   * @param dialogue The view component that is to be tested.
   * @param messages The collection of messages that is to be updated as
   *   appropriate.
   */
  protected void checkString( final String name,
      final ConnectionDialogue dialogue, final Collection<String> messages )
  {
    final String method = "get" + name;

    try
    {
      String value = (String) ReflectionUtility.execute( dialogue, method );

      if ( value.length() == 0 )
      {
        messages.add( Configuration.getString( this, "no" + name ) );
      }
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( this, "methodError" );
      message = message.replaceAll( "\\$method\\$", method );
      message = message.replaceAll( "\\$class\\$", dialogue.getClass().getName() );
      controller.processFatalException( message, t );
    }
  }

  /**
   * Display an {@link com.sptci.echo2.ErrorPane} with the contents of the
   * specified collection of messages.
   *
   * @param messages The collection of messages to display.
   */
  protected void displayMessages( final Collection<String> messages )
  {
    StringBuilder builder = new StringBuilder();
    for ( String message : messages )
    {
      builder.append( message ).append( "<br/>" );
    }

    ErrorPane pane = new ErrorPane(
        Configuration.getString( this, "title" ),
        builder.toString() );
    controller.addPane( pane );
  }
}
