package pe.edu.cibertec.matricula;

public class CaculadoraNotas {

    private static final double PESO_T1 =  0.20;
    private static final double PESO_T2 =  0.35;
    private static final double PESO_EF =  0.45;
    private static final double NOTA_MINIMA =  0;
    private static final double NOTA_MAXIMA =  20;
    private static final double NOTA_APROBATORIA = 13;

    public double calcularPromedio(double t1, double t2, double ef) {
        validarNota(t1);
        validarNota(t2);
        validarNota(ef);
        return t1 *  PESO_T1 + t2 * PESO_T2 + ef * PESO_EF;
    }

    void validarNota(double nota){
        if(nota <NOTA_MINIMA || nota > NOTA_MAXIMA){
            throw new IllegalArgumentException("La nota debe estar entre 0 y 20");
        }
    }
    boolean estaAprobado(double promedio){
        return promedio >= NOTA_APROBATORIA;
    }
}

