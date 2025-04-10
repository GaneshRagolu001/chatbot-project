package com.ganesh.projects.Ai.chatbot.repository;

import com.ganesh.projects.Ai.chatbot.entity.CodeRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CodeRequestRepository extends JpaRepository<CodeRequest, Long> {

    @Query("SELECT c FROM CodeRequest c WHERE c.user.id = :userId")
    List<CodeRequest> findAllByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CodeRequest WHERE user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
