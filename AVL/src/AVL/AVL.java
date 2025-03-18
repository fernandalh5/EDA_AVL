package AVL;

import java.util.LinkedList;
import java.util.Queue;

import Colas.ColaA;
import Colas.ColaADT;

public class AVL<T extends Comparable<T>> {

	private NodoAVL<T> raiz;
	private int cont;

	public AVL() {
		cont = 0;
	}

	public int getCont() {
		return cont;
	}

	public NodoAVL<T> insertaAVL(T elem) {
		if (cont == 0) {
			raiz = new NodoAVL<T>(elem);
			cont++;
			return raiz;
		}
		NodoAVL<T> actual = raiz;
		NodoAVL<T> nuevo = new NodoAVL<>(elem);
		NodoAVL<T> papa = null;
		boolean encontrado = false;

		//insercion
		//busca donde debe ir el nodo
		while (actual != null && encontrado==false) {
			papa = actual;
			//el elemento a meter es menor
			if (elem.compareTo(actual.getElem()) < 0)
				actual = actual.getIzq();
			//el elemento a meter es mayor
			else if (elem.compareTo(actual.getElem()) > 0)
				actual = actual.getDer();
			else
				encontrado = true;
		}
		
		if (encontrado==true)//si es igual ya no lo inserta
			return null;
		
		//una vez encontrado el papa, lo inserta donde corresponda
		if (elem.compareTo(papa.getElem()) < 0)
			papa.setIzq(nuevo);
		else
			papa.setDer(nuevo);

		nuevo.setPapa(papa);
		cont++;

		boolean end = false;
		actual = nuevo;

		//revisar si es necesario el balanceo
		while (end==false && actual.getPapa()!= null) {//balancearon o llegaron raiz sin necesidad de balancear
			//actual es el nodo insertado
			papa = actual.getPapa();
			//actualizamos los factores del papa
			if (papa.getIzq() == actual)//insertaste a la izquierda del papa
				papa.setFe(papa.getFe()-1);
			else//insertaste a la derecha del papa
				papa.setFe(papa.getFe()+1);
			
			//revisamos si se debe rebalancear
			if (papa.getFe() == 0)
				end = true;
			else if (Math.abs( papa.getFe()) == 2) {
				rota(papa);//rebalanceo
				end = true;
			}
			actual = papa;//actualizamos al papa para el siguiente analisis en el ciclo
		}//end while
		
		return nuevo;
	}
	
	private void rota(NodoAVL<T> alfa) {
		NodoAVL<T> beta=null;
		NodoAVL<T> B=null;
		if (alfa.getFe() > 1) {//derecha 
			if (alfa.getDer().getFe() < 0) {//der-izq
				//asignamos beta y B
				beta=alfa.getDer();
				B=beta.getIzq();
				
				//rotamos a la derecha
				B.setDer(beta);
				beta.setPapa(B);
				B.setPapa(alfa);
				alfa.setDer(B);
				
				//rotamos a la izq
				if(alfa!=raiz) {
					B.setPapa(alfa.getPapa());
					if(alfa.getPapa().getElem().compareTo(alfa.getElem())<0) {//alfa es hijo der
						alfa.getPapa().setDer(B);
					}else {//es hijo izq
						alfa.getPapa().setIzq(B);
					}
				}
				else {
					raiz=B;
					B.setPapa(null);
				}
				
				B.setIzq(alfa);
				alfa.setPapa(B);
				alfa.setDer(null);
				beta.setIzq(null);
				
				if(B.getPapa()!=null) {
					calculaFe(B.getPapa());
				}

			} else {//der-der
				//asignamos beta y B
				beta=alfa.getDer();
				B=beta.getDer();
				
				//rotamos a la izquierda
				if(alfa!=raiz) {
					beta.setPapa(alfa.getPapa());
					if(alfa.getPapa().getElem().compareTo(alfa.getElem())<0) {//alfa es hijo der
						alfa.getPapa().setDer(beta);
					}else {//es hijo izq
						alfa.getPapa().setIzq(beta);
					}
				}
				else {
					raiz=beta;
					beta.setPapa(null);
				}
				
				beta.setIzq(alfa);
				alfa.setPapa(beta);
				alfa.setDer(null);
				
				if(beta.getPapa()!=null) {
					calculaFe(beta.getPapa());
				}
			}
		} else if(alfa.getFe()<-1){//cargado a la izquierda
			if (alfa.getIzq().getFe() > 0) {//izq-der
				//asignamos beta y B
				beta=alfa.getIzq();
				B=beta.getDer();
				
				//rotamos a la izq
				B.setPapa(alfa);
				alfa.setIzq(B);
				B.setIzq(beta);
				beta.setPapa(B);
				
				//rotamos a la derecha
				if(alfa!=raiz) {
					B.setPapa(alfa.getPapa());
					if(alfa.getPapa().getElem().compareTo(alfa.getElem())<0) {//alfa es hijo der
						alfa.getPapa().setDer(B);
					}else {
						alfa.getPapa().setIzq(B);
					}
				}else {
					raiz=B;
					B.setPapa(null);
				}
				B.setDer(alfa);
				alfa.setPapa(B);
				alfa.setIzq(null);
				beta.setDer(null);
				
				if(B.getPapa()!=null) {
					calculaFe(B.getPapa());
				}

			} else {//izq-izq
				//asignamos beta y B
				beta=alfa.getIzq();
				B=beta.getIzq();
				
				//rotamos a la derecha
				if(alfa!=raiz) {
					beta.setPapa(alfa.getPapa());
					if(alfa.getPapa().getElem().compareTo(alfa.getElem())<0) {//alfa es hijo der
						alfa.getPapa().setDer(beta);
					}else {
						alfa.getPapa().setIzq(beta);
					}
				}else {
					raiz=beta;
					beta.setPapa(null);
				}
				beta.setDer(alfa);
				alfa.setPapa(beta);
				alfa.setIzq(null);
				
				if(beta.getPapa()!=null) {
					calculaFe(beta.getPapa());
				}
					
			}
			
		}
		calculaFe(alfa);
		calculaFe(beta);
		calculaFe(B);
		
	}

	private void calculaFe(NodoAVL<T> nodo) {
		nodo.setFe(altura(nodo.getDer(),0) - altura(nodo.getIzq(),0));
	}
	
    private int altura(NodoAVL<T> actual, int cont){
        if(actual == null){
            return cont;
        }
        
        int cont1 = altura(actual.getIzq(), cont+1);
        int cont2 = altura(actual.getDer(), cont+1);
        
        return Math.max(cont1, cont2);
    }
    
    private void calculaBalanceos(NodoAVL<T> actual) {
       	NodoAVL<T> nodoRevisarBalanceo;
    		boolean end = false;
    		
    		while (end==false && actual.getPapa()!= null) {//balancearon o llegaron raiz sin necesidad de balancear
    			nodoRevisarBalanceo = actual.getPapa();
    			if (nodoRevisarBalanceo.getIzq() == actual)//insertaste a la izquierda del papa
    				nodoRevisarBalanceo.setFe(nodoRevisarBalanceo.getFe()-1);
    			else//insertaste a la derecha del papa
    				nodoRevisarBalanceo.setFe(nodoRevisarBalanceo.getFe()+1);
    			
    			if (nodoRevisarBalanceo.getFe() == 0)
    				end = true;
    			else if (Math.abs( nodoRevisarBalanceo.getFe()) == 2) {
    				rota(nodoRevisarBalanceo);
    				end = true;
    			}
    			actual = nodoRevisarBalanceo;
    		}
    }
    
    public void elimina(T elem) {
        raiz = elimina(elem, raiz);
    }

    private NodoAVL<T> elimina(T elem, NodoAVL<T> actual) {
    	if (actual == null) {
            return null;
        }

        int comparacion = elem.compareTo(actual.getElem());

        if (comparacion < 0) {
            actual.setIzq(elimina(elem, actual.getIzq()));
        } else if (comparacion > 0) {
            actual.setDer(elimina(elem, actual.getDer()));
        } else {
            // Caso 1: Nodo sin hijos
            if (actual.getIzq() == null && actual.getDer() == null) {
            	calculaBalanceos(actual);
                return null;
            }

            // Caso 2: Nodo con un hijo
            if (actual.getIzq() == null) {
            	calculaBalanceos(actual);
                return actual.getDer();
            } else if (actual.getDer() == null) {
            	calculaBalanceos(actual);
                return actual.getIzq();
            }

            // Caso 3: Nodo con dos hijos - Encontrar sucesor
            NodoAVL<T> sucesor = buscaSucesor(actual);
            actual.setElem(sucesor.getElem()); // Copiar el valor del sucesor
            actual.setDer(elimina(sucesor.getElem(), actual.getDer())); // Eliminar sucesor
            
            //revisa factores
            calculaBalanceos(actual);
        }
        
        return actual;
    }
    
    private NodoAVL<T> buscaSucesor(NodoAVL<T> actual){

    	NodoAVL<T> sucesor = actual.getDer(); 
	    if(sucesor == null) 
	    	return null;
	    
	    while(sucesor.getIzq() != null){
	        sucesor = sucesor.getIzq();
	    }
	    return sucesor;
   }
    
	public NodoAVL<T> busca(T elem) {
		// no hay nodos en el arbol
		if (cont == 0) {
			return null;
		}

		// de haber nodos, comenzamos a buscarlo en raiz
		NodoAVL<T> actual = raiz;
		NodoAVL<T> papa = null;
		boolean encontrado = false;
		NodoAVL<T> nodoEncontrado=null;

		// busca donde esta el nodo
		while (actual != null && encontrado == false) {
			// el elemento a buscar es menor que actual
			if (elem.compareTo(actual.getElem()) < 0)
				actual = actual.getIzq();
			// el elemento a buscar es mayor que actual
			else if (elem.compareTo(actual.getElem()) > 0)
				actual = actual.getDer();
			//elemento encontrado en actual
			else {
				encontrado = true;
				nodoEncontrado = actual;
			}
		}//end while
		
		return nodoEncontrado;
	}
	
	public void imprimirPorNiveles() {
		imprimirPorNiveles(raiz);
	}
	
	private void imprimirPorNiveles(NodoAVL<T> raiz) {
		// Si el árbol está vacío, no se imprime nada
        if (raiz == null) 
        	return; 

        // Se usa una cola para el recorrido por niveles
        ColaADT<NodoAVL> cola = new ColaA<NodoAVL>();
        cola.agregar(raiz); // Se agrega la raíz a la cola
        NodoAVL nodo;

        while (!cola.isEmpty()) {
            int cantidadNodosNivel = cola.cuentaElementos(); // Cantidad de nodos en el nivel actual

            // Procesar todos los nodos de un nivel antes de pasar al siguiente
            for (int i = 1; i <= cantidadNodosNivel; i++) {
                nodo = cola.quitar(); // Extrae el nodo de la cola
                System.out.print(nodo.getElem() + " "); // Imprime la clave del nodo

                // Agrega los hijos a la cola para procesarlos en el siguiente nivel
                if (nodo.getIzq() != null) 
                	cola.agregar(nodo.getIzq());
                if (nodo.getDer() != null) 
                	cola.agregar(nodo.getDer());
            }
            
            System.out.println(); // Salto de línea para separar los niveles
        }
    }
	
	public void imprimirPorNivelesConFE() {
		imprimirPorNivelesConFE(raiz);
	}
	
	private void imprimirPorNivelesConFE(NodoAVL<T> raiz) {
		// Si el árbol está vacío, no se imprime nada
        if (raiz == null) 
        	return; 

        // Se usa una cola para el recorrido por niveles
        ColaADT<NodoAVL> cola = new ColaA<NodoAVL>();
        cola.agregar(raiz); // Se agrega la raíz a la cola
        NodoAVL nodo;

        while (!cola.isEmpty()) {
            int cantidadNodosNivel = cola.cuentaElementos(); // Cantidad de nodos en el nivel actual

            // Procesar todos los nodos de un nivel antes de pasar al siguiente
            for (int i = 1; i <= cantidadNodosNivel; i++) {
                nodo = cola.quitar(); // Extrae el nodo de la cola
                System.out.print(nodo.getElem() + " fe:" + nodo.getFe()+"  "); // Imprime la clave del nodo

                // Agrega los hijos a la cola para procesarlos en el siguiente nivel
                if (nodo.getIzq() != null) 
                	cola.agregar(nodo.getIzq());
                if (nodo.getDer() != null) 
                	cola.agregar(nodo.getDer());
            }
            
            System.out.println(); // Salto de línea para separar los niveles
        }
    }
	
	public void imprimePorNiveles() {
		System.out.println(raiz.getElem());
		imprimePorNiveles(raiz) ;
		return;
	}
		
	private void imprimePorNiveles(NodoAVL<T> actual) {
		
		if(actual.getIzq()!=null)
			System.out.println(actual.getIzq().getElem());
		
		if(actual.getDer()!=null)
			System.out.println(actual.getDer().getElem());
		
		if(actual.getIzq()!=null)
			imprimePorNiveles(actual.getIzq());
		
		if(actual.getDer()!=null)
			imprimePorNiveles(actual.getDer());

	}
	
	

}
