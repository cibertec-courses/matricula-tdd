package pe.edu.cibertec.matricula;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Servicio de Matricula (con fakes hechos a mano)")
public class ServicioMatriculaFakeTest {

    static class AlumnoRepositoryFalso implements AlumnoRepository {
        private final Map<String, Alumno> alumnos = new HashMap<>();

        void aregagr(Alumno alumno) {
            alumnos.put(alumno.codigo(), alumno);
        }

        @Override
        public Optional<Alumno> buscarPorCodigo(String codigo) {
            return Optional.ofNullable(alumnos.get(codigo));
        }
    }

    static class MatriculaRepositoryFalso implements MatriculaRepository {
        final List<Matricula> guardadas =new ArrayList<>();

        @Override
        public void guardar(Matricula matricula) {
            guardadas.add(matricula);
        }
    }

    static class NotificadorEmailFalso implements NotificadorEmail {
        int enviados = 0;
        String ultimoDestinatario;
        @Override
        public void enviar(String destinatario, String asunto, String mensaje) {
            enviados += 1;
            ultimoDestinatario = destinatario;
        }
    }

    // Preparacion
    private AlumnoRepositoryFalso alumnoRepository;
    private MatriculaRepositoryFalso matriculaRepository;
    private NotificadorEmailFalso notificador;
    private ServicioMatricula servicio;

    @BeforeEach
    public void setup() {
        alumnoRepository = new AlumnoRepositoryFalso();
        matriculaRepository = new MatriculaRepositoryFalso();
        notificador = new NotificadorEmailFalso();

        servicio = new ServicioMatricula(alumnoRepository, matriculaRepository, notificador, new CalculadoraMatricula());


    }
    // TEST
    @Test
    @DisplayName("Calcula el monto con el descuento del alumno")
    void calculaElMontoConDescuentoDelAlumno() {
        alumnoRepository.aregagr(new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0));
        Matricula matricula = servicio.matricular("A001",3);

        assertEquals("A001", matricula.codigoAlumno());
        assertEquals(3, matricula.creditos());
        assertEquals(288.0, matricula.monto(), 0.001);
    }

    @Test
    @DisplayName("Guarda la matricula y notifica al alumno por correo")
    void guardaLaMatriculaYNotificadorPorCorreo() {
        alumnoRepository.aregagr(new Alumno("A001", "Ana Torres", "ana@correoqa.com", 18.0));

        servicio.matricular("A001",3);

        assertEquals(1, matriculaRepository.guardadas.size());
        assertEquals(1, notificador.enviados);
        assertEquals("ana@correoqa.com", notificador.ultimoDestinatario);
    }


}
