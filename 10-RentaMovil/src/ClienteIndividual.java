import java.util.Set;

public class ClienteIndividual extends Cliente {
//Subclass de Cliente que representa a un cliente individual con un DPI único y un conjunto de licencias.

    public ClienteIndividual(
            String dpi,
            String nombre,
            Set<TipoLicencia> licencias,
            int confirmadosAnteriores) {

        super(dpi, nombre, licencias, confirmadosAnteriores);
//Constructor que inicializa un cliente individual con su DPI, nombre, licencias y conteo de alquileres confirmados previamente.

        if (!getIdentificador().matches("[0-9]{13}")) {
            throw new IllegalArgumentException(
                    "El DPI debe contener exactamente 13 digitos."
            );
        }
    }

//Calcula el descuento aplicable basado en el subtotal y el número de alquileres confirmados previamente. Si el subtotal es negativo o no es un número finito, o si el conteo de alquileres es negativo, se lanza una excepción. Si el cliente ha confirmado 3 o más alquileres previamente, se aplica un descuento del 5% sobre el subtotal.
    @Override
    public double calcularDescuento(
            double subtotal,
            int confirmadosPrevios) {

        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal debe ser un numero finito no negativo."
            );
        }

        if (confirmadosPrevios < 0) {
            throw new IllegalArgumentException(
                    "El conteo de alquileres no puede ser negativo."
            );
        }

        if (confirmadosPrevios >= 3) {
            return subtotal * 0.05;
        }

        return 0.0;
    }

    @Override
    public int obtenerLimiteAlquileresActivos() {
        return 1;
    }

    @Override
    public String obtenerDescripcion() {

        return "Cliente individual"
                + " | Nombre: " + getNombre()
                + " | DPI: " + getIdentificador()
                + " | Licencias: " + getLicencias();
    }
}