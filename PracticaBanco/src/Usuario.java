import java.util.Objects;

public abstract class Usuario {
    private String nombreCompleto;
    private String cedula;
    private String correoElectronico;
    private String contrasenia;

    public Usuario(String nombreCompleto, String cedula, String correoElectronico, String contrasenia) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        setContrasenia(contrasenia);
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (!validarFormatoContrasenia(contrasenia)) {
            throw new IllegalArgumentException("La contrasenia debe tener minimo 8 caracteres, una minuscula, una mayuscula, un numero y un caracter especial.");
        }
        this.contrasenia = contrasenia;
    }

    public boolean iniciarSesion(String correoElectronico, String contrasenia) {
        return this.correoElectronico.equalsIgnoreCase(correoElectronico)
                && this.contrasenia.equals(contrasenia);
    }

    public static boolean validarFormatoContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.length() < 8) {
            return false;
        }

        boolean tieneMinuscula = false;
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (int i = 0; i < contrasenia.length(); i++) {
            char caracter = contrasenia.charAt(i);

            if (Character.isLowerCase(caracter)) {
                tieneMinuscula = true;
            } else if (Character.isUpperCase(caracter)) {
                tieneMayuscula = true;
            } else if (Character.isDigit(caracter)) {
                tieneNumero = true;
            } else {
                tieneEspecial = true;
            }
        }

        return tieneMinuscula && tieneMayuscula && tieneNumero && tieneEspecial;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) objeto;
        return Objects.equals(cedula, usuario.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}
