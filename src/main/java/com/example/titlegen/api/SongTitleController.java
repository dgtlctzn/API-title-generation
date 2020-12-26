package com.example.titlegen.api;

import com.example.titlegen.dao.NounDao;
import com.example.titlegen.model.Nouns;
import com.example.titlegen.model.SongTitles;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/v1/songtitle")
@RestController
public class SongTitleController {

    @Autowired
    private NounDao nounDao;

    @PostMapping
    public @ResponseBody String addNewSongTitle (@RequestBody SongTitles songTitles,
                                                 @RequestHeader Map<String,String> headers) {
        if (!headers.get("authorization").equals("-2E]2n[Iy6<7Oma")) {
            return "Invalid Key";
        };
        Document doc = new Document(songTitles.getName());
        int nounsNum = 0;
        for (Sentence sentence : doc.sentences()) {
            for (int i = 0; i < sentence.length(); i++) {
                if (sentence.posTag(i).contains("NN")) {
                    Nouns noun = new Nouns();
                    noun.setNoun(sentence.word(i));
                    nounDao.save(noun);
                    nounsNum += 1;
                }
            }
        }
        return "Saved " + nounsNum + " nouns";
    }
//
//    @Autowired
//    public SongTitleController(SongTitleService songTitleService) {
//        this.songTitleService = songTitleService;
//    }
//
//    @PostMapping
//    public void addSongTitle(@Validated @NonNull @RequestBody SongTitle songTitle) {
//        songTitleService.addSongTitle(songTitle);
//    }
//
//    @GetMapping
//    public List<SongTitle> getAllSongTitles(@RequestParam("id") Optional<UUID> id) {
//        if (id.isPresent()) {
//            List<SongTitle> foundList = new ArrayList<>();
//            SongTitle foundId = songTitleService.getSongTitleById(id.get()).orElse(null);
//            foundList.add(foundId);
//            return foundList;
//        }
//        return songTitleService.getAllSongTitles();
//    }
//
//    @GetMapping
//    public List<SongTitles> getSongTitleById(@RequestParam("id") Optional<Integer> id) {
//        if (id.isPresent()) {
//            List<SongTitles> foundList = new ArrayList<>();
//            Optional<SongTitles> x = songTitledao.findById(id.get());
//            if (x.isPresent()) {
//                foundList.add(x.get());
//                return foundList;
//            }
//        }
//        return (List<SongTitles>) songTitledao.findAll();
//    }
//
//    @DeleteMapping(path = "{id}")
//    public void deleteSongTitleById(@PathVariable("id") UUID id) {
//        songTitleService.deleteSongTitle(id);
//    }
//
//    @PutMapping(path = "{id}")
//    public void updateSongTitle(@PathVariable("id") UUID id, @Validated @NonNull @RequestBody SongTitle songTitleToUpdate) {
//        songTitleService.updateSongTitle(id, songTitleToUpdate);
//    }
}