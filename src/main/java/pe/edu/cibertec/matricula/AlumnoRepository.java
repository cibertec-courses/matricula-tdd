package pe.edu.cibertec.matricula;

import java.util.Optional;

public interface AlumnoRepository {
    Optional<Alumno> buscarPorCodigo(String codigo);
}
