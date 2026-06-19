import java.time.LocalDate;
import java.util.Objects;

public class CuentaCredito extends Cuenta {
    private double limiteCredito;
    private String tipo;

    public CuentaCredito(String numeroCuenta, LocalDate fechaEmision,
                         LocalDate fechaVencimiento,
                         double porcentajeInteres,
                         double limiteCredito, String tipo) {

        super(numeroCuenta, 0, fechaEmision,
                fechaVencimiento, porcentajeInteres);

        this.limiteCredito = limiteCredito;
        this.tipo = tipo;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void realizarPago(double monto) {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de credito esta vencida.");
            return;
        }

        if (!montoValido(monto)) {
            System.out.println("El monto del pago debe ser mayor a 0.");
            return;
        }

        if (getSaldo() + monto > limiteCredito) {
            System.out.println(
                    "Pago rechazado: la cuenta de credito supera el limite disponible."
            );
            return;
        }

        setSaldo(getSaldo() + monto);
    }

    public void abonar(double monto) {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de credito esta vencida.");
            return;
        }

        if (!montoValido(monto)) {
            System.out.println("El monto del abono debe ser mayor a 0.");
            return;
        }

        if (getSaldo() - monto < 0) {
            setSaldo(0);
        } else {
            setSaldo(getSaldo() - monto);
        }
    }

    @Override
    public void generarIntereses() {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de credito esta vencida.");
            return;
        }

        double interes = getSaldo() * getPorcentajeInteres() / 100;

        if (getSaldo() + interes > limiteCredito) {
            System.out.println(
                    "Intereses rechazados: la cuenta de credito supera el limite disponible."
            );
            return;
        }

        setSaldo(getSaldo() + interes);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof CuentaCredito)) {
            return false;
        }

        if (!super.equals(objeto)) {
            return false;
        }

        CuentaCredito cuenta = (CuentaCredito) objeto;

        return Double.compare(cuenta.limiteCredito, limiteCredito) == 0
                && Objects.equals(tipo, cuenta.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), limiteCredito, tipo);
    }

    @Override
    public String toString() {
        return "Cuenta de credito\n" +
                "  Numero de cuenta: " + getNumeroCuenta() + "\n" +
                "  Tipo: " + tipo + "\n" +
                "  Saldo adeudado: $" +
                String.format(java.util.Locale.US, "%.2f", getSaldo()) + "\n" +
                "  Limite de credito: $" +
                String.format(java.util.Locale.US, "%.2f", limiteCredito) + "\n" +
                "  Fecha de emision: " + getFechaEmision() + "\n" +
                "  Fecha de vencimiento: " + getFechaVencimiento() + "\n" +
                "  Porcentaje de interes: " + getPorcentajeInteres() + "%";
    }
}