package br.com.brenoborges.finflow.modules.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Object> health() {

        try {
            return ResponseEntity.ok().body("Funcionando");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não funcionou");
        }
    }
}
