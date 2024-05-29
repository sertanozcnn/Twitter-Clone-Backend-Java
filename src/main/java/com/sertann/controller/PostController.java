package com.sertann.controller;


import com.sertann.models.Post;
import com.sertann.models.User;
import com.sertann.response.ApiResponse;
import com.sertann.service.PostService;
import com.sertann.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;


    //User Create Post
    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Post post) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post createdPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);

    }


    //Post Deleted
    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer postId
    ) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message, true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    //Find post ıd
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {

        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);

    }

    //Find Users Post
    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) {

        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

    }

    //Get All POst
    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> posts = postService.findAllPost();

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    //User saved post
    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.findPostById(postId);


        if (post.getSavedByUsers().contains(reqUser)) {
            throw new Exception("You have already liked this post.");
        }

        post = postService.savedPost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);

    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt
    )
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.findPostById(postId);

        if (post.getLiked().contains(reqUser)) {
            throw new Exception("You have already liked this post.");
        }

        post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/posts/unsave/{postId}")
    public ResponseEntity<Post> unsavedPostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.unsavedPost(postId, reqUser.getId());

        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/api/posts/unlike/{postId}")
    public ResponseEntity<Post> unlikePostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.unlikePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }


    @GetMapping("/api/users/postcount/{userId}")
    public ResponseEntity<Integer> getUserPostCount(
            @PathVariable Integer userId) {
        int postCount = postService.getUserPostCount(userId);
        return new ResponseEntity<>(postCount, HttpStatus.OK);
    }

    @GetMapping("/api/posts/likedcount/{postId}")
    public ResponseEntity<Integer> getPostLikedCount(
            @PathVariable Integer postId
    ) throws Exception {
        int likedCount = postService.getPostLikedCount(postId);

        return new ResponseEntity<>(likedCount, HttpStatus.OK);
    }


    @GetMapping("/api/posts/saved-post/{userId}")
    public ResponseEntity<List<Post>> getSavedPostsByUser(

            @PathVariable Integer userId,
            @RequestHeader("Authorization") String jwt) {

        List<Post> savedPosts = postService.getSavedPostsByUserId(userId);
        savedPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return new ResponseEntity<>(savedPosts, HttpStatus.OK);
    }


    @GetMapping("/api/posts/liked-posts/{userId}")
    public ResponseEntity<List<Post>> getLikedPostsByUser(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String jwt
    ) {
        List<Post> likedPosts = postService.getLikedPostsByUserId(userId);
        likedPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return new ResponseEntity<>(likedPosts, HttpStatus.OK);
    }


}
