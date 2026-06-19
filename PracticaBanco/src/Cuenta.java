import java.time.LocalDate;
import java.util.Objects;

public abstract class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private double porcentajeInteres;

    public Cuenta(String numeroCuenta, double saldo, LocalDate fechaEmision,
                  LocalDate fechaVencimiento, double porcentajeInteres) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.porcentajeInteres = porcentajeInteres;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getPorcentajeInteres() {
        return porcentajeInteres;
    }

    public void setPorcentajeInteres(double porcentajeInteres) {
        this.porcentajeInteres = porcentajeInteres;
    }

    public boolean cuentaVencida() {
        LocalDate fechaActual = LocalDate.now();
        return fechaVencimiento.isBefore(fechaActual) || fechaVencimiento.isEqual(fechaActual);
    }

    public boolean montoValido(double monto) {
        return monto > 0;
    }

    public abstract void realizarPago(double monto);

    public abstract void generarIntereses();

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        Cuenta cuenta = (Cuenta) objeto;
        return Objects.equals(numeroCuenta, cuenta.numeroCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCuenta);
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", fechaEmision=" + fechaEmision +
                ", fechaVencimiento=" + fechaVencimiento +
                ", porcentajeInteres=" + porcentajeInteres +
                '}';
    }
}

