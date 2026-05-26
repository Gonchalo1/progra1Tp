package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Castillo {

    private double x;
    private double y;
    private Image imagen;
    private Entorno entorno;
    

    // Modificamos el constructor para que también reciba el tamaño del castillo
    public Castillo(double x, double y, String rutaImagen, Entorno entorno) {
        this.x = x;
        this.y = y;
        this.entorno = entorno; 
        this.imagen = Herramientas.cargarImagen(rutaImagen); 
    }

    // Dibuja el castillo
    public void dibujar() {
        if (this.imagen != null) {
            this.entorno.dibujarImagen(this.imagen, this.x, this.y, 0);
        }
    }
    
    // --- GETTERS Y SETTERS CORRECTOS ---
    // Los necesitas en el Main para saber dónde está el castillo
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
