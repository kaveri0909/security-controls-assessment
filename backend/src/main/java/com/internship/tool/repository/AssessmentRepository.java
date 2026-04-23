package com.internship.tool.repository;

import com.internship.tool.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    // 🔍 1. Filter by Status
    List<Assessment> findByStatus(String status);


    // 🔍 2. Search by Name or Description (LIKE search)
    @Query("SELECT a FROM Assessment a " +
           "WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Assessment> searchByKeyword(@Param("keyword") String keyword);


    // 🔍 3. Find by Date Range (created_at)
    @Query("SELECT a FROM Assessment a " +
           "WHERE a.createdAt BETWEEN :startDate AND :endDate")
    List<Assessment> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    // 🔍 4. Combined Filter (Status + Date Range)
    @Query("SELECT a FROM Assessment a " +
           "WHERE a.status = :status " +
           "AND a.createdAt BETWEEN :startDate AND :endDate")
    List<Assessment> findByStatusAndDateRange(
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    // 🔍 5. Optional: Sort by Score (High to Low)
    @Query("SELECT a FROM Assessment a ORDER BY a.score DESC")
    List<Assessment> findTopByScore();
}