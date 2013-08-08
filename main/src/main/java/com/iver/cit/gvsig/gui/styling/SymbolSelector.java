/*
 * Created on 26-abr-2005
 *
 * gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.geotools.filter.FilterFactoryImpl;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.geotools.styling.TextSymbolizer;
import org.gvsig.gui.beans.AcceptCancelPanel;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.gui.beans.swing.JComboBoxFontSizes;
import org.gvsig.gui.beans.swing.JComboBoxFonts;
import org.gvsig.gui.beans.swing.JFileChooser;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;
import org.opengis.style.GraphicalSymbol;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindowListener;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.gui.JComboBoxUnits;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;
import com.iver.cit.gvsig.project.documents.view.legend.gui.ISymbolSelector;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolPreviewer;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Creates the panel where the user has the options to select a symbol. Apart
 * from the option to select one, the user will have a previsualization of all
 * the symbols stored and posibilities to modify an existing one, to create a
 * new symbol and so on.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SymbolSelector extends JPanel implements ISymbolSelector,
		ActionListener, FocusListener, IWindowListener {
	public static final String SYMBOL_FILE_EXTENSION = ".sym";

	private static final long serialVersionUID = -6405660392303659551L;
	private JPanel jPanel = null;
	protected JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private WindowInfo wi;
	private JSplitPane jSplitPane = null;
	protected AcceptCancelPanel okCancelPanel;
	private JPanel northPanel;
	private ColorChooserPanel jcc1;
	private ColorChooserPanel jcc2;
	private JIncrementalNumberField txtSize;
	private JIncrementalNumberField txtAngle;
	private JPanel jPanelButtons;
	private JButton btnProperties;
	private Class<? extends Symbolizer> type;
	private JButton btnSaveSymbol;
	private JButton btnResetSymbol;
	private JButton btnNewSymbol;
	private JComboBoxFonts cmbFonts;
	private JToggleButton btnBold;
	private JToggleButton btnItalic;
	private JToggleButton btnUnderlined;
	protected JLabel lblTitle;
	protected File dir;
	protected File rootDir;
	protected SymbolPreviewer jPanelPreview = null;
	protected GridBagLayoutPanel jPanelOptions = null;
	protected JList<Symbolizer> jListSymbols = null;
	protected String treeRootName;
	protected ILibraryModel library;
	private JIncrementalNumberField txtWidth;
	protected boolean act = true;
	protected boolean accepted = false;
	private String wiTitle = null;

	protected SelectorFilter sFilter = new SelectorFilter() {
		private GeometryFactory gf = new GeometryFactory();

		private final Geometry dummyPointGeom = gf
				.createPoint(new Coordinate());
		private final Geometry dummyLineGeom = gf
				.createLineString(new Coordinate[] { new Coordinate(0, 0),
						new Coordinate(5, 5) });
		private final Geometry dummyPolygonGeom = gf.createPolygon(
				gf.createLinearRing(new Coordinate[] { new Coordinate(0, 0),
						new Coordinate(5, 5), new Coordinate(5, 0),
						new Coordinate(0, 0) }), null);

		public boolean accepts(Object obj) {
			if (obj instanceof Style) {
				Style style = (Style) obj;

				Geometry compareGeometry = null;
				if (TextSymbolizer.class.isAssignableFrom(type)) {
					return style instanceof TextSymbolizer;
				} else if (PointSymbolizer.class.isAssignableFrom(type)) {
					compareGeometry = dummyPointGeom;
				} else if (LineSymbolizer.class.isAssignableFrom(type)) {
					compareGeometry = dummyLineGeom;
				} else if (PolygonSymbolizer.class.isAssignableFrom(type)) {
					compareGeometry = dummyPolygonGeom;
				}

				return true;
				// TODO gtintegration
				// return style.isSuitableFor(compareGeometry);
			}
			return false;
		}
	};

	protected JComboBoxUnits cmbUnits;
	protected JComboBoxUnitsReferenceSystem cmbReferenceSystem;
	private JComboBoxFontSizes cmbFontSize;
	protected LibraryBrowser libraryBrowser;
	private boolean showAcceptPanel;

	/**
	 * Constructor method
	 * 
	 * @param currentElement
	 * @param shapeType
	 */
	private SymbolSelector(Symbolizer currentElement,
			Class<? extends Symbolizer> shapeType, boolean initialize,
			boolean showAcceptPanel) {
		super();

		this.type = shapeType;

		// Preferences prefs = Preferences.userRoot().node( "gvsig.foldering" );
		rootDir = new File(SymbologyUtils.SymbolLibraryPath);
		// prefs.get("SymbolLibraryFolder",
		// System.getProperty("user.home")+"/gvSIG/Symbols"));
		if (!rootDir.exists())
			rootDir.mkdir();
		treeRootName = PluginServices.getText(this, "symbol_library");
		this.showAcceptPanel = showAcceptPanel;
		try {
			if (initialize)
				initialize(currentElement);
		} catch (ClassNotFoundException e) {
			throw new Error(e);
		}

	}

	/**
	 * Constructor method, it is <b>protected</b> by convenience to let
	 * StyleSelector to invoke it, but rigorously it should be <b>private</b>.
	 * 
	 * @param style
	 * @param shapeType
	 * @param filter
	 */
	protected SymbolSelector(Symbolizer style,
			Class<? extends Symbolizer> shapeType, SelectorFilter filter,
			boolean initialize) {
		this(style, shapeType, initialize, true);
		sFilter = filter;
	}

	/**
	 * Constructor method, it is <b>protected</b> by convenience to let
	 * StyleSelector to invoke it, but rigorously it should be <b>private</b>.
	 * 
	 * @param style
	 * @param shapeType
	 * @param filter
	 */
	protected SymbolSelector(Symbolizer style,
			Class<? extends Symbolizer> shapeType, SelectorFilter filter,
			boolean initialize, boolean showAcceptPanel) {
		this(style, shapeType, initialize, showAcceptPanel);
		sFilter = filter;
	}

	/**
	 * This method initializes this
	 * 
	 * @param currentElement
	 * @throws ClassNotFoundException
	 * 
	 */
	protected void initialize(Symbolizer currentElement)
			throws ClassNotFoundException {
		library = new SymbolLibrary(rootDir);

		this.setLayout(new BorderLayout());
		this.setSize(400, 221);

		this.add(getJNorthPanel(), BorderLayout.NORTH);
		this.add(getJSplitPane(), BorderLayout.CENTER);
		this.add(getJEastPanel(), BorderLayout.EAST);
		ActionListener okAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accepted = true;
				PluginServices.getMDIManager().closeWindow(SymbolSelector.this);
			}
		}, cancelAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accepted = false;
				// ((SymbolPreviewer) jPanelPreview).setSymbol(null);
				PluginServices.getMDIManager().closeWindow(SymbolSelector.this);

			}
		};

		if (showAcceptPanel) {
			okCancelPanel = new AcceptCancelPanel();
			okCancelPanel.setOkButtonActionListener(okAction);
			okCancelPanel.setCancelButtonActionListener(cancelAction);

			this.add(okCancelPanel, BorderLayout.SOUTH);
		}
		libraryBrowser.setSelectionRow(0);

		SillyDragNDropAction dndAction = new SillyDragNDropAction();
		libraryBrowser.addMouseListener(dndAction);
		libraryBrowser.addMouseMotionListener(dndAction);
		getJListSymbols().addMouseListener(dndAction);
		getJListSymbols().addMouseMotionListener(dndAction);
		setSymbol(currentElement);
	}

	/**
	 * Creates a new symbol selector list model in order to allow the user to
	 * select an existing symbol previously created.
	 * 
	 * @return listModel SymbolSelectorListModel
	 */
	protected SymbolSelectorListModel newListModel() {
		SymbolSelectorListModel listModel = new SymbolSelectorListModel(dir,
				sFilter, SYMBOL_FILE_EXTENSION);
		return listModel;
	}

	/**
	 * Initializes tha JNorthPanel.
	 * 
	 * @return northPanel JPanel
	 * @throws IllegalArgumentException
	 */
	protected JPanel getJNorthPanel() throws IllegalArgumentException {
		if (northPanel == null) {
			String text = "";
			if (PointSymbolizer.class.isAssignableFrom(type)) {
				text = PluginServices.getText(this, "point_symbols");
			} else if (LineSymbolizer.class.isAssignableFrom(type)) {
				text = PluginServices.getText(this, "line_symbols");
			} else if (PolygonSymbolizer.class.isAssignableFrom(type)) {
				text = PluginServices.getText(this, "polygon_symbols");
			} else if (TextSymbolizer.class.isAssignableFrom(type)) {
				text = PluginServices.getText(this, "text_symbols");
			} else {
				throw new IllegalArgumentException(PluginServices.getText(this,
						"shape_type_not_yet_supported"));
			}
			northPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			lblTitle = new JLabel(text);
			lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD));
			northPanel.add(lblTitle);
		}
		return northPanel;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	protected JList<Symbolizer> getJListSymbols() {
		if (jListSymbols == null) {
			jListSymbols = new JList<Symbolizer>();
			jListSymbols
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			jListSymbols.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			jListSymbols.setVisibleRowCount(-1);
			jListSymbols.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (jListSymbols.getSelectedValue() != null) {
						Symbolizer selSym = jListSymbols.getSelectedValue();
						setSymbol(selSym);
						updateOptionsPanel();
					}
				}
			});
			ListCellRenderer<Symbolizer> renderer = new ListCellRenderer<Symbolizer>() {
				private Color mySelectedBGColor = new Color(255, 145, 100, 255);

				@Override
				public Component getListCellRendererComponent(
						JList<? extends Symbolizer> list, Symbolizer value,
						int index, boolean isSelected, boolean cellHasFocus) {
					JPanel pnl = new JPanel();
					BoxLayout layout = new BoxLayout(pnl, BoxLayout.Y_AXIS);
					pnl.setLayout(layout);
					Color bgColor = (isSelected) ? mySelectedBGColor
							: getJListSymbols().getBackground();

					pnl.setBackground(bgColor);
					SymbolPreviewer sp = new SymbolPreviewer();
					sp.setAlignmentX(Component.CENTER_ALIGNMENT);
					sp.setPreferredSize(new Dimension(50, 50));
					sp.setSymbol(value);
					sp.setBackground(bgColor);
					pnl.add(sp);
					String desc = value.getDescription().getAbstract()
							.toString();
					if (desc == null) {
						desc = "[" + PluginServices.getText(this, "no_desc")
								+ "]";
					}
					JLabel lbl = new JLabel(desc);
					lbl.setBackground(bgColor);
					lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
					pnl.add(lbl);

					return pnl;
				}

			};
			jListSymbols.setCellRenderer(renderer);
		}
		return jListSymbols;
	}

	/**
	 * Updates the options panel depending on the type of symbol that the user
	 * is controlling or using to show specific options for each one.
	 * 
	 */
	protected void updateOptionsPanel() throws IllegalArgumentException {
		Symbolizer mySelectedElement = jPanelPreview.getSymbol();

		// if (mySelectedElement == null) return;
		act = false; // disable events

		// TODO gtintegration
		// cmbReferenceSystem.setSelectedIndex(mySelectedElement.getReferenceSystem());
		cmbUnits.setSelectedUnit(SymbologyUtils
				.convert2gvsigUnits(mySelectedElement.getUnitOfMeasure()));

		try {

			jcc1.setEnabled(mySelectedElement != null);
			jcc2.setEnabled(mySelectedElement != null);

			if (mySelectedElement instanceof PointSymbolizer) {
				PointSymbolizer m = (PointSymbolizer) mySelectedElement;
				txtSize.setEnabled(m != null);
				txtAngle.setEnabled(m != null);
				if (m != null) {
					jcc1.setColor(SLD.color(m));
					txtSize.setDouble(SLD.graphic(m).getSize()
							.evaluate(null, Double.class));
					txtAngle.setDouble(Math.toDegrees(m.getGraphic()
							.getRotation().evaluate(null, Double.class)));
				}
			} else if (mySelectedElement instanceof LineSymbolizer) {
				LineSymbolizer l = (LineSymbolizer) mySelectedElement;
				txtSize.setEnabled(l != null);
				if (l != null) {
					jcc1.setColor(SLD.color(l));
					jcc1.setAlpha((int) SLD.opacity(l.getStroke()) * 255);
					txtSize.setDouble(SLD.width(l));
				}
			} else if (mySelectedElement instanceof PolygonSymbolizer) {
				PolygonSymbolizer f = (PolygonSymbolizer) mySelectedElement;

				txtWidth.setEnabled(f != null);
				cmbReferenceSystem.setEnabled(f != null);
				cmbUnits.setEnabled(f != null);

				if (f != null) {
					int fillOpacity = (int) Math
							.round(SLD.opacity(f.getFill()) * 255);
					int strokeOpacity = (int) Math.round(SLD.opacity(f
							.getStroke()) * 255);
					Color fillColor = SLD.color(f.getFill());
					Color strokeColor = SLD.color(f.getStroke());

					jcc1.setColor(fillColor);
					jcc1.setAlpha(fillOpacity);
					jcc1.setUseColorIsSelected(fillOpacity > 0);
					jcc2.setUseColorIsSelected(strokeOpacity > 0);

					jcc2.setColor(strokeColor);
					txtWidth.setDouble(SLD.width(f.getStroke()));
				}
			} else if (mySelectedElement instanceof TextSymbolizer) {
				TextSymbolizer t = (TextSymbolizer) mySelectedElement;
				cmbFontSize.setEnabled(t != null);
				if (t != null) {
					jcc1.setColor(SLD.color(t.getFill()));
					Double s = new Double(SLD.font(t).getSize()
							.evaluate(null, Double.class));
					cmbFontSize.setSelectedItem(s);
					int i = cmbFontSize.getSelectedIndex();
					if (i == -1) {
						cmbFontSize.addItem(s);
						cmbFontSize.setSelectedItem(s);
					}
				}
			}
		} catch (NullPointerException npEx) {
			throw new IllegalArgumentException(npEx);
		}

		act = true; // enable events
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJEastPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanelOptions(), BorderLayout.CENTER);
			JPanel aux = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
			aux.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "preview")));
			aux.add(getJPanelPreview());
			jPanel.add(aux, BorderLayout.NORTH);

			jPanel.add(getJPanelOptions());
			aux = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			aux.add(getJPanelButtons());
			jPanel.add(aux, BorderLayout.SOUTH);
		}
		return jPanel;
	}

	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			GridLayout layout = new GridLayout();
			layout.setColumns(1);
			layout.setVgap(5);
			jPanelButtons.add(getBtnNewSymbol());
			jPanelButtons.add(getBtnSaveSymbol());
			jPanelButtons.add(getBtnResetSymbol());
			jPanelButtons.add(getBtnProperties());

			// do not add components bellow this line!
			layout.setRows(jPanelButtons.getComponentCount());
			jPanelButtons.setLayout(layout);
		}
		return jPanelButtons;
	}

	private JButton getBtnNewSymbol() {
		if (btnNewSymbol == null) {
			btnNewSymbol = new JButton();
			btnNewSymbol.setName("btnNewSymbol");
			btnNewSymbol.setText(PluginServices.getText(this, "new"));
			btnNewSymbol.addActionListener(this);
		}
		return btnNewSymbol;
	}

	private JButton getBtnResetSymbol() {
		if (btnResetSymbol == null) {
			btnResetSymbol = new JButton();
			btnResetSymbol.setName("btnResetSymbol");
			btnResetSymbol.setText(PluginServices.getText(this, "reset"));
			btnResetSymbol.addActionListener(this);
		}
		return btnResetSymbol;
	}

	private JButton getBtnSaveSymbol() {
		if (btnSaveSymbol == null) {
			btnSaveSymbol = new JButton();
			btnSaveSymbol.setName("btnSaveSymbol");
			btnSaveSymbol.setText(PluginServices.getText(this, "save"));
			btnSaveSymbol.addActionListener(this);
		}
		return btnSaveSymbol;
	}

	private JButton getBtnProperties() {
		if (btnProperties == null) {
			btnProperties = new JButton();
			btnProperties.setName("btnProperties");
			btnProperties.setText(PluginServices.getText(this, "properties"));
			btnProperties.addActionListener(this);
		}
		return btnProperties;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 * @throws ClassNotFoundException
	 */
	protected JScrollPane getLeftJScrollPane() throws ClassNotFoundException {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new java.awt.Dimension(80, 130));
			jScrollPane
					.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			libraryBrowser = new LibraryBrowser(library);
			libraryBrowser
					.addTreeSelectionListener(new TreeSelectionListener() {
						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							dir = (File) ((DefaultMutableTreeNode) libraryBrowser
									.getLastSelectedPathComponent())
									.getUserObject();

							if (dir == null)
								return;

							jListSymbols.setModel(newListModel());
							// jListSymbols.setSelectedValue(selectedElement,
							// true);
						}
					});
			jScrollPane.setViewportView(libraryBrowser);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJListSymbols());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jPanelPreview
	 * 
	 * @return javax.swing.JComponent
	 */
	protected JComponent getJPanelPreview() {
		if (jPanelPreview == null) {
			jPanelPreview = new SymbolPreviewer();
			jPanelPreview.setPreferredSize(new java.awt.Dimension(100, 100));
			jPanelPreview.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return jPanelPreview;
	}

	/**
	 * This method initializes jPanelOptions
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanelOptions() {
		if (jPanelOptions == null) {
			jPanelOptions = new GridBagLayoutPanel();
			jPanelOptions.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "options")));
			jcc2 = new ColorChooserPanel(true, true);
			jcc2.setAlpha(255);
			if (PointSymbolizer.class.isAssignableFrom(type)) {
				jcc1 = new ColorChooserPanel(true);

				jPanelOptions.addComponent(
						PluginServices.getText(this, "color") + ":", jcc1);
				jPanelOptions.addComponent(PluginServices.getText(this, "size")
						+ ":",
						txtSize = new JIncrementalNumberField(
								String.valueOf(3), 3, 0, Double.MAX_VALUE, 1));
				jPanelOptions.addComponent(
						PluginServices.getText(this, "units") + ":",
						cmbUnits = new JComboBoxUnits());
				jPanelOptions
						.addComponent(
								"",
								cmbReferenceSystem = new JComboBoxUnitsReferenceSystem());
				jPanelOptions
						.addComponent(PluginServices.getText(this, "angle")
								+ " (" + PluginServices.getText(this, "degree")
								+ "):",
								txtAngle = new JIncrementalNumberField());
			} else if (LineSymbolizer.class.isAssignableFrom(type)) {
				jcc1 = new ColorChooserPanel(true);
				jPanelOptions.addComponent(
						PluginServices.getText(this, "color") + ":", jcc1);
				jPanelOptions.addComponent(
						PluginServices.getText(this, "width") + ":",
						txtSize = new JIncrementalNumberField(
								String.valueOf(3), 3, 0, Double.MAX_VALUE, 1));
				jPanelOptions.addComponent(
						PluginServices.getText(this, "units") + ":",
						cmbUnits = new JComboBoxUnits());
				jPanelOptions
						.addComponent(
								"",
								cmbReferenceSystem = new JComboBoxUnitsReferenceSystem());

			} else if (PolygonSymbolizer.class.isAssignableFrom(type)) {
				jcc1 = new ColorChooserPanel(true, true);
				jPanelOptions.addComponent(
						PluginServices.getText(this, "fill_color") + ":", jcc1);
				jPanelOptions.addComponent(
						PluginServices.getText(this, "outline_color") + ":",
						jcc2);
				jPanelOptions.addComponent(
						PluginServices.getText(this, "outline_width"),
						txtWidth = new JIncrementalNumberField(String
								.valueOf(3), 3, 0, Double.MAX_VALUE, 1));
				jPanelOptions.addComponent(
						PluginServices.getText(this, "units") + ":",
						cmbUnits = new JComboBoxUnits());
				jPanelOptions
						.addComponent(
								"",
								cmbReferenceSystem = new JComboBoxUnitsReferenceSystem());
			} else if (TextSymbolizer.class.isAssignableFrom(type)) {
				jcc1 = new ColorChooserPanel(true);
				jPanelOptions.addComponent(PluginServices.getText(this, "font")
						+ ":", getCmbFonts());

				jPanelOptions.addComponent(
						PluginServices.getText(this, "color") + ":", jcc1);
				jPanelOptions.addComponent(PluginServices.getText(this, "size")
						+ ":", cmbFontSize = new JComboBoxFontSizes());
				jPanelOptions.addComponent(
						PluginServices.getText(this, "units") + ":",
						cmbUnits = new JComboBoxUnits());
				jPanelOptions
						.addComponent(
								"",
								cmbReferenceSystem = new JComboBoxUnitsReferenceSystem());

				JPanel aux = new JPanel(
						new FlowLayout(FlowLayout.LEADING, 0, 1));
				aux.add(getBtnBold());
				aux.add(getBtnItalic());
				aux.add(getBtnUnderlined());
				jPanelOptions.addComponent(
						PluginServices.getText(this, "style") + ":", aux);
			}

			jcc1.setAlpha(255);

			if (txtSize != null) {
				txtSize.addActionListener(this);
				txtSize.addFocusListener(this);
			}
			if (cmbUnits != null)
				cmbUnits.addActionListener(this);
			if (cmbReferenceSystem != null)
				cmbReferenceSystem.addActionListener(this);
			if (jcc1 != null)
				jcc1.addActionListener(this);
			if (jcc2 != null)
				jcc2.addActionListener(this);
			if (txtWidth != null)
				txtWidth.addActionListener(this);
			if (cmbFontSize != null)
				cmbFontSize.addActionListener(this);
			if (txtAngle != null)
				txtAngle.addActionListener(this);
		}
		return jPanelOptions;
	}

	private JToggleButton getBtnUnderlined() {
		if (btnUnderlined == null) {
			btnUnderlined = new JToggleButton(PluginServices.getIconTheme()
					.get("underline-icon"));
		}
		return btnUnderlined;
	}

	private JToggleButton getBtnItalic() {
		if (btnItalic == null) {
			btnItalic = new JToggleButton(PluginServices.getIconTheme().get(
					"italic-icon"));
		}
		return btnItalic;
	}

	private JToggleButton getBtnBold() {
		if (btnBold == null) {
			btnBold = new JToggleButton(PluginServices.getIconTheme().get(
					"bold-icon"));
		}
		return btnBold;
	}

	private JComboBoxFonts getCmbFonts() {
		if (cmbFonts == null) {
			cmbFonts = new JComboBoxFonts();
		}
		return cmbFonts;
	}

	public WindowInfo getWindowInfo() {
		if (wi == null) {
			wi = new WindowInfo(WindowInfo.MODALDIALOG | WindowInfo.RESIZABLE);
			wi.setWidth(706);
			wi.setHeight(500);
			if (wiTitle == null) {
				wi.setTitle(PluginServices.getText(this, "symbol_selector"));
			} else {
				wi.setTitle(wiTitle);
			}
		}
		return wi;
	}

	protected JSplitPane getJSplitPane() throws ClassNotFoundException {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setResizeWeight(0.4);
			jSplitPane.setLeftComponent(getLeftJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane1());
		}
		return jSplitPane;
	}

	public Symbolizer getSelectedObject() {
		Symbolizer mySelectedElement = jPanelPreview.getSymbol();

		if (mySelectedElement != null) {
			// TODO gtintegration
			// mySelectedElement.setReferenceSystem(cmbReferenceSystem.getSelectedIndex());
			mySelectedElement.setUnitOfMeasure(SymbologyUtils
					.convert2JavaUnits(cmbUnits.getSelectedUnitIndex()));
		}
		return mySelectedElement;
	}

	public void setSymbol(Symbolizer symbol) {
		jPanelPreview.setSymbol(symbol);
		updateOptionsPanel();
	}

	/**
	 * Invoked when the PROPERTIES button is pressed
	 */
	protected void propertiesPressed() {
		// boolean state = accepted;
		// accepted = true;
		Symbolizer mySelectedElement = jPanelPreview.getSymbol();
		if (mySelectedElement == null)
			return;

		FilterFactoryImpl factory = new FilterFactoryImpl();
		if (mySelectedElement instanceof PolygonSymbolizer && txtWidth != null) {
			PolygonSymbolizer f = (PolygonSymbolizer) mySelectedElement;
			Stroke stroke = f.getStroke();
			if (stroke != null) {
				stroke.setWidth(factory.literal(txtWidth.getDouble()));
			}
		}

		if (txtSize != null) {
			if (mySelectedElement instanceof PointSymbolizer) {
				PointSymbolizer m = (PointSymbolizer) mySelectedElement;
				m.getGraphic().setSize(factory.literal(txtSize.getDouble()));
			} else if (mySelectedElement instanceof LineSymbolizer) {
				LineSymbolizer l = (LineSymbolizer) mySelectedElement;
				l.getStroke().setWidth(factory.literal(txtSize.getDouble()));
			}
		}

		SymbolEditor se = new SymbolEditor(mySelectedElement, type);
		PluginServices.getMDIManager().addWindow(se);

		Symbolizer symbol = se.getSymbol();
		if (symbol != null) {
			setSymbol(symbol);
		}
		// accepted = state;
	}

	/**
	 * Invoked when the NEW button is pressed
	 */
	protected void newPressed() {
		SymbolEditor se = new SymbolEditor(null, type);
		PluginServices.getMDIManager().addWindow(se);
		setSymbol(se.getSymbol());
	}

	/**
	 * Invoked when the RESET button is pressed
	 */
	protected void resetPressed() {
		setSymbol(null);
	}

	/**
	 * Invoked when the SAVE button is pressed
	 */
	protected void savePressed() {
		if (getSelectedObject() == null)
			return;

		JFileChooser jfc = new JFileChooser("SYMBOL_SELECTOR_FILECHOOSER",
				rootDir);
		javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.getAbsolutePath().toLowerCase()
						.endsWith(SYMBOL_FILE_EXTENSION)
						|| f.isDirectory();
			}

			public String getDescription() {
				return PluginServices.getText(this,
						"gvSIG_symbol_definition_file") + " (*.sym)";
			}
		};
		jfc.setFileFilter(ff);
		JPanel accessory = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		accessory.add(new JLabel(PluginServices.getText(this,
				"enter_description")));
		JTextField txtDesc = new JTextField(25);
		accessory.add(txtDesc);
		jfc.setAccessory(accessory);
		if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File targetFile = jfc.getSelectedFile();

			// apply description
			String desc;
			if (txtDesc.getText() == null
					|| txtDesc.getText().trim().equals("")) {
				// default to file name
				String s = targetFile.getAbsolutePath();
				desc = s.substring(s.lastIndexOf(File.separator) + 1)
						.replaceAll(SYMBOL_FILE_EXTENSION, "");
			} else {
				desc = txtDesc.getText().trim();
			}
			Symbolizer s = getSelectedObject();
			s.getDescription().setAbstract(desc);
			s.getDescription().setTitle(desc);

			String symbolFileName = targetFile.getAbsolutePath()
					.substring(
							targetFile.getAbsolutePath().lastIndexOf(
									File.separator) + 1,
							targetFile.getAbsolutePath().length());
			File targetDir = new File(targetFile.getAbsolutePath().substring(0,
					targetFile.getAbsolutePath().lastIndexOf(File.separator)));
			library.addElement(s, symbolFileName, targetDir);
			getJListSymbols().setModel(newListModel());

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (!act)
			return;
		Symbolizer selectedElement = jPanelPreview.getSymbol();
		performActionOn(selectedElement, (JComponent) e.getSource());
		SymbolSelector.this.repaint();
	}

	protected void performActionOn(Symbolizer selectedElement, JComponent comp) {
		FilterFactoryImpl factory = new FilterFactoryImpl();

		if (comp.equals(getBtnProperties())) {
			propertiesPressed();
		} else if (comp.equals(getBtnNewSymbol())) {
			newPressed();
		} else if (comp.equals(getBtnResetSymbol())) {
			resetPressed();
		} else if (comp.equals(getBtnSaveSymbol())) {
			savePressed();
		} else if (comp.equals(jcc1)) {
			if (selectedElement == null)
				return;

			Color c = jcc1.getColor();

			if (selectedElement instanceof PointSymbolizer) {
				PointSymbolizer m = (PointSymbolizer) selectedElement;
				for (GraphicalSymbol symbol : m.getGraphic().graphicalSymbols()) {
					if (symbol instanceof Mark) {
						Mark mark = (Mark) symbol;
						mark.getFill().setColor(factory.literal(c));
						mark.getStroke().setColor(factory.literal(c));

						mark.getFill().setOpacity(
								factory.literal(c.getAlpha() / 255.0));
						mark.getStroke().setOpacity(
								factory.literal(c.getAlpha() / 255.0));
					}
				}
			} else if (selectedElement instanceof LineSymbolizer) {
				LineSymbolizer l = (LineSymbolizer) selectedElement;
				l.getStroke().setColor(factory.literal(c));
			} else if (selectedElement instanceof PolygonSymbolizer) {
				PolygonSymbolizer f = (PolygonSymbolizer) selectedElement;
				if (!jcc1.getUseColorisSelected()) {
					f.getFill().setOpacity(factory.literal(0));
				} else {
					f.getFill().setOpacity(
							factory.literal(c.getAlpha() / 255.0));
				}
				f.getFill().setColor(factory.literal(c));
			} else if (selectedElement instanceof TextSymbolizer) {
				TextSymbolizer t = (TextSymbolizer) selectedElement;
				t.getFill().setColor(factory.literal(c));
			}
		} else if (comp.equals(jcc2)) {
			if (selectedElement == null)
				return;
			Color c = jcc2.getColor();

			if (selectedElement instanceof PolygonSymbolizer) {
				PolygonSymbolizer f = (PolygonSymbolizer) selectedElement;
				Stroke stroke = f.getStroke();

				if (!jcc2.getUseColorisSelected()) {
					f.getStroke().setOpacity(factory.literal(0));
				} else {
					f.getStroke().setOpacity(
							factory.literal(c.getAlpha() / 255.0));
				}

				stroke.setColor(factory.literal(c));
			}
		} else if (comp.equals(txtSize)) {
			double s = txtSize.getDouble();

			if (selectedElement instanceof PointSymbolizer) {
				PointSymbolizer m = (PointSymbolizer) selectedElement;
				m.getGraphic().setSize(factory.literal(s));
			} else if (selectedElement instanceof LineSymbolizer) {
				LineSymbolizer l = (LineSymbolizer) selectedElement;
				l.getStroke().setWidth(factory.literal(s));
			}
		} else if (comp.equals(cmbUnits)) {
			selectedElement.setUnitOfMeasure(SymbologyUtils
					.convert2JavaUnits(cmbUnits.getSelectedUnitIndex()));
		} else if (comp.equals(cmbReferenceSystem)) {
			// TODO gtintegration
			// selectedElement.setReferenceSystem(cmbReferenceSystem.getSelectedIndex());
		} else if (comp.equals(txtWidth)) {
			double w = txtWidth.getDouble();
			if (selectedElement instanceof PolygonSymbolizer) {
				PolygonSymbolizer f = (PolygonSymbolizer) selectedElement;
				f.getStroke().setWidth(factory.literal(w));
			}
		} else if (comp.equals(cmbFontSize)) {
			double s = ((Integer) cmbFontSize.getSelectedItem()).doubleValue();
			if (selectedElement instanceof TextSymbolizer) {
				TextSymbolizer t = (TextSymbolizer) selectedElement;
				t.getFont().setSize(factory.literal(s));
			}
		} else if (comp.equals(txtAngle)) {
			double a = Math.toRadians(txtAngle.getDouble());
			if (selectedElement instanceof PointSymbolizer) {
				PointSymbolizer m = (PointSymbolizer) selectedElement;
				m.getGraphic().setRotation(factory.literal(a));
			}
		}
	}

	public static ISymbolSelector createSymbolSelector(Symbolizer currSymbol,
			Class<? extends Symbolizer> shapeType, boolean showAcceptPanel) {
		return createSymbolSelector(currSymbol, shapeType, null,
				showAcceptPanel);
	}

	public static ISymbolSelector createSymbolSelector(Symbolizer currSymbol,
			Class<? extends Symbolizer> shapeType) {
		return createSymbolSelector(currSymbol, shapeType, null);
	}

	public static ISymbolSelector createSymbolSelector(Symbolizer currSymbol,
			Class<? extends Symbolizer> shapeType, SelectorFilter filter) {
		return createSymbolSelector(currSymbol, shapeType, filter, true);
	}

	public static ISymbolSelector createSymbolSelector(Symbolizer currSymbol,
			Class<? extends Symbolizer> shapeType, SelectorFilter filter,
			boolean showAcceptPanel) {
		if (GeometryCollection.class.isAssignableFrom(shapeType)) {
			// TODO gtintegration
			throw new UnsupportedOperationException();
			// return new MultiShapeSymbolSelector(currSymbol);
		} else {
			if (filter == null) {
				return new SymbolSelector(currSymbol, shapeType, true,
						showAcceptPanel);
			} else {
				return new SymbolSelector(currSymbol, shapeType, filter, true);
			}
		}
	}

	class SillyDragNDropAction implements MouseListener, MouseMotionListener {
		private boolean doDrop = false;
		private Symbolizer selected;
		private File sourceFolder;

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			if (e.getSource().equals(getJListSymbols())) {
				selected = getJListSymbols().getSelectedValue();
				doDrop = selected != null;
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) libraryBrowser
						.getLastSelectedPathComponent();
				if (node.getUserObject() instanceof File) {
					sourceFolder = (File) node.getUserObject();
				}
			}
			e.consume();
		}

		public void mouseReleased(MouseEvent e) {
			if (doDrop && e.getSource().equals(getJListSymbols())) {
				java.awt.Point p = new java.awt.Point(getJListSymbols()
						.getLocation().x - e.getPoint().x, getJListSymbols()
						.getLocation().y + e.getPoint().y);
				if (libraryBrowser.getBounds().contains(p)) {
					File destFolder = libraryBrowser.getElementBellow(p);
					if (destFolder != null) {
						Symbolizer sym = selected;
						int move = InputEvent.SHIFT_DOWN_MASK
								| InputEvent.BUTTON1_DOWN_MASK;
						// int copy = MouseEvent.CTRL_DOWN_MASK |
						// MouseEvent.BUTTON1_DOWN_MASK;

						library.addElement(sym, sym.getDescription()
								.getAbstract().toString(), destFolder);
						if ((e.getModifiers() & (move)) != 0) {
							library.removeElement(sym, sourceFolder);
						}

					}
					libraryBrowser.refresh();
				}

			}
			doDrop = false;
		}

		public void mouseDragged(MouseEvent e) {
			if (e.getSource().equals(getJListSymbols())) {

				java.awt.Point p = new java.awt.Point(getJListSymbols()
						.getLocation().x - e.getPoint().x, getJListSymbols()
						.getLocation().y + e.getPoint().y);
				if (libraryBrowser.getBounds().contains(p)) {
					libraryBrowser.setSelectedElementBellow(p);
				}
			}
		}

		public void mouseMoved(MouseEvent e) {

		}

	}

	public void windowActivated() {
		// TODO Auto-generated method stub
	}

	public void windowClosed() {
		if (!accepted) {
			jPanelPreview.setSymbol(null);
		}
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

	public void setTitle(String title) {
		this.wiTitle = title;
	}

	public String getTitle() {
		return this.wiTitle;
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	public void focusLost(FocusEvent e) {
		if (!act)
			return;
		Symbolizer selectedElement = jPanelPreview.getSymbol();// getSelectedObject();
		performActionOn(selectedElement, (JComponent) e.getSource());
		SymbolSelector.this.repaint();

	}

}