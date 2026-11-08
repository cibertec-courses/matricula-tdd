package pe.edu.cibertec.matricula;

public class CalculadoraMatricula {

    private static final double PRECIO_POR_CREDITO = 120.0;
    public double calcularCosto(int creditos) {

        if (creditos < 1 || creditos > 24) {
            throw new IllegalArgumentException("Los creditos debe estar entre 1 y 24");
        }

        return  creditos * PRECIO_POR_CREDITO;
    }
}
