package com.sertann.repository;

import com.sertann.models.Post;
import com.sertann.models.Reels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels,Integer> {

    List<Reels> findByUserId(Integer userId);

    @Query("select r from Reels r where r.user.id = :userId")
    List<Reels> findReelsByUserId(Integer userId);






}
