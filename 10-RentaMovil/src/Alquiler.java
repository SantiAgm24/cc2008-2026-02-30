public class Alquiler {
//Clase que representa un alquiler de vehículo, incluyendo información sobre el cliente, vehículo, duración del alquiler, subtotal, descuento y estado del alquiler.

    private final int numero;
    private final Cliente cliente;
    private final Vehiculo vehiculo;
    private final int dias;
    private final double subtotal;
    private final double descuento;
    private final double total;

    private boolean activo;

    // Accesible desde las clases del mismo paquete.
    Alquiler(int numero, Cotizacion cotizacion) {

        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "El numero del alquiler debe ser mayor que cero."
            );
        }

        if (cotizacion == null) {
            throw new IllegalArgumentException(
                    "Se necesita una cotizacion para crear el alquiler."
            );
        }

// Verifica si la cotización tiene impedimentos para alquilar. Si hay impedimentos, se lanza una excepción indicando que no se puede proceder con el alquiler.
        if (!cotizacion.puedeAlquilar()) {
            throw new IllegalArgumentException(
                    "La cotizacion tiene impedimentos para alquilar."
            );
        }

        this.numero = numero;
        this.cliente = cotizacion.getCliente();
        this.vehiculo = cotizacion.getVehiculo();
        this.dias = cotizacion.getDias();
        this.subtotal = cotizacion.getSubtotal();
        this.descuento = cotizacion.getDescuento();
        this.total = cotizacion.getTotal();

        this.activo = true;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getDias() {
        return dias;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public boolean isActivo() {
        return activo;
    }

    // Registra la devolución y finaliza este alquiler
    void finalizar() {

        if (!activo) {
            throw new IllegalStateException(
                    "El alquiler ya fue finalizado."
            );
        }

//Usa el estado establecido en la clase de estado del vehículo para verificar si el vehículo asociado al alquiler está 
//actualmente alquilado. Si no lo está, se lanza una excepción indicando que no se puede finalizar el alquiler porque
//el vehículo no está en estado de alquiler.
        if (vehiculo.getEstado() != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException(
                    "El vehiculo asociado no esta alquilado."
            );
        }

        vehiculo.registrarDevolucion(dias);

        activo = false;
    }
}