package com.sptci.rwt.webui;

import com.sptci.echo2.table.Table;
import com.sptci.rwt.Row;

/**
 * A custom table used to display {@link com.sptci.rwt.Row} objects.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-05
 * @version $Id: RowTable.java 3578 2007-10-08 04:04:42Z rakesh $
 */
public class RowTable extends Table<Row>
{
  /**
   * Create a new table instance using the specified model.
   *
   * @param model The data model backing this table.
   */
  public RowTable( final RowTableModel model )
  {
    super( model );
  }

  /**
   * Return the data model backing this table.  Over-ridden to avoid
   * casting of the returned type.
   *
   * @return The table model for this table.
   */
  @Override
  public RowTableModel getModel()
  {
    return (RowTableModel) super.getModel();
  }
}
