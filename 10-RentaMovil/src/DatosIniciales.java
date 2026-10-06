import java.util.EnumSet;

//Clase que registra datos para poder probar el sistema de alquiler de vehículos, incluyendo vehículos y clientes iniciales.
public class DatosIniciales {

    public static void cargar(RentaMovil sistema) {

        if (sistema == null) {
            throw new IllegalArgumentException(
                    "Se necesita un sistema para cargar los datos."
            );
        }

        // AUTOMOVILES
        // Orden: placa, marca, modelo, tarifa, dias,
        // pasajeros, transmisionAutomatica.

        sistema.registrarVehiculo(
                new Automovil(
                        "P001AAA",
                        "Toyota",
                        "Corolla",
                        200.0,
                        28,
                        5,
                        true
                )
        );

        sistema.registrarVehiculo(
                new Automovil(
                        "P002AAA",
                        "Kia",
                        "Picanto",
                        180.0,
                        0,
                        5,
                        false
                )
        );

        // MOTOCICLETAS
        // Orden: placa, marca, modelo, tarifa, dias, cilindraje.

        sistema.registrarVehiculo(
                new Motocicleta(
                        "M001AAA",
                        "Honda",
                        "CB250",
                        100.0,
                        0,
                        250
                )
        );

        sistema.registrarVehiculo(
                new Motocicleta(
                        "M002AAA",
                        "BMW",
                        "g310R",
                        150.0,
                        0,
                        321
                )
        );

        // CAMIONETAS DE CARGA
        // Orden: placa, marca, modelo, tarifa, dias, toneladas.

        sistema.registrarVehiculo(
                new CamionetaCarga(
                        "C001AAA",
                        "Isuzu",
                        "NLR",
                        200.0,
                        0,
                        1.5
                )
        );

        sistema.registrarVehiculo(
                new CamionetaCarga(
                        "C002AAA",
                        "Hino",
                        "300",
                        300.0,
                        0,
                        3.0
                )
        );

        // MICROBUSES
        // Orden: placa, marca, modelo, tarifa, dias,
        // pasajeros, incluyePiloto.

        sistema.registrarVehiculo(
                new Microbus(
                        "B001AAA",
                        "Toyota",
                        "Hiace",
                        450.0,
                        0,
                        15,
                        true
                )
        );

        sistema.registrarVehiculo(
                new Microbus(
                        "B002AAA",
                        "Hyundai",
                        "H350",
                        400.0,
                        0,
                        15,
                        false
                )
        );

        // CLIENTES INDIVIDUALES
        // Orden: DPI, nombre, licencias, confirmadosAnteriores.

        sistema.registrarCliente(
                new ClienteIndividual(
                        "1000000000001",
                        "Kellie Lopez",
                        EnumSet.of(TipoLicencia.C),
                        0
                )
        );

        sistema.registrarCliente(
                new ClienteIndividual(
                        "1000000000002",
                        "Roger Mendez",
                        EnumSet.of(
                                TipoLicencia.A,
                                TipoLicencia.M
                        ),
                        3
                )
        );

        // CLIENTES CORPORATIVOS
        // Orden: NIT, nombreEmpresa, licencias,
        // confirmadosAnteriores, contacto.

        sistema.registrarCliente(
                new ClienteCorporativo(
                        "1234567-8",
                        "Transportes Aguilón",
                        EnumSet.of(TipoLicencia.B),
                        0,
                        "Camila Aguilón"
                )
        );

        sistema.registrarCliente(
                new ClienteCorporativo(
                        "7654321-0",
                        "CargoExpress S.A.",
                        EnumSet.of(TipoLicencia.M),
                        0,
                        "Erick Marroquin"
                )
        );
    }
}