package pe.edu.cibertec.matricula;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Servicio de Matricula (con Mockito)")
public class SrevicioMatriculaTest {

    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private MatriculaRepository matriculaRepository;
    @Mock
    private NotificadorEmail notificador;

    @Spy
    private CalculadoraMatricula calculadora = new CalculadoraMatricula();

    @InjectMocks
    private ServicioMatricula servicio;

    @Captor
    private ArgumentCaptor<Matricula> matriculaCaptor;

    @Captor
    private ArgumentCaptor<String> mensaCaptor;

    @Test
    @DisplayName("Calcula el monto con el descuento del alumno")
    void calculaElMontoConElDescuentoDelAlumno() {

        /// A-A-A

        //Arrange - Preaparar
        Alumno ana = new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0);
        when(alumnoRepository.buscarPorCodigo("A001")).thenReturn(Optional.of(ana));

        // Act - Ejecuctar
        Matricula matricula = servicio.matricular("A001",3);

        // Assert - Verficar
        assertEquals("A001", matricula.codigoAlumno());
        assertEquals(3, matricula.creditos());
        assertEquals(288.0, matricula.monto(),0.001);
    }

    @Test
    @DisplayName("Un alumno sin descuento paga el monto completo")
    void alumnoSinDescuentoPagaElMontoCompleto() {
        Alumno luis =  new Alumno("A002", "Luis Ramos", "luis@correoqa.com", 12.0);
        when(alumnoRepository.buscarPorCodigo("A002")).thenReturn(Optional.of(luis));
        Matricula matricula = servicio.matricular("A002",3);

        assertEquals(360.0,matricula.monto(),0.001);
    }

    @Test
    @DisplayName("Guarda la Matricula y notifica al alumno")
    void guardaLaMatriculaYNotificaAlumno() {
        Alumno ana = new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0);
        when(alumnoRepository.buscarPorCodigo("A001")).thenReturn(Optional.of(ana));

        servicio.matricular("A001",3);
        verify(matriculaRepository).guardar(any(Matricula.class));

        verify(notificador, times(1)).enviar(eq("ana@correoqa.com"), anyString(), anyString());

        verify(calculadora).calcularCostoFinal(3,18.0);
    }

    @Test
    @DisplayName("Guarda la matricula con los datos correcto")
    void guardaLaMatriculaConLosDatosCorrecto() {
        Alumno ana = new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0);
        when(alumnoRepository.buscarPorCodigo("A001")).thenReturn(Optional.of(ana));

        servicio.matricular("A001",3);
        verify(matriculaRepository).guardar(matriculaCaptor.capture());
        Matricula guardada = matriculaCaptor.getValue();
        assertEquals("A001", guardada.codigoAlumno());
        assertEquals(3, guardada.creditos());
        assertEquals(288.0, guardada.monto(),0.001);


    }

    @Test
    @DisplayName("El correo incluy el nombre del alumno y el monto")
    void elCorreoIncluyNombreYMonto(){
        Alumno ana = new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0);
        when(alumnoRepository.buscarPorCodigo("A001")).thenReturn(Optional.of(ana));

        servicio.matricular("A001",3);

        verify(notificador).enviar(eq("ana@correoqa.com"), eq("Matricula confirmada"), mensaCaptor.capture());

        String mensaje = mensaCaptor.getValue();

        assertTrue(mensaje.contains("Ana Torres"));
        assertTrue(mensaje.contains("288.0"));
    }

    @Test
    @DisplayName("Si el alumno no existe, no guardar ni notificar")
    void elAlumnoNoExisteNoGuardarNiNotificar() {
        when(alumnoRepository.buscarPorCodigo("Z999")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            servicio.matricular("Z999",3);
        });

        verify(matriculaRepository, never()).guardar(any());
        verifyNoInteractions(notificador);
    }

    // Primero guardar y despues notificar

}
