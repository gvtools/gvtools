package com.iver.cit.gvsig.project.documents.contextMenu;

import com.iver.cit.gvsig.project.documents.ProjectDocument;

public abstract class AbstractDocumentContextMenuAction extends
		AbstractContextMenuAction {

	public void execute(Object item, Object[] selectedItems) {
		this.execute((ProjectDocument) item, (ProjectDocument[]) selectedItems);

	}

	public boolean isVisible(Object item, Object[] selectedItems) {
		return this.isVisible((ProjectDocument) item,
				(ProjectDocument[]) selectedItems);
	}

	public boolean isEnabled(Object item, Object[] selectedItems) {
		return this.isEnabled((ProjectDocument) item,
				(ProjectDocument[]) selectedItems);
	}

	public abstract boolean isVisible(ProjectDocument item,
			ProjectDocument[] selectedItems);

	public abstract boolean isEnabled(ProjectDocument item,
			ProjectDocument[] selectedItems);

	public abstract void execute(ProjectDocument item,
			ProjectDocument[] selectedItems);
}
