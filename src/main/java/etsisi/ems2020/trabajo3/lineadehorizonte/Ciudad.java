package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xi, xd, h, siendo. Siendo
 xi la coordenada en X origen del edificio iésimo, xd la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {
	
    private ArrayList <Edificio> ciudad;

    public Ciudad()
		    {
		    	
		    	/*
		    	 * Generamos una ciudad de manera aleatoria para hacer 
		    	 * pruebas.
		    	 */
		ciudad = new ArrayList <Edificio>();
		int n = 5;
		int i=0;
		for(i=0;i<n;i++)
		{
		crearedificio(ciudad);
		}
		        
		ciudad = new ArrayList <Edificio>();
}
    private void crearedificio(ArrayList<Edificio> ciudad) {
    	int xi=(int)(Math.random()*100);
		int y=(int)(Math.random()*100);
		int xd=(int)(xi+(Math.random()*100));
		Edificio auxedificio= new Edificio(xi,y);
		auxedificio.setXd(xd);
		this.addEdificio(auxedificio);
    }
    
        
    public Edificio getEdificio(int i) {
        return (Edificio)this.ciudad.get(i);
    }
    
       
    public void addEdificio (Edificio e)
    {
        ciudad.add(e);
    }
    public void removeEdificio(int i)
    {
        ciudad.remove(i);
    }
    
    public int size()
    {
        return ciudad.size();
    }
    
    public LineaHorizonte getLineaHorizonte()
    {
    	// pi y pd, representan los edificios de la izquierda y de la derecha.
        int pi = 0,pd = ciudad.size()-1;                             
        return crearLineaHorizonte(pi, pd);  
    }
    
public LineaHorizonte crearLineaHorizonte(int pi, int pd)
{
LineaHorizonte linea = new LineaHorizonte(); // LineaHorizonte de salida
Punto p1 = new Punto(), p2 = new Punto();  // punto donde se guardara en su X la Xi del efificio y en su Y la altura del edificio
Edificio edificio = new Edificio();    
        
// Caso base, la ciudad solo tiene un edificio, el perfil es el de ese edificio. 
if(pi==pd) 
{
	edificio = this.getEdificio(pi); // Obtenemos el único edificio y lo guardo en b
	p1.setX(edificio.getXi()); p1.setY(edificio.getY());       
	p2.setX(edificio.getXd()); p2.setY(0);  
	linea.addPunto(p1); linea.addPunto(p2);     
}
else
{
int medio=(pi+pd)/2;
LineaHorizonte s1 = this.crearLineaHorizonte(pi,medio);  LineaHorizonte s2 = this.crearLineaHorizonte(medio+1,pd);
//Punto a=null, b=null, aux=null;
linea = LineaHorizonteFussion(s1,s2); 
}

return linea;

    }





    
    /**
     * Función encargada de fusionar los dos LineaHorizonte obtenidos por la técnica divide y
     * vencerás. Es una función muy compleja ya que es la encargada de decidir si un
     * edificio solapa a otro, si hay edificios contiguos, etc. y solucionar dichos
     * problemas para que el LineaHorizonte calculado sea el correcto.
     */

public LineaHorizonte LineaHorizonteFussion(LineaHorizonte s1,LineaHorizonte s2)
{
	// en estas variables guardaremos las alturas de los puntos anteriores, en s1y la del s1, en s2y la del s2 
	// y en prev guardaremos la previa del segmento anterior introducido
	 //Punto paux=new Punto();
    int s1y=-1, s2y=-1, prev=-1;    
    LineaHorizonte salida = new LineaHorizonte(); // LineaHorizonte de salida
    imprimirout(s1,s2);
    salida=mientrasBig(s1,s2);
   mientras1(s1,salida);
   mientras1(s2,salida);
   
    //Mientras tengamos elementos en s1 y en s2
    
    
    return salida;
}


private void  mientras1(LineaHorizonte s1, LineaHorizonte salida){
	
    Punto paux=new Punto();
    int prev=-1;
    while ((!s1.isEmpty())) //si aun nos quedan elementos en el s1
    {
        paux=s1.getPunto(0); // guardamos en paux el primer punto
        
        if (paux.getY()!=prev) // si paux no tiene la misma altura del segmento previo
        {
            salida.addPunto(paux); // lo añadimos al LineaHorizonte de salida
            prev = paux.getY();    // y actualizamos prev
        }
        s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
    }
    
}

private void iff(Punto paux,LineaHorizonte salida,Integer prev) {
	if (paux.getY()!=prev) // si este maximo no es igual al del segmento anterior
    {
        salida.addPunto(paux); // añadimos el punto al LineaHorizonte de salida
        prev = paux.getY();    // actualizamos prev
    }
}

 private LineaHorizonte mientrasBig(LineaHorizonte s1,LineaHorizonte s2){

	 Integer s1y=-1, s2y=-1, prev=-1;  
 	LineaHorizonte salida = new LineaHorizonte();
       Punto paux = new Punto(); Punto  p1 = s1.getPunto(0);Punto p2 = s2.getPunto(0);
       
    while ((!s1.isEmpty()) && (!s2.isEmpty())) 
    {
    	 // guardamos el primer elemento de s1 // guardamos el primer elemento de s2
        
        if (p1.getX() < p2.getX()) // si X del s1 es menor que la X del s2
        {
            paux.setX(p1.getX());                // guardamos en paux esa X
            paux.setY(Math.max(p1.getY(), s2y)); // y hacemos que el maximo entre la Y del s1 y la altura previa del s2 sea la altura Y de paux
            
            iff(paux,salida,prev);
            s1y = p1.getY();   // actualizamos la altura s1y
            s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }
        else if (p1.getX() > p2.getX()) // si X del s1 es mayor que la X del s2
        {
            paux.setX(p2.getX());                // guardamos en paux esa X
            paux.setY(Math.max(p2.getY(), s1y)); // y hacemos que el maximo entre la Y del s2 y la altura previa del s1 sea la altura Y de paux

            iff(paux,salida,prev);
            s2y = p2.getY();   // actualizamos la altura s2y
            s2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es valido)
        }
        else // si la X del s1 es igual a la X del s2
        {
            if ((p1.getY() > p2.getY()) && (p1.getY()!=prev)) // guardaremos aquel punto que tenga la altura mas alta
            {
                salida.addPunto(p1);
                prev = p1.getY();
            }
            if ((p1.getY() <= p2.getY()) && (p2.getY()!=prev))
            {
                salida.addPunto(p2);
                prev = p2.getY();
            }
            s1y = p1.getY();   // actualizamos la s1y e s2y
            s2y = p2.getY();
            s1.borrarPunto(0); // eliminamos el punto del s1 y del s2
            s2.borrarPunto(0);
        }
    }
    return salida;
}


void imprimirout(LineaHorizonte s1,LineaHorizonte s2){
    System.out.println("==== S1 ====");
    s1.imprimir();
    System.out.println("==== S2 ====");
    s2.imprimir();
    System.out.println("\n");
}
    
    /*
     Método que carga los edificios que me pasan en el
     archivo cuyo nombre se encuentra en "fichero".
     El formato del fichero nos lo ha dado el profesor en la clase del 9/3/2020,
     pocos días antes del estado de alarma.
     */

    public void cargarEdificios (String fichero)
    {
        try
        {
            int xi, y, xd;
            Scanner sr = new Scanner(new File(fichero));
            while(sr.hasNext())
            {
                xi = sr.nextInt();xd = sr.nextInt(); y = sr.nextInt();
                Edificio Salida = new Edificio(xi, y);
                Salida.setXd(xd);
                this.addEdificio(Salida);
            }
        }
        catch(Exception e){} 
           
    }

    
    public void metodoRandom(int n)
    {
        int i=0,xi,y,xd;
        for(i=0;i<n;i++)
        {
            xi=(int)(Math.random()*100);
            y=(int)(Math.random()*100);
            xd=(int)(xi+(Math.random()*100));
            Edificio auxedificio= new Edificio(xi,y);
    		auxedificio.setXd(xd);
    		this.addEdificio(auxedificio);
            
        }
    }
}
