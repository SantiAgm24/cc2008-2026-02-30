import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Cliente {

    private final String identificador;
    private final String nombre;
    private final Set<TipoLicencia> licencias;
    private final int confirmadosAnteriores;

    protected Cliente(
            String identificador,
            String nombre,
            Set<TipoLicencia> licencias,
            int confirmadosAnteriores) {

//Crea un nuevo cliente corporativo con el identificador, nombre, conjunto de licencias y número de alquileres confirmados previamente. Se realizan validaciones para asegurar que los datos proporcionados sean válidos.
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacio."
            );
        }

        if (licencias == null || licencias.isEmpty()) {
            throw new IllegalArgumentException(
                    "El cliente debe presentar al menos una licencia."
            );
        }

        if (licencias.contains(null)) {
            throw new IllegalArgumentException(
                    "Las licencias no pueden contener valores nulos."
            );
        }

        if (confirmadosAnteriores < 0) {
            throw new IllegalArgumentException(
                    "Los alquileres anteriores no pueden ser negativos."
            );
        }

        this.identificador = identificador.trim();
        this.nombre = nombre;

        // Conserva una copia independiente del conjunto recibido.
        this.licencias = new HashSet<>(licencias);

        this.confirmadosAnteriores = confirmadosAnteriores;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<TipoLicencia> getLicencias() {

        // Devuelve una copia que no permite modificaciones.
        return Collections.unmodifiableSet(
                new HashSet<>(licencias)
        );
    }

    public int getConfirmadosAnteriores() {
        return confirmadosAnteriores;
    }

    public abstract double calcularDescuento(
            double subtotal,
            int confirmadosPrevios
    );

    public abstract int obtenerLimiteAlquileresActivos();

    public abstract String obtenerDescripcion();
}