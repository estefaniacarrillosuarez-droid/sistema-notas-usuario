package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner; 
import service.UsuarioService;
import service.NotaService;

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
                    UsuarioService.registrarUsuario(sc);
                    break;

                case "2":
                    String email = UsuarioService.iniciarSesion(sc);

                    if(email !=null) {
                        menuUsuario(email);
                    }

                    break;

                case "0":
                    System.out.println("Saliendo del programa...");
                    return;

                default:
                    System.out.println("Opción no válida");
            }

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
                    NotaService.crearNota(email, sc);
                    break;

                case "2":
                    NotaService.listarNotas(email);
                    break;

                case "3":
                    NotaService.verNota(email, sc);
                    break;

                case "4":
                    NotaService.eliminarNota(email, sc);
                    break;

                case "0":
                    System.out.println("Sesión cerrada");
                    return;

                default:
                    System.out.println("Opción no válida");
            
                }

        }

    }
}
   