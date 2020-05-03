package model;

import app.Properties;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ProcesadorImagenes {

    private static ProcesadorImagenes instance;

    public static ProcesadorImagenes getInstance() {
        if (instance == null) {
            instance = new ProcesadorImagenes();
        }
        return instance;
    }

    public static BufferedImage generarMiniaturaImagen(BufferedImage imagen) {
        Integer miniatura_ancho = Integer.parseInt(Properties.getProperty( "img_MINIATURA_ANCHO"));
        Integer miniatura_alto = Integer.parseInt(Properties.getProperty("img_MINIATURA_ALTO"));
        BufferedImage miniatura = new BufferedImage(miniatura_ancho, miniatura_alto, imagen.getType());
        Graphics2D grafico = miniatura.createGraphics();
        grafico.drawImage(imagen, 0, 0, miniatura_ancho, miniatura_alto, null);
        grafico.dispose();
        return miniatura;
    }

    public String generarPathArchivo() {
        String fecha = new Date().toString();
        String caracteresAleatorios = RandomStringUtils.randomAlphabetic(4);
        String pathArchivo = fecha + caracteresAleatorios + ".jpg";
        return pathArchivo;
    }

    public String subirImagen(String pathImagen) {
        String pathArchivo = generarPathArchivo();
        try {
            BufferedImage imagen = cargarImagen(pathImagen);
            BufferedImage imagenMiniatura = generarMiniaturaImagen(imagen);
            ImageIO.write(imagenMiniatura, "jpg", new File(pathArchivo));
            return pathArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void borrarImagen(String pathImagen) {
        File archivoImagen = new File(pathImagen);
        archivoImagen.delete();
    }

    public BufferedImage cargarImagen(String pathImagen) {

        try {
            return ImageIO.read(new File(pathImagen));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
