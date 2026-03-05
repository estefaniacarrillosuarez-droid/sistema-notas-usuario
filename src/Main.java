import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        prepararSistema();

        System.out.println("Sistema listo");
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
            System.out.println("Error inicializando el sistema");
        }

    }
}
