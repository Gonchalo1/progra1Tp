package juego;

import java.awt.Color;
import entorno.Entorno;

public class Niveles {


    private Entorno entorno;
    private Princesa princesa;
    private Castillo castillo;
    private GestionadorPlataformas plataformas;
    private Proyectil proyectil;
    private GestionadorEnemigos enemigos;
    
    
    private double camaraX = 0;
	private double maxCamara = 4;
    
	private int nivel = 1; 
    
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
    }

    public void inicializarNivel() {
    	princesa = new Princesa(entorno.ancho()/2,200,20,20, entorno);
    	castillo = new Castillo(200, 550, "castillo.jpg", this.entorno);
    	plataformas = new GestionadorPlataformas();
		plataformas.crearPiso(300, entorno);
		proyectil = new Proyectil(600, entorno.alto() - 15);
		enemigos = new GestionadorEnemigos(entorno);
		enemigos.inicializarEnemigos(10);
    }
    
    
    public void actualizarCamara(Princesa princesa) {
		if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			camaraX += 1;
		} else {
			camaraX = 0;
		}
		if (camaraX > maxCamara) {
			camaraX = maxCamara;
		}
	}
    
    public void ejecutarNivel1() {
    	
        
      
        
        actualizarCamara(princesa);
        princesa.dibujarPrincesa();
        plataformas.colisionesPlataformas(princesa);
        princesa.moverPrincesa();
        plataformas.dibujarPlataformas(camaraX);
        
        if (proyectil != null && !proyectil.disparo(princesa, entorno)) {
            proyectil = null;
        }
        
        enemigos.actualizarEnemigos();
        enemigos.mantenerEnemigos();
        castillo.dibujar(camaraX);

        // Si pasa 2 segundos en el castillo, pasamos al nivel 2
        if (castillo.verificarVictoria(princesa)) {
            cambiarAlNivel2(princesa);
        }
    }

    public void ejecutarNivel2() {
        
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
        nivel = 2; 
        princesa.setX(100);
        princesa.setY(480); 
        enemigos.limpiarEnemigos(); 
    }
    
    public int getNivel() { return nivel; }
}