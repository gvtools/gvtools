package com.iver.andami.ui.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.status.IUnsavedData;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * Ask for elements to save before application exit
 * 
 * 
 */
public class UnsavedDataPanel extends JPanel implements IWindow, ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -4745219917358767905L;

	private static ImageIcon blanckIcon = null;
	private JPanel pResources = null;
	private JPanel pButtons = null;
	private JScrollPane pScrollList = null;
	private MyList list = null;
	private JLabel lblDescription = null;
	private JButton botSave = null;
	private JButton botDiscard = null;
	private JButton botDontExit = null;
	private JPanel pActionButtons = null;
	private JPanel pSelectionButtons = null;
	private JButton botSelectAll = null;
	private JButton botDeselectAll = null;

	private ArrayList<UnsavedDataPanelListener> listeners = new ArrayList<UnsavedDataPanelListener>();
	private JLabel lblResourceDescription = null;
	private String windowTitle = PluginServices.getText(this, "save_resources");

	/**
	 * This is the default constructor
	 */
	public UnsavedDataPanel(IUnsavedData[] unsavedDatalist) {
		super();
		initialize();
		this.setUnsavedDataArray(unsavedDatalist);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		lblDescription = new JLabel();
		lblDescription.setText(PluginServices.getText(this,
				"select_resources_to_save_before_exit"));
		lblDescription.setPreferredSize(new Dimension(497, 40));
		lblDescription.setName("lblDescription");
		this.setLayout(borderLayout);
		this.setSize(396, 272);
		this.add(getPResources(), java.awt.BorderLayout.CENTER);
		this.add(getPButtons(), java.awt.BorderLayout.SOUTH);
		this.add(lblDescription, BorderLayout.NORTH);
	}

	/**
	 * This method initializes pResources
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPResources() {
		if (pResources == null) {
			pResources = new JPanel();
			pResources.setLayout(new BorderLayout());
			pResources.add(getPScrollList(), BorderLayout.CENTER);
			pResources.add(getLblResourceDescription(), BorderLayout.SOUTH);
		}
		return pResources;
	}

	/**
	 * This method initializes lblResourceDescription
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getLblResourceDescription() {
		if (lblResourceDescription == null) {
			lblResourceDescription = new JLabel();
			lblResourceDescription.setText("");
			lblResourceDescription.setPreferredSize(new Dimension(38, 50));
			lblResourceDescription.setName("lblResourceDescription");
		}
		return lblResourceDescription;
	}

	/**
	 * This method initializes pButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPButtons() {
		if (pButtons == null) {
			BorderLayout borderLayout2 = new BorderLayout();
			borderLayout2.setHgap(5);
			borderLayout2.setVgap(5);
			pButtons = new JPanel();
			pButtons.setLayout(borderLayout2);
			pButtons.add(getPSelectionButtons(), BorderLayout.NORTH);
			pButtons.add(getPActionButtons(), BorderLayout.SOUTH);
		}
		return pButtons;
	}

	/**
	 * This method initializes pScrollList
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPScrollList() {
		if (pScrollList == null) {
			pScrollList = new JScrollPane();
			pScrollList.setPreferredSize(new java.awt.Dimension(350, 350));
			pScrollList.setViewportView(getList());
		}
		return pScrollList;
	}

	/**
	 * This method initializes list
	 * 
	 * @return javax.swing.JList
	 */
	private MyList getList() {
		if (list == null) {
			list = new MyList();
			list.setBackground(Color.white);
		}
		return list;
	}

	/**
	 * This method initializes botSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotSave() {
		if (botSave == null) {
			botSave = new JButton();
			botSave.setName("botSave");
			botSave.setToolTipText(PluginServices.getText(this,
					"save_selected_resources_and_exit"));
			botSave.setText(PluginServices.getText(this, "ok"));
			botSave.setActionCommand("Accept");
			botSave.addActionListener(this);
		}
		return botSave;
	}

	/**
	 * This method initializes botDiscard
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotDiscard() {
		if (botDiscard == null) {
			botDiscard = new JButton();
			botDiscard.setName("botDiscard");
			botDiscard.setToolTipText(PluginServices.getText(this,
					"discard_changes_and_exit"));
			botDiscard.setText(PluginServices.getText(this, "discard_changes"));
			botDiscard.setActionCommand("Discard");
			botDiscard.addActionListener(this);
		}
		return botDiscard;
	}

	/**
	 * This method initializes botDontExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotDontExit() {
		if (botDontExit == null) {
			botDontExit = new JButton();
			botDontExit.setName("botDontExit");
			botDontExit.setToolTipText(PluginServices.getText(this,
					"cancel_the_application_termination"));
			botDontExit.setText(PluginServices.getText(this, "cancelar"));
			botDontExit.setActionCommand("Cancel");
			botDontExit.addActionListener(this);
		}
		return botDontExit;
	}

	/**
	 * This method initializes pActionButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPActionButtons() {
		if (pActionButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pActionButtons = new JPanel();
			pActionButtons.setLayout(flowLayout);
			pActionButtons.setName("pActionButtons");
			pActionButtons.add(getBotSave(), null);
			pActionButtons.add(getBotDiscard(), null);
			pActionButtons.add(getBotDontExit(), null);
		}
		return pActionButtons;
	}

	/**
	 * This method initializes pSelectionButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPSelectionButtons() {
		if (pSelectionButtons == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.LEFT);
			pSelectionButtons = new JPanel();
			pSelectionButtons.setLayout(flowLayout1);
			pSelectionButtons.setName("pSelectionButtons");
			pSelectionButtons.add(getBotSelectAll(), null);
			pSelectionButtons.add(getBotDeselectAll(), null);
		}
		return pSelectionButtons;
	}

	/**
	 * This method initializes botSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotSelectAll() {
		if (botSelectAll == null) {
			botSelectAll = new JButton();
			botSelectAll.setText(PluginServices.getText(this, "select_all"));
			botSelectAll.setName("botSelectAll");
			botSelectAll.setToolTipText(PluginServices.getText(this,
					"select_all_resources"));
			botSelectAll.setActionCommand("SelectAll");
			botSelectAll.addActionListener(this);
		}
		return botSelectAll;
	}

	/**
	 * This method initializes botDeselectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotDeselectAll() {
		if (botDeselectAll == null) {
			botDeselectAll = new JButton();
			botDeselectAll.setName("botDeselectAll");
			botDeselectAll.setToolTipText(PluginServices.getText(this,
					"deselect_all_resources"));
			botDeselectAll
					.setText(PluginServices.getText(this, "deselect_all"));
			botDeselectAll.setActionCommand("DeselectAll");
			botDeselectAll.addActionListener(this);
		}
		return botDeselectAll;
	}

	public WindowInfo getWindowInfo() {
		WindowInfo wi = new WindowInfo(WindowInfo.MODALDIALOG
				| WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE);
		wi.setTitle(windowTitle);
		wi.setHeight(270);
		wi.setWidth(450);
		return wi;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public void setCancelText(String text, String tooltipText) {
		getBotDontExit().setToolTipText(tooltipText);
		getBotDontExit().setText(text);
	}

	public void setDiscardText(String text, String tooltipText) {
		getBotDiscard().setToolTipText(tooltipText);
		getBotDiscard().setText(text);
	}

	public void setAcceptText(String text, String tooltipText) {
		getBotSave().setToolTipText(tooltipText);
		getBotSave().setText(text);
	}

	public void setHeaderText(String text) {
		lblDescription.setText(text);
	}

	/**
	 * Register a listener to call
	 * 
	 * @param a
	 *            UnsavedDataPanelListener instance
	 */
	public void addActionListener(UnsavedDataPanelListener listener) {
		this.listeners.add(listener);
	}

	public void removeActionListener(UnsavedDataPanelListener listener) {
		this.listeners.remove(listener);
	}

	public class UnsavedDataPanelListener {
		public void cancel(UnsavedDataPanel panel) {

		}

		public void accept(UnsavedDataPanel panel) {

		}

		public void discard(UnsavedDataPanel panel) {

		}
	}

	private class MyList extends JPanel {

		/**
		 *
		 */
		private static final long serialVersionUID = 3179254463477354501L;

		private GridBagConstraints constraints;
		private GridBagConstraints constraintsLast;

		public MyList() {
			super();
			initialize();
		}

		private void initialize() {
			this.setLayout(new GridBagLayout());
			this.constraints = new GridBagConstraints();
			this.constraints.fill = GridBagConstraints.HORIZONTAL;
			this.constraints.gridwidth = GridBagConstraints.REMAINDER;
			this.constraints.weightx = 0;
			this.constraints.gridx = 0;
			this.constraints.gridy = 0;
			this.constraints.anchor = GridBagConstraints.WEST;
			this.constraints.insets = new Insets(3, 3, 0, 0);

			this.constraintsLast = new GridBagConstraints();
			this.constraintsLast.fill = GridBagConstraints.BOTH;
			this.constraintsLast.gridheight = GridBagConstraints.REMAINDER;
			this.constraintsLast.gridwidth = GridBagConstraints.REMAINDER;
			this.constraintsLast.gridx = 0;
			this.constraintsLast.weightx = 1;
			this.constraintsLast.weighty = 1;
			this.constraintsLast.anchor = GridBagConstraints.FIRST_LINE_START;

		}

		public void addItems(IUnsavedData[] itemList) {
			int i;
			this.removeAll();
			if (itemList.length == 0) {
				return;
			}

			for (i = 0; i < itemList.length; i++) {
				this.constraints.gridy = i;
				this.add(newItem(itemList[i]), this.constraints);
			}
			this.constraintsLast.gridy = itemList.length;
			this.add(new JLabel(), this.constraintsLast);

		}

		public int getItemsCount() {
			return this.getComponentCount() - 1;
		}

		public MyUnsavedItem getItem(int i) {
			if (i >= this.getComponentCount()) {
				return null;
			}
			return (MyUnsavedItem) this.getComponent(i);
		}

		private MyUnsavedItem newItem(IUnsavedData itemData) {
			MyUnsavedItem item;
			item = new MyUnsavedItem(itemData);
			item.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					MyUnsavedItem item = (MyUnsavedItem) e.getComponent();
					getLblResourceDescription().setText(item.getDescription());
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

			});
			return item;
		}

	}

	private class MyUnsavedItem extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = -6280057775368437349L;
		private JCheckBox chkChecked;
		private JLabel lblText;
		private IUnsavedData dataItem;

		public MyUnsavedItem(IUnsavedData dataItem) {
			super();
			this.initialize();
			this.setData(dataItem);
		}

		private void initialize() {
			this.setOpaque(false);
			BorderLayout layout = new BorderLayout();
			layout.setHgap(5);
			this.setLayout(layout);
			this.add(this.getChkChecked(), BorderLayout.WEST);
			this.add(this.getLblText(), BorderLayout.CENTER);
		}

		private JLabel getLblText() {
			if (lblText == null) {
				lblText = new JLabel();
				lblText.setName("lblText");
			}
			return lblText;
		}

		private JCheckBox getChkChecked() {
			if (chkChecked == null) {
				chkChecked = new JCheckBox();
				chkChecked.setName("chkChecked");
				chkChecked.setSelected(true);
				chkChecked.setOpaque(false);
			}
			return chkChecked;
		}

		public boolean isSelected() {
			return getChkChecked().isSelected();
		}

		public void setSelected(boolean selected) {
			getChkChecked().setSelected(selected);
		}

		public void setData(IUnsavedData dataItem) {
			this.dataItem = dataItem;
			JLabel lbl = getLblText();
			if (dataItem.getIcon() != null) {
				lbl.setIcon(dataItem.getIcon());
			} else {
				lbl.setIcon(getBlanckIcon());
			}
			lbl.setText(dataItem.getResourceName());
			getChkChecked().setSelected(true);
		}

		public IUnsavedData getData() {
			return this.dataItem;
		}

		public String getDescription() {
			return this.dataItem.getDescription();
		}

		private ImageIcon getBlanckIcon() {
			if (blanckIcon == null) {
				BufferedImage buff = new BufferedImage(16, 16,
						BufferedImage.TYPE_INT_ARGB);
				blanckIcon = new ImageIcon(buff);
			}
			return blanckIcon;
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Cancel") {
			fireDontExitEvent();
			return;
		}
		if (e.getActionCommand() == "Accept") {
			fireAcceptEvent();
			return;
		}
		if (e.getActionCommand() == "Discard") {
			fireDiscardEvent();
			return;
		}
		if (e.getActionCommand() == "SelectAll") {
			selectAll();
			return;
		}
		if (e.getActionCommand() == "DeselectAll") {
			deselectAll();
			return;
		}
	}

	public void close() {
		PluginServices.getMDIManager().closeWindow(this);

	}

	private void fireAcceptEvent() {
		Iterator<UnsavedDataPanelListener> iter = this.listeners.iterator();
		while (iter.hasNext()) {
			iter.next().accept(this);
		}
	}

	private void fireDiscardEvent() {
		Iterator<UnsavedDataPanelListener> iter = this.listeners.iterator();
		while (iter.hasNext()) {
			iter.next().discard(this);
		}
	}

	private void fireDontExitEvent() {
		Iterator<UnsavedDataPanelListener> iter = this.listeners.iterator();
		while (iter.hasNext()) {
			iter.next().cancel(this);
		}
	}

	private void setSelectedsAll(boolean selected) {
		int i;
		MyList theList = getList();
		MyUnsavedItem item;
		for (i = 0; i < theList.getItemsCount(); i++) {
			item = theList.getItem(i);
			item.setSelected(selected);
		}
		theList.doLayout();
	}

	public void deselectAll() {
		this.setSelectedsAll(false);
	}

	public void selectAll() {
		this.setSelectedsAll(true);
	}

	/**
	 * Set the resorces to ask for
	 * 
	 * @param IUnsavedData
	 *            [] resources pending to save
	 */
	public void setUnsavedDataArray(IUnsavedData[] unsavedDatalist) {
		MyList theList = getList();

		theList.addItems(unsavedDatalist);
		theList.validate();
	}

	private IUnsavedData[] getUnsavedData(boolean selected) {
		int i;
		MyList theList = getList();
		ArrayList<IUnsavedData> aList = new ArrayList<IUnsavedData>();
		MyUnsavedItem item;
		IUnsavedData[] result;

		for (i = 0; i < theList.getItemsCount(); i++) {
			item = theList.getItem(i);
			if (item.isSelected() == selected) {
				aList.add(item.getData());
			}
		}
		result = new IUnsavedData[aList.size()];
		System.arraycopy(aList.toArray(), 0, result, 0, aList.size());
		return result;
	}

	/**
	 * Returns the IUnsavedData selecteds for the user to save
	 * 
	 * @return IUnsaveData[] to save
	 */
	public IUnsavedData[] getSelectedsUnsavedData() {
		return this.getUnsavedData(true);

	}

	/**
	 * Returns the IUnsavedData not selecteds for the user
	 * 
	 * @return IUnsavedData[] to ignore
	 */
	public IUnsavedData[] getUnselectedsUnsavedData() {
		return this.getUnsavedData(false);
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

