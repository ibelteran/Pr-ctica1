import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Banco banco = new Banco();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero(scanner, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarAdministrador(scanner);
                    break;
                case 2:
                    registrarCliente(scanner);
                    break;
                case 3:
                    crearCuenta(scanner);
                    break;
                case 4:
                    validarInicioSesion(scanner);
                    break;
                case 5:
                    realizarTransaccion(scanner);
                    break;
                case 6:
                    imprimirReporteCliente(scanner);
                    break;
                case 7:
                    listarClientes();
                    break;
                case 8:
                    listarCuentas();
                    break;
                case 9:
                    ejecutarPruebaAutomatica();
                    break;
                case 0:
                    System.out.println("Gracias por usar el sistema bancario.");
                    break;
                default:
                    System.out.println("Opcion inválida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }

    public static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("SISTEMA DE CUENTAS BANCARIAS");
        System.out.println("========================================");
        System.out.println("1. Registrar administrador");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear cuenta bancaria");
        System.out.println("4. Validar inicio de sesión");
        System.out.println("5. Realizar transacción");
        System.out.println("6. Ver reporte de cuentas de un cliente");
        System.out.println("7. Listar clientes registrados");
        System.out.println("8. Listar todas las cuentas registradas");
        System.out.println("9. Ejecutar prueba automatica de la practica");
        System.out.println("0. Salir");
        System.out.println("========================================");
    }

    public static void registrarAdministrador(Scanner scanner) {
        imprimirSubtitulo("Registro de administrador");

        if (banco.tieneAdministrador()) {
            System.out.println("El sistema ya tiene un administrador registrado.");
            return;
        }

        String nombre = leerTexto(scanner, "Nombre completo: ");
        String cedula = leerTexto(scanner, "Cedula: ");
        String correo = leerTexto(scanner, "Correo electronico: ");
        String contraseña = leerTexto(scanner, "Contraseña: ");

        try {
            Administrador administrador = new Administrador(nombre, cedula, correo, contraseña);
            banco.registrarAdministrador(administrador);
        } catch (IllegalArgumentException error) {
            System.out.println("No se pudo registrar el administrador: " + error.getMessage());
        }
    }

    public static void registrarCliente(Scanner scanner) {
        imprimirSubtitulo("Registro de cliente");

        if (!validarAdministradorCreado()) {
            return;
        }

        String nombre = leerTexto(scanner, "Nombre completo: ");
        String cedula = leerTexto(scanner, "Cedula: ");
        String sexo = leerTexto(scanner, "Sexo: ");
        String correo = leerTexto(scanner, "Correo electronico: ");
        String profesión = leerTexto(scanner, "Profesion: ");
        String dirección = leerTexto(scanner, "Direccion: ");
        String contraseña = leerTexto(scanner, "Contraseña: ");

        try {
            Cliente cliente = new Cliente(nombre, cedula, sexo, correo, profesión, dirección, contraseña);
            banco.registrarCliente(cliente);
        } catch (IllegalArgumentException error) {
            System.out.println("No se pudo registrar el cliente: " + error.getMessage());
        }
    }

    public static void crearCuenta(Scanner scanner) {
        imprimirSubtitulo("Creacion de cuenta bancaria");

        if (!validarAdministradorCreado()) {
            return;
        }

        Cliente cliente = buscarClientePorCorreo(scanner);
        if (cliente == null) {
            return;
        }

        System.out.println("Tipo de cuenta:");
        System.out.println("1. Cuenta de ahorro");
        System.out.println("2. Cuenta de débito");
        System.out.println("3. Cuenta de crédito");
        int tipoCuenta = leerEntero(scanner, "Seleccione el tipo de cuenta: ");

        String numeroCuenta = leerTexto(scanner, "Número de cuenta: ");
        double porcentajeInteres = leerDouble(scanner, "Porcentaje de interes: ");
        LocalDate fechaEmision = LocalDate.now();
        LocalDate fechaVencimiento = fechaEmision.plusYears(2);

        try {
            if (tipoCuenta == 1) {
                double saldo = leerDouble(scanner, "Saldo inicial: ");
                CuentaAhorro cuenta = new CuentaAhorro(numeroCuenta, saldo, fechaEmision, fechaVencimiento, porcentajeInteres);
                cliente.agregarCuentaAhorro(cuenta);
                System.out.println("Cuenta de ahorro registrada correctamente.");
            } else if (tipoCuenta == 2) {
                double saldo = leerDouble(scanner, "Saldo inicial: ");
                CuentaDebito cuenta = new CuentaDebito(numeroCuenta, saldo, fechaEmision, fechaVencimiento, porcentajeInteres);
                cliente.agregarCuentaDebito(cuenta);
                System.out.println("Cuenta de debito registrada correctamente.");
            } else if (tipoCuenta == 3) {
                double limiteCredito = leerDouble(scanner, "Limite de credito: ");
                String tipo = leerTexto(scanner, "Tipo de tarjeta de credito: ");
                CuentaCredito cuenta = new CuentaCredito(numeroCuenta, fechaEmision, fechaVencimiento,
                        porcentajeInteres, limiteCredito, tipo);
                cliente.agregarCuentaCredito(cuenta);
                System.out.println("Cuenta de credito registrada correctamente.");
            } else {
                System.out.println("Tipo de cuenta invalido.");
            }
        } catch (IllegalArgumentException error) {
            System.out.println("No se pudo crear la cuenta: " + error.getMessage());
        }
    }

    public static void validarInicioSesion(Scanner scanner) {
        imprimirSubtitulo("Validacion de inicio de sesion");

        if (!validarAdministradorCreado()) {
            return;
        }

        System.out.println("1. Administrador");
        System.out.println("2. Cliente");
        int tipoUsuario = leerEntero(scanner, "Tipo de usuario: ");
        String correo = leerTexto(scanner, "Correo electronico: ");
        String contrasenia = leerTexto(scanner, "Contraseña: ");

        if (tipoUsuario == 1) {
            mostrarResultadoSesion("Administrador", banco.getAdministrador().iniciarSesion(correo, contrasenia));
        } else if (tipoUsuario == 2) {
            Cliente cliente = buscarClientePorCorreo(correo);
            if (cliente == null) {
                System.out.println("No se encontro un cliente con ese correo.");
            } else {
                mostrarResultadoSesion("Cliente", cliente.iniciarSesion(correo, contrasenia));
            }
        } else {
            System.out.println("Tipo de usuario invalido.");
        }
    }

    public static void realizarTransaccion(Scanner scanner) {
        imprimirSubtitulo("Transacciones");

        if (!validarAdministradorCreado()) {
            return;
        }

        Cliente cliente = buscarClientePorCorreo(scanner);
        if (cliente == null) {
            return;
        }

        System.out.println("Tipo de cuenta:");
        System.out.println("1. Cuenta de ahorro");
        System.out.println("2. Cuenta de debito");
        System.out.println("3. Cuenta de credito");
        int tipoCuenta = leerEntero(scanner, "Seleccione el tipo de cuenta: ");

        if (tipoCuenta == 1) {
            CuentaAhorro cuenta = elegirCuentaAhorro(scanner, cliente);
            if (cuenta != null) {
                menuCuentaAhorro(scanner, cuenta);
            }
        } else if (tipoCuenta == 2) {
            CuentaDebito cuenta = elegirCuentaDebito(scanner, cliente);
            if (cuenta != null) {
                menuCuentaDebito(scanner, cuenta);
            }
        } else if (tipoCuenta == 3) {
            CuentaCredito cuenta = elegirCuentaCredito(scanner, cliente);
            if (cuenta != null) {
                menuCuentaCredito(scanner, cuenta);
            }
        } else {
            System.out.println("Tipo de cuenta invalido.");
        }
    }

    public static void menuCuentaAhorro(Scanner scanner, CuentaAhorro cuenta) {
        System.out.println("1. Depositar");
        System.out.println("2. Realizar pago");
        System.out.println("3. Generar intereses");
        int opcion = leerEntero(scanner, "Seleccione una transaccion: ");

        if (opcion == 1) {
            cuenta.depositar(leerDouble(scanner, "Monto del deposito: "));
        } else if (opcion == 2) {
            cuenta.realizarPago(leerDouble(scanner, "Monto del pago: "));
        } else if (opcion == 3) {
            cuenta.generarIntereses();
        } else {
            System.out.println("Transaccion invalida.");
            return;
        }

        System.out.println("Estado actualizado de la cuenta:");
        System.out.println(cuenta);
    }

    public static void menuCuentaDebito(Scanner scanner, CuentaDebito cuenta) {
        System.out.println("1. Depositar");
        System.out.println("2. Realizar pago");
        System.out.println("3. Generar intereses");
        int opcion = leerEntero(scanner, "Seleccione una transaccion: ");

        if (opcion == 1) {
            cuenta.depositar(leerDouble(scanner, "Monto del deposito: "));
        } else if (opcion == 2) {
            cuenta.realizarPago(leerDouble(scanner, "Monto del pago: "));
        } else if (opcion == 3) {
            cuenta.generarIntereses();
        } else {
            System.out.println("Transaccion invalida.");
            return;
        }

        System.out.println("Estado actualizado de la cuenta:");
        System.out.println(cuenta);
    }

    public static void menuCuentaCredito(Scanner scanner, CuentaCredito cuenta) {
        System.out.println("1. Realizar pago con credito");
        System.out.println("2. Abonar al saldo actual");
        System.out.println("3. Generar intereses");
        int opcion = leerEntero(scanner, "Seleccione una transaccion: ");

        if (opcion == 1) {
            cuenta.realizarPago(leerDouble(scanner, "Monto del pago: "));
        } else if (opcion == 2) {
            cuenta.abonar(leerDouble(scanner, "Monto del abono: "));
        } else if (opcion == 3) {
            cuenta.generarIntereses();
        } else {
            System.out.println("Transaccion invalida.");
            return;
        }

        System.out.println("Estado actualizado de la cuenta:");
        System.out.println(cuenta);
    }

    public static void imprimirReporteCliente(Scanner scanner) {
        imprimirSubtitulo("Reporte de cuentas del cliente");

        if (!validarAdministradorCreado()) {
            return;
        }

        Cliente cliente = buscarClientePorCorreo(scanner);
        if (cliente != null) {
            System.out.println(cliente.generarReporteCuentas());
        }
    }

    public static void listarClientes() {
        imprimirSubtitulo("Lista de clientes registrados");

        if (!validarAdministradorCreado()) {
            return;
        }

        System.out.println(banco.listarClientes());
    }

    public static void listarCuentas() {
        imprimirSubtitulo("Lista de cuentas registradas");

        if (!validarAdministradorCreado()) {
            return;
        }

        System.out.println(banco.listarTodasLasCuentas());
    }

    public static Cliente buscarClientePorCorreo(Scanner scanner) {
        String correo = leerTexto(scanner, "Correo del cliente: ");
        Cliente cliente = buscarClientePorCorreo(correo);

        if (cliente == null) {
            System.out.println("No se encontro un cliente con ese correo.");
        }

        return cliente;
    }

    public static Cliente buscarClientePorCorreo(String correo) {
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.getCorreoElectronico().equalsIgnoreCase(correo)) {
                return cliente;
            }
        }

        return null;
    }

    public static CuentaAhorro elegirCuentaAhorro(Scanner scanner, Cliente cliente) {
        if (cliente.getCuentasAhorro().isEmpty()) {
            System.out.println("El cliente no tiene cuentas de ahorro registradas.");
            return null;
        }

        for (int i = 0; i < cliente.getCuentasAhorro().size(); i++) {
            System.out.println((i + 1) + ". " + cliente.getCuentasAhorro().get(i).getNumeroCuenta());
        }

        int opcion = leerEntero(scanner, "Seleccione una cuenta: ");
        if (opcion < 1 || opcion > cliente.getCuentasAhorro().size()) {
            System.out.println("Cuenta invalida.");
            return null;
        }

        return cliente.getCuentasAhorro().get(opcion - 1);
    }

    public static CuentaDebito elegirCuentaDebito(Scanner scanner, Cliente cliente) {
        if (cliente.getCuentasDebito().isEmpty()) {
            System.out.println("El cliente no tiene cuentas de debito registradas.");
            return null;
        }

        for (int i = 0; i < cliente.getCuentasDebito().size(); i++) {
            System.out.println((i + 1) + ". " + cliente.getCuentasDebito().get(i).getNumeroCuenta());
        }

        int opcion = leerEntero(scanner, "Seleccione una cuenta: ");
        if (opcion < 1 || opcion > cliente.getCuentasDebito().size()) {
            System.out.println("Cuenta invalida.");
            return null;
        }

        return cliente.getCuentasDebito().get(opcion - 1);
    }

    public static CuentaCredito elegirCuentaCredito(Scanner scanner, Cliente cliente) {
        if (cliente.getCuentasCredito().isEmpty()) {
            System.out.println("El cliente no tiene cuentas de credito registradas.");
            return null;
        }

        for (int i = 0; i < cliente.getCuentasCredito().size(); i++) {
            System.out.println((i + 1) + ". " + cliente.getCuentasCredito().get(i).getNumeroCuenta());
        }

        int opcion = leerEntero(scanner, "Seleccione una cuenta: ");
        if (opcion < 1 || opcion > cliente.getCuentasCredito().size()) {
            System.out.println("Cuenta invalida.");
            return null;
        }

        return cliente.getCuentasCredito().get(opcion - 1);
    }

    public static boolean validarAdministradorCreado() {
        if (!banco.tieneAdministrador()) {
            System.out.println("Primero debe registrar un administrador para usar esta opcion.");
            return false;
        }

        return true;
    }

    public static void ejecutarPruebaAutomatica() {
        Banco bancoPrueba = new Banco();

        imprimirTitulo("PRUEBA AUTOMATICA DE LA PRACTICA");

        imprimirSubtitulo("1. Registro de usuarios");
        Cliente clientePrueba = new Cliente(
                "Valeria Campos Mora",
                "208880999",
                "Femenino",
                "valeria@gmail.com",
                "Estudiante",
                "Alajuela",
                "Valeria1!"
        );

        System.out.println("Intento de registrar un cliente antes de crear el administrador:");
        bancoPrueba.registrarCliente(clientePrueba);

        Administrador administrador = new Administrador(
                "Daniel Mora Rojas",
                "105550444",
                "admin@banco.com",
                "Admin123!"
        );
        bancoPrueba.registrarAdministrador(administrador);

        Cliente cliente = new Cliente(
                "Sofia Ramirez Lopez",
                "207770888",
                "Femenino",
                "sofia@gmail.com",
                "Disenadora",
                "Heredia",
                "Sofia123!"
        );
        bancoPrueba.registrarCliente(cliente);

        imprimirSubtitulo("2. Creacion de cuentas");
        CuentaAhorro cuentaAhorro = new CuentaAhorro(
                "AH-001",
                250,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2028, 6, 1),
                2
        );

        CuentaDebito cuentaDebito = new CuentaDebito(
                "DE-001",
                80,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2028, 6, 1),
                1
        );

        CuentaCredito cuentaCredito = new CuentaCredito(
                "CR-001",
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2028, 6, 1),
                5,
                1000,
                "Cashback"
        );

        cliente.agregarCuentaAhorro(cuentaAhorro);
        cliente.agregarCuentaDebito(cuentaDebito);
        cliente.agregarCuentaCredito(cuentaCredito);
        System.out.println("Se agrego una cuenta de ahorro, una de debito y una de credito al cliente.");

        imprimirSubtitulo("3. Validacion de inicio de sesion");
        mostrarResultadoSesion("Administrador", administrador.iniciarSesion("admin@banco.com", "Admin123!"));
        mostrarResultadoSesion("Cliente", cliente.iniciarSesion("sofia@gmail.com", "Sofia123!"));

        imprimirSubtitulo("4. Prueba de cuenta de ahorro");
        cuentaAhorro.depositar(100);
        cuentaAhorro.realizarPago(50);
        cuentaAhorro.generarIntereses();
        System.out.println(cuentaAhorro);

        imprimirSubtitulo("5. Prueba de cuenta de debito");
        cuentaDebito.depositar(40);
        cuentaDebito.realizarPago(20);
        cuentaDebito.generarIntereses();
        System.out.println(cuentaDebito);

        imprimirSubtitulo("6. Prueba de cuenta de credito");
        cuentaCredito.realizarPago(300);
        cuentaCredito.abonar(75);
        cuentaCredito.generarIntereses();
        System.out.println(cuentaCredito);

        imprimirSubtitulo("7. Prueba del metodo equals");
        CuentaAhorro otraCuentaAhorro = new CuentaAhorro(
                "AH-001",
                300,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2028, 6, 1),
                2
        );
        System.out.println("La cuenta AH-001 es igual a otra cuenta con el mismo numero: "
                + cuentaAhorro.equals(otraCuentaAhorro));

        imprimirSubtitulo("8. Reporte del cliente");
        System.out.println(cliente.generarReporteCuentas());

        imprimirSubtitulo("9. Lista de clientes desde el administrador");
        System.out.println(bancoPrueba.listarClientes());

        imprimirSubtitulo("10. Lista de cuentas desde el administrador");
        System.out.println(bancoPrueba.listarTodasLasCuentas());
    }

    public static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException error) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    public static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException error) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    public static void imprimirTitulo(String texto) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(texto);
        System.out.println("========================================");
    }

    public static void imprimirSubtitulo(String texto) {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println(texto);
        System.out.println("----------------------------------------");
    }

    public static void mostrarResultadoSesion(String tipoUsuario, boolean resultado) {
        if (resultado) {
            System.out.println(tipoUsuario + ": inicio de sesion correcto.");
        } else {
            System.out.println(tipoUsuario + ": inicio de sesion incorrecto.");
        }
    }
}
