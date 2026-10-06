import java.util.Set;

public class Microbus extends VehiculoPasajeros {
// Subclase de VehiculoPasajeros que representa microbuses, con un atributo adicional para indicar si incluyen un piloto.

    private final boolean incluyePiloto;

    public Microbus(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales,
            int cantidadPasajeros,
            boolean incluyePiloto) {

        super(
                placa,
                marca,
                modelo,
                tarifaDiaria,
                diasIniciales,
                cantidadPasajeros
        );

        if (diasIniciales >= 25) {
            throw new IllegalArgumentException(
                    "Un microbus inicialmente disponible "
                    + "debe tener menos de 25 dias acumulados."
            );
        }

        this.incluyePiloto = incluyePiloto;
    }

//verifica si el microbus incluye un piloto, lo que afecta la licencia requerida para conducirlo.
    public boolean isIncluyePiloto() {
        return incluyePiloto;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = calcularCostoBase(dias);

        if (incluyePiloto) {
            subtotal += 250.0 * dias;
        }

        return subtotal;
    }

    @Override
    public boolean cumpleLicencia(Set<TipoLicencia> licencias) {

        if (incluyePiloto) {
            return true;
        }

        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A)
                || licencias.contains(TipoLicencia.B);
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 25;
    }

    @Override
    public String obtenerDescripcion() {

        String servicioPiloto;

        if (incluyePiloto) {
            servicioPiloto = "Incluido";
        } else {
            servicioPiloto = "No incluido";
        }

        return obtenerDescripcionComun()
                + " | Pasajeros: " + getCantidadPasajeros()
                + " | Piloto: " + servicioPiloto;
    }

    @Override
    public String obtenerCategoria() {
        return "Microbus";
    }
}