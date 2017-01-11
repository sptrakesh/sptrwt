package com.sptci.rwt.webui;

import java.io.IOException;
import java.io.OutputStream;

import nextapp.echo2.app.filetransfer.DownloadProvider;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sptci.echo2.Configuration;
import com.sptci.rwt.BatchQueryExecutor;
import com.sptci.rwt.ConnectionManager;

/**
 * Action listener for exporting the results of a SQL statement to
 * Excel.  This uses the
 * <a href='http://poi.apache.org/hssf/index.html'>Apache POI HSSF</a>
 * library for creating Excel workbooks.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-09
 * @version $Id: ExcelDownloadProvider.java 3684 2007-11-08 23:40:08Z rakesh $
 */
public class ExcelDownloadProvider implements DownloadProvider
{
  /** The SQL statement to be executed to fetch the data to be exported. */
  private final String query;

  /** The connection manager to use to fetch database connection. */
  private final ConnectionManager manager;

  /**
   * Create a new instance of the download provider using the specified
   * values.
   *
   * @param query The {@link #query} to execute.
   * @param manager The {@link #manager} to use to fetch connection.
   */
  public ExcelDownloadProvider( final String query,
      final ConnectionManager manager )
  {
    this.query = query;
    this.manager = manager;
  }

  /**
   * Return the <code>content-type</code> for the excel workbook.
   *
   * @return The content-type for the workbook.
   */
  public String getContentType()
  {
    return "application/vnd.ms-excel";
  }

  /**
   * Return the <code>content-disposition</code> for the excel workbook.
   *
   * @return The content-disposition.  Returns <code>attachment</code> to
   *   avoid some issues with full content not being pushed to client
   *   in <code>inline</code> mode.
   */
  public String getContentDisposition()
  {
    return "attachment";
  }

  /**
   * Return the file name for the workbook.
   *
   * @return Returns <code>QueryResults.xls</code>.
   */
  public String getFileName()
  {
    return "QueryResults.xls";
  }

  /**
   * Returns the size in bytes of the workbook.
   *
   * @return Returns <code>-1</code> to indicate that the size is unknown.
   */
  public int getSize()
  {
    return -1;
  }

  /**
   * The <code>DownloadProvider</code> implementation method.  Generates
   * the excel workbook and streams to the client.
   *
   * @param out The {@link java.io.OutputStream} to which the contents of
   *   the Excel workbook is to be written.
   * @throws IOException If errors are encountered while writing the
   *   contents.
   */
  public void writeFile( final OutputStream out ) throws IOException
  {
    final BatchQueryExecutor executor = new BatchQueryExecutor( manager );
    HSSFWorkbook workbook = null;

    try
    {
      workbook = executor.export( query );
    }
    catch ( Throwable t )
    {
      MainController.getController().processFatalException(
          Configuration.getString( this, "error" ), t );
    }

    workbook.write( out );
    out.flush();
  }
}
