package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class LineaHorizonte {
	
	private ArrayList <Punto> LineaHorizonte ;
	
    /*
     * Constructor sin par�metros
     */
    public LineaHorizonte()
    {
        LineaHorizonte = new ArrayList <Punto>();
    }
            
    /*
     * m�todo que devuelve un objeto de la clase Punto
     */
    public Punto getPunto(int i) {
        return (Punto)this.LineaHorizonte.get(i);
    }
    
    // A�ado un punto a la l�nea del horizonte
    public void addPunto(Punto p)
    {
        LineaHorizonte.add(p);
    }    
    
    // m�todo que borra un punto de la l�nea del horizonte
    public void borrarPunto(int i)
    {
        LineaHorizonte.remove(i);
    }
    
    public int size()
    {
        return LineaHorizonte.size();
    }
    // m�todo que me dice si la l�nea del horizonte est� o no vac�a
    public boolean isEmpty()
    {
        return LineaHorizonte.isEmpty();
    }
   
    /*
      M�todo al que le pasamos una serie de par�metros para poder guardar 
      la linea del horizonte resultante despu�s de haber resuelto el ejercicio
      mediante la t�cnica de divide y vencer�s.
     */
    
    public void guardaLineaHorizonte (String fichero, PrintWriter out)
    {
        try
        {
           Punto p = new Punto();
           FileWriter fileWriter = new FileWriter(fichero);
            out = new PrintWriter (fileWriter);
         
            for(int i=0; i<this.size(); i++)
            {
                p=(getPunto(i));
                imprimirout(out,p);
            }
            out.close();
        }
        catch(Exception e){}
    }
    private void imprimirout(PrintWriter out ,Punto p) {
    	out.print(p.getX());
        out.print(" ");
        out.print(p.getY());
        out.println();
    }
    
    public void imprimir (){
    	
    	for(int i=0; i< LineaHorizonte.size(); i++ ){
    		//System.out.println("X: " + LineaHorizonte.get(i).getX() + " Y: " + LineaHorizonte.get(i).getY());
    		System.out.println(cadena(i));
    	}
    }
    
    public String cadena (int i){
    	Punto p = LineaHorizonte.get(i);
    	int x = p.getX();
    	int y = p.getY();
    	
		return xy(x,y);
    }
    
    private String xy(int x, int y) {
    	String linea = "Punto [x=";
		linea = linea + x;
		linea = linea + ", y=";
		linea = linea + y;
		linea = linea +  "]";
    	return linea;
    }
    
    
    
}
