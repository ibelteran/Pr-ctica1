public class Administrador extends Usuario {

    public Administrador(String nombreCompleto, String cedula,
                         String correoElectronico, String contrasenia) {
        super(nombreCompleto, cedula, correoElectronico, contrasenia);
    }

    @Override
    public boolean equals(Object objeto) {
        return super.equals(objeto);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Administrador\n" +
                "  Nombre completo: " + getNombreCompleto() + "\n" +
                "  Cedula: " + getCedula() + "\n" +
                "  Correo electronico: " + getCorreoElectronico();
    }
}
