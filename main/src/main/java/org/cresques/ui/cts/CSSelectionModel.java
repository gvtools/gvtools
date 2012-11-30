/*
 * Cresques Mapping Suite. Graphic Library for constructing mapping applications.
 *
 * Copyright (C) 2004-5.
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 * cresques@gmail.com
 */
package org.cresques.ui.cts;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * @author Luis W. Sevilla (sevilla_lui@gva.es)
 */
public class CSSelectionModel {
	public static int NONE = 0x01;
	public static int GEODETIC = 0x02;
	public static int TRANSVERSAL = 0x03;
	public String[] datumList = { "WGS 84", "European 1950", "Datum 73",
			"North American Datum 1927", "North American Datum 1983",
			"La Canoa", "NTF (Paris)", "ETRS 89", "Moon (IAU 2000)",
			"Mars (IAU 2000)", "Campo_Inchauspe", "RGF93" };
	/*
	 * private String[] projList = { "Geodesica",
	 * "(UTM) Universal Transversal Mercator",
	 * "Datum 73 / Modified Portuguese Grid", "WGS 84 / LCC Canada",
	 * "NAD83 / NRCan LCC Canada", "Lambert Etendu", "RGF93 / Lambert-93",
	 * "WGS 84 / Mercator", "Campo Inchauspe / Zonas 1-7" };
	 */
	private String[][] projList = {
			{ "Geodesica", "(UTM) Universal Transversal Mercator",
					"LCC Canada", "Mercator" }, // WGS84
			{ "Geodesica", "(UTM) Universal Transversal Mercator" }, // ED50
			{ "Geodesica", "Modified Portuguese Grid" }, // Datum 73
			{ "Geodesica", "(UTM) Universal Transversal Mercator" }, // NAD27
			{ "Geodesica", "(UTM) Universal Transversal Mercator",
					"NRCan LCC Canada" }, // NAD83
			{ "Geodesica", "(UTM) Universal Transversal Mercator" }, // La Canoa
			{ "Geodesica", "Lambert Etendu" }, // NTF (Paris)
			{ "Geodesica", "(UTM) Universal Transversal Mercator" }, // ETRS89
			{ "Geodesica" }, // Moon
			{ "Geodesica" }, // Mars
			{ "Geodesica", "Zonas 1-7" }, // CampoInchauspe
			{ "Geodesica", "Lambert-93" } // RFG93
	};

	private int[][] projType = { { GEODETIC, TRANSVERSAL, NONE, NONE },
			{ GEODETIC, TRANSVERSAL }, { GEODETIC, NONE },
			{ GEODETIC, TRANSVERSAL }, { GEODETIC, TRANSVERSAL, NONE },
			{ GEODETIC, TRANSVERSAL }, { GEODETIC, NONE },
			{ GEODETIC, TRANSVERSAL }, { GEODETIC }, { GEODETIC },
			{ GEODETIC, TRANSVERSAL }, { GEODETIC, NONE } };

	private int[][] zoneRange = { { 1, 60 }, { 1, 60 }, { 0, 0 }, { 3, 23 },
			{ 3, 23 }, { 18, 21 }, { 0, 0 }, { 28, 38 }, { 0, 0 }, { 0, 0 },
			{ 1, 7 }, { 0, 0 } };
	private String[] zoneList = {};
	private int selectedDatum = 0;
	private int selectedProj = 0;
	private int selectedZone = 0;

	/**
     *
     */
	public CSSelectionModel() {
		super();
		zoneList = new String[60];

		for (int i = 1; i <= 60; i++)
			zoneList[i - 1] = "Huso " + Integer.toString(i);
	}

	public String[] getDatumList() {
		return datumList;
	}

	public String[] getProjectionList() {
		return projList[getSelectedDatum()];
	}

	public String[] getZoneList() {
		int[] r = zoneRange[getSelectedDatum()];
		return getZoneList(r[0], r[1]);
	}

	public String[] getZoneList(int from, int to) {
		zoneList = new String[to - from + 1];

		for (int i = from; i <= to; i++)
			zoneList[i - from] = "Huso " + Integer.toString(i);
		return zoneList;
	}

	public void setSelectedDatum(int opNr) {
		selectedDatum = opNr;
	}

	public void setSelectedDatum(Object item) {
		String[] list = datumList;

		for (int i = 0; i < list.length; i++)
			if (list[i].compareTo((String) item) == 0) {
				selectedDatum = i;
				break;
			}
	}

	public int getSelectedDatum() {
		return selectedDatum;
	}

	public void setSelectedProj(int opNr) {
		selectedProj = opNr;
	}

	public void setSelectedProj(Object item) {
		String[] list = projList[getSelectedDatum()];

		for (int i = 0; i < list.length; i++)
			if (list[i].compareTo((String) item) == 0) {
				selectedProj = i;
				break;
			}
	}

	public int getSelectedProj() {
		return selectedProj;
	}

	public int getSelectedProjType() {
		return projType[getSelectedDatum()][selectedProj];
	}

	public void setSelectedZone(int opNr) {
		selectedZone = opNr;
	}

	public void setSelectedZone(Object item) {
		String[] list = zoneList;

		for (int i = 0; i < list.length; i++)
			if (list[i].compareTo((String) item) == 0) {
				selectedZone = i;
				break;
			}
	}

	public int getSelectedZone() {
		return selectedZone;
	}

	public void setCrs(CoordinateReferenceSystem crs) {
		String key = CRS.toSRS(crs);
		// Para usos posteriores.
		// String db = key.split(":")[0];
		key = key.split(":")[1];

		setSelectedProj(0);
		setSelectedZone(-1);

		if (key.endsWith("4326")) {
			setSelectedDatum(0);
		} else if (key.endsWith("4230")) {
			setSelectedDatum(1);
		} else if (key.endsWith("4274")) {
			setSelectedDatum(2);
		} else if (key.endsWith("4267")) {
			setSelectedDatum(3);
		} else if (key.endsWith("4269")) {
			setSelectedDatum(4);
		} else if (key.endsWith("4247")) {
			setSelectedDatum(5);
		} else if (key.endsWith("4807")) {
			setSelectedDatum(6);
		} else if (key.endsWith("4258")) {
			setSelectedDatum(7);
		} else if (key.endsWith("30100")) {
			setSelectedDatum(8);
		} else if (key.endsWith("49900")) {
			setSelectedDatum(9);
		} else if (key.endsWith("4221")) {
			setSelectedDatum(10);
		} else if (key.endsWith("4171")) {
			setSelectedDatum(11);
		} else if (key.startsWith("326")) {
			setSelectedDatum(0);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("230")) {
			setSelectedDatum(1);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("267")) {
			setSelectedDatum(3);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("269")) {
			setSelectedDatum(4);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("247")) {
			setSelectedDatum(5);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("258")) {
			setSelectedDatum(7);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(3)));
		} else if (key.startsWith("221")) {
			setSelectedDatum(10);
			setSelectedProj(1);
			setSelectedZone("Huso " + Integer.parseInt(key.substring(4)));
		} else if (key.endsWith("27492")) { // Datum 73 / Modified Portuguese
											// Grid
			setSelectedDatum(2);
			setSelectedProj(1);
			setSelectedZone(-1);
		} else if (key.endsWith("42101")) { // WGS 84 / LCC Canada
			setSelectedDatum(0);
			setSelectedProj(2);
			setSelectedZone(-1);
		} else if (key.endsWith("9804")) { // WGS 84 / Mercator
			setSelectedDatum(0);
			setSelectedProj(3);
			setSelectedZone(-1);
		} else if (key.endsWith("30100")) { // Moon
			setSelectedDatum(8);
			setSelectedProj(0);
			setSelectedZone(-1);
		} else if (key.endsWith("49900")) { // Mars
			setSelectedDatum(9);
			setSelectedProj(0);
			setSelectedZone(-1);
		} else if (key.endsWith("42304")) { // NAD83 / / LCC Canada
			setSelectedDatum(4);
			setSelectedProj(2);
			setSelectedZone(-1);
		} else if (key.endsWith("27582")) { // NTFParis / / Lambert Etendu
			setSelectedDatum(6);
			setSelectedProj(1);
			setSelectedZone(-1);
		} else if (key.equals("2154")) { // RGF93 / / Lambert 93
			setSelectedDatum(11);
			setSelectedProj(1);
			setSelectedZone(-1);
		} else {
			System.err.println("CAGADA EN EL PARSING DE LA PROYECCION: " + key);
		}
	}

	public CoordinateReferenceSystem getCrs() {
		try {
			CoordinateReferenceSystem crs = null;
			String datum = "326";

			if (selectedDatum == 0) {
				datum = "326";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 1);
					if (selectedZone < 9)
						zone = "0" + zone;
					return CRS.decode("EPSG:" + datum + zone);
				} else if (selectedProj == 2)
					return CRS.decode("EPSG:42101");
				else if (selectedProj == 3)
					return CRS.decode("EPSG:9804");

			} else if (selectedDatum == 1) {
				datum = "230";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 1);
					if (selectedZone < 9)
						zone = "0" + zone;
					return CRS.decode("EPSG:" + datum + zone);
				}

			} else if (selectedDatum == 2) { // Lisboa 73
				datum = "274";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1)
					return CRS.decode("EPSG:" + datum + "92");

			} else if (selectedDatum == 3) {
				datum = "267";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 3);
					if (selectedZone + 3 <= 9)
						zone = "0" + zone;
					return CRS.decode("EPSG:" + datum + zone);
				}

			} else if (selectedDatum == 4) {
				datum = "269";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 3);
					if (selectedZone + 3 <= 9)
						zone = "0" + zone;
					return CRS.decode("EPSG:" + datum + zone);
				} else if (selectedProj == 2)
					return CRS.decode("EPSG:42304");

			} else if (selectedDatum == 5) {
				datum = "247";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 18);
					return CRS.decode("EPSG:" + datum + zone);
				}

			} else if (selectedDatum == 6) {
				datum = "807";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1)
					return CRS.decode("EPSG:27582");

			} else if (selectedDatum == 7) {
				datum = "258";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 28);
					return CRS.decode("EPSG:" + datum + zone);
				}

			} else if (selectedDatum == 8) {
				datum = "30100";
				return CRS.decode("IAU2000:" + datum);

			} else if (selectedDatum == 9) {
				datum = "49900";
				return CRS.decode("IAU2000:" + datum);

			} else if (selectedDatum == 10) {
				datum = "221";
				if (selectedProj == 0)
					return CRS.decode("EPSG:4" + datum);
				else if (selectedProj == 1) {
					String zone = Integer.toString(selectedZone + 1);
					return CRS.decode("EPSG:" + datum + "9" + zone);
				}

			} else if (selectedDatum == 11) {
				if (selectedProj == 0)
					return CRS.decode("EPSG:4171");
				else if (selectedProj == 1)
					return CRS.decode("EPSG:2154");
			}

			if (selectedProj == 2) {
				return CRS.decode("EPSG:27492");
			} else if (selectedProj == 3) {
				return CRS.decode("EPSG:42101");
			} else if (selectedProj == 4) {
				return CRS.decode("EPSG:42304");
			} else if (selectedProj == 5) {
				return CRS.decode("EPSG:27582");
			} else if (selectedProj == 6) {
				return CRS.decode("EPSG:2154");
			} else if (selectedProj == 7) {
				return CRS.decode("EPSG:9804");
			} else if (selectedProj == 8) {
				datum = "221";
				String zone = "9";
				// if (selectedDatum == 10) {
				if (selectedZone < 1) {
					zone += "1";
				} else if (selectedZone > 7) {
					zone += "7";
				} else
					zone += (selectedZone + 1) + "";
				// }
				return CRS.decode("EPSG:" + datum + zone);
			}

			if (selectedProj == 0) {
				return CRS.decode("EPSG:4" + datum);
			} else if (selectedProj == 1) {
				String Zone = Integer.toString(selectedZone + 1);

				if (selectedZone < 9) {
					Zone = "0" + Zone;
				}

				if ((selectedDatum == 2) || (selectedDatum == 3)) {
					if (selectedZone < 3) {
						Zone = "03";
					}

					if (selectedZone > 23) {
						Zone = "23";
					}
				}

				if (selectedDatum == 5) {
					if (selectedZone < 18) {
						Zone = "18";
					}

					if (selectedZone > 22) {
						Zone = "22";
					}
				}

				if (selectedDatum == 7) {
					if (selectedZone < 28) {
						Zone = "28";
					}

					if (selectedZone > 38) {
						Zone = "38";
					}
				}
				return CRS.decode("EPSG:" + datum + Zone);
			}

			return crs;
		} catch (NoSuchAuthorityCodeException e) {
			// Ey you!, it's time to refactor the code above, come on
			throw new RuntimeException("Bug", e);
		} catch (FactoryException e) {
			// Ey you!, it's time to refactor the code above, come on
			throw new RuntimeException("Bug", e);
		}
	}
}
