package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class UsuarioService {

    public static void registrarUsuario(Scanner sc) {

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email y contraseña no pueden estar vacíos");
            return;
        }

        Path usersFile = Path.of("data").resolve("users.txt");

        try {

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

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", true))) {

                writer.write(email + ";" + password);
                writer.newLine();
            }

            String carpetaEmail = email.replace("@", "_").replace(".", "_");

            Path carpetaUsuario = Path.of("data").resolve("usuarios").resolve(carpetaEmail);

            Files.createDirectories(carpetaUsuario);

            System.out.println("Usuario registrado correctamente");

        } catch (IOException e) {
            System.out.println("Error registrando usuario");
        }
    }

    public static String iniciarSesion(Scanner sc) {

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        Path usersFile = Path.of("data").resolve("users.txt");

        try (BufferedReader reader = Files.newBufferedReader(usersFile)) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(";");

                if (partes.length >= 2 && partes[0].equals(email) && partes[1].equals(password)) {

                    System.out.println("Inicio de sesión correcto");
                    return email;
                }
            }

            System.out.println("Email o contraseña incorrectos");

        } catch (IOException e) {
            System.out.println("Error leyendo usuarios");
        }

        return null;
    }
}
