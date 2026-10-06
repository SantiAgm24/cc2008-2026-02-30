import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class RentaMovil {
//Clase principal que representa el sistema de alquiler de vehículos, gestionando clientes, vehículos y alquileres. Proporciona métodos para registrar clientes y vehículos, realizar cotizaciones, confirmar alquileres, registrar devoluciones y generar reportes económicos y de flota.

    private final List<Vehiculo> vehiculos;
    private final List<Cliente> clientes;
    private final List<Alquiler> alquileres;

    private int siguienteNumeroAlquiler;

    public RentaMovil() {
        vehiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        alquileres = new ArrayList<>();

        siguienteNumeroAlquiler = 1;
    }

    // ==================================================
    // REGISTRO
    // ==================================================

    public void registrarVehiculo(Vehiculo vehiculo) {

        if (vehiculo == null) {
            throw new IllegalArgumentException(
                    "El vehiculo no puede ser nulo."
            );
        }

        for (Vehiculo registrado : vehiculos) {
            if (registrado.getPlaca().equals(vehiculo.getPlaca())) {
                throw new IllegalArgumentException(
                        "Ya existe un vehiculo con esa placa."
                );
            }
        }

        vehiculos.add(vehiculo);
    }

    public void registrarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        for (Cliente registrado : clientes) {
            if (registrado.getIdentificador().equals(
                    cliente.getIdentificador())) {

                throw new IllegalArgumentException(
                        "Ya existe un cliente con ese identificador."
                );
            }
        }

        clientes.add(cliente);
    }

    // ==================================================
    // BUSQUEDAS Y CONSULTAS
    // ==================================================

    public Vehiculo buscarVehiculo(String placa) {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe indicar una placa."
            );
        }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equals(placa.trim())) {
                return vehiculo;
            }
        }

        throw new NoSuchElementException(
                "No existe un vehiculo con la placa indicada."
        );
    }

    public Cliente buscarCliente(String identificador) {

        if (identificador == null
                || identificador.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe indicar un identificador de cliente."
            );
        }

        for (Cliente cliente : clientes) {
            if (cliente.getIdentificador().equals(
                    identificador.trim())) {

                return cliente;
            }
        }

        throw new NoSuchElementException(
                "No existe un cliente con ese identificador."
        );
    }

    public List<Vehiculo> getVehiculos() {
        return Collections.unmodifiableList(
                new ArrayList<>(vehiculos)
        );
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(
                new ArrayList<>(clientes)
        );
    }

    public int contarAlquileresActivos(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        int cantidad = 0;

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente() == cliente
                    && alquiler.isActivo()) {

                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarAlquileresConfirmados(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        int cantidad = cliente.getConfirmadosAnteriores();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente() == cliente) {
                cantidad = Math.addExact(cantidad, 1);
            }
        }

        return cantidad;
    }

    // ==================================================
    // COTIZACION Y CONFIRMACION
    // ==================================================

    private List<String> evaluarImpedimentos(
            Vehiculo vehiculo,
            Cliente cliente) {

        List<String> motivos = new ArrayList<>();

        if (!vehiculo.estaDisponible()) {
            motivos.add("El vehiculo no esta disponible.");
        }

        if (!vehiculo.cumpleLicencia(cliente.getLicencias())) {
            motivos.add(
                    "El cliente no tiene una licencia adecuada."
            );
        }

        int activos = contarAlquileresActivos(cliente);
        int limite = cliente.obtenerLimiteAlquileresActivos();

        if (activos >= limite) {
            motivos.add(
                    "El cliente alcanzo su limite de alquileres activos."
            );
        }

        return motivos;
    }

    private double redondearMoneda(double monto) {

        if (!Double.isFinite(monto) || monto < 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser finito y no negativo."
            );
        }

        double centavos = monto * 100.0;

        // Evita superar el rango que Math.round puede representar.
        if (!Double.isFinite(centavos)
                || centavos >= Long.MAX_VALUE) {

            throw new IllegalArgumentException(
                    "El monto es demasiado grande."
            );
        }

        return Math.round(centavos) / 100.0;
    }

//Metodo para realizar una cotización de alquiler de vehículo, que toma la placa del vehículo, el identificador del cliente y la duración del alquiler en días. Calcula el subtotal, el descuento aplicable y los motivos de impedimento, y devuelve un objeto Cotizacion con toda la información relevante.
    public Cotizacion cotizar(
            String placa,
            String identificador,
            int dias) {

        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias deben ser mayores que cero."
            );
        }

        Vehiculo vehiculo = buscarVehiculo(placa);
        Cliente cliente = buscarCliente(identificador);

        double subtotal = redondearMoneda(
                vehiculo.calcularSubtotal(dias)
        );

        int confirmadosPrevios =
                contarAlquileresConfirmados(cliente);

        double descuento = redondearMoneda(
                cliente.calcularDescuento(
                        subtotal,
                        confirmadosPrevios
                )
        );

        List<String> motivos =
                evaluarImpedimentos(vehiculo, cliente);

        return new Cotizacion(
                cliente,
                vehiculo,
                dias,
                subtotal,
                descuento,
                motivos
        );
    }

//Metodo para confirmar un alquiler de vehículo basado en una cotización previa. Verifica si el cliente puede alquilar el vehículo y, si es así, crea un nuevo objeto Alquiler, marca el vehículo como alquilado y lo agrega a la lista de alquileres activos. Devuelve el objeto Alquiler creado.
    public Alquiler confirmarAlquiler(
            String placa,
            String identificador,
            int dias) {

        // Recalcula el precio y comprueba las condiciones actuales.
        Cotizacion cotizacion = cotizar(
                placa,
                identificador,
                dias
        );

        if (!cotizacion.puedeAlquilar()) {
            throw new IllegalStateException(
                    "No se puede confirmar: "
                    + String.join(
                            " ",
                            cotizacion.getMotivosImpedimento()
                    )
            );
        }

        // Comprueba el siguiente número antes de modificar el sistema.
        int siguiente = Math.addExact(siguienteNumeroAlquiler, 1);

        Alquiler alquiler = new Alquiler(
                siguienteNumeroAlquiler,
                cotizacion
        );

        cotizacion.getVehiculo().marcarAlquilado();
        alquileres.add(alquiler);

        siguienteNumeroAlquiler = siguiente;

        return alquiler;
    }

    // ==================================================
    // DEVOLUCIONES Y MANTENIMIENTO
    // ==================================================

    private Alquiler buscarAlquilerActivo(String placa) {

        Vehiculo vehiculo = buscarVehiculo(placa);

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo() == vehiculo
                    && alquiler.isActivo()) {

                return alquiler;
            }
        }

        throw new NoSuchElementException(
                "El vehiculo no tiene un alquiler activo."
        );
    }

    public void registrarDevolucion(String placa) {

        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo.getEstado() != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException(
                    "Solo se puede devolver un vehiculo alquilado."
            );
        }

        Alquiler alquiler = buscarAlquilerActivo(placa);
        alquiler.finalizar();
    }

    public void finalizarMantenimiento(String placa) {

        Vehiculo vehiculo = buscarVehiculo(placa);
        vehiculo.finalizarMantenimiento();
    }

    // ==================================================
    // ALQUILERES ACTIVOS E HISTORIALES
    // ==================================================

    public List<Alquiler> obtenerAlquileresActivos() {

        List<Alquiler> activos = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.isActivo()) {
                activos.add(alquiler);
            }
        }

        return Collections.unmodifiableList(activos);
    }

    public List<Alquiler> obtenerHistorialCliente(
            String identificador) {

        Cliente cliente = buscarCliente(identificador);
        List<Alquiler> historial = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente() == cliente) {
                historial.add(alquiler);
            }
        }

        return Collections.unmodifiableList(historial);
    }

    public double calcularTotalPagadoCliente(
            String identificador) {

        double total = 0;

        for (Alquiler alquiler :
                obtenerHistorialCliente(identificador)) {

            total += alquiler.getTotal();
        }

        return redondearMoneda(total);
    }

    // ==================================================
    // REPORTES ECONOMICOS
    // ==================================================

    public double calcularIngresosTotales() {

        double ingresos = 0;

        for (Alquiler alquiler : alquileres) {
            ingresos += alquiler.getTotal();
        }

        return redondearMoneda(ingresos);
    }

    public double calcularDescuentosTotales() {

        double descuentos = 0;

        for (Alquiler alquiler : alquileres) {
            descuentos += alquiler.getDescuento();
        }

        return redondearMoneda(descuentos);
    }

    public Map<String, Double> obtenerIngresosPorCategoria() {

        Map<String, Double> ingresos = new LinkedHashMap<>();

        // Incluye las categorías registradas, aunque no tengan ingresos.
        for (Vehiculo vehiculo : vehiculos) {
            ingresos.putIfAbsent(
                    vehiculo.obtenerCategoria(),
                    0.0
            );
        }

        for (Alquiler alquiler : alquileres) {

            String categoria =
                    alquiler.getVehiculo().obtenerCategoria();

            double acumulado = ingresos.getOrDefault(
                    categoria,
                    0.0
            );

            ingresos.put(
                    categoria,
                    redondearMoneda(acumulado + alquiler.getTotal())
            );
        }

        return ingresos;
    }

    // ==================================================
    // REPORTES DE FLOTA
    // ==================================================

    public Map<String, Integer> contarVehiculosPorCategoria() {

        Map<String, Integer> cantidades = new LinkedHashMap<>();

        for (Vehiculo vehiculo : vehiculos) {

            String categoria = vehiculo.obtenerCategoria();

            int cantidad = cantidades.getOrDefault(
                    categoria,
                    0
            );

            cantidades.put(categoria, cantidad + 1);
        }

        return cantidades;
    }

    public Map<EstadoVehiculo, Integer> contarVehiculosPorEstado() {

        Map<EstadoVehiculo, Integer> cantidades =
                new LinkedHashMap<>();

        for (EstadoVehiculo estado : EstadoVehiculo.values()) {
            cantidades.put(estado, 0);
        }

        for (Vehiculo vehiculo : vehiculos) {

            EstadoVehiculo estado = vehiculo.getEstado();

            cantidades.put(
                    estado,
                    cantidades.get(estado) + 1
            );
        }

        return cantidades;
    }

    public Map<String, Map<EstadoVehiculo, Integer>>
            contarVehiculosPorCategoriaYEstado() {

        Map<String, Map<EstadoVehiculo, Integer>> resultado =
                new LinkedHashMap<>();

        for (Vehiculo vehiculo : vehiculos) {

            String categoria = vehiculo.obtenerCategoria();

            if (!resultado.containsKey(categoria)) {

                Map<EstadoVehiculo, Integer> cantidades =
                        new LinkedHashMap<>();

                for (EstadoVehiculo estado : EstadoVehiculo.values()) {
                    cantidades.put(estado, 0);
                }

                resultado.put(categoria, cantidades);
            }

            Map<EstadoVehiculo, Integer> cantidades =
                    resultado.get(categoria);

            EstadoVehiculo estado = vehiculo.getEstado();

            cantidades.put(
                    estado,
                    cantidades.get(estado) + 1
            );
        }

        return resultado;
    }
}