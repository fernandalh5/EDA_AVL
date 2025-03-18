package AVL;


public class NodoAVL <T>{
	private T elem;
	private NodoAVL<T> izq, der, papa;
	private int fe=0;

	public NodoAVL(T elem) {
		this.elem = elem;
	}
	public NodoAVL(T elem, NodoAVL<T> papa) {
		super();
		this.elem = elem;
		this.papa = papa;
	}

	public T getElem() {
		return elem;
	}

	public void setElem(T elem) {
		this.elem = elem;
	}

	public NodoAVL<T> getIzq() {
		return izq;
	}

	public void setIzq(NodoAVL<T> izq) {
		this.izq = izq;
	}

	public NodoAVL<T> getDer() {
		return der;
	}

	public void setDer(NodoAVL<T> der) {
		this.der = der;
	}

	public NodoAVL<T> getPapa() {
		return papa;
	}

	public void setPapa(NodoAVL<T> papa) {
		this.papa = papa;
	}
	public int getFe() {
		return fe;
	}
	public void setFe(int fe) {
		this.fe = fe;
	}
	
	


}
