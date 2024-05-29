package com.sertann.service;

import com.sertann.models.Reels;
import com.sertann.models.User;
import com.sertann.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class ReelsServiceImplementation implements ReelsService {

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private UserService userService;


    @Override
    public Reels createReel(Reels reel, User user) {

        Reels createReel = new Reels();
        createReel.setTitle(reel.getTitle());
        createReel.setUser(user);
        createReel.setVideo(reel.getVideo());
        createReel.setCreatedAt(LocalDateTime.now());
        return reelsRepository.save(createReel);

    }



    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUserReels(Integer userId) throws Exception {

        userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);

    }

    @Override
    public List<Reels> findReelsByUserId(Integer userId) {
        List<Reels> reels = reelsRepository.findReelsByUserId(userId);
        reels.sort(Comparator.comparing(Reels::getCreatedAt).reversed());

        return reels;
    }

    @Override
    public Reels findReelsById(Integer reelsId) throws Exception {
        Optional<Reels> otp = reelsRepository.findById(reelsId);
        if(otp.isEmpty()){
            throw new Exception("Reels does not found with id " + reelsId);
        }
        return otp.get();

    }

    @Override
    public String deleteReels(Integer reelsId, Integer reqUserId) throws Exception {
        Reels reels = findReelsById(reelsId);
        User user = userService.findUserById(reqUserId);
        if(reels.getUser().getId() != user.getId()){
            throw new Exception("You can't delete another users reels");

        }
        reelsRepository.delete(reels);

        return "Reels deleted successfully";

    }



    @Override
    public List<Reels> findLastReelsByUserId(Integer userId) {
        List<Reels> reels = reelsRepository.findReelsByUserId(userId);
        if (reels != null && !reels.isEmpty()) {
            // Son reels'i bulmak için listeyi tarihe göre sıralayın
            reels.sort(Comparator.comparing(Reels::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));
            // Listede en sonuncusunu içeren bir alt liste döndürün
            return Collections.singletonList(reels.get(0));
        }
        return Collections.emptyList(); // Eğer reels yoksa boş bir liste döndürün
    }
}
