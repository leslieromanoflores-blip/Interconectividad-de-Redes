// Define el paquete al que pertenece la clase para organizar el proyecto en Java.
package SimuladorEncendidoRouter;
// Importa la clase Scanner del paquete java.util para leer datos ingresados
// por el usuario desde la consola (teclado).
import java.util.Scanner;
// Declaracion de la clase principal del simulador.
// El nombre de la clase debe coincidir con el nombre del archivo (.java).
public class SimuladorEncendidoRouter {


    // 1. DECLARACION DEL ARREGLO DE ETAPAS DEL ROUTER

    // 'static' indica que el arreglo pertenece a la clase y puede usarse dentro
    // de metodos estaticos (como main, ejecutarArranque, etc.) sin instanciar
    // un objeto de SimuladorEncendidoRouter.
    // 'String[]' crea un arreglo unidimensional de cadenas de texto.
    // Cada indice (de 0 a 10) representa una fase secuencial del encendido
    // de un router basada en el algoritmo de PSeInt.
    static String[] etapas = {
        "FUENTE DE ALIMENTACION",       // Indice 0 -> Etapa 1
        "PROCESADOR",                   // Indice 1 -> Etapa 2
        "MEMORIA RAM",                  // Indice 2 -> Etapa 3
        "MEMORIA FLASH",                // Indice 3 -> Etapa 4
        "BOOTLOADER",                   // Indice 4 -> Etapa 5
        "SISTEMA OPERATIVO",            // Indice 5 -> Etapa 6
        "PROCESO HOST",                 // Indice 6 -> Etapa 7
        "FIREWALL",                     // Indice 7 -> Etapa 8
        "INTERFACES DE RED",            // Indice 8 -> Etapa 9
        "SERVICIOS DE COMUNICACION",    // Indice 9 -> Etapa 10
        "COMPROBACION DE CONECTIVIDAD"  // Indice 10 -> Etapa 11
    };

    // 2. METODO PRINCIPAL MAIN (PUNTO DE ENTRADA DEL PROGRAMA)
    public static void main(String[] args) {

        // Crea una instancia de Scanner conectada a la entrada estandar (System.in).
        Scanner scanner = new Scanner(System.in);

        // Variable entera que almacenara la opcion elegida por el usuario en el menu.
        int opcion;

        // Bucle 'do-while' nos garantiza que el menu se muestre en la consola
        // y se repita mientras 'opcion' sea distinta de 0.
        do {
            // Imprime el encabezado visual del menu principal en consola.
            System.out.println("\n------------------------------------------");
            System.out.println("       SIMULADOR DE ENCENDIDO DE ROUTER");
            System.out.println("--------------------------------------------");
            System.out.println("1. Ejecutar arranque normal (sin fallas)");
            System.out.println("2. Simular falla en una etapa especifica");
            System.out.println("3. Ejecutar prueba completa (normal + falla en Memoria Flash)");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            // Validacion de tipo de dato:
            // Mientras lo que hay en el buffer de entrada NO sea un numero entero,
            // se le pide al usuario que intente de nuevo y se descarta la entrada invalida.
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingresa un numero valido: ");
                scanner.next(); // Consume/limpia el token no numerico
            }

            // Lee el numero entero ingresado por el usuario
            opcion = scanner.nextInt();

            // Limpia el caracter de salto de linea ('\n') que queda pendiente en el buffer
            // despues de leer un entero con nextInt().
            scanner.nextLine();

            // Evalua la opcion seleccionada mediante una estructura switch.
            switch (opcion) {
                // Opcion 1: ejecuta el arranque pasando 'null' para indicar que no hay falla.
                case 1 -> ejecutarArranque(null);

                // Opcion 2: abre el submenu para elegir en que etapa provocar el error.
                case 2 -> {
                    String etapaSeleccionada = elegirEtapaConFalla(scanner);

                    // Solo ejecuta la simulacion si el usuario eligio una etapa valida
                    // (es decir, si no cancelo ni ingreso un numero fuera de rango).
                    if (etapaSeleccionada != null) {
                        ejecutarArranque(etapaSeleccionada);
                    }
                }

                // Opcion 3: realiza dos corridas consecutivas para comparar un arranque
                // exitoso contra uno detenido por falla en la Memoria Flash.
                case 3 -> {
                    System.out.println("\n--- CORRIDA 1: ARRANQUE EXITOSO ---");
                    ejecutarArranque(null);

                    System.out.println("\n--- CORRIDA 2: FALLA EN MEMORIA FLASH ---");
                    ejecutarArranque("MEMORIA FLASH");
                }

                // Opcion 0: muestra mensaje de despedida y terminara el ciclo do-while.
                case 0 -> System.out.println("\nSaliendo del simulador. Adios USUARIO..BYE");

                // Cualquier otro numero entero distinto de 0, 1, 2 o 3 cae aqui.
                default -> System.out.println("\nOpcion no valida. Intenta de nuevo");
            }

        } while (opcion != 0); // Condicion de permanencia del ciclo.

        // Cierra el objeto Scanner para liberar el recurso de entrada asociado a System.in.
        scanner.close();
    }

  
    // 3. SUBMENU PARA SELECCIONAR EN QUE ETAPA OCURRE LA FALLA

    // Recibe el objeto Scanner desde main para reutilizar el mismo flujo de entrada.
    // Devuelve un String con el nombre de la etapa elegida, o null si se cancela/falla.
    public static String elegirEtapaConFalla(Scanner scanner) {

        System.out.println("\nSelecciona la etapa donde deseas provocar la falla:");

        // Recorre el arreglo 'etapas' para mostrar cada opcion numerada del 1 al 11.
        for (int i = 0; i < etapas.length; i++) {
            System.out.println((i + 1) + ". " + etapas[i]);
        }

        // Opcion adicional para regresar al menu principal sin simular fallas.
        System.out.println("0. Cancelar y volver al menu");
        System.out.print("Etapa: ");

        // Valida que la seleccion sea un numero entero.
        while (!scanner.hasNextInt()) {
            System.out.print("Ingresa un numero que este en el menu: ");
            scanner.next();
        }

        // Guarda el numero elegido por el usuario y limpia el salto de linea pendiente.
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        // Si el usuario eligio 0, cancela la operacion y retorna null.
        if (seleccion == 0) {
            System.out.println("Simulacion cancelada.");
            return null;
        }

        // Verifica que el numero este dentro del rango valido (1 hasta etapas.length).
        if (seleccion < 1 || seleccion > etapas.length) {
            System.out.println("Numero de etapa fuera de rango.");
            return null;
        }

        // Retorna la etapa correspondiente. Se resta 1 porque el usuario ve numeros
        // desde 1, pero los indices del arreglo inician en 0.
        return etapas[seleccion - 1];
    }


    // 4. METODO QUE EJECUTA LA SECUENCIA DE ENCENDIDO

    // Parametro 'etapaConFalla':
    //  Si es null: todas las etapas se ejecutan sin error.
    // Si tiene el nombre de una etapa: al llegar a ella se simula una falla
    //   y se detiene el arranque (equivalente a cortar el flujo en los Si/SiNo
    //   anidados del algoritmo de PSeInt).
    public static void ejecutarArranque(String etapaConFalla) {

        System.out.println("\nENCENDIDO DEL ROUTER");
        System.out.println("Iniciando secuencia de arranque...\n");

        // Ciclo 'for' que recorre secuencialmente cada etapa del router.
        for (int i = 0; i < etapas.length; i++) {

            // Muestra el numero de paso (empezando en 1) y el nombre de la etapa actual.
            System.out.println((i + 1) + ". Etapa: " + etapas[i]);

            // Condicional para detectar si se configuro una falla en la etapa actual.
            // Primero se verifica 'etapaConFalla != null' para evitar NullPointerException
            // al llamar a .equals(...).
            if (etapaConFalla != null && etapas[i].equals(etapaConFalla)) {
                System.out.println("[ERROR] Fallo detectado en " + etapas[i]);

                // Muestra el diagnostico y las posibles causas de la falla.
                mostrarError(etapas[i]);

                // Informa que el arranque se interrumpe y no avanza a las siguientes etapas.
                System.out.println("\nARRANQUE DETENIDO");
                System.out.println("El router no puede completar el inicio.");

                // 'return' finaliza inmediatamente la ejecucion de 'ejecutarArranque'.
                return;
            }

            // Si no hubo falla en esta etapa, se marca como activa/encendida.
            System.out.println("[LISTO!] " + etapas[i] + ": ENCENDIDA / ACTIVA");

            // Muestra una breve descripcion de lo que ocurre en esta fase.
            mostrarDetalle(etapas[i]);

            // Separador visual entre etapas.
            System.out.println("------------------------------------------");
        }

        // Este bloque solo se alcanza si el ciclo 'for' termino todas las etapas
        // sin ejecutar 'return' por alguna falla.
        System.out.println("\n Conectividad EXITOSA");
        System.out.println("ROUTER ENCENDIDO CORRECTAMENTE");
        System.out.println("LISTO PARA PROPORCIONAR INTERNET");
    }

    // 5. DETALLE DE CADA ETAPA DEL ROUTER
  
    // Recibe el nombre de una etapa y explica brevemente su funcion en el router.
    public static void mostrarDetalle(String etapa) {
        switch (etapa) {
            case "FUENTE DE ALIMENTACION" ->
                System.out.println("Recibiendo alimentacion electrica y estabilizando energia.");

            case "PROCESADOR" ->
                System.out.println("Inicializando CPU del router para ejecutar instrucciones.");

            case "MEMORIA RAM" ->
                System.out.println("Inicializando memoria volatil para datos y tablas de trabajo.");

            case "MEMORIA FLASH" ->
                System.out.println("Verificando memoria no volatil donde reside la imagen del sistema.");

            case "BOOTLOADER" ->
                System.out.println("Cargando el cargador de arranque de bajo nivel.");

            case "SISTEMA OPERATIVO" ->
                System.out.println("Iniciando el sistema operativo de red (ej. IOS / firmware del router).");

            case "PROCESO HOST" ->
                System.out.println("Inicializando proceso Host para gestionar configuracion y control.");

            case "FIREWALL" ->
                System.out.println("Activando reglas de filtrado y seguridad de red.");

            case "INTERFACES DE RED" ->
                System.out.println("Inicializando puertos WAN/LAN e interfaces fisicas/logicas.");

            case "SERVICIOS DE COMUNICACION" ->
                System.out.println("Servicios DHCP, DNS, NAT y Wi-Fi: ACTIVOS.");

            case "COMPROBACION DE CONECTIVIDAD" ->
                System.out.println("Verificando enlace y alcance hacia la red.");
        }
    }

    // 6. DESCRIPCION DE ERRORES Y POSIBLES CAUSAS POR ETAPA

    // Recibe la etapa donde ocurrio el fallo y despliega el mensaje de error
    // junto con posibles causas tecnicas asociadas.
    public static void mostrarError(String etapa) {
        switch (etapa) {
            case "FUENTE DE ALIMENTACION" -> {
                System.out.println("ERROR en la fuente de alimentacion.");
                System.out.println("Posibles causas:");
                System.out.println("- Adaptador de corriente desconectado o danado.");
                System.out.println("- Sin voltaje en la toma electrica.");
            }

            case "PROCESADOR" -> {
                System.out.println("ERROR en el procesador.");
                System.out.println("Posibles causas:");
                System.out.println("- Sobrecalentamiento o falla interna del SoC/CPU.");
            }

            case "MEMORIA RAM" -> {
                System.out.println("ERROR en la memoria RAM.");
                System.out.println("Posibles causas:");
                System.out.println("- Modulo o chip de memoria defectuoso.");
                System.out.println("- Error al asignar memoria de trabajo.");
            }

            case "MEMORIA FLASH" -> {
                System.out.println("ERROR en la memoria Flash.");
                System.out.println("Posibles causas:");
                System.out.println("- Imagen del sistema corrupta o faltante.");
                System.out.println("- Sectores danados en la memoria Flash.");
            }

            case "BOOTLOADER" -> {
                System.out.println("ERROR en el Bootloader.");
                System.out.println("Posibles causas:");
                System.out.println("- Cargador de arranque danado o mal configurado.");
            }

            case "SISTEMA OPERATIVO" -> {
                System.out.println("ERROR en el Sistema Operativo.");
                System.out.println("Posibles causas:");
                System.out.println("- Firmware incompatible o danado al descomprimir.");
            }

            case "PROCESO HOST" -> {
                System.out.println("ERROR en el proceso Host.");
                System.out.println("Posibles causas:");
                System.out.println("- Fallo en el demonio principal de control.");
            }

            case "FIREWALL" -> {
                System.out.println("ERROR en el Firewall.");
                System.out.println("Posibles causas:");
                System.out.println("- Reglas corruptas o fallo al cargar el modulo de seguridad.");
            }

            case "INTERFACES DE RED" -> {
                System.out.println("ERROR en las interfaces de red.");
                System.out.println("Posibles causas:");
                System.out.println("- Puerto o controlador de interfaz inoperativo.");
            }

            case "SERVICIOS DE COMUNICACION" -> {
                System.out.println("ERROR en los servicios de comunicacion.");
                System.out.println("Posibles causas:");
                System.out.println("- Fallo al levantar DHCP, DNS, NAT o radio Wi-Fi.");
            }

            case "COMPROBACION DE CONECTIVIDAD" -> {
                System.out.println("ERROR: No hay conectividad.");
                System.out.println("Posibles causas:");
                System.out.println("- Cable WAN desconectado o sin senal del proveedor (ISP).");
                System.out.println("- Puerta de enlace o ruta predeterminada inalcanzable.");
            }
        }
    }
}
/*Revisado Calif 100
25/11/2026 
Jimena Martinez Montiel
Dulce Maite Espinoza Garcia
Lesly Romano Flores
Angie Karen Perez Godinez*/