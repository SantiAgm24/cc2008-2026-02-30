public abstract class VehiculoPasajeros extends Vehiculo {
//Subclase de Vehiculo que representa vehículos diseñados para transportar pasajeros, como automóviles y autobuses.

    private final int cantidadPasajeros;

// Constructor de la subclase VehiculoPasajeros, que llama al constructor de la superclase Vehiculo y valida la cantidad de pasajeros.
    protected VehiculoPasajeros(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales,
            int cantidadPasajeros) {

        // Inicializa los datos comunes mediante el constructor padre.
        super(placa, marca, modelo, tarifaDiaria, diasIniciales);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de pasajeros debe ser mayor que cero."
            );
        }

        this.cantidadPasajeros = cantidadPasajeros;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }
}