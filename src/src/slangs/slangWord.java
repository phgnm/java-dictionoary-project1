package slangs;

import java.io.*;
import java.util.*;

public class slangWord {
    private TreeMap<String, List<String>> slangMap = new TreeMap<>();
    private int size;
    private String dictFile = ".\\data\\slang.txt";

    public slangWord() {
        try(BufferedReader br = new BufferedReader(new FileReader(dictFile))) {
            while(true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] split = line.split("`");
                List<String> definition = new ArrayList();
                if (split.length == 2) {
                    String[] splitDefinition = split[1].split("\\|");
                    for (String def : splitDefinition) {
                        definition.add(def.trim());
                    }
                    slangMap.put(split[0], definition);
                }
                else {
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public String[][] getMeaning(String key) {
        List<String> meaningsList = slangMap.get(key);
        if (meaningsList == null) {
            return null;
        }
        String[][] meanings = new String[meaningsList.size()][3];
        for (int i = 0; i < meaningsList.size(); i++) {
            meanings[i][0] = String.valueOf(i);
            meanings[i][1] = key;
            meanings[i][2] = meaningsList.get(i);
        }
        return meanings;
    }

    public String[][] findDefinition(String query) {
        List<String> keyList = new ArrayList<>();
        List<String> meaningsList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : slangMap.entrySet()) {
            List<String> meaning = entry.getValue();
            for (String s : meaning) {
                if (s.toLowerCase().contains(query.toLowerCase())) {
                    keyList.add(entry.getKey());
                    meaningsList.add(s);
                }
            }
        }
        String[][] definition = new String[keyList.size()][3];
        for (int i = 0; i < keyList.size(); i++) {
            definition[i][0] = String.valueOf(i);
            definition[i][1] = keyList.get(i);
            definition[i][2] = meaningsList.get(i);
        }
        return definition;
    }
}
