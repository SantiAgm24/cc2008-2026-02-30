import java.util.Set;

//Abstract para que no se pueda instanciar directamente, solo a través de subclases
public abstract class Vehiculo {

    // Datos que no cambian después de construir el vehículo
    private final String placa;
    private final String marca;
    private final String modelo;
    private final double tarifaDiaria;

    // Datos que cambian durante las operaciones del sistema
    private EstadoVehiculo estado;
    private int diasAcumulados;

    // Constructor utilizado por las subclases
    protected Vehiculo(
            String placa,
            String marca,
            String modelo,
            double tarifaDiaria,
            int diasIniciales) {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La placa no puede estar vacia."
            );
        }

        if (!Double.isFinite(tarifaDiaria) || tarifaDiaria <= 0) {
            throw new IllegalArgumentException(
                    "La tarifa diaria debe ser un numero positivo y finito."
            );
        }

        if (diasIniciales < 0) {
            throw new IllegalArgumentException(
                    "Los dias iniciales no pueden ser negativos."
            );
        }

        this.placa = placa.trim();
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;

        this.estado = EstadoVehiculo.DISPONIBLE;
        this.diasAcumulados = diasIniciales;
    }

    // Métodos para consultar los datos del vehículo. No hay setters, ya que no se permite modificar los datos después de la construcción.
    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public int getDiasAcumulados() {
        return diasAcumulados;
    }

    // Cálculo de todas las categorías.
    protected double calcularCostoBase(int dias) {

        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias de alquiler tienen que ser mayores que cero."
            );
        }

        return tarifaDiaria * dias;
    }

    // Información común para las descripciones de las subclases.
    protected String obtenerDescripcionComun() {

        return "Placa: " + placa
                + " | Marca: " + marca
                + " | Modelo: " + modelo
                + " | Tarifa diaria: Q"
                + String.format("%.2f", tarifaDiaria);
    }

    public boolean estaDisponible() {
        return estado == EstadoVehiculo.DISPONIBLE;
    }

    // Sin modificador: acceso desde clases del mismo paquete.
    void marcarAlquilado() {

        if (!estaDisponible()) {
            throw new IllegalStateException(
                    "El vehiculo no esta disponible."
            );
        }

        estado = EstadoVehiculo.ALQUILADO;
    }

    void registrarDevolucion(int dias) {

        if (estado != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException(
                    "Solo se puede devolver un vehiculo alquilado."
            );
        }

        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias del alquiler deben ser mayores que cero."
            );
        }

        // Calculamos antes de modificar el objeto.
        // addExact evita que un desbordamiento produzca días negativos.
        int nuevoAcumulado = Math.addExact(diasAcumulados, dias);

        EstadoVehiculo nuevoEstado;

        if (nuevoAcumulado >= obtenerUmbralMantenimiento()) {
            nuevoEstado = EstadoVehiculo.EN_MANTENIMIENTO;
        } else {
            nuevoEstado = EstadoVehiculo.DISPONIBLE;
        }

        diasAcumulados = nuevoAcumulado;
        estado = nuevoEstado;
    }

    void finalizarMantenimiento() {

        if (estado != EstadoVehiculo.EN_MANTENIMIENTO) {
            throw new IllegalStateException(
                    "El vehiculo no esta en mantenimiento."
            );
        }

        diasAcumulados = 0;
        estado = EstadoVehiculo.DISPONIBLE;
    }

    // Cada categoría tendrá que implementar sus propias reglas.
    public abstract double calcularSubtotal(int dias);

    public abstract boolean cumpleLicencia(
            Set<TipoLicencia> licencias
    );

    public abstract int obtenerUmbralMantenimiento();

    public abstract String obtenerDescripcion();

    public abstract String obtenerCategoria();
}