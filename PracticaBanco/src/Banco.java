import java.util.ArrayList;

public class Banco {
    private Administrador administrador;
    private ArrayList<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean tieneAdministrador() {
        return administrador != null;
    }

    public void registrarAdministrador(Administrador administrador) {
        if (this.administrador != null) {
            System.out.println("No se puede registrar otro administrador. El sistema solo permite uno.");
            return;
        }

        this.administrador = administrador;
        System.out.println("Administrador registrado correctamente.");
    }

    public void registrarCliente(Cliente cliente) {
        if (!tieneAdministrador()) {
            System.out.println("No se puede registrar clientes sin un administrador creado.");
            return;
        }

        clientes.add(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    public String listarClientes() {
        StringBuilder lista = new StringBuilder();
        lista.append("Clientes registrados en el sistema\n");
        lista.append("----------------------------------------\n");

        if (clientes.isEmpty()) {
            lista.append("No hay clientes registrados.\n");
        } else {
            for (Cliente cliente : clientes) {
                lista.append(cliente).append("\n");
                lista.append("----------------------------------------\n");
            }
        }

        return lista.toString();
    }

    public String listarTodasLasCuentas() {
        StringBuilder lista = new StringBuilder();
        lista.append("Cuentas registradas en el banco\n");
        lista.append("========================================\n");

        if (clientes.isEmpty()) {
            lista.append("No hay cuentas registradas porque no hay clientes.\n");
        } else {
            for (Cliente cliente : clientes) {
                lista.append(cliente.generarReporteCuentas()).append("\n");
            }
        }

        return lista.toString();
    }

    @Override
    public String toString() {
        return "Banco\n" +
                "  Administrador registrado: " + tieneAdministrador() + "\n" +
                "  Cantidad de clientes: " + clientes.size();
    }
}
