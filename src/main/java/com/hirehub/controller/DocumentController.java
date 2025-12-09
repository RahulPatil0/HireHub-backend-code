package com.hirehub.controller;

import com.hirehub.model.Document;
import com.hirehub.model.User;
import com.hirehub.repository.DocumentRepository;
import com.hirehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<?> uploadDocument(
            @PathVariable Long userId,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/" + fileName);
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, file.getBytes());

        Document doc = Document.builder()
                .type(type)
                .url(uploadPath.toString())
                .user(user)
                .build();

        documentRepository.save(doc);

        return ResponseEntity.ok("Uploaded successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDocuments(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Document> documents = documentRepository.findByUser(user);
        return ResponseEntity.ok(documents);
    }
}