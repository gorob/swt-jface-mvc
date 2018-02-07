package com.gorob.gui.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTreeNode<T extends Object> implements ITreeNode {
	private T model;
	private ITreeNode parentTreeNode;
	protected List<ITreeNode> childTreeNodes;
	
	public AbstractTreeNode(T model, ITreeNode parentTreeNode) {
		this.model = model;
		this.parentTreeNode = parentTreeNode;
		this.childTreeNodes = null;
	}
	
	public T getModel() {
		return model;
	}

	@Override
	public ITreeNode getParentTreeNode() {
		return parentTreeNode;
	}
	
	public abstract String getId();
	
	@Override
	public List<ITreeNode> getChildTreeNodes() {
		if (childrenToBeInitialized() || childrenToBeReinitialized()) {
			this.childTreeNodes = createChildTreeNodes();			
		}
		
		return this.childTreeNodes;
	}
	

	private boolean childrenToBeInitialized() {
		return this.childTreeNodes==null;
	}

	protected boolean childrenToBeReinitialized() {
		return false;
	}
	
	protected List<ITreeNode> createChildTreeNodes() {
		return new ArrayList<ITreeNode>();
	}
	
	public void verwerfeChildTreeNodes() {
		this.childTreeNodes = null;
	}

	
	protected int getAnzahlChildTreeNodes() {
		return getChildTreeNodes().size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTreeNode other = (AbstractTreeNode) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
