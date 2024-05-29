package com.sertann.repository;

import com.sertann.models.Post;
import com.sertann.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {


    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findPostByUserId(Integer userId);

    @Query("SELECT p FROM Post p JOIN p.savedByUsers u WHERE u.id = :userId")
    List<Post> findBySavedByUsersId(Integer userId);

    @Query("SELECT p FROM Post p JOIN p.liked l WHERE l.id = :userId")
    List<Post> findLikedPostsByUserId(Integer userId);






}
