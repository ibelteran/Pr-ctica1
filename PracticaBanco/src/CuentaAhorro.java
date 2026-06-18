import java.time.LocalDate;

public class CuentaAhorro extends Cuenta {
    private static final double SALDO_MINIMO = 100;

    public CuentaAhorro(String numeroCuenta, double saldo, LocalDate fechaEmision,
                        LocalDate fechaVencimiento, double porcentajeInteres) {
        super(numeroCuenta, saldo, fechaEmision, fechaVencimiento, porcentajeInteres);

        if (saldo < SALDO_MINIMO) {
            throw new IllegalArgumentException("La cuenta de ahorro debe iniciar con al menos $100.");
        }
    }

    public void depositar(double monto) {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de ahorro esta vencida.");
            return;
        }

        if (!montoValido(monto)) {
            System.out.println("El monto del deposito debe ser mayor a 0.");
            return;
        }

        setSaldo(getSaldo() + monto);
    }

    @Override
    public void realizarPago(double monto) {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de ahorro esta vencida.");
            return;
        }

        if (!montoValido(monto)) {
            System.out.println("El monto del pago debe ser mayor a 0.");
            return;
        }

        if (getSaldo() - monto < SALDO_MINIMO) {
            System.out.println("Pago rechazado: la cuenta de ahorro no puede quedar con menos de $100.");
            return;
        }

        setSaldo(getSaldo() - monto);
    }

    @Override
    public void generarIntereses() {
        if (cuentaVencida()) {
            System.out.println("Transaccion bloqueada: la cuenta de ahorro esta vencida.");
            return;
        }

        double interes = getSaldo() * getPorcentajeInteres() / 100;
        setSaldo(getSaldo() + interes);
    }

    @Override
    public String toString() {
        return "Cuenta de ahorro\n" +
                "  Numero de cuenta: " + getNumeroCuenta() + "\n" +
                "  Saldo actual: $" + String.format(java.util.Locale.US, "%.2f", getSaldo()) + "\n" +
                "  Fecha de emision: " + getFechaEmision() + "\n" +
                "  Fecha de vencimiento: " + getFechaVencimiento() + "\n" +
                "  Porcentaje de interes: " + getPorcentajeInteres() + "%";
    }
}
