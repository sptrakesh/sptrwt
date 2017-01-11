package com.sptci.rwt.webui;

import nextapp.echo2.app.event.ActionEvent;

import com.sptci.echo2.Listener;
import com.sptci.rwt.ConnectionData;
import com.sptci.rwt.DatabaseType;

/**
 * Action listener for launching a {@link ConnectionDialogue} initialised
 * with the the parameters in a saved connection.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-14
 * @version $Id: SavedConnectionListener.java 3616 2007-10-14 20:16:46Z rakesh $
 */
public class SavedConnectionListener extends Listener<MainController>
{
  /** The type of database to connect to. */
  private final DatabaseType databaseType;

  /** The name of the saved connection. */
  private final String name;

  /**
   * Create a new instance of the listener using the specified query and
   * controller.
   *
   * @param databaseType The {@link #databaseType} value to use.
   * @param controller The controller to use to interact with the
   *   application.
   */
  public SavedConnectionListener( final DatabaseType databaseType,
      final String name, final MainController controller )
  {
    super( controller );
    this.databaseType = databaseType;
    this.name = name;
  }

  /**
   * The action listener implementation.  Executes the query and exports
   * the results.
   *
   * @see #createDialogue
   * @param event The event that triggers the export process.
   */
  public void actionPerformed( ActionEvent event )
  {
    ConnectionDialogue dialogue = createDialogue();
    controller.addPane( dialogue );
  }

  /**
   * Create the {@link ConnectionDialogue} and initialise its components
   * with the values in the saved connection.
   *
   * @return The properly initialised connection dialogue.
   */
  private ConnectionDialogue createDialogue()
  {
    ConnectionData data = databaseType.getConnectionData( name );

    ConnectionDialogue dialogue = new ConnectionDialogue( controller );
    dialogue.setDatabaseType( databaseType.getName() );
    dialogue.setHost( data.getHost() );
    dialogue.setPort( data.getPort() );
    dialogue.setDatabase( data.getDatabase() );
    dialogue.setUserName( data.getUserName() );
    dialogue.setPassword( data.getPassword() );

    return dialogue;
  }
}
