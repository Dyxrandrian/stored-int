package school.hei.school.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.hei.school.endpoint.rest.controller.StoredIntController;

public class StoredIntTest {

  private StoredIntController controller;
  private final String filePath = System.getProperty("java.io.tmpdir") + "/stored-int.txt";

  @BeforeEach
  void setUp() {
    // Supprimer le fichier avant chaque test pour repartir à zéro
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
    controller = new StoredIntController();
  }

  @Test
  void creates_and_reads_same_value() throws IOException {
    var response1 = controller.getStoredInt();
    String value1 = response1.getBody();

    System.out.println("Valeur 1 : " + value1);

    // Vérifier que c’est un nombre
    assertTrue(value1.matches("\\d+"), "La valeur doit être un nombre");

    var response2 = controller.getStoredInt();
    String value2 = response2.getBody();

    // Le deuxième appel retourne la même valeur
    assertEquals(value1, value2);

    // Vérifier que le fichier contient bien cette valeur
    String fileValue = Files.readString(new File(filePath).toPath()).trim();
    assertEquals(value1, fileValue);
  }
}
