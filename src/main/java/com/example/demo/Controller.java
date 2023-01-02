package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class Controller {
    @Autowired
    public Controller(UserRepository userRepository,
                      MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id,
                                     @RequestParam(value = "messageId", defaultValue = "-1") int messageId){
        if(messageId > 0){
            var messages = messageRepository.findById(messageId);
            var message = messages.orElse(null);
            if(message == null){
                return ResponseEntity.ok().build();
            } else if (message.getValidUntil().before(new Date(System.currentTimeMillis()))) {
                return ResponseEntity.status(403).body("Время доступа кончилось.");
            } else if((message.getAccessModifier() == AccessModifier.Private
                    && message.getAuthor().getId() == id)
                    || (message.getAccessModifier() == AccessModifier.Public)){
                return ResponseEntity.ok(message.getMessage());
            }
        }else{
            var user = userRepository.findById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(403).body("Нет доступа.");
    }
}
