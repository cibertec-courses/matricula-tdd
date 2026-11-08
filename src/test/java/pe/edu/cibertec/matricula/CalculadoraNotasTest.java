package pe.edu.cibertec.matricula;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraNotasTest {

    /// Promedio final = T1 (20%) + T2 (35%) + EF(45%)
    /// Nota es de 0 - 20
    /// Minimo aprobatorio es 13

    // Calcular el Promedio Con notas Iguales
    @Test
    void calculaPromedioConNotasIguales(){
        CaculadoraNotas calculadora = new CaculadoraNotas();
        double promedio = calculadora.calcularPromedio(10,10,10);
        assertEquals(10.0, promedio, 0.001);
    }

    // Calcular Prmedio ponderado con notas distintas
    @Test
    void calculaPromedioPonderadoConNotasDistintas(){
        CaculadoraNotas calculadoraNotas = new CaculadoraNotas();
        double promedio = calculadoraNotas.calcularPromedio(12,14,16);
        assertEquals(14.5, promedio, 0.001);
    }
    // Lanzar un error si un nota es negativa
    @Test
    void lanzarErrorSiTieneNotaNegativa(){
        CaculadoraNotas calculadora = new CaculadoraNotas();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularPromedio(-20, -10 , 10));

        assertEquals("La nota debe estar entre 0 y 20", exception.getMessage());
    }

    // aceptarNotasMinimas
    @Test
    void aceptaNotasMinimas(){
        CaculadoraNotas calculadora = new CaculadoraNotas();
        double promedio = calculadora.calcularPromedio(0,0,0);
        assertEquals(0, promedio, 0.001);
    }
    // aceptarNotasMaximas
    @Test
    void aceptaNotasMaximas(){
        CaculadoraNotas calculadora = new CaculadoraNotas();
        double promedio = calculadora.calcularPromedio(20,20,20);
        assertEquals(20, promedio, 0.001);
    }
    // Aprueba con 13
    @Test
    void apruebaConTrece(){
        CaculadoraNotas calculadora = new CaculadoraNotas();
        assertTrue(calculadora.estaAprobado(13.0));
    }

}
