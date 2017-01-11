package com.sptci.rwt.webui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;

import nextapp.echo2.app.filetransfer.Download;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Listener;

/**
 * Action listener for exporting the results of a SQL statement to
 * Excel.  This uses the
 * <a href='http://poi.apache.org/hssf/index.html'>Apache POI HSSF</a>
 * library for creating Excel workbooks.
 *
 * @see ExcelDownloadProvider
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-05
 * @version $Id: ExportListener.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ExportListener extends Listener<MainController>
{
  /**
   * Create a new instance of the listener using the specified controller.
   *
   * @param controller The controller to use to interact with the
   *   application.
   */
  public ExportListener( final MainController controller )
  {
    super( controller );
  }

  /**
   * The action listener implementation.  Executes the query and exports
   * the results.
   *
   * @see #checkView
   * @param event The event that triggers the export process.
   */
  public void actionPerformed( ActionEvent event )
  {
    Button button = (Button) event.getSource();
    button.setEnabled( false );

    try
    {
      final ExecutorView view =
        (ExecutorView) getController().getParentView( button );
    if ( ! checkView( view ) ) return;

      final ExcelDownloadProvider provider = new ExcelDownloadProvider(
          view.getQuery(), getController().getConnectionManager() );

      final Download download = new Download();
      download.setProvider( provider );
      download.setActive( true );
      getController().getApplication().enqueueCommand( download );
    }
    catch ( Throwable t )
    {
      getController().processFatalException( Configuration.getString(
            ExcelDownloadProvider.class, "error" ), t );
    }
    finally
    {
      button.setEnabled( true );
    }
  }

  /**
   * Check the {@link QueryExecutorView} to ensure that a valid excel
   * workbook can be generated.  Ensures that some SQL statement has been
   * entered into {@link QueryExecutorView#query}.
   *
   * @return Returns <code>true</code> if some text has been entered.
   */
  private boolean checkView( final ExecutorView view )
  {
    return ( view.getQuery().length() > 0 );
  }
}
