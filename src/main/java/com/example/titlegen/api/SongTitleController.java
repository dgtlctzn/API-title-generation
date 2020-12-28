package com.example.titlegen.api;

import com.example.titlegen.dao.*;
import com.example.titlegen.model.*;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/v1/songtitle")
@RestController
public class SongTitleController {

    @Autowired
    private NounDao nounDao;
    @Autowired
    private VerbDao verbDao;
    @Autowired
    private PronounDao pronounDao;
    @Autowired
    private DeterminerDao determinerDao;
    @Autowired
    private PrepositionDao prepositionDao;

    @PostMapping
    public @ResponseBody String addNewSongTitle (@RequestBody SongTitles songTitles,
                                                 @RequestHeader Map<String,String> headers) {
        if (!headers.get("authorization").equals("-2E]2n[Iy6<7Oma")) {
            return "Invalid Key";
        };
        Document doc = new Document(songTitles.getName());
        int nounsNum = 0;
        int verbsNum = 0;
        int pronounsNum = 0;
        int determinersNum = 0;
        int prepositionsNum = 0;
        for (Sentence sentence : doc.sentences()) {
            for (int i = 0; i < sentence.length(); i++) {
                String pos = sentence.posTag(i);
                if (pos.contains("NN")) {
                    // save nouns "NN" to noun table
                    Nouns noun = new Nouns();
                    noun.setNoun(sentence.word(i));
                    nounDao.save(noun);
                    nounsNum += 1;
                } else if (pos.contains("VB")) {
                    // save verbs "VB" to verb table
                    Verbs verb = new Verbs();
                    verb.setVerb(sentence.word(i));
                    verbDao.save(verb);
                    verbsNum += 1;
                } else if (pos.contains("PRP")) {
                    // save pronouns "PRP" to pronoun table
                    Pronouns pronoun = new Pronouns();
                    pronoun.setPronoun(sentence.word(i));
                    pronounDao.save(pronoun);
                    pronounsNum += 1;
                } else if (pos.contains("DT")) {
                    // save determiners "DT" to determiner table
                    Determiners determiner = new Determiners();
                    determiner.setDeterminer(sentence.word(i));
                    determinerDao.save(determiner);
                    determinersNum += 1;
                } else if (pos.contains("IN")) {
                    // save prepositions "IN" to preposition table
                    Prepositions preposition = new Prepositions();
                    preposition.setPreposition(sentence.word(i));
                    prepositionDao.save(preposition);
                    prepositionsNum += 1;
                }
            }
        }
        return "Saved " + nounsNum + " nouns\n" +
                verbsNum + " verbs\n" +
                pronounsNum + " pronouns\n" +
                determinersNum + " determiners\n" +
                prepositionsNum + " prepositions";
    }

    @GetMapping
    public String getAllSongTitles() {
        Random random = new Random();

        Iterable<Nouns> nouns = nounDao.findAll();
        int nounLength = (int)nouns.spliterator().getExactSizeIfKnown();
        int randNounNum = random.nextInt(nounLength);
        List<String> nounList = new ArrayList<>();
        for (Nouns noun : nouns) {
            nounList.add(noun.getSongTitle());
        }

        Iterable<Verbs> verbs = verbDao.findAll();
        int verbLength = (int)verbs.spliterator().getExactSizeIfKnown();
        int randVerbNum = random.nextInt(verbLength);
        List<String> verbList = new ArrayList<>();
        for (Verbs verb : verbs) {
            verbList.add(verb.getVerb());
        }
        return verbList.get(randVerbNum) + " " + nounList.get(randNounNum);
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