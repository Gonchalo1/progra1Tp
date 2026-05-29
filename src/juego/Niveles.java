package juego;

import java.awt.Color;
import entorno.Entorno;

public class Niveles {

    private Juego juego;
    private Entorno entorno;

    public Niveles(Juego juego, Entorno entorno) {
        this.juego = juego;
        this.entorno = entorno;
    }

    public void ejecutarNivel1() {
        Princesa princesa = juego.getPrincesa();
        Castillo castillo = juego.getCastillo();
        GestionadorPlataformas plataformas = juego.getPlataformas();
        
        juego.actualizarCamara(princesa);
        entorno.dibujarRectangulo(princesa.getX(), princesa.getY(), princesa.getAncho(), princesa.getAlto(), 0, Color.RED);
        plataformas.colisionesPlataformas(princesa);
        princesa.moverPrincesa();
        plataformas.dibujarPlataformas(juego.getCamaraY());
        
        if (juego.getProyectil() != null && !juego.getProyectil().disparo(princesa, entorno)) {
            juego.setProyectil(null);
        }
        
        juego.actualizarEnemigos();
        juego.mantenerEnemigos();
        castillo.dibujar();

        // Si pasa 2 segundos en el castillo, pasamos al nivel 2
        if (castillo.verificarVictoria(princesa)) {
            cambiarAlNivel2(princesa);
        }
    }

    public void ejecutarNivel2() {
        Princesa princesa = juego.getPrincesa();
        
        // 1. Suelo plano
        double nivelSueloY = 550;
        entorno.dibujarRectangulo(400, nivelSueloY, 800, 100, 0, Color.GRAY);
        
        // 2. Gravedad simple para el piso plano
        double limitePiso = nivelSueloY - 50 - (princesa.getAlto() / 2);
        if (princesa.getY() < limitePiso) {
            princesa.setY(princesa.getY() + 4); 
        } else {
            princesa.setY(limitePiso); 
        }
        
        // 3. Movimiento y render de la princesa
        princesa.moverPrincesa(); 
        entorno.dibujarRectangulo(princesa.getX(), princesa.getY(), princesa.getAncho(), princesa.getAlto(), 0, Color.RED);
        
        // 4. Texto informativo
        entorno.cambiarFont("Arial", 26, Color.RED, entorno.NEGRITA);
        entorno.escribirTexto("¡NIVEL 2: JEFE FINAL!", 260, 80);
    }

    private void cambiarAlNivel2(Princesa princesa) {
        juego.setNivel(2); 
        princesa.setX(100);
        princesa.setY(480); 
        juego.limpiarEnemigos(); 
    }
}