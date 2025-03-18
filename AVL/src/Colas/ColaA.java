package Colas;

import java.util.ArrayList;

public class ColaA <T> implements ColaADT<T>{
	
    private T[] colaArreglo;
    private int inicio;
    private int fin;
    private int MAX=100;

    public ColaA() {
        this.colaArreglo = (T[]) new Object [MAX];
        this.inicio = -1;
        this.fin = -1;
    }
    
    
    public ColaA(int max){
        this.colaArreglo=(T[]) new Object [max];
        this.inicio=-1;
        this.fin=-1;
    }
    
    private boolean colaLlena(){
        return (fin+1)% this.colaArreglo.length==this.inicio;
    }
    
    @Override
    public void agregar(T dato) {
        /*
        if(colaLlena()){
            expande();
        }
        if(isEmpty()){
            inicio=0;
        }
        */
        if(isEmpty()){
            inicio=0;
        }else{
            if(colaLlena()){
                expande();
            }
        }
        
        fin=(fin+1)%this.colaArreglo.length;
        this.colaArreglo[fin]=dato;
    }

    private void expande() {
        int length=this.colaArreglo.length;
        
        T[] actualizado=(T[])new Object[length*2];
        
        for (int i = 0; i < length; i++) {
            actualizado[i]=(T) this.colaArreglo[(this.inicio+i)%length];
        }
            this.colaArreglo=actualizado;
            inicio=0;
            fin=length-1;
    }
    
    
    @Override
    public T quitar() {
        T eliminado=null;
        if(!isEmpty()){
            eliminado=this.colaArreglo[inicio];
            this.colaArreglo[inicio]=null;//agregado por mí
            if(inicio==fin){
                inicio=-1;
                fin=-1;
            }else{
                inicio=(inicio+1)%colaArreglo.length;
            }
        }
        return eliminado;
    }

    @Override
    public boolean isEmpty() {
       return fin==-1;
    }

    @Override
    public T consultaInicio() {
        T elem=null;
        if(!this.isEmpty()){
            elem=this.colaArreglo[this.inicio];
        }
        return elem;
    }

    @Override
    public int cuentaElementos(){
        int total=0;
        if(this.inicio<this.fin){
            total=(this.fin-this.inicio)+1;
        }else if(this.fin<this.inicio){
            total=((this.colaArreglo.length)-this.inicio)+(this.fin+1);
        }else if(this.inicio==this.fin && fin!=-1){
            total=1;
        }
        
        return total;
    }
    
    
    private String toStringRecursivo(int indice, StringBuilder sb, int length){
        sb.append(this.colaArreglo[indice]);
        if(indice!=fin){//   LA CONDICIÓN PARA INTERRUMPIR ES LLEGAR AL FIN
            return toStringRecursivo((indice+1)%length, sb, length);
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        int length=this.colaArreglo.length;
        StringBuilder sb=new StringBuilder();
        if(!this.isEmpty()){
            return toStringRecursivo(this.inicio, sb,length);
        }else{
            return "Cola Vacía";
        }
    }

    @Override
    public T consultaUltimo() {
        T elem=null;
        if(!this.isEmpty()){
            elem=this.colaArreglo[this.fin];
        }
        return elem;  
    }

    @Override
    public ArrayList<T> multiQuita(int n) {
         ArrayList<T> lista=new ArrayList<T>();
         while(!this.isEmpty() && n<=this.cuentaElementos() && n!=0){
             lista.add(this.quitar());
             n--;
         }
         return lista;
    }

}
