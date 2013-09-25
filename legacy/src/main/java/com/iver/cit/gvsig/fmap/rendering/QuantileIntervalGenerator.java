package com.iver.cit.gvsig.fmap.rendering;

/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
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

import java.util.ArrayList;
import java.util.List;

/**
 * Calcula los intervalos en funci�n del n�mero de intervalos que se pidan.
 * 
 * @author Vicente Caballero Navarro
 */
public class QuantileIntervalGenerator {
	private int miNumIntervalosSolicitados;
	private double[] mdaValoresRuptura;
	private double[] mdaValInit;
	private int num = 0;
	private double[] values;
	private double min, max;

	public QuantileIntervalGenerator(double min, double max, double[] values,
			int numIntervals) {
		this.values = values;
		this.min = min;
		this.max = max;
		miNumIntervalosSolicitados = numIntervals;
	}

	public double[][] getIntervals() {
		generarIntervalos();

		int nIntervals = getNumIntervalGen();
		double[][] intervals = new double[nIntervals][];

		if (nIntervals > 1) {
			intervals[0] = new double[] { min, mdaValoresRuptura[0] };

			for (int i = 1; i < (nIntervals - 1); i++) {
				intervals[i] = new double[] { mdaValInit[i - 1],
						mdaValoresRuptura[i] };
			}

			intervals[nIntervals - 1] = new double[] {
					mdaValInit[nIntervals - 2], max };
		} else {
			intervals[nIntervals - 1] = new double[] { min, max };
		}

		return intervals;
	}

	private void generarIntervalos() {
		List<Double> ordenadas = new ArrayList<Double>();
		List<Integer> coincidencias = new ArrayList<Integer>();
		mdaValoresRuptura = new double[miNumIntervalosSolicitados - 1];
		mdaValInit = new double[miNumIntervalosSolicitados - 1];

		// int MARGEN = 5;
		for (int i = 0; i < values.length; i++) {
			insertarEnVector(ordenadas, coincidencias, values[i]);
		}

		int index = 0;
		int posj = 0;

		for (int i = 1; i < miNumIntervalosSolicitados; i++) {
			int x = (i * values.length) / miNumIntervalosSolicitados;

			for (int j = posj; j < ordenadas.size(); j++) {
				int auxcoin = coincidencias.get(j);
				index = index + auxcoin;

				if (x <= index) {
					mdaValoresRuptura[i - 1] = ordenadas.get(j);

					/*
					 * index = (int) ((x + (auxcoin /
					 * miNumIntervalosSolicitados)) - 1);
					 */
					posj = j + 1;

					if (posj < ordenadas.size()) {
						mdaValInit[i - 1] = ordenadas.get(posj);
					} else {
						mdaValInit[i - 1] = ordenadas.get(j);
					}

					num++;

					break;
				}
			}

			// double value=getValue(sds.getFieldValue(x,pos));
		}

		// }
	}

	/**
	 * Esta funci�n busca en el vector de datos la posici�n que le corresponde
	 * al valor almacenado en vdValor y devuelve dicha posici�n en
	 * vdValorAInsertar. Para hallar la posici�n se realiza una b�squeda
	 * binaria. Si se trata de un elemento que ya est� en el vector devolvemos
	 * el �ndice que le corresponde en rlIndiceCorrespondiente y false en
	 * rbNuevoElemento. Si se trata de un nuevo elemento que hay que insertar...
	 * devolvemos el �ndice en el que ir�a y True en rbNuevoElemento En caso de
	 * que ocurra alg�n error devuelve false
	 * 
	 * @param rVectorDatos
	 *            ArrayList con los datos.
	 * @param coincidencia
	 *            �ndice.
	 * @param vdValorAInsertar
	 *            Valor a insertar.
	 */
	private void insertarEnVector(List<Double> rVectorDatos,
			List<Integer> coincidencia, double valorAInsertar) {
		int llIndiceIzq;
		int llIndiceDer;
		int llMedio;
		int indice = -1;
		double ldValorComparacion;

		if (rVectorDatos.size() == 0) {
			rVectorDatos.add(valorAInsertar);
			coincidencia.add(new Integer(1));

			return;
		}

		llIndiceIzq = 0;
		llIndiceDer = rVectorDatos.size() - 1;
		llMedio = (llIndiceIzq + llIndiceDer) / 2; // 'Divisi�n entera!

		while (llIndiceIzq <= llIndiceDer) {
			// 'Coger el valor situado en la mitad de la zona de b�squeda como
			// valor de comparaci�n
			ldValorComparacion = rVectorDatos.get(llMedio);

			// 'Si el valor a insertar es mayor que el valor de comparaci�n...
			if (valorAInsertar > ldValorComparacion) {
				// 'La zona de b�squeda queda restringida a la parte de la
				// derecha
				llIndiceIzq = llMedio + 1;
				llMedio = (llIndiceIzq + llIndiceDer) / 2;

				// 'Si el valor a insertar es menor que el valor de
				// comparaci�n...
			} else if (valorAInsertar < ldValorComparacion) {
				// 'La zona de b�squeda queda restringida a la parte de la
				// derecha
				llIndiceDer = llMedio - 1;
				llMedio = (llIndiceIzq + llIndiceDer) / 2;

				// 'Si el valor de comparaci�n coincide con el valor a insertar
			} else if (valorAInsertar == ldValorComparacion) {
				indice = llMedio;

				int index = rVectorDatos.indexOf(valorAInsertar);
				coincidencia.set(index, new Integer(coincidencia.get(index)
						.intValue() + 1));

				return;
			}
		}

		// 'Nota:
		// 'En este caso (cuando en rbNuevoElemento se devuelve True) lo que hay
		// que hacer al salir de esta funci�n
		// 'es a�adir un nuevo elemento al vector y desplazar todos los valores
		// correspondientes a partir de rlIndiceCorrespondiente
		// '�D�nde va el nuevo elemento?
		// 'El �ltimo sitio estudiado viene dado por el valor de llMedio.
		// 'Si el valor a insertar es menor que el valor almacenado en la
		// posici�n llMedio, el nuevo valor deber� ir a su izquierda.
		// 'Si fuera mayor deber�a ir a su derecha.
		ldValorComparacion = rVectorDatos.get(llMedio);

		if (valorAInsertar > ldValorComparacion) {
			indice = llMedio + 1;
		} else {
			indice = llMedio;
		}

		rVectorDatos.add(indice, valorAInsertar);
		coincidencia.add(indice, new Integer(1));
	}

	/**
	 * Devuelve el n�mero de intervalos que se han generado.
	 * 
	 * @return N�mero de intervalos generados.
	 */
	private int getNumIntervalGen() {
		return num + 1;
	}
}
