import java.util.List;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class ABB {
    private NodoABB raiz;

    public void insertar(Multa multa) {
        raiz = insertarRec(raiz, multa);
    }

    private NodoABB insertarRec(NodoABB nodo, Multa multa) {
        if (nodo == null) return new NodoABB(multa);
        int cmp = multa.getPlaca().compareTo(nodo.multa.getPlaca());
        if (cmp < 0) nodo.izq = insertarRec(nodo.izq, multa);
        else nodo.der = insertarRec(nodo.der, multa);
        return nodo;
    }
        // Método para buscar todas las multas con una placa específica
    public List<Multa> buscarPorPlaca(String placa) {
        List<Multa> resultado = new ArrayList<>();
        buscarPorPlacaRec(raiz, placa, resultado);
        return resultado;
    }

    private void buscarPorPlacaRec(NodoABB nodo, String placa, List<Multa> resultado) {
        if (nodo == null) {
            return;
        }
        int cmp = placa.compareTo(nodo.multa.getPlaca());

        if (cmp == 0) {
            resultado.add(nodo.multa);
            // Aunque sea ABB, puede haber duplicados en ambos lados si usas igual a la derecha
            // Por eso seguimos buscando en ambos subárboles:
            buscarPorPlacaRec(nodo.izq, placa, resultado);
            buscarPorPlacaRec(nodo.der, placa, resultado);
        } else if (cmp < 0) {
            buscarPorPlacaRec(nodo.izq, placa, resultado);
        } else {
            buscarPorPlacaRec(nodo.der, placa, resultado);
        }
    }

    public List<Multa> inorden() {
        List<Multa> lista = new ArrayList<>();
        inordenRec(raiz, lista);
        return lista;
    }

    private void inordenRec(NodoABB nodo, List<Multa> lista) {
        if (nodo != null) {
            inordenRec(nodo.izq, lista);
            lista.add(nodo.multa);
            inordenRec(nodo.der, lista);
        }
    }

}