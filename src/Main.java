import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

            System.out.println("\n=== SISTEMA DE NOTAS ===");
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
                    System.out.println("Inicio de sesión (pendiente)");
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

        try { //Controla errores

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
            try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("data/users.txt", true))) {

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

}