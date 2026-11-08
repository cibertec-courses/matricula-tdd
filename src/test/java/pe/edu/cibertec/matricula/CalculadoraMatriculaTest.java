package pe.edu.cibertec.matricula;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraMatriculaTest {

    @Test
    void calculaCostoMultiplicandoCreditosPorPrecioDelCredito(){
        // Arrange (preparar)
        CalculadoraMatricula calculadora = new CalculadoraMatricula();
        // Act (ejecutar)
        double costo = calculadora.calcularCosto(10);
        // Assert (verificar)
        assertEquals(1200.0, costo, 0.001);
    }

    @Test
    void laznarErrorSiLosCreditosSonCero(){
        CalculadoraMatricula calculadora = new CalculadoraMatricula();

        IllegalArgumentException error =  assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCosto(0)
        );
        assertEquals("Los creditos debe estar entre 1 y 24", error.getMessage());
    }

    @Test
    void lanzarErrorSiLosCreditosSuperanElMaximo(){
        CalculadoraMatricula calculadora = new CalculadoraMatricula();

        IllegalArgumentException error =  assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCosto(25)
        );
        assertEquals("Los creditos debe estar entre 1 y 24", error.getMessage());
    }

    @Test
    void acpetaElMinimoDeUnCreditos(){
        CalculadoraMatricula calculadora = new CalculadoraMatricula();
        assertEquals(120.0, calculadora.calcularCosto(1), 0.001);
    }

    @Test
    void acpetaElMaximoDeVeinticuadreCreditos(){
        CalculadoraMatricula calculadora = new CalculadoraMatricula();
        assertEquals(2880., calculadora.calcularCosto(24), 0.001);
    }
}
