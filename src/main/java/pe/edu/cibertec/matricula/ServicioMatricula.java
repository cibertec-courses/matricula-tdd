package pe.edu.cibertec.matricula;

public class ServicioMatricula {

    private final AlumnoRepository alumnoRepository;
    private final MatriculaRepository matriculaRepository;
    private final NotificadorEmail notificador;
    private final CalculadoraMatricula calculadora;


    public ServicioMatricula(AlumnoRepository alumnoRepository,
                             MatriculaRepository matriculaRepository,
                             NotificadorEmail notificador, CalculadoraMatricula calculadora
                             ){
        this.alumnoRepository = alumnoRepository;
        this.matriculaRepository = matriculaRepository;
        this.notificador = notificador;
        this.calculadora = calculadora;

    }


    public Matricula matricular(String codigoAlumno, int creditos) {
        Alumno alumno = alumnoRepository.buscarPorCodigo( codigoAlumno).orElseThrow();
        double monto = calculadora.calcularCostoFinal(creditos, alumno.promedio());
        Matricula matricula = new Matricula(alumno.codigo(), creditos, monto);

        matriculaRepository.guardar(matricula);
        notificador.enviar(
                alumno.email(),
                "Matricula confirmada",
                "Hola, "+ alumno.nombre() +",  tu matricula de " + creditos
                + " creditos fue registrada. Monto S/."+ monto
        );

        return matricula;
    }
}
