import java.util.Set;

public class ClienteCorporativo extends Cliente {
// Subclass de Cliente que representa a un cliente corporativo con un NIT único, nombre de empresa, conjunto de licencias y un contacto principal.

    private final String contacto;

    public ClienteCorporativo(
            String nit,
            String nombreEmpresa,
            Set<TipoLicencia> licencias,
            int confirmadosAnteriores,
            String contacto) {

        super(nit, nombreEmpresa, licencias, confirmadosAnteriores);

        if (contacto == null || contacto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del contacto no puede estar vacio."
            );
        }

        this.contacto = contacto.trim();
    }

    public String getContacto() {
        return contacto;
    }

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

        return subtotal * 0.10;
    }

    @Override
    public int obtenerLimiteAlquileresActivos() {
        return 3;
    }

    @Override
    public String obtenerDescripcion() {

        return "Cliente corporativo"
                + " | Empresa: " + getNombre()
                + " | NIT: " + getIdentificador()
                + " | Contacto: " + contacto
                + " | Licencias: " + getLicencias();
    }
}