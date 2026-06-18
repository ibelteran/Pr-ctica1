public class Administrador extends Usuario {

    public Administrador(String nombreCompleto, String cedula, String correoElectronico, String contraseña) {
        super(nombreCompleto, cedula, correoElectronico, contraseña);
    }

    @Override
    public String toString() {
        return "Administrador\n" +
                "  Nombre completo: " + getNombreCompleto() + "\n" +
                "  Cedula: " + getCedula() + "\n" +
                "  Correo electronico: " + getCorreoElectronico();
    }
}
