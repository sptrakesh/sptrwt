package com.sptci.rwt.webui.tree;

import com.sptci.rwt.SchemaMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a schema in the
 * active database connection.  This class uses the {@link
 * com.sptci.rwt.SchemaMetaData} value object as the model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-09-27
 * @version $Id: SchemaNode.java 3563 2007-10-06 00:34:53Z rakesh $
 */
public class SchemaNode extends AbstractNode<SchemaMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a schema in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public SchemaNode( final SchemaMetaData metadata )
  {
    super( metadata );
  }

  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link SchemaNode}.
   *
   * @see TablesNode
   * @see ViewsNode
   * @see ProceduresNode
   * @see TriggersNode
   */
  protected void createChildren()
  {
    initialised = true;
    add( new TablesNode( getUserObject() ) );
    add( new ViewsNode( getUserObject() ) );
    add( new ProceduresNode( getUserObject() ) );
    add( new TriggersNode<SchemaMetaData>( getUserObject() ) );
    add( new SequencesNode( getUserObject() ) );
  }
}
