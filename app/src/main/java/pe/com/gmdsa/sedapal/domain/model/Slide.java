package pe.com.gmdsa.sedapal.domain.model;

/**
 * Created by innovagmd on 25/01/17.
 */

public class Slide {
    private int titulo;
    private int descripcion;
    private int imagen;

    public Slide(int titulo, int descripcion, int imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
