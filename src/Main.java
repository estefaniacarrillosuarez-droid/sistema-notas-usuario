
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

            System.out.println(" SISTEMA DE NOTAS ");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            String opcion = sc.nextLine();

            switch (opcion) {

                case "1":
                    System.out.println("Registro de usuario (pendiente)");
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

}
