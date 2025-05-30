    import java.util.Scanner;

    public class Main {

        public static void insertarSugerencia(Scanner sc, Cola<String> cola) {
            boolean continuar = true;

            while (continuar) {
                System.out.println("¿Cuál es tu sugerencia? Escríbela aquí ↓");
                String sugerencia = sc.nextLine().trim();

                if (sugerencia.isEmpty()) {
                    System.out.println("Ingresaste una sugerencia vacía, inténtalo nuevamente");
                    continue;
                }

                cola.enqueue(sugerencia);

                while (true) {
                    System.out.println("¿Deseas ingresar otra sugerencia?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");

                    String input = sc.nextLine().trim();

                    if (input.equals("1")) {
                        break;
                    } else if (input.equals("2")) {
                        continuar = false;
                        break;
                    } else {
                        System.out.println("Entrada inválida. Por favor, ingresa 1 o 2.");
                    }
                }
            }
        }

        public static void leerSugerencia(Scanner sc, Cola<String> cola) {
            boolean continuar = true;

            while (continuar) {
                String ultima_sugerencia = cola.peek();
                if (cola.isEmpty()) {
                    System.out.println("Este buzón está muy vacío, anímate a escribir la primera sugerencia desde el buzón principal.\n");
                } else {
                    System.out.println("La sugerencia más reciente dice: " + ultima_sugerencia + "\n");
                }

                while (true) {
                    System.out.println("Oprime 0 para regresar al menú anterior");

                    String input = sc.nextLine().trim();

                    if (input.equals("0")) {
                        continuar = false;
                        break;
                    } else {
                        System.out.println("Entrada inválida.");
                    }
                }
            }
        }

        public static void eliminarSugerencia(Scanner sc, Cola<String> cola) {
            boolean continuar = true;

            while (continuar) {
                String sugerencia = cola.peek();

                if (cola.isEmpty()) {
                    System.out.println("¡Ya has eliminado la última sugerencia! El buzón está vacío.\n");
                } else {
                    System.out.println("Eliminaste la siguiente sugerencia: " + sugerencia + "\n");
                    cola.dequeue();
                }


                while (true) {
                    System.out.println("Oprime 0 para regresar al menú anterior");

                    String input = sc.nextLine().trim();

                    if (input.equals("0")) {
                        continuar = false;
                        break;
                    } else {
                        System.out.println("Entrada inválida.");
                    }
                }
            }
        }

        public static void mostrarMenu() {
            System.out.println("""
        ¡Bienvenido al Buzón de Sugerencias del Ensamble Músico-Vocal!
        
        A continuación elige una de las siguientes opciones:

        1. Déjanos tus Sugerencias.
        2. Si eres administrador, aquí puedes leer la sugerencia más antigua que recibimos.
        3. Para administradores también ;) esta opción te permite eliminar la sugerencia más antigua.
        
        Si deseas salir, oprime cualquier tecla diferente a las anteriores.
        """);
        }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Cola<String> cola = new Cola<>();
            boolean menu_principal = true;

            while (menu_principal) {
                mostrarMenu();
                System.out.print("Selecciona una opción: ");
                String opcion = sc.nextLine().trim();

                switch (opcion) {
                    case "1":
                        insertarSugerencia(sc, cola);
                        break;
                    case "2":
                        leerSugerencia(sc, cola);
                        break;
                    case "3":
                        eliminarSugerencia(sc, cola);
                        break;
                    default:
                        System.out.println("Gracias por usar el Buzón de Sugerencias, en este momento estamos cerrando el buzón...");
                        menu_principal = false;
                        break;
                }
            }

            sc.close();
        }
    }