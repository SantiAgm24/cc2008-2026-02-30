public class Main {

    public static void main(String[] args) {

        // Crea la administradora con sus colecciones vacías.
        RentaMovil sistema = new RentaMovil();

        // Registra los vehículos y clientes de demostración.
        DatosIniciales.cargar(sistema);

        // Conecta la vista con la misma administradora.
        VistaRentaMovil vista = new VistaRentaMovil(sistema);

        // Abre el menú y mantiene la interacción hasta salir.
        vista.iniciar();
    }
}