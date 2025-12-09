package com.hirehub.repository;

import com.hirehub.model.Document;
import com.hirehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUser(User user);   // <-- add this method
}
