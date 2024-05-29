package com.sertann.service;

import com.sertann.models.Post;
import com.sertann.models.User;
import com.sertann.repository.PostRepository;
import com.sertann.repository.UserRepository;
import com.sertann.util.PostServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;


    @Autowired
    UserRepository userRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {

        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);


        return postRepository.save(newPost);

    }

    //Delete User Post
    @Override
    public String deletePost(Integer postId, Integer reqUserId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(reqUserId);
        if (post.getUser().getId() != user.getId()) {
            throw new Exception("You can't delete another users post");
        }

        postRepository.delete(post);

        return "Post deleted successfully";


    }

    //Find User Post
    @Override
    public List<Post> findPostByUserId(Integer userId) {

        List<Post> posts = postRepository.findPostByUserId(userId);
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());


        return posts;

    }

    //Find Post id
    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> otp = postRepository.findById(postId);
        if (otp.isEmpty()) {
            throw new Exception("Post does not found with id " + postId);
        }
        return otp.get();
    }

    @Override
    public List<Post> findAllPost() {


        //Get All Post
        List<Post> posts = postRepository.findAll();
        posts.forEach(post -> {
            String elapsedTime = PostServiceUtil.calculateElapsedTime(post.getCreatedAt());
            post.setElapsedTime(elapsedTime);
        });
        //Get the most recent posts by sorting by date
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());


        return posts;
    }
    //Saved Post
    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getSavedByUsers().contains(user)) {
            throw new Exception("You have already saved this post.");
        } else {
            post.getSavedByUsers().add(user);
        }

        return postRepository.save(post);
    }

    //Liked post
    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }

        String elapsedTime = PostServiceUtil.calculateElapsedTime(post.getCreatedAt());
        post.setElapsedTime(elapsedTime);

        return postRepository.save(post);


    }


    //Unsaved Post
    @Override
    public Post unsavedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getSavedByUsers().contains(user)) {
            post.getSavedByUsers().remove(user);
        } else {
            throw new Exception("You have not saved this post yet.");
        }

        return postRepository.save(post);
    }


    //Unliked Post
    @Override
    public Post unlikePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            throw new Exception("You have not liked this post yet.");
        }

        String elapsedTime = PostServiceUtil.calculateElapsedTime(post.getCreatedAt());
        post.setElapsedTime(elapsedTime);
        return postRepository.save(post);
    }


    //User Post Count
    @Override
    public int getUserPostCount(Integer userId) {

        List<Post> usersPost = postRepository.findPostByUserId(userId);
        return usersPost.size();
    }

    //User Liked Count
    @Override
    public int getPostLikedCount(Integer postId) throws Exception {
        Post post = findPostById(postId);
        return post.getLiked().size();
    }

    //User Saved Post Id

    @Override
    public List<Post> getSavedPostsByUserId(Integer userId) {
        return postRepository.findBySavedByUsersId(userId);
    }

    //User Liked Post Id
    @Override
    public List<Post> getLikedPostsByUserId(Integer userId) {
        return postRepository.findLikedPostsByUserId(userId);
    }




}
