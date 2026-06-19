import java.util.ArrayList;
import java.util.Objects;

public class Cliente extends Usuario {
    private String sexo;
    private String profesion;
    private String direccion;
    private ArrayList<CuentaAhorro> cuentasAhorro;
    private ArrayList<CuentaDebito> cuentasDebito;
    private ArrayList<CuentaCredito> cuentasCredito;

    public Cliente(String nombreCompleto, String cedula, String sexo,
                   String correoElectronico, String profesion,
                   String direccion, String contrasenia) {

        super(nombreCompleto, cedula, correoElectronico, contrasenia);

        this.sexo = sexo;
        this.profesion = profesion;
        this.direccion = direccion;
        this.cuentasAhorro = new ArrayList<>();
        this.cuentasDebito = new ArrayList<>();
        this.cuentasCredito = new ArrayList<>();
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<CuentaAhorro> getCuentasAhorro() {
        return cuentasAhorro;
    }

    public void setCuentasAhorro(ArrayList<CuentaAhorro> cuentasAhorro) {
        this.cuentasAhorro = cuentasAhorro;
    }

    public ArrayList<CuentaDebito> getCuentasDebito() {
        return cuentasDebito;
    }

    public void setCuentasDebito(ArrayList<CuentaDebito> cuentasDebito) {
        this.cuentasDebito = cuentasDebito;
    }

    public ArrayList<CuentaCredito> getCuentasCredito() {
        return cuentasCredito;
    }

    public void setCuentasCredito(ArrayList<CuentaCredito> cuentasCredito) {
        this.cuentasCredito = cuentasCredito;
    }

    public void agregarCuentaAhorro(CuentaAhorro cuentaAhorro) {
        cuentasAhorro.add(cuentaAhorro);
    }

    public void agregarCuentaDebito(CuentaDebito cuentaDebito) {
        cuentasDebito.add(cuentaDebito);
    }

    public void agregarCuentaCredito(CuentaCredito cuentaCredito) {
        cuentasCredito.add(cuentaCredito);
    }

    public double calcularSaldoFinalConsolidado() {
        double saldoFinal = 0;

        for (CuentaAhorro cuenta : cuentasAhorro) {
            saldoFinal += cuenta.getSaldo();
        }

        for (CuentaDebito cuenta : cuentasDebito) {
            saldoFinal += cuenta.getSaldo();
        }

        for (CuentaCredito cuenta : cuentasCredito) {
            saldoFinal -= cuenta.getSaldo();
        }

        return saldoFinal;
    }

    public String generarReporteCuentas() {
        StringBuilder reporte = new StringBuilder();

        reporte.append("Reporte de cuentas del cliente\n");
        reporte.append("Cliente: ").append(getNombreCompleto()).append("\n");
        reporte.append("Cedula: ").append(getCedula()).append("\n");
        reporte.append("Correo: ").append(getCorreoElectronico()).append("\n");
        reporte.append("----------------------------------------\n");

        reporte.append("Cuentas de ahorro:\n");

        if (cuentasAhorro.isEmpty()) {
            reporte.append("  No hay cuentas de ahorro registradas.\n");
        } else {
            for (CuentaAhorro cuenta : cuentasAhorro) {
                reporte.append(cuenta).append("\n");
            }
        }

        reporte.append("\nCuentas de debito:\n");

        if (cuentasDebito.isEmpty()) {
            reporte.append("  No hay cuentas de debito registradas.\n");
        } else {
            for (CuentaDebito cuenta : cuentasDebito) {
                reporte.append(cuenta).append("\n");
            }
        }

        reporte.append("\nCuentas de credito:\n");

        if (cuentasCredito.isEmpty()) {
            reporte.append("  No hay cuentas de credito registradas.\n");
        } else {
            for (CuentaCredito cuenta : cuentasCredito) {
                reporte.append(cuenta).append("\n");
            }
        }

        reporte.append("----------------------------------------\n");
        reporte.append("Saldo final consolidado: $")
                .append(String.format(java.util.Locale.US, "%.2f",
                        calcularSaldoFinalConsolidado()))
                .append("\n");

        return reporte.toString();
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Cliente)) {
            return false;
        }

        if (!super.equals(objeto)) {
            return false;
        }

        Cliente cliente = (Cliente) objeto;

        return Objects.equals(sexo, cliente.sexo)
                && Objects.equals(profesion, cliente.profesion)
                && Objects.equals(direccion, cliente.direccion)
                && Objects.equals(cuentasAhorro, cliente.cuentasAhorro)
                && Objects.equals(cuentasDebito, cliente.cuentasDebito)
                && Objects.equals(cuentasCredito, cliente.cuentasCredito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                sexo,
                profesion,
                direccion,
                cuentasAhorro,
                cuentasDebito,
                cuentasCredito
        );
    }

    @Override
    public String toString() {
        return "Cliente\n" +
                "  Nombre completo: " + getNombreCompleto() + "\n" +
                "  Cedula: " + getCedula() + "\n" +
                "  Sexo: " + sexo + "\n" +
                "  Correo electronico: " + getCorreoElectronico() + "\n" +
                "  Profesion: " + profesion + "\n" +
                "  Direccion: " + direccion;
    }
}