package com.gorob.gui.model;

import java.util.List;

public interface ITreeNode {
	public Object getModel();
	public ITreeNode getParentTreeNode();
	public List<ITreeNode> getChildTreeNodes();
}
