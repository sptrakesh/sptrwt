package com.sptci.rwt.webui.tree;

import com.sptci.rwt.CatalogueMetaData;

/**
 * A {@link echopointng.tree.TreeNode} that represents a catalogue in the
 * active database connection.  This class uses the {@link
 * com.sptci.rwt.CatalogueMetaData} value object as the model.
 *
 * <p>&copy; Copyright 2007 Sans Pareil Technologies, Inc.</p>
 * @author Rakesh Vidyadharan 2007-10-23
 * @version $Id: CatalogueNode.java 3649 2007-10-25 00:15:24Z rakesh $
 * @since Version 1.1
 */
public class CatalogueNode extends AbstractNode<CatalogueMetaData>
{
  /**
   * Create a new tree node using the specified metadata object.  The
   * object represents a catalogue in the database.
   *
   * @param metadata The metadata object to use as the model for this node.
   */
  public CatalogueNode( final CatalogueMetaData metadata )
  {
    super( metadata );
  }
  
  /**
   * Create the child nodes for this node.  Child nodes are instances of
   * {@link SchemaNode}.
   *
   * @see SchemaNode
   * @see TablesNode
   * @see ViewsNode
   * @see ProceduresNode
   * @see TriggersNode
   */
  protected void createChildren()
  {
    initialised = true;
    add( new SchemasNode( getUserObject() ) );
    add( new TablesNode( getUserObject() ) );
    add( new ViewsNode( getUserObject() ) );
    add( new ProceduresNode( getUserObject() ) );
    add( new TriggersNode<CatalogueMetaData>( getUserObject() ) );
    add( new SequencesNode( getUserObject() ) );
  }
}