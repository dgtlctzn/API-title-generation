package com.example.titlegen.api;

import com.example.titlegen.dao.*;
import com.example.titlegen.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.titlegen.format.formatString;

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

    public Random random = new Random();

    // functions to randomly choose a word from the db by word type
    public String findNoun(Boolean plural) {
        Iterable<Nouns> nouns = nounDao.findAll();
        List<String> nounList = new ArrayList<>();
        for (Nouns noun : nouns) {
            if (plural && noun.getType().equals("NNS")) {
                nounList.add(noun.getNoun());
            } else if (!plural) {
                nounList.add(noun.getNoun());
            }
        }
        int nounLength = nounList.size();
        int randNounNum = this.random.nextInt(nounLength);
        return nounList.get(randNounNum);
    }

    public String findVerb(Boolean gerund) {
        Iterable<Verbs> verbs = verbDao.findAll();
        List<String> verbList = new ArrayList<>();
        for (Verbs verb : verbs) {
            if (gerund && verb.getType().equals("VBG")) {
                verbList.add(verb.getVerb());
            } else if (!gerund) {
                verbList.add(verb.getVerb());
            }
        }
        int verbLength = verbList.size();
        int randVerbNum = random.nextInt(verbLength);
        return verbList.get(randVerbNum);
    }

    public String findPronoun() {
        Iterable<Pronouns> pronouns = pronounDao.findAll();
        int pronounLength = (int)pronouns.spliterator().getExactSizeIfKnown();
        int randPronounNum = random.nextInt(pronounLength);
        List<String> pronounList = new ArrayList<>();
        for (Pronouns pronoun : pronouns) {
            pronounList.add(pronoun.getPronoun());
        }
        return pronounList.get(randPronounNum);
    }

    public String findDeterminer() {
        Iterable<Determiners> determiners = determinerDao.findAll();
        int determinerLength = (int)determiners.spliterator().getExactSizeIfKnown();
        int randDeterminerNum = random.nextInt(determinerLength);
        List<String> determinerList = new ArrayList<>();
        for (Determiners determiner : determiners) {
            determinerList.add(determiner.getDeterminer());
        }
        return determinerList.get(randDeterminerNum);
    }

    public String findPreposition() {
        Iterable<Prepositions> prepositions = prepositionDao.findAll();
        int prepositionLength = (int)prepositions.spliterator().getExactSizeIfKnown();
        int randPrepositionNum = random.nextInt(prepositionLength);
        List<String> prepositionList = new ArrayList<>();
        for (Prepositions preposition : prepositions) {
            prepositionList.add(preposition.getPreposition());
        }
        return prepositionList.get(randPrepositionNum);
    }

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> addNewSongTitle (@RequestBody SongTitles songTitles,
                                                 @RequestHeader Map<String,String> headers) {
        if (!headers.get("authorization").equals("-2E]2n[Iy6<7Oma")) {
            formatString format = new formatString(true, null, "invalid token");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
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
                    noun.setType(sentence.posTag(i));
                    nounDao.save(noun);
                    nounsNum += 1;
                } else if (pos.contains("VB")) {
                    // save verbs "VB" to verb table
                    Verbs verb = new Verbs();
                    verb.setVerb(sentence.word(i));
                    verb.setType(sentence.posTag(i));
                    verbDao.save(verb);
                    verbsNum += 1;
                } else if (pos.contains("PRP$")) {
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
        String savedWords = "Saved " + nounsNum + " nouns\n" +
                verbsNum + " verbs\n" +
                pronounsNum + " pronouns\n" +
                determinersNum + " determiners\n" +
                prepositionsNum + " prepositions";
        formatString format = new formatString(false, null, "words added to database");
        Gson gson = new Gson();
        String json = gson.toJson(format);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllSongTitles(@RequestParam HashMap<String, String> params) {
        List<String> songTitles = new ArrayList<>();
        int num;
        if (params.containsKey("no")) {
            num = Integer.parseInt(params.get("no"));
        } else {
            num = 1;
        }
        for (int i = 0; i < num; i++) {
            String songTitle;
            int randomPos = this.random.nextInt(4);
            switch(randomPos) {
                case 0:
                    songTitle = findVerb(false) + " " + findNoun(false);
                    break;
                case 1:
                    songTitle = findVerb(true) + " " + findDeterminer() + " " + findNoun(true);
                    break;
                case 2:
                    songTitle = findVerb(false) + " " + findPreposition() + " " + findNoun(false);
                    break;
                default:
                    songTitle = findNoun(false) + " " + findPreposition() + " " + findPronoun() + " " + findNoun(false);
                    break;
            }
            songTitles.add(songTitle);
        }


        formatString format = new formatString(false, songTitles.toArray(), "song title generated");
        Gson gson = new Gson();
        String json = gson.toJson(format);
        return new ResponseEntity<>(json, HttpStatus.OK);
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