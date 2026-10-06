import java.util.Set;

public class Automovil extends VehiculoPasajeros {
//Subclase de VehiculoPasajeros que representa automóviles, con un atributo adicional para indicar si tienen transmisión automática.

    private final boolean transmisionAutomatica;

// Constructor de la subclase Automovil, que llama al constructor de la superclase VehiculoPasajeros y valida los días iniciales.
    public Automovil(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales,
            int cantidadPasajeros,
            boolean transmisionAutomatica) {

// Inicializa los datos comunes mediante el constructor padre y valida los días iniciales.
        super(
                placa,
                marca,
                modelo,
                tarifaDiaria,
                diasIniciales,
                cantidadPasajeros
        );

// Valida que los días iniciales sean menores a 30, ya que un automóvil inicialmente disponible no puede tener 30 o más días acumulados.
        if (diasIniciales >= 30) {
            throw new IllegalArgumentException(
                    "Un automovil inicialmente disponible "
                    + "debe tener menos de 30 dias acumulados."
            );
        }

        this.transmisionAutomatica = transmisionAutomatica;
    }

    public boolean isTransmisionAutomatica() {
        return transmisionAutomatica;
    }

// Implementación de los métodos abstractos heredados de Vehiculo y VehiculoPasajeros.
    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = calcularCostoBase(dias);

        if (transmisionAutomatica) {
            subtotal += 50.0 * dias;
        }

        return subtotal;
    }

    @Override
    public boolean cumpleLicencia(Set<TipoLicencia> licencias) {

        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A)
                || licencias.contains(TipoLicencia.B)
                || licencias.contains(TipoLicencia.C);
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 30;
    }

    @Override
    public String obtenerDescripcion() {

        String transmision;

        if (transmisionAutomatica) {
            transmision = "Automatica";
        } else {
            transmision = "Manual";
        }

        return obtenerDescripcionComun()
                + " | Pasajeros: " + getCantidadPasajeros()
                + " | Transmision: " + transmision;
    }

    @Override
    public String obtenerCategoria() {
        return "Automovil";
    }
}