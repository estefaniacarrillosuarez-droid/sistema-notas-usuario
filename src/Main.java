import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner; 

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        prepararSistema();
        mostrarMenuPrincipal();

    }

    public static void prepararSistema() {

        Path carpetaData = Path.of("data");
        Path usersFile = carpetaData.resolve("users.txt");
        Path carpetaUsuarios = carpetaData.resolve("usuarios");

        try {

            Files.createDirectories(carpetaUsuarios);

            if (!Files.exists(usersFile)) {
                Files.createFile(usersFile);
            }

        } catch (IOException e) {
            System.out.println("Error preparando el sistema");
        }

    }

    public static void mostrarMenuPrincipal() {

        while (true) {

            System.out.println(" SISTEMA DE NOTAS ");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            String opcion = sc.nextLine();

            switch (opcion) {

                case "1":
                    registrarUsuario();
                    break;

                case "2":
                    iniciarSesion();
                    break;

                case "0":
                    System.out.println("Saliendo del programa...");
                    return;

                default:
                    System.out.println("Opción no válida");
            }

        }

    }

    public static void registrarUsuario() {

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email y contraseña no pueden estar vacíos");
            return;
        }

        Path usersFile = Path.of("data").resolve("users.txt");

        try { // Controla errores

            // Comprobamos si el usuario ya existe
            try (BufferedReader reader = Files.newBufferedReader(usersFile)) {

                String linea;

                while ((linea = reader.readLine()) != null) {

                    String[] partes = linea.split(";");

                    if (partes[0].equals(email)) {
                        System.out.println("El usuario ya existe");
                        return;
                    }
                }
            }

            // Guardamos el usuario
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", true))) {

                writer.write(email + ";" + password);
                writer.newLine();
            }

            // Crear carpeta del usuario
            String carpetaEmail = email.replace("@", "_").replace(".", "_");

            Path carpetaUsuario = Path.of("data")
            .resolve("usuarios")
            .resolve(carpetaEmail);

            Files.createDirectories(carpetaUsuario);

            System.out.println("Usuario registrado correctamente");

        } catch (IOException e) {
            System.out.println("Error registrando usuario");
        }

    }

    public static void iniciarSesion() {

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        Path usersFile = Path.of("data").resolve("users.txt");

        try (BufferedReader reader = Files.newBufferedReader(usersFile)) {

            String linea;

            // Leemos el archivo para comprobar 
            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(";");

                if (partes.length >= 2 && partes[0].equals(email) && partes[1].equals(password)) {

                    System.out.println("Inicio de sesión correcto");
                    menuUsuario(email);
                    return;
                }
            }

            System.out.println("Email o contraseña incorrectos");

        } catch (IOException e) {
            System.out.println("Error leyendo usuarios");
        }

    }

    public static void menuUsuario(String email) {

        while (true) {

            System.out.println(" MENÚ DE USUARIO ");
            System.out.println("Usuario: " + email);
            System.out.println("1. Crear nota");
            System.out.println("2. Listar notas");
            System.out.println("3. Ver nota");
            System.out.println("4. Eliminar nota");
            System.out.println("0. Cerrar sesión");
            System.out.print("Elige una opción: ");

            String opcion = sc.nextLine();

            switch (opcion) {

                case "1":
                    crearNota(email); // Método para guardar una nota
                    break;

                case "2":
                    listarNotas(email); // Muestra las notas guardadas
                    break;

                case "3":
                    verNota(email); // Muestra una nota específica
                    break;

                case "4":
                    eliminarNota(email); // Elimina una nota
                    break;

                case "0":
                    System.out.println("Sesión cerrada");
                    return;

                default:
                    System.out.println("Opción no válida");
            }

        }

    }

    // Crear una nota y guardarla en notas.txt del usuario
    public static void crearNota(String email) {

        System.out.print("Título: ");
        String titulo = sc.nextLine();

        System.out.print("Contenido: ");
        String contenido = sc.nextLine();

        // Convertimos el email en nombre de carpeta válido
        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        // Ruta del archivo de notas del usuario
        Path notasFile = Path.of("data")
        .resolve("usuarios")
        .resolve(carpetaEmail)
        .resolve("notas.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notasFile.toString(), true))) {

            writer.write(titulo + ";" + contenido);
            writer.newLine();

            System.out.println("Nota guardada correctamente");

        } catch (IOException e) {
            System.out.println("Error guardando nota");
        }

    }

    // Listar todas las notas del usuario
    public static void listarNotas(String email) {

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data")
        .resolve("usuarios")
        .resolve(carpetaEmail)
        .resolve("notas.txt");

        try {

            if (!Files.exists(notasFile)) {
                System.out.println("No hay notas guardadas");
                return;
            }

            int i = 1;

            for (String linea : Files.readAllLines(notasFile)) {

                String[] partes = linea.split(";");

                System.out.println(i + ". " + partes[0]);
                i++;
            }

        } catch (IOException e) {
            System.out.println("Error leyendo notas");
        }

    }

    // Mostrar el contenido completo de una nota
    public static void verNota(String email) {

        listarNotas(email);

        System.out.print("Número de nota: ");
        int num = Integer.parseInt(sc.nextLine());

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data")
                .resolve("usuarios")
                .resolve(carpetaEmail)
                .resolve("notas.txt");

        try {

            List<String> lineas = Files.readAllLines(notasFile);

            if (num < 1 || num > lineas.size()) {
                System.out.println("Número inválido");
                return;
            }

            String[] partes = lineas.get(num - 1).split(";");

            System.out.println("Título: " + partes[0]);
            System.out.println("Contenido: " + partes[1]);

        } catch (IOException e) {
            System.out.println("Error leyendo nota");
        }

    }

    // Eliminar una nota del archivo
    public static void eliminarNota(String email) {

        listarNotas(email);

        System.out.print("Número de nota a eliminar: ");
        int num = Integer.parseInt(sc.nextLine());

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data")
                .resolve("usuarios")
                .resolve(carpetaEmail)
                .resolve("notas.txt");

        try {

            List<String> lineas = Files.readAllLines(notasFile);

            if (num < 1 || num > lineas.size()) {
                System.out.println("Número inválido");
                return;
            }

            lineas.remove(num - 1);

            Files.write(notasFile, lineas);

            System.out.println("Nota eliminada correctamente");

        } catch (IOException e) {
            System.out.println("Error eliminando nota");
        }

    }

}