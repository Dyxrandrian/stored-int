package school.hei.school.endpoint.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.school.PojaGenerated;

@PojaGenerated
@RestController
public class StoredIntController {

    // 🔁 Compatible Windows + Lambda
    private static final String FILE_PATH = System.getProperty("java.io.tmpdir") + "/stored-int.txt";

    @GetMapping("/stored-int")
    public ResponseEntity<String> getStoredInt() {
        File file = new File(FILE_PATH);

        try {
            if (file.exists()) {
                String existing = Files.readString(file.toPath()).trim();
                return ResponseEntity.ok(existing);
            } else {
                int random = new Random().nextInt(1000);
                Files.writeString(file.toPath(), String.valueOf(random));
                return ResponseEntity.ok(String.valueOf(random));
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur de lecture/écriture : " + e.getMessage());
        }
    }
}
