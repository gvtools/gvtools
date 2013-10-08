package org.gvsig.symbology.gui.layerproperties;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.AcceptCancelPanel;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.legend.Legend;
import org.gvsig.legend.LegendFactory;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.QuantityByCategoryLegend;
import org.gvsig.legend.impl.SizeIntervalLegend;
import org.opengis.feature.type.AttributeDescriptor;

import com.google.inject.Inject;
import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.project.documents.view.legend.gui.ILegendPanel;
import com.iver.cit.gvsig.project.documents.view.legend.gui.MultipleAttributes;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolTable;
import com.iver.cit.gvsig.project.documents.view.legend.gui.VectorialInterval;
import com.iver.utiles.swing.JComboBox;

/**
 * Implements the panel of a legend where the user can compare two different
 * characteristics of a region in the map. These two "fields" will be compared,
 * on one side, using a color for the region and , on the other side, using a
 * graduated symbol. Both methods will change (the color or the size of the
 * symbol) depending on the value of the fields.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class QuantityByCategory extends JPanel implements ILegendPanel,
		ActionListener {
	private static final Logger logger = Logger
			.getLogger(QuantityByCategory.class);

	private static final long serialVersionUID = 5098346573350040756L;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	// private QuantityByCategoryLegend legend;
	private SizeIntervalLegend sizeLegend;
	private IntervalLegend colorLegend;
	private GridBagLayoutPanel pnlFields;
	private JPanel pnlColorAndSymbol;
	private JComboBox<String> cmbColorField;
	private JComboBox<String> cmbGraduatedSymbolField;
	private JButton btnColor;
	private JButton btnSymbol;
	private Layer layer;
	private SymbolTable symbolTable;

	@Inject
	private LegendFactory legendFactory;

	/**
	 * Constructor method
	 */
	public QuantityByCategory() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(490, 300);
		this.add(getPnlNorth(), BorderLayout.NORTH);
		this.add(getPnlSouth(), BorderLayout.CENTER);
		// this.add(getPnlButtons(),BorderLayout.SOUTH);
	}

	/**
	 * Obtains the south panel
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlSouth() {
		if (pnlSouth == null) {
			pnlSouth = new JPanel();
			pnlSouth.setLayout(new BorderLayout());
		}
		return pnlSouth;
	}

	/**
	 * Obtains the north panel
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlNorth() {
		if (pnlNorth == null) {
			pnlNorth = new JPanel(new GridLayout(1, 2));
			pnlNorth.add(getPnlFields());
			pnlNorth.add(getPnlColorAndSymbol());
		}

		return pnlNorth;
	}

	/**
	 * Obtains the panel where the user has the options to select the variation
	 * to be applied
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlColorAndSymbol() {
		if (pnlColorAndSymbol == null) {
			pnlColorAndSymbol = new JPanel();
			pnlColorAndSymbol.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "variation_by")));
			pnlColorAndSymbol.add(getBtnColor());
			pnlColorAndSymbol.add(getBtnSymbol());
		}

		return pnlColorAndSymbol;
	}

	/**
	 * Creates the button which is used to selecte the variation by symbol
	 * 
	 * @return JButton
	 */
	private JButton getBtnSymbol() {
		if (btnSymbol == null) {
			btnSymbol = new JButton(PluginServices.getText(this, "symbol"));
			btnSymbol.addActionListener(this);
		}
		return btnSymbol;
	}

	/**
	 * Creates the button which is used to selecte the variation by color ramp
	 * 
	 * @return JButton
	 */
	private JButton getBtnColor() {
		if (btnColor == null) {
			btnColor = new JButton(PluginServices.getText(this, "color_ramp"));
			btnColor.addActionListener(this);
		}

		return btnColor;
	}

	/**
	 * Creates the panel where the JComboBoxes to select the fields for the
	 * variation will be placed
	 * 
	 * @return GridBagLayoutPanel
	 */
	private GridBagLayoutPanel getPnlFields() {
		if (pnlFields == null) {
			pnlFields = new GridBagLayoutPanel();
			pnlFields.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "value_fields")));
			pnlFields.addComponent(PluginServices.getText(this, "color_field"),
					getCmbColorField());
			pnlFields.addComponent(
					PluginServices.getText(this, "symbol_field"),
					getCmbGraduatedField());
		}

		return pnlFields;
	}

	/**
	 * Creates a JComboBox where the user will select the field for the symbol
	 * variation
	 * 
	 * @return JComboBox
	 */
	private JComboBox getCmbGraduatedField() {
		if (cmbGraduatedSymbolField == null) {
			cmbGraduatedSymbolField = new JComboBox<String>();
			cmbGraduatedSymbolField.addActionListener(this);
		}
		return cmbGraduatedSymbolField;
	}

	/**
	 * Creates a JComboBox where the user will select the field for the color
	 * ramp variation
	 * 
	 * @return JComboBox
	 */
	private JComboBox getCmbColorField() {
		if (cmbColorField == null) {
			cmbColorField = new JComboBox<String>();
			cmbColorField.addActionListener(this);
		}
		return cmbColorField;
	}

	@Override
	public void setData(Layer lyr, Legend l) throws IOException {
		this.layer = lyr;

		if (symbolTable != null) {
			pnlSouth.remove(symbolTable);
		}

		symbolTable = new SymbolTable(SymbolTable.INTERVALS_TYPE,
				layer.getShapeType());
		pnlSouth.add(symbolTable, BorderLayout.CENTER);
		fillFieldNames();

		if (l instanceof QuantityByCategoryLegend) {
			QuantityByCategoryLegend legend = (QuantityByCategoryLegend) l;
			this.sizeLegend = legend.getSizeIntervalLegend();
			this.colorLegend = legend.getColorIntervalLegend();
			cmbColorField.setSelectedItem(colorLegend.getFieldName());
			cmbGraduatedSymbolField.setSelectedItem(sizeLegend.getFieldName());
			symbolTable.removeAllItems();
			symbolTable.fillTable(legend.getSymbols(), legend.getIntervals());
		} else {
			this.sizeLegend = legendFactory.createSizeIntervalLegend(layer,
					getCmbGraduatedField().getSelectedItem().toString());
			this.colorLegend = legendFactory.createIntervalLegend(layer,
					getCmbColorField().getSelectedItem().toString());
		}
	}

	@Override
	public Legend getLegend() throws IOException {
		Map<Interval, Symbolizer> colorSymbols = new HashMap<Interval, Symbolizer>();
		Map<Interval, Symbolizer> sizeSymbols = new HashMap<Interval, Symbolizer>();

		for (int row = 0; row < symbolTable.getRowCount(); row++) {
			Interval interval = (Interval) symbolTable.getValue(row);
			Symbolizer symbol = symbolTable.getSymbol(row);
			String label = symbolTable.getLabel(row);
			symbol.setDescription(new DescriptionImpl(label, label));

			if (symbol instanceof PolygonSymbolizer) {
				colorSymbols.put(interval, symbol);
			} else if (symbol instanceof PointSymbolizer) {
				sizeSymbols.put(interval, symbol);
			}
		}
		String colorField = cmbColorField.getItemAt(cmbColorField
				.getSelectedIndex());
		Symbolizer colorDefaultSymbol = colorLegend.getDefaultSymbol();
		boolean colorUseDefault = colorLegend.useDefaultSymbol();
		Type colorType = colorLegend.getType();

		String sizeField = cmbGraduatedSymbolField
				.getItemAt(cmbGraduatedSymbolField.getSelectedIndex());
		Symbolizer sizeDefaultSymbol = sizeLegend.getDefaultSymbol();
		boolean sizeUseDefault = sizeLegend.useDefaultSymbol();
		Symbolizer background = sizeLegend.getBackground();
		Type sizeType = sizeLegend.getType();

		colorLegend = legendFactory.createIntervalLegend(colorSymbols,
				colorType, colorDefaultSymbol, colorUseDefault, layer,
				colorField);
		sizeLegend = legendFactory.createSizeIntervalLegend(sizeSymbols,
				sizeType, sizeDefaultSymbol, sizeUseDefault, layer, sizeField,
				background);

		return legendFactory.createQuantityByCategoryLegend(layer, colorLegend,
				sizeLegend);
	}

	public String getDescription() {
		return PluginServices
				.getText(this, "draw_quantities_for_each_category");
	}

	public ImageIcon getIcon() {
		return null;
		// TODO gtintegration
		// return new ImageIcon(this.getClass().getClassLoader()
		// .getResource("images/QuantitiesByCategory.png"));
	}

	public Class<MultipleAttributes> getParentClass() {
		return MultipleAttributes.class;
	}

	public String getTitle() {
		return PluginServices.getText(this, "quantity_by_category");
	}

	public JPanel getPanel() {
		return this;
	}

	public Class<QuantityByCategoryLegend> getLegendClass() {
		return QuantityByCategoryLegend.class;
	}

	public void actionPerformed(ActionEvent e) {
		JComponent c = (JComponent) e.getSource();

		try {
			if (c.equals(getBtnColor())) {
				updateColor();
			} else if (c.equals(getBtnSymbol())) {
				updateSize();
			} else if (c.equals(getCmbColorField())) {
				symbolTable.removeAllItems();
				symbolTable.fillTable(sizeLegend.getSymbols(),
						sizeLegend.getIntervals());
			} else if (c.equals(getCmbGraduatedField())) {
				symbolTable.removeAllItems();
				symbolTable.fillTable(colorLegend.getSymbols(),
						colorLegend.getIntervals());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void updateColor() throws IOException {
		String fieldName = (String) getCmbColorField().getSelectedItem();
		if (colorLegend == null
				|| !fieldName.equals(colorLegend.getFieldName())) {
			colorLegend = legendFactory.createIntervalLegend(layer, fieldName);
		}
		// create a new modal window to edit the color ramp legend
		VectorialInterval legPanel = InjectorSingleton.getInjector()
				.getInstance(VectorialInterval.class);
		legPanel.setData(layer, colorLegend);
		InnerWindow window = new InnerWindow(legPanel);
		PluginServices.getMDIManager().addWindow(window);
		cmbColorField.setSelectedItem(colorLegend.getFieldName());
		IntervalLegend newLegend = (IntervalLegend) legPanel.getLegend();
		if (newLegend != null) {
			colorLegend = newLegend;
		}
		symbolTable.removeAllItems();
		symbolTable.fillTable(colorLegend.getSymbols(),
				colorLegend.getIntervals());
		symbolTable.fillTable(sizeLegend.getSymbols(),
				sizeLegend.getIntervals());
	}

	private void updateSize() throws IOException {
		String fieldName = (String) getCmbGraduatedField().getSelectedItem();
		if (sizeLegend == null || !fieldName.equals(sizeLegend.getFieldName())) {
			sizeLegend = legendFactory.createSizeIntervalLegend(layer,
					fieldName);
		}

		GraduatedSymbols legPanel = InjectorSingleton.getInjector()
				.getInstance(GraduatedSymbols.class);
		legPanel.setData(layer, sizeLegend);
		InnerWindow window = new InnerWindow(legPanel);
		PluginServices.getMDIManager().addWindow(window);
		cmbGraduatedSymbolField.setSelectedItem(sizeLegend.getFieldName());
		SizeIntervalLegend newLegend = (SizeIntervalLegend) window.getLegend();
		if (newLegend != null) {
			sizeLegend = newLegend;
		}
		symbolTable.removeAllItems();
		symbolTable.fillTable(colorLegend.getSymbols(),
				colorLegend.getIntervals());
		symbolTable.fillTable(sizeLegend.getSymbols(),
				sizeLegend.getIntervals());
	}

	private class InnerWindow extends JPanel implements IWindow {
		private ActionListener okAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					legend = panel.getLegend();
				} catch (IOException e1) {
					logger.error("Cannot obtain legend", e1);
					NotificationManager.addError(e1);
				}
				PluginServices.getMDIManager().closeWindow(InnerWindow.this);
			}

		}, cancelAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PluginServices.getMDIManager().closeWindow(InnerWindow.this);
			}
		};
		private Legend legend;
		private ILegendPanel panel;
		private WindowInfo wi;

		public InnerWindow(ILegendPanel panel) {
			this.panel = panel;
			this.setLayout(new BorderLayout());
			add((JComponent) panel, BorderLayout.NORTH);
			add(new AcceptCancelPanel(okAction, cancelAction),
					BorderLayout.SOUTH);
		}

		public Legend getLegend() {
			return legend;
		}

		public WindowInfo getWindowInfo() {
			if (wi == null) {
				wi = new WindowInfo(WindowInfo.MODALDIALOG
						| WindowInfo.RESIZABLE);
				JComponent c = (JComponent) panel;
				wi.setWidth(c.getWidth());
				wi.setHeight(c.getHeight());
				wi.setTitle(panel.getDescription());
			}
			return wi;
		}

		public Object getWindowProfile() {
			return WindowInfo.DIALOG_PROFILE;
		}

	}

	protected void fillFieldNames() throws IOException {
		ArrayList<String> fields = new ArrayList<String>();
		List<AttributeDescriptor> attributes = layer.getFeatureSource()
				.getSchema().getAttributeDescriptors();
		for (AttributeDescriptor attribute : attributes) {
			if (isNumericField(attribute.getType().getBinding())) {
				fields.add(attribute.getLocalName());
			}
		}

		String[] fieldsArray = fields.toArray(new String[fields.size()]);
		cmbColorField.setModel(new DefaultComboBoxModel<String>(fieldsArray));
		cmbGraduatedSymbolField.setModel(new DefaultComboBoxModel<String>(
				fieldsArray));

		symbolTable.removeAllItems();
	}

	public boolean isSuitableFor(Layer layer) {
		if (layer.hasFeatures()) {
			try {
				List<AttributeDescriptor> attributes = layer.getFeatureSource()
						.getSchema().getAttributeDescriptors();
				for (AttributeDescriptor attribute : attributes) {
					if (isNumericField(attribute.getType().getBinding())) {
						return true;
					}
				}
				return false;
			} catch (Exception e) {
				logger.error("Cannot read layer metadata", e);
				return false;
			}
		}
		return false;
	}

	private boolean isNumericField(Class<?> fieldType) {
		return Integer.class.isAssignableFrom(fieldType)
				|| Double.class.isAssignableFrom(fieldType);
	}
}
