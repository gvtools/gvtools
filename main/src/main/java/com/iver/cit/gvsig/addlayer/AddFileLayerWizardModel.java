package com.iver.cit.gvsig.addlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import org.gvsig.layer.Layer;

public class AddFileLayerWizardModel extends AbstractListModel<String> {
	private List<String> files = new ArrayList<String>();
	private List<Layer> layers = new ArrayList<Layer>();
	private int[] selected;

	public void add(String file, Layer layer) throws IOException {
		files.add(file);
		layers.add(layer);
		fireContentsChanged(this, 0, files.size());
	}

	public void remove() {
		if (isRemoveEnabled()) {
			Arrays.sort(selected);
			for (int i = selected.length - 1; i >= 0; i--) {
				files.remove(selected[i]);
				layers.remove(selected[i]);
			}
		}
	}

	public void up() {
		if (isUpEnabled()) {
			Collections.swap(files, selected[0], selected[0] - 1);
			Collections.swap(layers, selected[0], selected[0] - 1);
		}
	}

	public void down() {
		if (isDownEnabled()) {
			Collections.swap(files, selected[0], selected[0] + 1);
			Collections.swap(layers, selected[0], selected[0] + 1);
		}
	}

	public void setSelected(int[] index) {
		this.selected = index;
	}

	@Override
	public int getSize() {
		return files.size();
	}

	@Override
	public String getElementAt(int index) {
		return files.get(index);
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public boolean isUpEnabled() {
		return selected != null && selected.length == 1 && files.size() > 1
				&& selected[0] > 0;
	}

	public boolean isDownEnabled() {
		return selected != null && selected.length == 1 && files.size() > 1
				&& selected[0] < files.size() - 1;
	}

	public boolean isRemoveEnabled() {
		return selected != null && selected.length > 0;
	}
}
