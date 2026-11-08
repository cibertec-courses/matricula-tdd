package pe.edu.cibertec.matricula;

public class CalculadoraMatricula {

    private static final double PRECIO_POR_CREDITO = 120.0;
    private static final int CREDITOS_MINIMOS = 1;
    private static final int CREDITOS_MAXIMOS = 24;

    private static final double PROMEDIO_EXCELENCIA = 17.0;
    private static final double PROMEDIO_DESTACADO = 15.0;
    private static final double DESCUENTO_EXCELENCIA = 0.20;
    private static final double DESCUENTO_DESTACADO = 0.10;


    public double calcularCosto(int creditos) {
        validarCreditos(creditos);
        return creditos * PRECIO_POR_CREDITO;
    }

    void validarCreditos(int creditos) {
        if (creditos < CREDITOS_MINIMOS || creditos > CREDITOS_MAXIMOS) {
            throw new IllegalArgumentException("Los creditos debe estar entre " + CREDITOS_MINIMOS + " y " + CREDITOS_MAXIMOS );
        }
    }

    public double calcularPorcentajeDescuento(double promedio) {
        if (promedio >= PROMEDIO_EXCELENCIA) {
            return DESCUENTO_EXCELENCIA;
        }
        if (promedio >= PROMEDIO_DESTACADO) {
            return DESCUENTO_DESTACADO;
        }
        return 0.0;
    }

    public double calcularCostoFinal(int creditos, double promedio) {
        double costo = calcularCosto(creditos);
        return costo - (costo * calcularPorcentajeDescuento(promedio));
    }
}
