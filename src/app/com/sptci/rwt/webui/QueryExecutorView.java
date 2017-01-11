package com.sptci.rwt.webui;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;

import com.sptci.echo2.Utilities;
import com.sptci.echo2.table.TableNavigation;

/**
 * Query executor view component used to interact with the {@link
 * com.sptci.rwt.QueryExecutor} class.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Adarsh 2007-08-18
 * @version $Id: QueryExecutorView.java 38 2007-11-22 00:48:04Z sptrakesh $
 */
public class QueryExecutorView extends ExecutorView
{
  /**
   * Create instance of the pane using the specified controller.
   *
   * @param controller The controller to use to interact with the rest of the
   *   application.
   */
  public QueryExecutorView( final MainController controller )
  {
    super( controller );
  }

  /**
   * Create the layout component to use to display the {@link #maxResults}
   * component and the other controls used to execute the statement.
   *
   * @see #createExecute
   * @see #createMaxResults
   * @see #createMaxColumnLength
   * @see #createHistory
   * @see #createExport
   * @see #createSave
   * @return The layout component.
   */
  @Override
  protected Component createControls()
  {
    Row row = new Row();
    row.add( createExecute() );
    createMaxResults( row );
    createMaxColumnLength( row );
    row.add( createExport() );
    row.add( createSave() );
    row.add( createHistory() );

    return row;
  }

  /**
   * Create a {@link com.sptci.echo2.table.TableNavigation} for the specified
   * {@link RowTable} if necessary.
   *
   * @param table The results table for which the navigation is to be
   *   displayed.
   */
  private void createNavigation( final RowTable table )
  {
    if ( table.getModel().getRowCount() > TableNavigation.MINIMUM_PAGE_SIZE )
    {
      TableNavigation<Row> navigation =
        new TableNavigation<Row>( table.getModel() );
      results.add( navigation );
    }
  }

  /**
   * Display the specified table in {@link #results}.
   *
   * @see #createNavigation
   * @param table The results table to display.
   */
  public void setResults( final RowTable table )
  {
    reset();
    createNavigation( table );
    results.add( table );
  }
}
