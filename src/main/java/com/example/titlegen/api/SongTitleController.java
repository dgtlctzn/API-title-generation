package com.example.titlegen.api;

import com.example.titlegen.dao.*;
import com.example.titlegen.model.*;
import com.google.gson.Gson;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.titlegen.format.formatString;

import java.util.*;

@RequestMapping("/api/v1/titlegen")
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
    @Autowired
    private StartupVerbsDao startupVerbsDao;
    @Autowired
    private BookNounDao bookNounDao;

    public Random random = new Random();

    // functions to randomly choose a word from the db by word type
    public String findNoun(String type) {
        Iterable<Nouns> nouns = nounDao.findAll();
        List<String> nounList = new ArrayList<>();
        for (Nouns noun : nouns) {
            if (!type.equals("") && noun.getType().equals(type)) {
                nounList.add(noun.getNoun());
            } else if (type.equals("")) {
                nounList.add(noun.getNoun());
            }
        }
        int nounLength = nounList.size();
        int randNounNum = this.random.nextInt(nounLength);
        return nounList.get(randNounNum);
    }

    public String findBookNoun(String type) {
        Iterable<BookNouns> bookNouns = bookNounDao.findAll();
        List<String> nounList = new ArrayList<>();
        for (BookNouns bookNoun : bookNouns) {
            if (!type.equals("") && bookNoun.getType().equals(type)) {
                nounList.add(bookNoun.getNoun());
            } else if (type.equals("")) {
                nounList.add(bookNoun.getNoun());
            }
        }
        int nounLength = nounList.size();
        int randNounNum = this.random.nextInt(nounLength);
        return nounList.get(randNounNum);
    }

    public String findStartupVerb() {
        Iterable<StartupVerbs> verbs = startupVerbsDao.findAll();
        List<String> verbList = new ArrayList<>();
        for (StartupVerbs verb : verbs) {
            verbList.add(verb.getVerb());
        }
        int verbLength = verbList.size();
        int randVerbNum = random.nextInt(verbLength);
        return verbList.get(randVerbNum);
    }

    public String findVerb(String type) {
        Iterable<Verbs> verbs = verbDao.findAll();
        List<String> verbList = new ArrayList<>();
        for (Verbs verb : verbs) {
            if (!type.equals("") && verb.getType().equals(type)) {
                verbList.add(verb.getVerb());
            } else if (type.equals("")) {
                verbList.add(verb.getVerb());
            }
        }
        int verbLength = verbList.size();
        int randVerbNum = random.nextInt(verbLength);
        return verbList.get(randVerbNum);
    }

    public String findPronoun(String type) {
        Iterable<Pronouns> pronouns = pronounDao.findAll();
        List<String> pronounList = new ArrayList<>();
        for (Pronouns pronoun : pronouns) {
            if (!type.equals("") && pronoun.getType().equals(type)) {
                pronounList.add(pronoun.getPronoun());
            } else if (type.equals("")) {
                pronounList.add(pronoun.getPronoun());
            }
        }
        int pronounLength = pronounList.size();
        int randVerbNum = random.nextInt(pronounLength);
        return pronounList.get(randVerbNum);
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
            formatString format = new formatString(true, null, null,"invalid token");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
        };
        if (headers.containsKey("type") && headers.get("type").equals("song")) {
            // title generation for songs
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
            // list of word types added to db
            List<String> namesSaved = new ArrayList<>();
            namesSaved.add(nounsNum + " nouns");
            namesSaved.add(verbsNum + " verbs");
            namesSaved.add(pronounsNum + " pronouns");
            namesSaved.add(determinersNum + " determiners");
            namesSaved.add(prepositionsNum + " prepositions");

            formatString format = new formatString(false, namesSaved.toArray(), "song", "words saved to database");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } else if (headers.containsKey("type") && headers.get("type").equals("startup")) {
            // title generation for startups
            int startupVerbNum = 0;
            Document doc = new Document(songTitles.getName());
            for (Sentence sentence : doc.sentences()) {
                for (int i = 0; i < sentence.length(); i++) {
                    StartupVerbs startupVerb = new StartupVerbs();
                    startupVerb.setVerb(sentence.word(i));
                    startupVerbsDao.save(startupVerb);
                    startupVerbNum += 1;
                }
            }
            formatString format = new formatString(false, null, "startup", String.format("%d words saved to database", startupVerbNum));
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else if (headers.containsKey("type") && headers.get("type").equals("book")) {
            // title generation for startups
            int bookNum = 0;
            Document doc = new Document(songTitles.getName());
            for (Sentence sentence : doc.sentences()) {
                for (int i = 0; i < sentence.length(); i++) {
                    String pos = sentence.posTag(i);
                    if (pos.contains("NN")) {
                        BookNouns bookNoun = new BookNouns();
                        bookNoun.setNoun(sentence.word(i));
                        bookNoun.setType(pos);
                        bookNounDao.save(bookNoun);
                        bookNum += 1;
                    }
                }
            }
            formatString format = new formatString(false, null, "book", String.format("%d words saved to database", bookNum));
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            formatString format = new formatString(true, null, null, "title type not specified in header");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllSongTitles(@RequestParam HashMap<String, String> params) {
        Gson gson = new Gson();

        int num;
        String type;
        try {
            type = params.get("type");

            if (params.containsKey("no")) {
                // allows for optional number of song titles to be generated
                try {
                    num = Integer.parseInt(params.get("no"));
                    if (num > 15) {
                        // limits song title generation to 15 titles
                        formatString format = new formatString(true, null, null, "Results queried must be 15 or less at a time");
                        String json = gson.toJson(format);
                        return new ResponseEntity<>(json, HttpStatus.NOT_ACCEPTABLE);
                    }
                } catch (Exception e) {
                    formatString format = new formatString(true, null, null, "invalid param");
                    String json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                }
            } else {
                num = 1;
            }
            formatString format;
            String json;

            switch(type) {
                case "song":
                    // song title generation
                    List<String> songTitles = new ArrayList<>();
                    for (int i = 0; i < num; i++) {
                        String songTitle;
                        int randomPos = this.random.nextInt(5);
                        switch(randomPos) {
                            case 0:
                                songTitle = "The " + findNoun("") + " " + findNoun("NNS");
                                break;
                            case 1:
                                songTitle = findVerb("VBG") + " " + findNoun("NNS");
                                break;
                            case 2:
                                songTitle = findVerb("") + " " + findPreposition() + " " + findNoun("");
                                break;
                            case 3:
                                songTitle = findPronoun("PRP") + " " + findVerb("VBD") + " " + findPronoun("PRP$") + " " + findNoun("NNP");
                                break;
                            default:
                                songTitle = findNoun("") + " " + findPreposition() + " " + findPronoun("PRP$") + " " + findNoun("");
                                break;
                        }
                        songTitles.add(songTitle);
                    }


                    format = new formatString(false, songTitles.toArray(), "song", String.format("%d song title(s) generated", songTitles.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                case "startup":
                    // startup title generation
                    List<String> startups = new ArrayList<>();

                    List<String> vowels = new LinkedList<>();
                    vowels.add("a");
                    vowels.add("e");
                    vowels.add("i");
                    vowels.add("o");
                    vowels.add("u");

                    for (int i = 0; i < num; i++) {
                        String modified = "";
                        String name = findStartupVerb();
                        int randomStartup = this.random.nextInt(4);

                        switch(randomStartup) {
                            case 0:
                                modified += name;
                                modified += ".io";
                                startups.add(modified);
                                break;
                            case 1:
                                modified += name;
                                modified += ".ai";
                                startups.add(modified);
                                break;
                            case 2:
                                String[] startup = name.split("");
                                String lastLetter = startup[startup.length - 1];
                                if (lastLetter.equals("y")) {
                                    modified += name.replace("y", "i");
                                } else if (!vowels.contains(lastLetter)){
                                    modified += name + "ify";
                                } else {
                                    modified += name + "X";
                                }
                                startups.add(modified);
                                break;
                            case 3:
                                StringBuilder c = new StringBuilder();
                                String[] arr = name.split("");
                                boolean stop = false;
                                if (arr.length > 6) {
                                    for (int j = arr.length - 1; j >= 0; j--) {
                                        if (!stop && vowels.contains(arr[j]) && j != arr.length - 1) {
                                            stop = true;
                                        } else {
                                            c.append(arr[j]);
                                        }
                                    }
                                    modified += c.reverse().toString();
                                } else {
                                    c.append("e-");
                                    c.append(name);
                                    modified += c.toString();
                                }
                                startups.add(modified);
                                break;
                        }
                    }
                    format = new formatString(false, startups.toArray(), "startup", String.format("%d startup title(s) generated", startups.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                case "book":
                    // book title generation
                    String booktitle;
                    List<String> books = new ArrayList<>();

                    for (int i = 0; i < num; i++) {
                        int randomBook = this.random.nextInt(4);

                        switch(randomBook) {
                            case 0:
                                booktitle = findPronoun("PRP$") + " " + findBookNoun("");
                                books.add(booktitle);
                                break;
                            case 1:
                                booktitle = findBookNoun("") + " " + findPreposition() + " " + findPronoun("PRP$") + " " + findBookNoun("NNS");
                                books.add(booktitle);
                                break;
                            case 2:
                                booktitle = findVerb("VBG") + " " + findPreposition() + " " + findDeterminer() + " " + findBookNoun("NNS");
                                books.add(booktitle);
                                break;
                            case 3:
                                booktitle = "The " + findBookNoun("NNP") + "'s " + findBookNoun("");
                                books.add(booktitle);
                                break;
                        }

                    }
                    format = new formatString(false, books.toArray(), "book", String.format("%d book title(s) generated", books.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                default:
                    format = new formatString(true, null, null, "invalid title type");
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
            formatString format = new formatString(true, null, null, "missing title type in params");
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

}