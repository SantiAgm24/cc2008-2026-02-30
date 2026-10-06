import java.util.Set;

public class Motocicleta extends Vehiculo {
// Subclase de Vehiculo que representa motocicletas, con un atributo adicional para indicar el cilindraje.

    private final int cilindraje;

    public Motocicleta(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales,
            int cilindraje) {

        super(placa, marca, modelo, tarifaDiaria, diasIniciales);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException(
                    "El cilindraje debe ser mayor que cero."
            );
        }

        if (diasIniciales >= 20) {
            throw new IllegalArgumentException(
                    "Una motocicleta inicialmente disponible "
                    + "debe tener menos de 20 dias acumulados."
            );
        }

        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

// Calcula el subtotal del alquiler de la motocicleta, considerando un costo adicional si el cilindraje es mayor a 250 cc.SS
    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = calcularCostoBase(dias);

        if (cilindraje > 250) {
            subtotal += 75.0;
        }

        return subtotal;
    }

//Se verifica si el conjunto de licencias proporcionado incluye la licencia tipo M, que es necesaria para conducir una motocicleta.
    @Override
    public boolean cumpleLicencia(Set<TipoLicencia> licencias) {

        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.M);
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 20;
    }

    @Override
    public String obtenerDescripcion() {

        return obtenerDescripcionComun()
                + " | Cilindraje: " + cilindraje + " cc";
    }

    @Override
    public String obtenerCategoria() {
        return "Motocicleta";
    }
}