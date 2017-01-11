package com.sptci.rwt.webui;

import com.sptci.echo2.table.SortableHeaderCellRenderer;
import com.sptci.echo2.table.SortableTable;

/**
 * A sortable table used to display {@link com.sptci.rwt.Row} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-11-13
 * @version $Id: SortableRowTable.java 38 2007-11-22 00:48:04Z sptrakesh $
 * @since Version 1.3
 */
public class SortableRowTable extends RowTable implements SortableTable
{
  /**
   * Create a new table instance using the specified model.
   *
   * @param model The data model backing this table.
   */
  public SortableRowTable( final RowTableModel model )
  {
    super( model );
    setDefaultHeaderRenderer( new SortableHeaderCellRenderer() );
  }
  
  /**
   * Return the data model backing this table.  Over-ridden to avoid
   * casting of the returned type.
   *
   * @return The table model for this table.
   */
  @Override
  public SortableRowTableModel getModel()
  {
    return (SortableRowTableModel) super.getModel();
  }
}