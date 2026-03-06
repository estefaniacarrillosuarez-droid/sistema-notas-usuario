package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class NotaService {

    // Crear nota
    public static void crearNota(String email, Scanner sc) {

        System.out.print("Título: ");
        String titulo = sc.nextLine();

        System.out.print("Contenido: ");
        String contenido = sc.nextLine();

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data").resolve("usuarios").resolve(carpetaEmail).resolve("notas.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notasFile.toString(), true))) {

            writer.write(titulo + ";" + contenido);
            writer.newLine();

            System.out.println("Nota guardada correctamente");

        } catch (IOException e) {
            System.out.println("Error nota");
        }
    }

    // Listar notas
    public static void listarNotas(String email) {

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data").resolve("usuarios").resolve(carpetaEmail).resolve("notas.txt");

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

    // Ver nota
    public static void verNota(String email, Scanner sc) {

        listarNotas(email);

        System.out.print("Número de nota: ");
        int num = Integer.parseInt(sc.nextLine());

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data").resolve("usuarios").resolve(carpetaEmail).resolve("notas.txt");

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

    // Eliminar nota
    public static void eliminarNota(String email, Scanner sc) {

        listarNotas(email);

        System.out.print("Número de nota a eliminar: ");
        int num = Integer.parseInt(sc.nextLine());

        String carpetaEmail = email.replace("@", "_").replace(".", "_");

        Path notasFile = Path.of("data").resolve("usuarios").resolve(carpetaEmail).resolve("notas.txt");

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