package com.example.demo.music;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MusicService {
    private final MusicRepository musicRepository;
    public List<Music> MusicList = new ArrayList<>();


    @Transactional(readOnly = true)
    public Music musicFindByTitle(String title){
        return musicRepository.findByTitle(title)
                .orElseGet(() -> null);
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public List<Music> musicFindByTitleContain(String title){
        List<Music> music = musicRepository.findByTitleContains(title).orElseGet(() -> {
            return new ArrayList<>();
        });
        return music; // 정상
    }


    @Transactional(readOnly = true)
    public List<Music> musicFindByTitleAndSingerContain(String keyword){
        List<Music> music = musicRepository.findByTitleContainsOrSingerContains(keyword,keyword).orElseGet(() -> {
            return new ArrayList<>();
        });


        return music; // 정상
    }


    @Transactional
    public void save(Music music){
        musicRepository.save(music);
        // 해당 id값에 해당하는 Storage를 Return
    }


    @Transactional
    public int deleteTestDataAfter() {

        for (Long i =  musicRepository.findFirstByOrderByIdDesc().get().getId(); i > MusicList.size(); i--) {
            musicRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        return HttpStatus.OK.value();


    }
    @PostConstruct
    public void init() {


        /**
         *  Music Test Data
         */
        MusicList.add(new Music("Jail", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc","Take what you want~" +
                "Take everything~" +
                "Take what you want~" +
                "Take what you want~" +
                "Better that I change my number so you can't explain~" +
                "Violence in the night, violence in the night~" +
                "Priors, priors, do you have any priors?~" +
                "Well, that one time, I'll be honest~" +
                "I'll be honest, we all liars~" +
                "Let it go~" +
                "I'll be honest, we all liars~" +
                "I'll be honest, we all liars~" +
                "I'm pulled over and I got priors~" +
                "Guess we goin' down, guess who's goin' to jail?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~" +
                "Don't you curse at me on text, why you try to hit the flex?~" +
                "I hold up, like, \"What?\" I scroll, I scroll up like, \"Next\"~" +
                "Guess who's gettin' exed? Like, next~" +
                "Guess who's gettin' exed?~" +
                "You made a choice, that's your bad~" +
                "Single life ain't so bad, but we ain't finna go there~" +
                "Something's off, I'll tell you why~" +
                "Guess who's goin' to jail tonight?~" +
                "What a grand plan to sell you out~" +
                "I could scream and shout, let it out~" +
                "I'll be honest, we all liars~" +
                "I'll be honest, we all liars~" +
                "I'm pulled over and I got priors~" +
                "Guess we goin' down, guess who's goin' to jail?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~" +
                "God in my cells, that's my celly~" +
                "Made in the image of God, that's a selfie~" +
                "Pray five times a day, so many felonies~" +
                "Who gon' post my bail? Lord, help me~" +
                "Hol' up, Donda, I'm with your baby when I touch back road~" +
                "Told him, \"Stop all of that red cap, we goin' home\"~" +
                "Not me with all of these sins, casting stones~" +
                "This might be the return of The Throne, Throne~" +
                "Hova and Yeezus, like Moses and Jesus~" +
                "You are not in control of my thesis~" +
                "You already know what I think 'bout thinkpieces~" +
                "'Fore you ask, he already told you who he think he is~" +
                "Don't try to jail my thoughts and think precincts~" +
                "I can't be controlled with program and presets~" +
                "Reset~" +
                "On my cell, in my cell tonight~" +
                "Don't have to see you to touch you~" +
                "This is what braille look like, it's on sight, huh, huh, huh~" +
                "If they take me to jail, call my girl, tell her send my mail~" +
                "We know what Hell look like, still, it's a hell of a life, yikes~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~"));
        MusicList.add(new Music("Jail Pt.2", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc","Take what you want~" +
                "Take everything~" +
                "Take what you want~" +
                "Take what you want~" +
                "Better that I change my number so you can't explain~" +
                "Violence in the night, violence in the night~" +
                "Priors, priors, do you have any product?~" +
                "Well, that one time, I'll be honest~" +
                "I'll be honest, we all liars, let it go~" +
                "I'll be honest, we all liars~" +
                "I'll be honest, we all liars~" +
                "I'm pulled over and I got priors (priors)~" +
                "Guess we goin' down, guess who's goin' to jail?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~" +
                "Don't you curse at me on text, why you try to hit the flex?~" +
                "I hold up, like, \"What?\" I scroll, I scroll up like, \"Next\"~" +
                "Guess who's getting X'ed? Like, next~" +
                "Guess who's getting X'ed?~" +
                "You made a choice, that's your bad, single life ain't so bad~" +
                "But we ain't finna go there~" +
                "Something's off, I'll tell you why~" +
                "Guess who's goin' to jail tonight~" +
                "What a grand plan to sell you out~" +
                "I could scream and shout, let it out~" +
                "I'll be honest, we all liars~" +
                "I'll be honest, we all liars~" +
                "I'm pulled over and I got priors~" +
                "Guess we goin' down, guess who's goin' to jail?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~" +
                "Man, tell them haters open up the jail~" +
                "(Open up the jail)~" +
                "And you can tell my baby mamas, \"Get the bail money\"~" +
                "(Bail me)~" +
                "I said one thing they ain't like~" +
                "Threw me out like they ain't care for me~" +
                "Threw me out like I'm garbage, huh?~" +
                "And that food that y'all took off my table~" +
                "You know that feed my daughters, huh? (Mmm)~" +
                "But I ain't really mad 'cause when I look at it~" +
                "I'm getting them snakes up out my grass and n- that's a good habit~" +
                "But I'm ready for war, let's get at 'em~" +
                "And teaming up ain't gon' help 'em~" +
                "'Cause beating the odds too deep, just me and God, sh-, n-, I'm good at it~" +
                "Matter of fact, I'm great at it, my cell phone back at it~" +
                "I know these people gon' try to tell me how to talk~" +
                "Don't know what I seen or what I was taught~" +
                "My momma worked two or three jobs~" +
                "To take care of three of her kids, my uncles watched~" +
                "Yeah, we was raised by the crack addicts~" +
                "Mmm, raised by the drug dealers, killers, and the junkies (junkies)~" +
                "Mama couldn't save us 'cause she had to get the...~" +
                "Mama couldn't save us 'cause she had to get the money~" +
                "Feel like your world falling, getting too hard to catch it, ain't it?~" +
                "You and your girl arguin', you don't like how she actin' lately~" +
                "Giving it everything that you can give and you don't get half the patience~" +
                "You was busy hustlin', the things come with your hustle~" +
                "They got in her head, corrupted her~" +
                "Yeah, that's probably what happened, ain't it?~" +
                "Large amount of capital, invested in myself~" +
                "Underground, I ain't even have a basement, I read the affidavit~" +
                "Let's see what it is with you~" +
                "Only thing I did to you~" +
                "Was always keep it real and true~" +
                "Guilty, guess they 'gon have to take me~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "Guess who's goin' to jail tonight?~" +
                "God gon' post my bail tonight~"));
        MusicList.add(new Music("Circles", "Post Malone", "http://image.yes24.com/goods/79640397/XL","We couldn't turn around~" +
                "'Til we were upside down~" +
                "I'll be the bad guy now~" +
                "But no, I ain't too proud~" +
                "I couldn't be there~" +
                "Even when I tried~" +
                "You don't believe it~" +
                "We do this every time~" +
                "Seasons change and our love went cold~" +
                "Feed the flame 'cause we can't let it go~" +
                "Run away, but we're running in circles~" +
                "Run away, run away~" +
                "I dare you to do something~" +
                "I'm waiting on you again~" +
                "So I don't take the blame~" +
                "Run away, but we're running in circles~" +
                "Run away, run away, run away~" +
                "Let go~" +
                "I got a feeling that it's time to let go~" +
                "I say so~" +
                "I knew that this was doomed from the get-go~" +
                "You thought that it was special, special~" +
                "But it was just the sex though, the sex though~" +
                "And I still hear the echoes (the echoes)~" +
                "I got a feeling that it's time to let it go, let it go~" +
                "Seasons change and our love went cold~" +
                "Feed the flame 'cause we can't let it go~" +
                "Run away, but we're running in circles~" +
                "Run away, run away~" +
                "I dare you to do something~" +
                "I'm waiting on you again~" +
                "So I don't take the blame~" +
                "Run away, but we're running in circles~" +
                "Run away, run away, run away~" +
                "Maybe you don't understand what I'm going through~" +
                "It's only me~" +
                "What you got to lose?~" +
                "Make up your mind, tell me~" +
                "What are you gonna do?~" +
                "It's only me~" +
                "Let it go~" +
                "Seasons change and our love went cold~" +
                "Feed the flame 'cause we can't let it go~" +
                "Run away, but we're running in circles~" +
                "Run away, run away~" +
                "I dare you to do something~" +
                "I'm waiting on you again~" +
                "So I don't take the blame~" +
                "Run away, but we're running in circles~" +
                "Run away, run away, run away~"));
        MusicList.add(new Music("아로하", "조정석", "https://image.bugsm.co.kr/album/images/500/203142/20314288.jpg","어두운 불빛아래 촛불 하나~" +
                "와인 잔에 담긴 약속하나~" +
                "항상 너의 곁에서 널 지켜줄거야~" +
                "날 믿어준 너였잖아~" +
                "나 바라는 건 오직 하나~" +
                "영원한 행복을 꿈꾸지만~" +
                "화려하지 않아도 꿈같진 않아도~" +
                "너만 있어주면 돼~" +
                "걱정 마 (I believe)~" +
                "언제나 (I believe)~" +
                "이 순간을 잊지 않을게~" +
                "내 품에 (I believe)~" +
                "안긴 너의 미소가~" +
                "영원히 빛을 잃어 가지 않게~" +
                "Cause your love is so sweet~" +
                "You are my everything~" +
                "첫날 밤에 단 꿈에 젖어~" +
                "하는 말이 아냐 난 변하지 않아~" +
                "오직 너만 바라볼거야 oh~" +
                "You're light of my life~" +
                "You are the one in my life~" +
                "내 모든걸 다 잃는데도~" +
                "후회하지 않아~" +
                "오직 너를 위한~" +
                "변하지 않는 사랑으로~" +
                "나 바라는 건 오직 하나~" +
                "영원한 행복을 꿈꾸지만~" +
                "화려하지 않아도 꿈같진 않아도~" +
                "너만 있어주면 돼~" +
                "약속해 (I believe)~" +
                "힘들 땐 (I believe)~" +
                "너의 그늘이 되어줄게~" +
                "내품에 (I believe)~" +
                "안긴 너의 미소가~" +
                "영원히 빛을 잃어 가지 않게~" +
                "Cause your love is so sweet~" +
                "You are my everything~" +
                "첫날 밤에 단 꿈에 젖어~" +
                "하는 말이 아냐 난 변하지 않아~" +
                "오직 너만 바라볼거야 oh~" +
                "You're light of my life~" +
                "You are the one in my life~" +
                "내 모든걸 다 잃는데도~" +
                "후회하지 않아~" +
                "오직 너를 위한~" +
                "변하지 않는 사랑으로~" +
                "You're light of my life~" +
                "You are the one in my life~" +
                "내 모든걸 다 잃는데도~" +
                "후회하지 않아~" +
                "오직 너를 위한~" +
                "변하지 않는 사랑으로~" +
                "All I ever want is your love"));
        MusicList.add(new Music("TestTitle", "Jail", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));


        for (Music music : MusicList) {
            Music Check = musicRepository.findByTitle(music.getTitle()).orElseGet(() -> {
                return new Music();
            });
            if (Check.getTitle() == null) {
                musicRepository.save(music);
                log.info("새 노래 생성");
            }
            else log.info("이미 노래가 생성 되어 있습니다.");
        }
    }
}
