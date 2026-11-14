package pe.edu.cibertec.matricula;

public interface NotificadorEmail {
    void enviar(String destinatario, String asunto, String mensaje);
}