import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cotizacion {
//Nueva clase que representa una cotización de alquiler de vehículo, incluyendo información sobre el cliente, vehículo, duración del alquiler, subtotal, descuento y motivos de impedimento.

    private final Cliente cliente;
    private final Vehiculo vehiculo;
    private final int dias;
    private final double subtotal;
    private final double descuento;
    private final List<String> motivosImpedimento;

    // Accesible desde las clases del mismo paquete.
    Cotizacion(
            Cliente cliente,
            Vehiculo vehiculo,
            int dias,
            double subtotal,
            double descuento,
            List<String> motivosImpedimento) {

        if (cliente == null || vehiculo == null) {
            throw new IllegalArgumentException(
                    "La cotizacion requiere un cliente y un vehiculo."
            );
        }

        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias deben ser mayores que cero."
            );
        }

        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal debe ser un numero finito no negativo."
            );
        }

        if (!Double.isFinite(descuento)
                || descuento < 0
                || descuento > subtotal) {

            throw new IllegalArgumentException(
                    "El descuento debe ser finito y estar entre cero "
                    + "y el subtotal."
            );
        }

        if (motivosImpedimento == null) {
            throw new IllegalArgumentException(
                    "La lista de impedimentos no puede ser nula."
            );
        }

        for (String motivo : motivosImpedimento) {
            if (motivo == null || motivo.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Los motivos no pueden ser nulos ni vacios."
                );
            }
        }

        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;

        this.motivosImpedimento = new ArrayList<>(
                motivosImpedimento
        );
    }

//getters para acceder a los atributos de la cotización, incluyendo cliente, vehículo, duración del alquiler, subtotal, descuento, total y motivos de impedimento. También incluye un método para verificar si el cliente puede alquilar el vehículo basado en los motivos de impedimento.
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
        return Math.round((subtotal - descuento) * 100.0) / 100.0;
    }

    public List<String> getMotivosImpedimento() {

        return Collections.unmodifiableList(
                new ArrayList<>(motivosImpedimento)
        );
    }

//Verifica si el cliente puede alquilar el vehículo basado en los motivos de impedimento. Si la lista de motivos está vacía, significa que no hay impedimentos y el cliente puede proceder con el alquiler.
    public boolean puedeAlquilar() {
        return motivosImpedimento.isEmpty();
    }
}