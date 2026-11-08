package pe.edu.cibertec.matricula;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraMatriculaTest {
    private CalculadoraMatricula calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraMatricula();
    }

    @Test
    void calculaCostoMultiplicandoCreditosPorPrecioDelCredito() {
        // Act (ejecutar)
        double costo = calculadora.calcularCosto(10);
        // Assert (verificar)
        assertEquals(1200.0, costo, 0.001);
    }

    @Test
    void laznarErrorSiLosCreditosSonCero() {
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCosto(0)
        );
        assertEquals("Los creditos debe estar entre 1 y 24", error.getMessage());
    }

    @Test
    void lanzarErrorSiLosCreditosSuperanElMaximo() {
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCosto(25)
        );
        assertEquals("Los creditos debe estar entre 1 y 24", error.getMessage());
    }

    @Test
    void acpetaElMinimoDeUnCreditos() {
        assertEquals(120.0, calculadora.calcularCosto(1), 0.001);
    }

    @Test
    void acpetaElMaximoDeVeinticuadreCreditos() {
        assertEquals(2880., calculadora.calcularCosto(24), 0.001);
    }

    /// SIN PARAMETRIZACION
    @Test
    void aplicaVeintePorCientoConPromedioDeExcelencia() {
        double descuento = calculadora.calcularPorcentajeDescuento(18.0);
        assertEquals(0.20, descuento, 0.001);

    }

    /// CON PARAMETRIZACION
    @ParameterizedTest(name = "promedio {0} -> descuento {1}")
    @CsvSource({
            "20.0,  0.20",
            "17.0,  0.20",
            "16.99,  0.10",
            "15.0,  0.10",
            "14.99,  0.0",
            "0.0,  0.0",
    })
    void caclulaPorcentajeDescuentoSegunPromedio(double promedio, double esperado) {
        double descuento = calculadora.calcularPorcentajeDescuento(promedio);
        assertEquals(esperado, descuento, 0.001);
    }

    @ParameterizedTest(name = "{0} creditos con promedio {1} -> S/ {2}")
    @CsvSource(textBlock = """
            3,18.0,288,0
            3,15.5, 324.0
            3,12.0,360.0
            24,20.0,2304.0
            """)
    void calculaCostoFinalConDescuento(int creditos, double promedio, double esperado) {
        double costoFinal = calculadora.calcularCostoFinal(creditos, promedio);
        assertEquals(esperado, costoFinal, 0.001);
    }

    @Nested
    @DisplayName("Costo por credito")
    class CostoPorCreditos {

        @ParameterizedTest(name = "{0} creditos -> S/ {1}")
        @CsvSource({
                "1,120.0",
                "3,360.0",
                "5,600.0",
                "24,2880.0",
        })
        @DisplayName("Multiplica los creditos por S/120")
        void calculaCostoSegunCredito(int creditos, double esperado) {
            assertEquals(esperado, calculadora.calcularCosto(creditos), 0.001);
        }

        @ParameterizedTest(name = "{0} creditos -> error")
        @ValueSource(ints = {0, 25, -1, -20})
        @DisplayName("Rechaza creditos fuera de rando 1-24")
        void lanzaErrorSiLosCreditosEstanFueraDeRango(int creditos) {
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> calculadora.calcularCosto(creditos));
            assertEquals("Los creditos debe estar entre 1 y 24", error.getMessage());
        }


    }

    @Nested
    @DisplayName("Descuento por promedio")
    class DescuentoPorPromedio {

        @ParameterizedTest(name = "promedio {0} → descuento {1}")
        @CsvSource({
                "20.0,  0.20",
                "17.0,  0.20",
                "16.99, 0.10",
                "15.0,  0.10",
                "14.99, 0.0",
                "0.0,   0.0"
        })
        @DisplayName("Aplica 20 %, 10 % o 0 % según el promedio")
        void calculaPorcentajeDescuentoSegunPromedio(double promedio, double esperado) {
            assertEquals(esperado, calculadora.calcularPorcentajeDescuento(promedio), 0.001);
        }
    }

    @Nested
    @DisplayName("Costo final")
    class CostoFinal {

        @ParameterizedTest(name = "{0} créditos con promedio {1} → S/ {2}")
        @CsvSource(textBlock = """
                3,  18.0, 288.0
                3,  15.5, 324.0
                3,  12.0, 360.0
                24, 20.0, 2304.0
                """)
        @DisplayName("Descuenta el porcentaje al costo por créditos")
        void calculaCostoFinalConDescuento(int creditos, double promedio, double esperado) {
            assertEquals(esperado, calculadora.calcularCostoFinal(creditos, promedio), 0.001);
        }
    }
}



