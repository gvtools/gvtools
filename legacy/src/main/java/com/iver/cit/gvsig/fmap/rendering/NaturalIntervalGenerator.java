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
package com.iver.cit.gvsig.fmap.rendering;

import java.util.ArrayList;
import java.util.List;

/**
 * Calcula los intervalos naturales.
 * 
 * @author Vicente Caballero Navarro
 */
public class NaturalIntervalGenerator {
	private int miNumIntervalosSolicitados;
	private int miNumIntervalosGenerados;
	private double[] mdaValoresRuptura;
	private double[] mdaValInit;
	private double[] values;
	private double min, max;

	public NaturalIntervalGenerator(double min, double max, double[] values,
			int numIntervals) {
		this.values = values;
		this.min = min;
		this.max = max;
		miNumIntervalosSolicitados = numIntervals;
	}

	public double[][] getIntervals() {
		generarIntervalos();

		int numIntervalsGen = getNumIntervals() - 1;

		if (numIntervalsGen == -1) {
			// TODO cuando no puede calcular los intervalos.
			numIntervalsGen = 1;
		}

		double[][] intervals = new double[numIntervalsGen][];

		if (numIntervalsGen > 1) {
			intervals[0] = new double[] { min, getValorRuptura(0) };

			for (int i = 1; i < (numIntervalsGen - 1); i++) {
				intervals[i] = new double[] { getValInit(i - 1),
						getValorRuptura(i) };
			}

			intervals[numIntervalsGen - 1] = new double[] {
					getValInit(numIntervalsGen - 2), max };
		} else {
			intervals[numIntervalsGen - 1] = new double[] { min, max };
		}

		return intervals;
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
	 * @param vdValorAInsertar
	 *            Valor a insertar.
	 * @param rlIndiceCorrespondiente
	 *            �ndice.
	 * @param rbNuevoElemento
	 *            True si es un nuevo elemento.
	 * 
	 * @return True si ha conseguido correctamente la posici�n en el vector.
	 */
	private boolean mbObtenerPosicionEnVector(
			List<udtDatosEstudio> rVectorDatos, double vdValorAInsertar,
			int[] rlIndiceCorrespondiente, boolean[] rbNuevoElemento) {
		int llIndiceIzq;
		int llIndiceDer;
		int llMedio;

		double ldValorComparacion;

		rbNuevoElemento[0] = false;
		rlIndiceCorrespondiente[0] = -1;

		// 'Si el vector estiviese vac�o... (tuviese un s�lo elemento y el
		// n�mero de coincidencias fuese 0)
		if (rVectorDatos.size() == 1) {
			if (rVectorDatos.get(0).Coincidencias == 0) {
				rlIndiceCorrespondiente[0] = 0;
				rbNuevoElemento[0] = false; // 'No tenemos que a�adir un nuevo
											// elemento al vector

				return true;
			}
		}

		llIndiceIzq = 0;
		llIndiceDer = rVectorDatos.size() - 1;
		llMedio = (llIndiceIzq + llIndiceDer) / 2; // 'Divisi�n entera!

		while (llIndiceIzq <= llIndiceDer) {
			// 'Coger el valor situado en la mitad de la zona de b�squeda como
			// valor de comparaci�n
			ldValorComparacion = rVectorDatos.get(llMedio).Valor;

			// 'Si el valor a insertar es mayor que el valor de comparaci�n...
			if (vdValorAInsertar > ldValorComparacion) {
				// 'La zona de b�squeda queda restringida a la parte de la
				// derecha
				llIndiceIzq = llMedio + 1;
				llMedio = (llIndiceIzq + llIndiceDer) / 2;

				// 'Si el valor a insertar es menor que el valor de
				// comparaci�n...
			} else if (vdValorAInsertar < ldValorComparacion) {
				// 'La zona de b�squeda queda restringida a la parte de la
				// derecha
				llIndiceDer = llMedio - 1;
				llMedio = (llIndiceIzq + llIndiceDer) / 2;

				// 'Si el valor de comparaci�n coincide con el valor a
				// insertar
			} else if (vdValorAInsertar == ldValorComparacion) {
				rlIndiceCorrespondiente[0] = llMedio;
				rbNuevoElemento[0] = false;

				return true;
			}
		}

		// 'Si llegamos a este punto es que no hemos encontrado el valor a
		// insertar en el vector, es decir,
		// 'seguro que tendremos que a�adir un nuevo elemento.
		rbNuevoElemento[0] = true;

		// 'Nota:
		// 'En este caso (cuando en rbNuevoElemento se devuelve True) lo que hay
		// que hacer al salir de esta funci�n
		// 'es a�adir un nuevo elemento al vector y desplazar todos los
		// valores correspondientes a partir de rlIndiceCorrespondiente
		// '�D�nde va el nuevo elemento?
		// 'El �ltimo sitio estudiado viene dado por el valor de llMedio.
		// 'Si el valor a insertar es menor que el valor almacenado en la
		// posici�n llMedio, el nuevo valor deber� ir a su izquierda.
		// 'Si fuera mayor deber�a ir a su derecha.
		ldValorComparacion = rVectorDatos.get(llMedio).Valor;

		if (vdValorAInsertar > ldValorComparacion) {
			rlIndiceCorrespondiente[0] = llMedio + 1;
		} else {
			rlIndiceCorrespondiente[0] = llMedio;
		}

		return true;
	}

	/**
	 * M�todo para generar los intervalos.
	 * 
	 * @return true si se han generado correctamente.
	 * 
	 * @throws com.iver.cit.gvsig.fmap.DriverException
	 * @throws DriverException
	 */
	private boolean generarIntervalos() {
		List<udtDatosEstudio> lVectorDatos;
		double[] ldMediaTotal = new double[1];
		double[] ldSDAM = new double[1];

		int[] llaIndicesRupturas;

		double[] ldUltimaGVF = new double[1];
		double[] ldNuevaGVF = new double[1];

		int i;
		int liNumClasesReales;
		int llNumElementosPorClase;

		// 'Obtener los datos a estudiar ordenados ascendentemente y obtener la
		// media total
		// ReDim lVectorDatos(0)
		lVectorDatos = new ArrayList<udtDatosEstudio>();

		lVectorDatos.add(new udtDatosEstudio());

		if (!mbObtenerDatos(lVectorDatos, ldMediaTotal)) {
			return false; // SalidaSinMensaje
		}

		// / Call gEstablecerDescripcionProceso("Analizando datos ...", False)
		// 'Calcular la suma de las desviaciones t�picas del total de los
		// datos respecto de la media total
		ldSDAM[0] = mbGetSumSquaredDeviationArrayMean(lVectorDatos,
				ldMediaTotal[0]);

		// /if (lVectorDatos.length==0){
		if (lVectorDatos.isEmpty()) {
			// 'S�lo se pueden generar dos intervalos -> hay un valor de
			// ruptura
			// /ReDim mdaValoresRuptura(0)
			mdaValoresRuptura[0] = lVectorDatos.get(0).Valor;
			mdaValInit[0] = lVectorDatos.get(0).Valor;
			miNumIntervalosGenerados = 2;

			return true;
		}

		// 'Calculamos el n�mero m�ximo de clases reales que podemos
		// generar.
		// 'Este n�mero ser�a igual al n� de elementos que tenga el vector
		// de datos.
		if (miNumIntervalosSolicitados > (lVectorDatos.size())) {
			liNumClasesReales = lVectorDatos.size() + 1;
		} else {
			liNumClasesReales = miNumIntervalosSolicitados;
		}

		// 'Establecemos las clases iniciales especificando unos �ndices de
		// ruptura en ppo. equidistantes
		llaIndicesRupturas = new int[liNumClasesReales - 1];
		llNumElementosPorClase = (lVectorDatos.size()) / liNumClasesReales;

		for (i = 0; i < llaIndicesRupturas.length; i++) {
			if (i == 0) {
				llaIndicesRupturas[i] = llNumElementosPorClase - 1;
			} else {
				llaIndicesRupturas[i] = llaIndicesRupturas[i - 1]
						+ llNumElementosPorClase;
			}
		}

		udtDatosClase[] ldaSDCM_Parciales = new udtDatosClase[llaIndicesRupturas.length + 1];
		udtDatosClase[] ldaSDCM_Validos = new udtDatosClase[llaIndicesRupturas.length + 1];

		// /ReDim ldaSDCM_Parciales(UBound(llaIndicesRupturas()) + 1)
		// /ReDim ldaSDCM_Validos(UBound(ldaSDCM_Parciales()) + 1)
		if (llaIndicesRupturas.length == 0) {
			return true;
		}

		// 'Calcular la bondad inicial
		if (!mbCalcularGVF(lVectorDatos, ldaSDCM_Parciales, llaIndicesRupturas,
				ldSDAM[0], ldUltimaGVF, -1, false)) {
			// JOptionPane.showMessageDialog((Component)PluginServices.getMainFrame(),PluginServices.getText(this,"el_numero_maximo_de_intervalos_para_este_campo_es")+": "+lVectorDatos.size());
			miNumIntervalosSolicitados = lVectorDatos.size();
			generarIntervalos();
			return false;
		}

		ldaSDCM_Validos = getArray(ldaSDCM_Parciales);

		boolean lbMoverADerecha;
		boolean lbMoverAIzquierda;
		boolean lbIntentarDesplazamiento;
		int llIndiceRupturaOriginal;

		long k;
		double ldGVFentrepasadas;

		ldGVFentrepasadas = ldUltimaGVF[0];

		// 'liNumClasesReales no ser� muy grande (11 es el m�ximo)
		for (k = 1; k <= 100; k++) {
			// 'Para cada �ndice de ruptura...
			for (i = 0; i < (llaIndicesRupturas.length); i++) {
				lbMoverADerecha = false;
				lbMoverAIzquierda = false;
				llIndiceRupturaOriginal = llaIndicesRupturas[i];
				ldaSDCM_Validos = getArray(ldaSDCM_Parciales);

				// 'Hay que decidir hacia donde hay que desplazar el �ndice de
				// ruptura
				// 'Probamos moviendo a derecha (si se puede)
				lbIntentarDesplazamiento = false;

				if (i == (llaIndicesRupturas.length - 1)) {
					if ((llaIndicesRupturas[i] + 1) < lVectorDatos.size()) {
						lbIntentarDesplazamiento = true;
					}
				} else {
					if ((llaIndicesRupturas[i] + 1) < llaIndicesRupturas[i + 1]) {
						lbIntentarDesplazamiento = true;
					} // 'If (llaIndicesRupturas(i) + 1) < llaIndicesRupturas(i
						// + 1) Then
				} // 'If i = UBound(llaIndicesRupturas) Then

				if (lbIntentarDesplazamiento) {
					llaIndicesRupturas[i] = llaIndicesRupturas[i] + 1;

					if (!mbCalcularGVF(lVectorDatos, ldaSDCM_Parciales,
							llaIndicesRupturas, ldSDAM[0], ldNuevaGVF, i, false)) {
						return false;
					}

					if (ldNuevaGVF[0] > ldUltimaGVF[0]) {
						lbMoverADerecha = true;
						ldaSDCM_Validos = getArray(ldaSDCM_Parciales);
					} else {
						// 'Dejamos el �ndice de ruputura como estaba y
						// probamos con un desplazamiento a izquierda
						llaIndicesRupturas[i] = llIndiceRupturaOriginal;
						ldaSDCM_Parciales = getArray(ldaSDCM_Validos);
					}
				} // 'If lbIntentarDesplazamiento Then

				lbIntentarDesplazamiento = false;

				// 'Probamos moviendo a izquierda (si se puede y no estamos
				// moviendo ya a derechas)
				if (!lbMoverADerecha) {
					if (i == 0) { // LBound(llaIndicesRupturas) Then

						if ((llaIndicesRupturas[i] - 1) >= 0) { // LBound(lVectorDatos())
																// Then
							lbIntentarDesplazamiento = true;
						} // 'If (llaIndicesRupturas(i) - 1) >=
							// LBound(lVectorDatos()) Then
					} else {
						if ((llaIndicesRupturas[i] - 1) > llaIndicesRupturas[i - 1]) {
							lbIntentarDesplazamiento = true;
						} // 'If (llaIndicesRupturas(i) - 1) >
							// llaIndicesRupturas(i - 1) Then
					} // 'If i = LBound(llaIndicesRupturas) Then
				} // 'If Not lbMoverADerecha Then

				if (lbIntentarDesplazamiento) {
					llaIndicesRupturas[i] = llaIndicesRupturas[i] - 1;

					if (!mbCalcularGVF(lVectorDatos, ldaSDCM_Parciales,
							llaIndicesRupturas, ldSDAM[0], ldNuevaGVF, i, true)) {
						return false;
					}

					if (ldNuevaGVF[0] > ldUltimaGVF[0]) {
						lbMoverAIzquierda = true;
						ldaSDCM_Validos = getArray(ldaSDCM_Parciales);
					} else {
						// 'Dejamos el �ndice de ruputura como estaba
						llaIndicesRupturas[i] = llIndiceRupturaOriginal;
						ldaSDCM_Parciales = getArray(ldaSDCM_Validos);
					}
				} // 'If lbIntentarDesplazamiento Then

				lbIntentarDesplazamiento = false;

				// 'Si se ha decidido desplazar el �ndice ... continuamos
				// hasta que no podamos mejorar la GVF
				if (lbMoverAIzquierda || lbMoverADerecha) {
					ldUltimaGVF[0] = ldNuevaGVF[0];

					boolean exit = false;

					while (!exit) {
						llIndiceRupturaOriginal = llaIndicesRupturas[i];

						if (lbMoverADerecha) {
							if (i == (llaIndicesRupturas.length - 1)) {
								if ((llaIndicesRupturas[i] + 1) >= lVectorDatos
										.size()) {
									exit = true;
								}
							} else {
								if ((llaIndicesRupturas[i] + 1) >= llaIndicesRupturas[i + 1]) {
									exit = true;
								}
							} // 'If i = UBound(llaIndicesRupturas) Then

							llaIndicesRupturas[i] = llaIndicesRupturas[i] + 1;
						} else { // 'If lbMoverAIzquierda Then

							if (i == 0) { // LBound(llaIndicesRupturas) Then

								if ((llaIndicesRupturas[i] - 1) < 0) { // LBound(lVectorDatos())
																		// Then
																		// Exit
																		// Do
									exit = true;
								}
							} else {
								if ((llaIndicesRupturas[i] - 1) <= llaIndicesRupturas[i - 1]) {
									exit = true;
								}
							} // 'If i = LBound(llaIndicesRupturas) Then

							llaIndicesRupturas[i] = llaIndicesRupturas[i] - 1; // ////////////////
						} // 'If lbMoverADerecha Then

						if (!mbCalcularGVF(lVectorDatos, ldaSDCM_Parciales,
								llaIndicesRupturas, ldSDAM[0], ldNuevaGVF, i,
								lbMoverAIzquierda)) {
							return false;
						}

						if (ldNuevaGVF[0] < ldUltimaGVF[0]) {
							// 'Dejar el �ndice donde estaba
							llaIndicesRupturas[i] = llIndiceRupturaOriginal;
							ldaSDCM_Parciales = getArray(ldaSDCM_Validos);
							exit = true;
						} else {
							ldUltimaGVF[0] = ldNuevaGVF[0];
							ldaSDCM_Validos = getArray(ldaSDCM_Parciales);
						}
					}
				} // 'If lbMoverAIzquierda Or lbMoverADerecha Then
			}

			if (ldUltimaGVF[0] <= ldGVFentrepasadas) {
				i = 101;
			}

			ldGVFentrepasadas = ldUltimaGVF[0];
		}

		// 'A partir de aqu� ya no se puede cancelar nada
		mdaValoresRuptura = new double[llaIndicesRupturas.length];
		mdaValInit = new double[llaIndicesRupturas.length];

		for (i = 0; i < mdaValoresRuptura.length; i++) { // LBound(mdaValoresRuptura)
															// To
															// UBound(mdaValoresRuptura)
			if (llaIndicesRupturas[i] == -1)
				llaIndicesRupturas[i] = 1;
			if (llaIndicesRupturas[i] > lVectorDatos.size() - 1)
				llaIndicesRupturas[i] = lVectorDatos.size() - 1;
			mdaValoresRuptura[i] = lVectorDatos.get(llaIndicesRupturas[i]).Valor;

			if ((llaIndicesRupturas[i] + 1) < lVectorDatos.size()) {
				mdaValInit[i] = lVectorDatos.get(llaIndicesRupturas[i] + 1).Valor;
			} else {
				mdaValInit[i] = lVectorDatos.get(llaIndicesRupturas[i]).Valor;
			}

			// 'Hay que aplicar una peque�a correcci�n a los valores de
			// ruptura para que los intervalos que genera
			// 'mapobjects se aprechen en su totalidad, o lo que es lo mismo,
			// vengan todos representados en el mapa.
			// 'Con esto tambi�n se consigue hallar intervalos equivalentes a
			// los de ArcView. Esta correcci�n consiste
			// 'en sumar el m�nimo incremento a los valores de ruptura. No lo
			// hago aqu� sino en la ventana de propiedades de capa.
		}

		miNumIntervalosGenerados = mdaValoresRuptura.length + 2;

		ldaSDCM_Validos = null;
		ldaSDCM_Parciales = null;
		lVectorDatos = null;

		return true;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param array
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private udtDatosClase[] getArray(udtDatosClase[] array) {
		udtDatosClase[] aux = new udtDatosClase[array.length];

		for (int i = 0; i < array.length; i++) {
			aux[i] = new udtDatosClase();
			aux[i].Media = array[i].Media;
			aux[i].NumElementos = array[i].NumElementos;
			aux[i].SDCM = array[i].SDCM;
			aux[i].SumaCuadradoTotal = array[i].SumaCuadradoTotal;
			aux[i].SumaTotal = array[i].SumaTotal;
		}

		return aux;
	}

	/**
	 * Devuelve la "SDAM" de un conjunto de datos que vienen almacenados en el
	 * vector rVectorDatos
	 * 
	 * @param rVectorDatos
	 *            Datos
	 * @param vdMedia
	 *            Media
	 * 
	 * @return suma de las desviaciones t�picas del total de los datos respecto
	 *         de la media total
	 */
	private double mbGetSumSquaredDeviationArrayMean(
			List<udtDatosEstudio> rVectorDatos, double vdMedia) {
		int i;

		double rdSDAM = 0;

		for (i = 0; i < rVectorDatos.size(); i++) { // LBound(rVectorDatos) To
													// UBound(rVectorDatos)
			rdSDAM = rdSDAM
					+ (Math.pow((rVectorDatos.get(i).Valor - vdMedia), 2) * rVectorDatos
							.get(i).Coincidencias);
		}

		return rdSDAM;
	}

	/**
	 * Esta funci�n obtiene los datos en los que queremos hallar las rupturas
	 * naturales. Los datos se devuelven ordenados en un vector. Tambi�n
	 * devuelve la media total
	 * 
	 * @param rVectorDatos
	 *            Datos
	 * @param rdMediaTotal
	 *            Media total
	 * 
	 * @return True si se ha calculado correctamente.
	 * 
	 * @throws com.iver.cit.gvsig.fmap.DriverException
	 * @throws DriverException
	 */
	private boolean mbObtenerDatos(List<udtDatosEstudio> rVectorDatos,
			double[] rdMediaTotal) {
		int[] llIndice = new int[1];
		boolean[] lbNuevoElemento = new boolean[1];

		// 'Nos cuidamos de recorrer todos los registros sin consultar la
		// propiedad EOF del recordset
		for (int i = 0; i < values.length; i++) {
			rdMediaTotal[0] = rdMediaTotal[0] + values[i];

			// 'Hay que insertar el valor en la posici�n adecuada. Para ello
			// hacemos una b�squeda binaria
			if (!mbObtenerPosicionEnVector(rVectorDatos, values[i], llIndice,
					lbNuevoElemento)) {
				return false;
			}

			if (!lbNuevoElemento[0]) {
				if ((llIndice[0] < 0) || (llIndice[0] > rVectorDatos.size())) {
					System.out.println("�ndice incorrecto!");

					return false;
				}

				// 'Por si fuese el primer elemento
				rVectorDatos.get(llIndice[0]).Valor = values[i];

				// 'Incrementamos el n� de coincidencias y damos el valor por
				// insertado
				rVectorDatos.get(llIndice[0]).Coincidencias = rVectorDatos
						.get(llIndice[0]).Coincidencias + 1;
			} else {
				udtDatosEstudio udt = new udtDatosEstudio();
				udt.Valor = values[i];
				udt.Coincidencias = 1;
				rVectorDatos.add(llIndice[0], udt);
			}
		}

		rdMediaTotal[0] = rdMediaTotal[0] / values.length;

		return true;
	}

	/**
	 * Esta funci�n s�lo calcula las SDCM de las clases adyacentes al �ndice de
	 * ruptura actual. Si el �ndice de ruptura actual es -1 entonces las calcula
	 * todas! . En este caso no importa el valor de vbDesplazAIzquierda
	 * 
	 * @param rVectorDatos
	 *            Datos
	 * @param raClases
	 *            Clases encontradas
	 * @param rlaIndicesRuptura
	 *            �ndices de ruptura
	 * @param vdSDAM
	 *            suma de la desviaci�n standard.
	 * @param rdGVF
	 *            Desviaci�n standard de los intervalos.
	 * @param vlIndiceRupturaActual
	 *            �ndice de ruptura actual.
	 * 
	 * @return True si se ha calcula do correctamente.
	 */
	/*
	 * private boolean mbCalcularGVF(ArrayList rVectorDatos, udtDatosClase[]
	 * raClases, int[] rlaIndicesRuptura, double vdSDAM, double[] rdGVF, int
	 * vlIndiceRupturaActual ) { return mbCalcularGVF(rVectorDatos, raClases,
	 * rlaIndicesRuptura, vdSDAM, rdGVF, vlIndiceRupturaActual, true); }
	 */
	/**
	 * Esta funci�n s�lo calcula las SDCM de las clases adyacentes al �ndice de
	 * ruptura actual. Si el �ndice de ruptura actual es -1 entonces las calcula
	 * todas! . En este caso no importa el valor de vbDesplazAIzquierda
	 * 
	 * @param rVectorDatos
	 *            Datos
	 * @param raClases
	 *            Calses que representan a los intervalos.
	 * @param rlaIndicesRuptura
	 *            �ndices de ruptura.
	 * @param vdSDAM
	 *            Desviaci�n standard.
	 * @param rdGVF
	 *            Desviaci�n standard de los intervalos.
	 * @param vlIndiceRupturaActual
	 *            �ndice de ruptura actual.
	 * @param vbDesplazAIzquierda
	 *            Desplazamiento a la izquierda.
	 * 
	 * @return True si se ha calculado correctamente.
	 */
	private boolean mbCalcularGVF(List<udtDatosEstudio> rVectorDatos,
			udtDatosClase[] raClases, int[] rlaIndicesRuptura, double vdSDAM,
			double[] rdGVF, int vlIndiceRupturaActual /* As Long = -1 */,
			boolean vbDesplazAIzquierda) {
		double ldSDCM_aux;

		int i;

		if (vlIndiceRupturaActual == -1) {
			// 'Obtenemos los datos para todas las clases = intervalos
			for (i = 0; i < rlaIndicesRuptura.length; i++) {
				if (i == 0) { // LBound(rlaIndicesRuptura) Then

					if (!mbGetDatosClase(rVectorDatos, 0, rlaIndicesRuptura[i],
							raClases, i)) {
						return false;
					}
				} else {
					if (!mbGetDatosClase(rVectorDatos,
							rlaIndicesRuptura[i - 1] + 1, rlaIndicesRuptura[i],
							raClases, i)) {
						return false;
					}
				}
			}

			// 'Falta la �ltima clase
			if (!mbGetDatosClase(rVectorDatos,
					rlaIndicesRuptura[rlaIndicesRuptura.length - 1] + 1,
					rVectorDatos.size() - 1, raClases, raClases.length - 1)) {
				return false;
			}
		} else {
			i = vlIndiceRupturaActual;

			// 'Hay que determinar para qu� clases debemos volver a recalcular
			// los datos en funci�n del �ndice de ruptura que estamos
			// modificando
			if (vbDesplazAIzquierda) {
				// 'Recalcular los datos de la clase izquierda
				if (!mbRecalcularDatosClase(raClases, i, rVectorDatos,
						rlaIndicesRuptura[i] + 1, vdSDAM, false)) {
					return false;
				}

				// 'Recalcular los datos de la clase derecha
				if (!mbRecalcularDatosClase(raClases, i + 1, rVectorDatos,
						rlaIndicesRuptura[i] + 1, vdSDAM, true)) {
					return false;
				}
			} else {
				// 'Recalcular los datos de la clase izquierda
				if (!mbRecalcularDatosClase(raClases, i, rVectorDatos,
						rlaIndicesRuptura[i], vdSDAM, true)) {
					return false;
				}

				// 'Recalcular los datos de la clase derecha
				if (!mbRecalcularDatosClase(raClases, i + 1, rVectorDatos,
						rlaIndicesRuptura[i], vdSDAM, false)) {
					return false;
				}
			}
		}

		ldSDCM_aux = 0;

		for (i = 0; i < raClases.length; i++) { // LBound(raClases) To
												// UBound(raClases)
			ldSDCM_aux = ldSDCM_aux + raClases[i].SDCM;
		}

		rdGVF[0] = (vdSDAM - ldSDCM_aux) / vdSDAM;
		// System.out.println(ldSDCM_aux);

		return true;
	}

	/**
	 * Devuelve la "SDCM" de un conjunto de datos que vienen almacenados en el
	 * vector rVectorDatos y que est�n delimitados por vlLimiteInf y vlLimiteInf
	 * 
	 * @param rVectorDatos
	 *            Datos
	 * @param vlLimiteInf
	 *            L�mite inferior.
	 * @param vlLimiteSup
	 *            L�mite superior.
	 * @param rClase
	 *            Calses que representan a los intervalos.
	 * @param numClas
	 *            N�mero de calses.
	 * 
	 * @return True si se ha calculado correctamente.
	 */
	private boolean mbGetDatosClase(List<udtDatosEstudio> rVectorDatos,
			int vlLimiteInf, int vlLimiteSup, udtDatosClase[] rClase,
			int numClas) {
		int i;

		if (vlLimiteInf < 0) {
			return false;
		}

		if (vlLimiteSup > rVectorDatos.size()) {
			return false;
		}

		if (vlLimiteSup < vlLimiteInf) {
			return false;
		}

		// 'Inicializamos los datos de la clase
		rClase[numClas] = new udtDatosClase();

		// 'Hallamos la media de la clase
		for (i = vlLimiteInf; i < (vlLimiteSup + 1); i++) {
			rClase[numClas].NumElementos = rClase[numClas].NumElementos
					+ rVectorDatos.get(i).Coincidencias;

			// 'rClase.Media = rClase.Media + (rVectorDatos(i).Valor *
			// rVectorDatos(i).Coincidencias)
			rClase[numClas].SumaTotal = rClase[numClas].SumaTotal
					+ (rVectorDatos.get(i).Valor * rVectorDatos.get(i).Coincidencias);

			// 'rClase.ProductoTotal = rClase.ProductoTotal *
			// (rVectorDatos(i).Valor * rVectorDatos(i).Coincidencias)
			rClase[numClas].SumaCuadradoTotal = rClase[numClas].SumaCuadradoTotal
					+ (Math.pow(rVectorDatos.get(i).Valor
							* rVectorDatos.get(i).Coincidencias, 2));
		}

		// 'rClase.Media = rClase.Media / llTotalElementos
		rClase[numClas].Media = rClase[numClas].SumaTotal
				/ rClase[numClas].NumElementos;
		rClase[numClas].SDCM = (rClase[numClas].SumaCuadradoTotal)
				- (2 * rClase[numClas].Media * rClase[numClas].SumaTotal)
				+ (rClase[numClas].NumElementos * Math.pow(
						rClase[numClas].Media, 2));
		/*
		 * if (new Double(rClase[numClas].SDCM).isNaN()){
		 * System.out.println(rClase[numClas].SDCM); }
		 */
		return true;
	}

	/**
	 * Recalcula los datos de las clases.
	 * 
	 * @param rClase
	 *            Clases.
	 * @param i
	 *            �adir �adir �adir �adir indica si a la clase se le a�ade un
	 *            elemento (True) o se le quita (False)
	 * @param rVectorDatos
	 *            Datos.
	 * @param vlIndiceElemento
	 *            es el �ndice del elemento que se le va a�adir o a quitar a la
	 *            clase
	 * @param vdSDAM
	 *            desviaci�n standard.
	 * @param vbA
	 *            �adir DOCUMENT ME!
	 * 
	 * @return True si se ha calculado correctamente.
	 */
	private boolean mbRecalcularDatosClase(udtDatosClase[] rClase, int i,
			List<udtDatosEstudio> rVectorDatos, int vlIndiceElemento,
			double vdSDAM, boolean vbAnyadir) {
		double ldValor;
		long llNumCoincidencias;
		try {
			if (vlIndiceElemento > rVectorDatos.size() - 1)
				return true;
			ldValor = rVectorDatos.get(vlIndiceElemento).Valor;
			llNumCoincidencias = rVectorDatos.get(vlIndiceElemento).Coincidencias;

			if (vbAnyadir) {
				// 'Actualizamos la suma total
				rClase[i].SumaTotal = rClase[i].SumaTotal
						+ (ldValor * llNumCoincidencias);

				// 'Actualizamos la suma de cuadrados total
				rClase[i].SumaCuadradoTotal = rClase[i].SumaCuadradoTotal
						+ (Math.pow((ldValor * llNumCoincidencias), 2));

				// 'Actualizamos el producto total
				// 'rClase.ProductoTotal = rClase.ProductoTotal * (ldValor *
				// llNumCoincidencias)
				// 'Actualizamos el n� de elementos
				rClase[i].NumElementos = rClase[i].NumElementos
						+ llNumCoincidencias;
			} else {
				// 'Actualizamos la suma total
				rClase[i].SumaTotal = rClase[i].SumaTotal
						- (ldValor * llNumCoincidencias);

				// 'Actualizamos la suma de cuadrados total
				rClase[i].SumaCuadradoTotal = rClase[i].SumaCuadradoTotal
						- (Math.pow((ldValor * llNumCoincidencias), 2));

				// 'Actualizamos el producto total
				// 'rClase.ProductoTotal = rClase.ProductoTotal / (ldValor *
				// llNumCoincidencias)
				// 'Actualizamos el n� de elementos
				rClase[i].NumElementos = rClase[i].NumElementos
						- llNumCoincidencias;
			} // 'If vbA�adir Then
			if (rClase[i].NumElementos <= 0)
				rClase[i].NumElementos = 1;
			// 'Obtenemos la nueva media
			rClase[i].Media = rClase[i].SumaTotal / rClase[i].NumElementos;

			// 'Actualizamos la SDCM
			rClase[i].SDCM = (rClase[i].SumaCuadradoTotal)
					- (2 * rClase[i].Media * rClase[i].SumaTotal)
					+ (rClase[i].NumElementos * Math.pow(rClase[i].Media, 2));

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Devuelve el valor de ruptura seg�n el �ndice que se le pasa como
	 * par�metro.
	 * 
	 * @param viIndice
	 *            �nidice del valor de ruptura.
	 * 
	 * @return Valor de ruptura.
	 */
	private double getValorRuptura(int viIndice) {
		return mdaValoresRuptura[viIndice];
	}

	/**
	 * Devuelve el valor inicial de cada intervalo
	 * 
	 * @param index
	 *            �ndice del intervalo
	 * 
	 * @return valor del intervalo.
	 */
	private double getValInit(int index) {
		return mdaValInit[index];
	}

	/**
	 * Devuelve el n�mero de intervalos que se pueden representar, no tiene
	 * porque coincidir con el n�mero de intervalos que se piden.
	 * 
	 * @return N�mero de intervalos calculados.
	 */
	private int getNumIntervals() {
		return miNumIntervalosGenerados;
	}

	/**
	 * Clase para contener los atributos Valor y coincidencias.
	 * 
	 * @author Vicente Caballero Navarro
	 */
	private class udtDatosEstudio {
		public double Valor; //
		public long Coincidencias;
	}

	/**
	 * Clase para contener los atributos: N�mero de Elementos. Media. SumaTotal.
	 * SumaCuadradoTotal. Desviaci�n standard.
	 * 
	 * @author Vicente Caballero Navarro
	 */
	private class udtDatosClase {
		public long NumElementos; // 'N� total de elementos que hay en la
									// clase
		public double Media; // 'Media de la clase
		public double SumaTotal; // 'Suma total de los elementos

		// 'ProductoTotal 'Producto total de los elementos '�dar� problemas
		// de desbordamiento?
		public double SumaCuadradoTotal; // 'Suma del total de los cuadrados de
											// los elementos
		public double SDCM; // 'Suma de la desviaci�n t�pica de los
							// elementos de la clase respecto de la media de la
							// clase
	}
}
