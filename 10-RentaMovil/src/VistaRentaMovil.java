import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class VistaRentaMovil {

    private final RentaMovil sistema;
    private final Scanner entrada;

    public VistaRentaMovil(RentaMovil sistema) {

        if (sistema == null) {
            throw new IllegalArgumentException(
                    "La vista necesita un sistema."
            );
        }

        this.sistema = sistema;
        this.entrada = new Scanner(System.in);
    }

    // ==================================================
    // MENU PRINCIPAL
    // ==================================================

    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            mostrarMenu();

            // Permite terminar si se cierra la entrada de consola.
            if (!entrada.hasNextLine()) {
                System.out.println("\nEntrada finalizada.");
                return;
            }

            try {
                int opcion = Integer.parseInt(
                        leerTexto("Seleccione una opcion: ")
                );

                switch (opcion) {
                    case 1:
                        registrarVehiculo();
                        break;

                    case 2:
                        registrarCliente();
                        break;

                    case 3:
                        mostrarFlota();
                        break;

                    case 4:
                        mostrarClientes();
                        break;

                    case 5:
                        solicitarCotizacion();
                        break;

                    case 6:
                        solicitarAlquiler();
                        break;

                    case 7:
                        solicitarDevolucion();
                        break;

                    case 8:
                        solicitarFinMantenimiento();
                        break;

                    case 9:
                        mostrarReportes();
                        break;

                    case 0:
                        continuar = false;
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "La opcion debe ser un numero entero."
                );

            } catch (IllegalArgumentException
                    | IllegalStateException
                    | NoSuchElementException e) {

                System.out.println("No se pudo completar: " + e.getMessage());

            } catch (ArithmeticException e) {
                System.out.println(
                        "La operacion supera el limite numerico permitido."
                );
            }
        }
    }

    private void mostrarMenu() {

        System.out.println();
        System.out.println("██████╗░███████╗███╗░░██╗████████╗░█████╗░███╗░░░███╗░█████╗░██╗░░░██╗██╗██╗░░░░░");
        System.out.println("██╔══██╗██╔════╝████╗░██║╚══██╔══╝██╔══██╗████╗░████║██╔══██╗██║░░░██║██║██║░░░░░");
        System.out.println("██████╔╝█████╗░░██╔██╗██║░░░██║░░░███████║██╔████╔██║██║░░██║╚██╗░██╔╝██║██║░░░░░");
        System.out.println("██╔══██╗██╔══╝░░██║╚████║░░░██║░░░██╔══██║██║╚██╔╝██║██║░░██║░╚████╔╝░██║██║░░░░░");
        System.out.println("██║░░██║███████╗██║░╚███║░░░██║░░░██║░░██║██║░╚═╝░██║╚█████╔╝░░╚██╔╝░░██║███████╗");
        System.out.println("╚═╝░░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝░╚════╝░░░░╚═╝░░░╚═╝╚══════╝");
        System.out.println();
        System.out.println("Lista de opciones disponibles:");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Consultar flota");
        System.out.println("4. Consultar clientes");
        System.out.println("5. Cotizar");
        System.out.println("6. Contratar alquiler");
        System.out.println("7. Registrar devolucion");
        System.out.println("8. Finalizar mantenimiento");
        System.out.println("9. Consultar reportes");
        System.out.println("0. Salir");
    }

    // ==================================================
    // REGISTRO DE VEHICULOS
    // ==================================================

    private void registrarVehiculo() {

        System.out.println();
        System.out.println("██████████████████████████████████████████████████████████");
        System.out.println("█─▄▄▄─██▀▄─██─▄─▄─█▄─▄▄─█─▄▄▄▄█─▄▄─█▄─▄▄▀█▄─▄██▀▄─██─▄▄▄▄█");
        System.out.println("█─███▀██─▀─████─████─▄█▀█─██▄─█─██─██─▄─▄██─███─▀─██▄▄▄▄─█");
        System.out.println("▀▄▄▄▄▄▀▄▄▀▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▄▀▄▄▀▄▄▀▄▄▄▀▄▄▀▄▄▀▄▄▄▄▄▀");
        System.out.println();
        System.out.println("1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");
        System.out.println("4. Microbus");

        int categoria = leerEnteroPositivo("Categoria: ");

        if (categoria > 4) {
            throw new IllegalArgumentException(
                    "La categoria debe estar entre 1 y 4."
            );
        }

        String placa = leerTexto("Placa: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");

        double tarifa = leerDecimalPositivo(
                "Tarifa diaria en quetzales: "
        );

        Vehiculo vehiculo;

        switch (categoria) {
            case 1: {
                int pasajeros = leerEnteroPositivo(
                        "Cantidad de pasajeros: "
                );

                boolean automatica = leerSiNo(
                        "Tiene transmision automatica"
                );

                vehiculo = new Automovil(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        0,
                        pasajeros,
                        automatica
                );
                break;
            }

            case 2: {
                int cilindraje = leerEnteroPositivo(
                        "Cilindraje en cc: "
                );

                vehiculo = new Motocicleta(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        0,
                        cilindraje
                );
                break;
            }

            case 3: {
                double capacidad = leerDecimalPositivo(
                        "Capacidad maxima en toneladas: "
                );

                vehiculo = new CamionetaCarga(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        0,
                        capacidad
                );
                break;
            }

            case 4: {
                int pasajeros = leerEnteroPositivo(
                        "Cantidad de pasajeros: "
                );

                boolean piloto = leerSiNo(
                        "Incluye piloto como caracteristica fija"
                );

                vehiculo = new Microbus(
                        placa,
                        marca,
                        modelo,
                        tarifa,
                        0,
                        pasajeros,
                        piloto
                );
                break;
            }

            default:
                throw new IllegalArgumentException(
                        "Categoria no valida."
                );
        }

        sistema.registrarVehiculo(vehiculo);

        System.out.println("Vehiculo registrado correctamente.");
    }

    // ==================================================
    // REGISTRO DE CLIENTES
    // ==================================================

    private void registrarCliente() {

        System.out.println();
        System.out.println("██████████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█─▄─▄─█▄─▄█▄─▄▄─█─▄▄─█─▄▄▄▄███▄─▄▄▀█▄─▄▄─███─▄▄▄─█▄─▄███▄─▄█▄─▄▄─█▄─▀█▄─▄█─▄─▄─█▄─▄▄─█");
        System.out.println("███─████─███─▄▄▄█─██─█▄▄▄▄─████─██─██─▄█▀███─███▀██─██▀██─███─▄█▀██─█▄▀─████─████─▄█▀█");
        System.out.println("▀▀▄▄▄▀▀▄▄▄▀▄▄▄▀▀▀▄▄▄▄▀▄▄▄▄▄▀▀▀▄▄▄▄▀▀▄▄▄▄▄▀▀▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▀▄▄▄▄▄▀▄▄▄▀▀▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀");
        System.out.println();
        System.out.println("1. Individual");
        System.out.println("2. Corporativo");

        int tipo = leerEnteroPositivo("Tipo: ");

        if (tipo > 2) {
            throw new IllegalArgumentException(
                    "El tipo debe ser 1 o 2."
            );
        }

        Cliente cliente;

        switch (tipo) {
            case 1: {
                String dpi = leerTexto("DPI de 13 digitos: ");
                String nombre = leerTexto("Nombre: ");
                Set<TipoLicencia> licencias = leerLicencias();

                cliente = new ClienteIndividual(
                        dpi,
                        nombre,
                        licencias,
                        0
                );
                break;
            }

            case 2: {
                String nit = leerTexto("NIT: ");
                String nombreEmpresa = leerTexto(
                        "Nombre de la empresa: "
                );

                String contacto = leerTexto(
                        "Nombre del contacto: "
                );

                Set<TipoLicencia> licencias = leerLicencias();

                cliente = new ClienteCorporativo(
                        nit,
                        nombreEmpresa,
                        licencias,
                        0,
                        contacto
                );
                break;
            }

            default:
                throw new IllegalArgumentException(
                        "Tipo de cliente no valido."
                );
        }

        sistema.registrarCliente(cliente);

        System.out.println("Cliente registrado correctamente.");
    }

    // ==================================================
    // CONSULTAS
    // ==================================================

    private void mostrarFlota() {

        System.out.println();
        System.out.println("██████████████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█▄─▄▄─█▄─▄███─▄▄─█─▄─▄─██▀▄─████▄─▄▄▀█▄─▄▄─█─▄▄▄▄█▄─▄█─▄▄▄▄█─▄─▄─█▄─▄▄▀██▀▄─██▄─▄▄▀██▀▄─██");
        System.out.println("██─▄████─██▀█─██─███─████─▀─█████─▄─▄██─▄█▀█─██▄─██─██▄▄▄▄─███─████─▄─▄██─▀─███─██─██─▀─██");
        System.out.println("▀▄▄▄▀▀▀▄▄▄▄▄▀▄▄▄▄▀▀▄▄▄▀▀▄▄▀▄▄▀▀▀▄▄▀▄▄▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▀▄▄▄▄▄▀▀▄▄▄▀▀▄▄▀▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀▄▄▀▄▄▀");
        System.out.println();

        List<Vehiculo> vehiculos = sistema.getVehiculos();

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }

        for (Vehiculo vehiculo : vehiculos) {

            System.out.println(vehiculo.obtenerDescripcion());

            System.out.println(
                    "Estado: " + vehiculo.getEstado()
                    + " | Dias acumulados: "
                    + vehiculo.getDiasAcumulados()
            );

            System.out.println();
        }
    }

    private void mostrarClientes() {

        System.out.println();
        System.out.println("██████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
        System.out.println("█─▄▄▄─█▄─▄███▄─▄█▄─▄▄─█▄─▀█▄─▄█─▄─▄─█▄─▄▄─█─▄▄▄▄███▄─▄▄▀█▄─▄▄─█─▄▄▄▄█▄─▄█─▄▄▄▄█─▄─▄─█▄─▄▄▀██▀▄─██▄─▄▄▀█─▄▄─█─▄▄▄▄█");
        System.out.println("█─███▀██─██▀██─███─▄█▀██─█▄▀─████─████─▄█▀█▄▄▄▄─████─▄─▄██─▄█▀█─██▄─██─██▄▄▄▄─███─████─▄─▄██─▀─███─██─█─██─█▄▄▄▄─█");
        System.out.println("▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▀▄▄▄▄▄▀▄▄▄▀▀▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀▄▄▄▄▄▀▀▀▄▄▀▄▄▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▀▄▄▄▄▄▀▀▄▄▄▀▀▄▄▀▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀▄▄▄▄▀▄▄▄▄▄▀");
        System.out.println();

        List<Cliente> clientes = sistema.getClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {

            System.out.println(cliente.obtenerDescripcion());

            System.out.println(
                    "Alquileres activos: "
                    + sistema.contarAlquileresActivos(cliente)
                    + " | Confirmados, incluidos antecedentes: "
                    + sistema.contarAlquileresConfirmados(cliente)
            );

            System.out.println();
        }
    }

    // ==================================================
    // COTIZACION Y CONTRATACION
    // ==================================================

    private void solicitarCotizacion() {

        String placa = leerTexto("Placa del vehiculo: ");
        String identificador = leerTexto("DPI o NIT del cliente: ");
        int dias = leerEnteroPositivo("Dias de alquiler: ");

        Cotizacion cotizacion = sistema.cotizar(
                placa,
                identificador,
                dias
        );

        mostrarCotizacion(cotizacion);
    }

    private void mostrarCotizacion(Cotizacion cotizacion) {

        System.out.println();
        System.out.println("█████████████████████████████████████████████████████████");
        System.out.println("█─▄▄▄─█─▄▄─█─▄─▄─█▄─▄█░▄▄░▄██▀▄─██─▄▄▄─█▄─▄█─▄▄─█▄─▀█▄─▄█");
        System.out.println("█─███▀█─██─███─████─███▀▄█▀██─▀─██─███▀██─██─██─██─█▄▀─██");
        System.out.println("▀▄▄▄▄▄▀▄▄▄▄▀▀▄▄▄▀▀▄▄▄▀▄▄▄▄▄▀▄▄▀▄▄▀▄▄▄▄▄▀▄▄▄▀▄▄▄▄▀▄▄▄▀▀▄▄▀");
        System.out.println();

        System.out.println(
                cotizacion.getVehiculo().obtenerDescripcion()
        );

        System.out.println(
                "Estado: " + cotizacion.getVehiculo().getEstado()
        );

        System.out.println(
                "Cliente: " + cotizacion.getCliente().getNombre()
        );

        System.out.println("Dias: " + cotizacion.getDias());

        System.out.printf(
                "Subtotal: Q%.2f%n",
                cotizacion.getSubtotal()
        );

        System.out.printf(
                "Descuento: Q%.2f%n",
                cotizacion.getDescuento()
        );

        System.out.printf(
                "Total: Q%.2f%n",
                cotizacion.getTotal()
        );

        if (cotizacion.puedeAlquilar()) {

            System.out.println(
                    "El cliente puede alquilarlo en este momento."
            );

        } else {

            System.out.println("No se puede alquilar por estos motivos:");

            for (String motivo : cotizacion.getMotivosImpedimento()) {
                System.out.println("- " + motivo);
            }
        }
    }

    private void solicitarAlquiler() {

        String placa = leerTexto("Placa del vehiculo: ");
        String identificador = leerTexto("DPI o NIT del cliente: ");
        int dias = leerEnteroPositivo("Dias de alquiler: ");

        Cotizacion cotizacion = sistema.cotizar(
                placa,
                identificador,
                dias
        );

        mostrarCotizacion(cotizacion);

        if (!cotizacion.puedeAlquilar()) {
            return;
        }

        boolean acepta = leerSiNo(
                "Acepta el cobro y confirma el alquiler"
        );

        if (!acepta) {
            System.out.println(
                    "Operacion cancelada. No se realizaron cambios."
            );
            return;
        }

        Alquiler alquiler = sistema.confirmarAlquiler(
                placa,
                identificador,
                dias
        );

        System.out.println(
                "Alquiler confirmado. Numero: "
                + alquiler.getNumero()
        );

        System.out.printf(
                "Monto cobrado: Q%.2f%n",
                alquiler.getTotal()
        );
    }

    // ==================================================
    // DEVOLUCIONES Y MANTENIMIENTO
    // ==================================================

    private void solicitarDevolucion() {

        String placa = leerTexto("Placa del vehiculo a devolver: ");

        sistema.registrarDevolucion(placa);

        Vehiculo vehiculo = sistema.buscarVehiculo(placa);

        System.out.println("Devolucion registrada.");
        System.out.println("Estado actual: " + vehiculo.getEstado());

        System.out.println(
                "Dias acumulados: " + vehiculo.getDiasAcumulados()
        );
    }

    private void solicitarFinMantenimiento() {

        String placa = leerTexto("Placa del vehiculo: ");

        sistema.finalizarMantenimiento(placa);

        System.out.println(
                "Mantenimiento finalizado. "
                + "El vehiculo esta disponible y su acumulado es cero."
        );
    }

    // ==================================================
    // REPORTES
    // ==================================================

    private void mostrarReportes() {

        System.out.println();
        System.out.println("███████████████████████████████████████████████████");
        System.out.println("█▄─█─▄█▄─▄▄─█─█─█▄─▄█─▄▄▄─█▄─██─▄█▄─▄███─▄▄─█─▄▄▄▄█");
        System.out.println("██▄▀▄███─▄█▀█─▄─██─██─███▀██─██─███─██▀█─██─█▄▄▄▄─█");
        System.out.println("▀▀▀▄▀▀▀▄▄▄▄▄▀▄▀▄▀▄▄▄▀▄▄▄▄▄▀▀▄▄▄▄▀▀▄▄▄▄▄▀▄▄▄▄▀▄▄▄▄▄▀");
        System.out.println("████████████████████████████████▀█████████████████████████");
        System.out.println("█▀▄█─▄▄▄─██▀▄─██─▄─▄─█▄─▄▄─█─▄▄▄▄█─▄▄─█▄─▄▄▀█▄─▄██▀▄─██▄▀█");
        System.out.println("█░██─███▀██─▀─████─████─▄█▀█─██▄─█─██─██─▄─▄██─███─▀─███░█");
        System.out.println("▀▀▄▀▄▄▄▄▄▀▄▄▀▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▄▀▄▄▀▄▄▀▄▄▄▀▄▄▀▄▄▀▄█▀");
        System.out.println();

        Map<String, Integer> porCategoria =
                sistema.contarVehiculosPorCategoria();

        if (porCategoria.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
        }

        for (Map.Entry<String, Integer> dato : porCategoria.entrySet()) {
            System.out.println(dato.getKey() + ": " + dato.getValue());
        }

        System.out.println("\n--- Cantidad de vehiculos por estado ---");

        Map<EstadoVehiculo, Integer> porEstado =
                sistema.contarVehiculosPorEstado();

        for (Map.Entry<EstadoVehiculo, Integer> dato
                : porEstado.entrySet()) {

            System.out.println(dato.getKey() + ": " + dato.getValue());
        }

        System.out.println("\n--- Estados dentro de cada categoria ---");

        Map<String, Map<EstadoVehiculo, Integer>> detalle =
                sistema.contarVehiculosPorCategoriaYEstado();

        for (Map.Entry<String, Map<EstadoVehiculo, Integer>> categoria
                : detalle.entrySet()) {

            System.out.println(categoria.getKey() + ":");

            for (Map.Entry<EstadoVehiculo, Integer> estado
                    : categoria.getValue().entrySet()) {

                System.out.println(
                        "  " + estado.getKey() + ": " + estado.getValue()
                );
            }
        }

        System.out.println("\n--- Ingresos de esta ejecucion ---");

        System.out.printf(
                "Ingresos totales: Q%.2f%n",
                sistema.calcularIngresosTotales()
        );

        Map<String, Double> ingresos =
                sistema.obtenerIngresosPorCategoria();

        for (Map.Entry<String, Double> dato : ingresos.entrySet()) {
            System.out.printf(
                    "%s: Q%.2f%n",
                    dato.getKey(),
                    dato.getValue()
            );
        }

        System.out.printf(
                "Descuentos otorgados: Q%.2f%n",
                sistema.calcularDescuentosTotales()
        );

        System.out.println("\n--- Alquileres activos ---");

        mostrarAlquileres(sistema.obtenerAlquileresActivos());

        if (leerSiNo("Desea consultar el historial de un cliente")) {
            mostrarHistorialCliente();
        }
    }

    private void mostrarHistorialCliente() {

        String identificador = leerTexto("DPI o NIT del cliente: ");

        Cliente cliente = sistema.buscarCliente(identificador);

        System.out.println();
        System.out.println("████████████████████████████████████████████████");
        System.out.println("█─█─█▄─▄█─▄▄▄▄█─▄─▄─█─▄▄─█▄─▄▄▀█▄─▄██▀▄─██▄─▄███");
        System.out.println("█─▄─██─██▄▄▄▄─███─███─██─██─▄─▄██─███─▀─███─██▀█");
        System.out.println("▀▄▀▄▀▄▄▄▀▄▄▄▄▄▀▀▄▄▄▀▀▄▄▄▄▀▄▄▀▄▄▀▄▄▄▀▄▄▀▄▄▀▄▄▄▄▄▀");
        System.out.println();
        System.out.println(cliente.obtenerDescripcion());

        mostrarAlquileres(
                sistema.obtenerHistorialCliente(identificador)
        );

        System.out.printf(
                "Total pagado en esta ejecucion: Q%.2f%n",
                sistema.calcularTotalPagadoCliente(identificador)
        );

        if (cliente.getConfirmadosAnteriores() > 0) {

            System.out.println(
                    "Antecedente previo para el descuento: "
                    + cliente.getConfirmadosAnteriores()
                    + " alquileres confirmados."
            );

            System.out.println(
                    "Esos antecedentes no tienen montos "
                    + "ni detalle registrado en esta ejecucion."
            );
        }
    }

    private void mostrarAlquileres(List<Alquiler> alquileres) {

        if (alquileres.isEmpty()) {
            System.out.println("No hay alquileres para mostrar.");
            return;
        }

        for (Alquiler alquiler : alquileres) {

            String estado;

            if (alquiler.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Finalizado";
            }

            System.out.println(
                    "\nAlquiler #" + alquiler.getNumero()
                    + " | Estado: " + estado
            );

            System.out.println(
                    "Cliente: " + alquiler.getCliente().getNombre()
                    + " | Identificador: "
                    + alquiler.getCliente().getIdentificador()
            );

            System.out.println(
                    "Vehiculo: " + alquiler.getVehiculo().getPlaca()
                    + " | Dias: " + alquiler.getDias()
            );

            System.out.printf(
                    "Subtotal: Q%.2f | Descuento: Q%.2f | Total: Q%.2f%n",
                    alquiler.getSubtotal(),
                    alquiler.getDescuento(),
                    alquiler.getTotal()
            );
        }
    }

    // ==================================================
    // LECTURA Y VALIDACION DE ENTRADAS
    // ==================================================

    private String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    private int leerEnteroPositivo(String mensaje) {

        while (true) {

            String texto = leerTexto(mensaje);

            try {
                int numero = Integer.parseInt(texto);

                if (numero > 0) {
                    return numero;
                }

                System.out.println(
                        "Ingrese un entero mayor que cero."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un numero entero valido."
                );
            }
        }
    }

    private double leerDecimalPositivo(String mensaje) {

        while (true) {

            String texto = leerTexto(mensaje);

            try {
                double numero = Double.parseDouble(texto);

                if (Double.isFinite(numero) && numero > 0) {
                    return numero;
                }

                System.out.println(
                        "Ingrese un numero positivo y finito."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un numero valido. "
                        + "Use punto para decimales, por ejemplo 1.5."
                );
            }
        }
    }

    private boolean leerSiNo(String mensaje) {

        while (true) {

            String respuesta = leerTexto(
                    mensaje + " (s/n): "
            );

            if (respuesta.equalsIgnoreCase("s")
                    || respuesta.equalsIgnoreCase("si")
                    || respuesta.equalsIgnoreCase("sí")) {

                return true;
            }

            if (respuesta.equalsIgnoreCase("n")
                    || respuesta.equalsIgnoreCase("no")) {

                return false;
            }

            System.out.println("Responda s o n.");
        }
    }

    private Set<TipoLicencia> leerLicencias() {

        while (true) {

            String texto = leerTexto(
                    "Licencias A, B, C o M, separadas por comas: "
            );

            if (texto.isEmpty()) {
                System.out.println(
                        "Debe ingresar al menos una licencia."
                );
                continue;
            }

            Set<TipoLicencia> licencias =
                    EnumSet.noneOf(TipoLicencia.class);

            boolean validas = true;

            for (String parte : texto.split(",", -1)) {

                String valor = parte.trim();

                try {
                    TipoLicencia licencia = TipoLicencia.valueOf(
                            valor.toUpperCase(java.util.Locale.ROOT)
                    );

                    licencias.add(licencia);

                } catch (IllegalArgumentException e) {

                    System.out.println(
                            "Licencia no valida: [" + valor + "]. "
                            + "Use solamente A, B, C o M."
                    );

                    validas = false;
                    break;
                }
            }

            if (validas) {
                return licencias;
            }
        }
    }
}