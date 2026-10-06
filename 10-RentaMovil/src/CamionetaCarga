import java.util.Set;

public class CamionetaCarga extends Vehiculo {
// Subclase de Vehiculo que representa camionetas de carga, con un atributo adicional para indicar la capacidad máxima en toneladas.

    private final double capacidadToneladas;

    public CamionetaCarga(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales,
            double capacidadToneladas) {

        super(placa, marca, modelo, tarifaDiaria, diasIniciales);

        if (!Double.isFinite(capacidadToneladas)
                || capacidadToneladas <= 0) {

            throw new IllegalArgumentException(
                    "La capacidad debe ser un numero positivo y finito."
            );
        }

        if (diasIniciales >= 15) {
            throw new IllegalArgumentException(
                    "Una camioneta inicialmente disponible "
                    + "debe tener menos de 15 dias acumulados."
            );
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = calcularCostoBase(dias);
        double recargo = 100.0 * capacidadToneladas * dias;

        return subtotal + recargo;
    }

//Verifica si el conjunto de licencias proporcionado incluye la licencia tipo A o B, que son necesarias para conducir una camioneta de carga.
    @Override
    public boolean cumpleLicencia(Set<TipoLicencia> licencias) {

        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A)
                || licencias.contains(TipoLicencia.B);
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 15;
    }

    @Override
    public String obtenerDescripcion() {

        return obtenerDescripcionComun()
                + " | Capacidad maxima: "
                + capacidadToneladas + " toneladas";
    }

    @Override
    public String obtenerCategoria() {
        return "Camioneta de carga";
    }
}