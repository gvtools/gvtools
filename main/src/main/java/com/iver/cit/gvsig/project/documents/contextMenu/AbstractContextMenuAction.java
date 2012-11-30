package com.iver.cit.gvsig.project.documents.contextMenu;

import com.iver.cit.gvsig.project.documents.IContextMenuAction;

public abstract class AbstractContextMenuAction implements IContextMenuAction {

	public boolean isVisible(Object item, Object[] selectedItems) {
		return true;
	}

	public boolean isEnabled(Object item, Object[] selectedItems) {
		return true;
	}

	public String getGroup() {
		return "general";
	}

	public int getGroupOrder() {
		return 50;
	}

	public int getOrder() {
		return 0;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
