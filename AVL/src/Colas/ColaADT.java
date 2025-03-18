package Colas;

import java.util.ArrayList;

public interface ColaADT <T>{

	    
	    public void agregar(T dato);
	    public T quitar();
	    public boolean isEmpty();
	    public T consultaInicio();
	    public int cuentaElementos();
	    public T consultaUltimo();
	    public ArrayList<T> multiQuita(int n);
	            
	
}
