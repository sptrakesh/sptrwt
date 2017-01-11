package com.sptci.rwt.webui;

import java.io.File;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.filetransfer.UploadSelect;

import echopointng.TabbedPane;
import echopointng.tabbedpane.DefaultTabModel;

import com.sptci.echo2.Configuration;
import com.sptci.echo2.Dimensions;
import com.sptci.echo2.FileUploadListener;
import com.sptci.echo2.table.Table;
import com.sptci.echo2.table.TableNavigation;
import com.sptci.util.StringUtilities;

/**
 * Query executor view component used to send multiple SQL statements in
 * one batch to the database server.  Uses the {@link
 * com.sptci.rwt.BatchQueryExecutor} class.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-10
 * @version $Id: BatchQueryExecutorView.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class BatchQueryExecutorView extends ExecutorView
{
  /** The tabbed pane used to display the results of query execution. */
  private TabbedPane tabbedPane;

  /** The SQL statement that is to be displayed in {@link #query} when it
   * is initialised.
   */
  private String statement;

  /**
   * Create instance of the pane using the specified controller.
   *
   * @param controller The controller to use to interact with the rest of
   *   the application.
   */
  public BatchQueryExecutorView( final MainController controller )
  {
    super( controller );
  }

  /**
   * Create instance of the pane using the specified controller.
   *
   * @param controller The controller to use to interact with the rest of
   *   the application.
   * @param statement The SQL statement that is to be displayed in the
   *   {@link #query} component.
   */
  public BatchQueryExecutorView( final MainController controller,
      final String statement )
  {
    this( controller );
    this.statement = statement;
  }

  /**
   * Over-ridden to set the default value of {@link #maxResults}.
   */
  @Override
  public void init()
  {
    super.init();
    if ( statement != null ) query.setText( statement );
    maxResults.setText(
        String.valueOf( Dimensions.getInt( this, "maxRows" ) ) );
  }

  /**
   * Over-ridden to display controls relevant to this class.
   *
   * @see #createExecute
   * @see #createMaxResults
   * @see #createMaxColumnLength
   * @see #createHistory
   * @see #createSave
   * @see #createUpload
   * @return The layout component.
   */
  @Override
  protected Component createControls()
  {
    Column column = new Column();
    Row row = new Row();
    row.add( createExecute() );
    createMaxResults( row );
    createMaxColumnLength( row );
    row.add( createExport() );
    row.add( createSave() );
    row.add( createHistory() );
    column.add( row );

    row = new Row();
    row.add( createUpload() );
    column.add( row );

    return column;
  }

  /**
   * Removes the results of a previous query execution.  Removes all the
   * child components of {@link #results} and reinitialises {@link
   * #tabbedPane}.
   */
  @Override
  public void reset()
  {
    results.removeAll();
    tabbedPane = new TabbedPane();
    results.add( tabbedPane );
  }

  /**
   * Create the component used to upload SQL script files.
   *
   * @return The file upload component.
   */
  protected Component createUpload()
  {
    UploadSelect select = new UploadSelect();

    try
    {
      select.addUploadListener( new FileUploadListener( this, "setFile" ) );
    }
    catch ( Throwable t )
    {
      controller.getLogger().log( java.util.logging.Level.WARNING,
          "Too many upload select listeners added.", t );
    }

    return select;
  }

  /**
   * Set the contents of {@link #query} to the contents of the file
   * specified.
   *
   * @param file The file whose contents are to be displayed in {@link
   *   #query}.
   */
  public void setFile( final File file )
  {
    try
    {
      setQuery( StringUtilities.fromFile( file.getAbsolutePath() ) );
      file.delete();
    }
    catch ( Throwable t )
    {
      String message = Configuration.getString( this, "fileError" );
      message = message.replaceAll( "\\$file\\$", file.getAbsolutePath() );
      controller.processFatalException( message, t );
    }
  }

  /**
   * Create a {@link nextapp.echo2.app.Table} using the specified 
   * {@link nextapp.echo2.app.table.TableModel} and display in a {@link
   * echopointng.TabbedPane}.
   *
   * @see #createNavigation
   * @param name The name to assign for the results tab.
   * @param model The results table model to display.
   */
  public void addResults( final String name, final RowsTableModel model )
  {
    Column column = new Column();
    createNavigation( model, column );
    Table<com.sptci.rwt.Row> table = new Table<com.sptci.rwt.Row>( model );
    column.add( table );
    ( (DefaultTabModel) tabbedPane.getModel() ).addTab( name, column );
  }
  
  
  /**
   * Create a {@link com.sptci.echo2.table.TableNavigation} for the specified
   * {@link RowTable} if necessary.
   *
   * @since Version 1.2
   * @param model The table model based on whose size the navigation
   *   comonent is to be displayed.
   * @param parent The parent component to which the navigation component
   *   is to be added.
   */
  void createNavigation( final RowsTableModel model, final Component parent )
  {
    if ( model.getTotalRows() > TableNavigation.MINIMUM_PAGE_SIZE )
    {
      TableNavigation<Row> navigation = new TableNavigation<Row>( model );
      parent.add( navigation );
    }
  }
}
