package AVL;

public class PruebaAVL {

	public static void main(String[] args) {
		
		AVL<Integer> arbol=new AVL<Integer>();
		
		arbol.insertaAVL(100);
		arbol.insertaAVL(50);
		arbol.insertaAVL(150);
		arbol.insertaAVL(25);
		arbol.insertaAVL(75);
		arbol.insertaAVL(125);
		arbol.insertaAVL(175);
		arbol.insertaAVL(10);
		arbol.insertaAVL(110);
		arbol.insertaAVL(180);
		
		
		arbol.imprimirPorNivelesConFE();
		
	}

}
 