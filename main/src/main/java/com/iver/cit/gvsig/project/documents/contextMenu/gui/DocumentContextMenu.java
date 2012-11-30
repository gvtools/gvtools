package com.iver.cit.gvsig.project.documents.contextMenu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.iver.cit.gvsig.project.documents.IContextMenuAction;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

public class DocumentContextMenu extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8153469580038240864L;
	private ProjectDocument item;
	private String option;
	private ExtensionPoint extensionPoint;
	private ProjectDocument[] seleteds;
	private IContextMenuAction[] actions = null;

	public DocumentContextMenu(String option, ProjectDocument item,
			ProjectDocument[] seleteds) {
		super();
		this.initialize(option, item, seleteds);
	}

	private void initialize(String option, ProjectDocument item,
			ProjectDocument[] seleteds) {
		this.option = option;
		this.item = item;
		this.seleteds = seleteds;
		this.extensionPoint = ExtensionPointsSingleton.getInstance().get(
				"DocumentActions_" + this.option);
		if (this.extensionPoint == null) {
			// FIXME: que hacemos aqui?
			return;
		}

		List<IContextMenuAction> actionArrayList = new ArrayList<IContextMenuAction>();
		Iterator<String> iter = this.extensionPoint.keySet().iterator();
		IContextMenuAction action;
		while (iter.hasNext()) {
			action = null;
			try {
				action = (IContextMenuAction) this.extensionPoint.create(iter
						.next());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (action != null) {
				if (action.isVisible(this.item, this.seleteds)) {
					actionArrayList.add(action);
				}
			}

		}
		this.actions = (IContextMenuAction[]) Array.newInstance(
				IContextMenuAction.class, actionArrayList.size());
		System.arraycopy(actionArrayList.toArray(), 0, this.actions, 0,
				actionArrayList.size());

		Arrays.sort(this.actions, new CompareAction());
		this.createMenuElements();
	}

	private void createMenuElements() {
		String group = null;
		for (int i = 0; i < actions.length; i++) {
			IContextMenuAction action = this.actions[i];
			MenuItem item = new MenuItem(action.getText(), action);
			item.setEnabled(action.isEnabled(this.item, this.seleteds));
			if (!action.getGroup().equals(group)) {
				if (group != null)
					this.addSeparator();
				group = action.getGroup();
			}
			this.add(item);
		}

	}

	public int getActionsVisibles() {
		if (this.actions == null)
			return 0;
		return this.actions.length;
	}

	public class CompareAction implements Comparator<IContextMenuAction> {
		public int compare(IContextMenuAction o1, IContextMenuAction o2) {
			// FIXME: flata formatear los enteros!!!!
			NumberFormat formater = NumberFormat.getInstance();
			formater.setMinimumIntegerDigits(3);
			String key1 = "" + formater.format(o1.getGroupOrder())
					+ o1.getGroup() + formater.format(o1.getOrder());
			String key2 = "" + formater.format(o2.getGroupOrder())
					+ o2.getGroup() + formater.format(o2.getOrder());
			return key1.compareTo(key2);
		}
	}

	public class MenuItem extends JMenuItem implements ActionListener {
		;
		private IContextMenuAction action;

		public MenuItem(String text, IContextMenuAction documentAction) {
			super(text);
			this.action = documentAction;
			String tip = this.action.getDescription();
			if (tip != null && tip.length() > 0) {
				this.setToolTipText(tip);
			}
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			this.action.execute(DocumentContextMenu.this.item,
					DocumentContextMenu.this.seleteds);
		}
	}

}
