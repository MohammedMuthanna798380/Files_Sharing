package foldersharing.pl;

import java.io.File;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FolderTreeModel implements TreeModel {

    private File root;

    public FolderTreeModel(File root) {
        this.root = root;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof File) {
            File[] children = ((File) parent).listFiles();
            if (children != null && index < children.length) {
               return children[index];
            }
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof File) {
            File[] children = ((File) parent).listFiles();
            if (children != null) {
                return children.length;
            }
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof File) {
            return ((File) node).isFile();
        }
        return false;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // not implemented
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof File && child instanceof File) {
            File[] children = ((File) parent).listFiles();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    if (children[i].equals(child)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        // not implemented
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        // not implemented
    }
}


