package com.greenatom.utils.mapper;

import java.util.HashMap;

public class TranslateRusToEng {

    private TranslateRusToEng() { }

    private static final HashMap<String,String> letters;

    static {
        letters = new HashMap<>();
        letters.put("а","a");
        letters.put("б","b");
        letters.put("ц","ts");
        letters.put("к","k");
        letters.put("д","d");
        letters.put("и","e");
        letters.put("г","g");
        letters.put("ш","sh");
        letters.put("ж","j");
        letters.put("л","l");
        letters.put("м","m");
        letters.put("н","n");
        letters.put("о","o");
        letters.put("п","p");
        letters.put("р","r");
        letters.put("с","s");
        letters.put("т","t");
        letters.put("у","u");
        letters.put("в","v");
        letters.put("ы","y");
        letters.put("й","y");
        letters.put("з","z");
        letters.put("ч","ch");
        letters.put("ф","f");
        letters.put("х","kh");
        letters.put("я","y");
        letters.put("ю","y");
        letters.put("ь"," ");
        letters.put("е","e");
    }

    public static String translateFromRusToEng(String word){
        StringBuilder translateWord = new StringBuilder();
        for(int i = 0; i < word.length(); i ++){
            String lowerCase = String.valueOf(word.charAt(i)).toLowerCase();
            if(letters.containsKey(lowerCase)){
                if (i == 0){
                    translateWord.append(letters.get(lowerCase).toUpperCase());
                    continue;
                }
                translateWord.append(letters.get(lowerCase));
                continue;
            }
            translateWord.append(word.charAt(i));
        }
        return translateWord.toString();
    }
}
