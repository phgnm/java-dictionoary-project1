package slangs;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class slangWord {
    private TreeMap<String, List<String>> slangMap = new TreeMap<>();
    private int size;
    private String dictFile = ".\\data\\slang.txt";
    private String originFile = ".\\data\\slangOrigin.txt";
    private static String historyFile = ".\\data\\history.txt";

    public int randomizeInt(int min, int max) {
        return (min + (int) (Math.random() * max));
    }

    public slangWord() {
        try(BufferedReader br = new BufferedReader(new FileReader(dictFile))) {
            while(true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                boolean isLegit = false;
                for (char c : line.toCharArray()) {
                    if (c == '`')
                        isLegit = true;
                }
                if (!isLegit) {
                    continue;
                }
                String[] split = line.split("`");
                List<String> definition = new ArrayList<>();
                if (split.length == 2) {
                    String[] splitDefinition = split[1].split("\\|");
                    for (String def : splitDefinition) {
                        definition.add(def.trim());
                    }
                    slangMap.put(split[0], definition);
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

    public void addToHistory(String[][] history) {
        try (FileWriter f = new FileWriter(historyFile, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            for (String[] s : history) {
                p.println(s[1] + "`" + s[2] + "`" + dtf.format(now));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] readHistory() {
        String[][] s = null;

        List<String> slangHistory = new ArrayList<>();
        List<String> definitionHistory = new ArrayList<>();
        List<String> timeHistory = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            while (true) {
                String line = br.readLine();

                if (line == null) {
                    break;
                }

                String[] split = line.split("`");
                if (split.length == 3) {
                    slangHistory.add(split[0]);
                    definitionHistory.add(split[1]);
                    timeHistory.add(split[2]);
                }
                else break;
            }
            int size = slangHistory.size();
            s = new String[size][4];
            for (int i = 0; i < size; i++) {
                s[size -i - 1][0] = String.valueOf(size - i);
                s[size -i - 1][1] = slangHistory.get(i);
                s[size -i - 1][2] = definitionHistory.get(i);
                s[size -i - 1][3] = timeHistory.get(i);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    void updateFile(String file) {
        try (FileWriter f = new FileWriter(file);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            StringBuilder sb = new StringBuilder();
            sb.append("Slag`Meaning\n");
            String[][] s = new String[slangMap.size()][3];
            Set<String> keySet = slangMap.keySet();
            Object[] keyArray = keySet.toArray();
            for (int i = 0; i < slangMap.size(); i++) {
                int id = i + 1;
                s[i][0] = String.valueOf(id);
                s[i][1] = (String) keyArray[i];
                List<String> meaning = slangMap.get(keyArray[i]);
                sb.append(s[i][1]).append("`").append(meaning.getFirst());
                for (int j = 1; j < meaning.size(); j++) {
                    sb.append("|").append(meaning.get(j));
                }
                sb.append("\n");
            }
            p.write(sb.toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Overwrite(String slang, String definition) {
        List<String> list = new ArrayList<String>();
        list.add(definition);
        slangMap.put(slang, list);
        this.updateFile(dictFile);
    }

    public void Duplicate(String slang, String definition) {
        slangMap.get(slang).add(definition);
        this.updateFile(dictFile);
    }

    public void Set(String slang, String curValue, String newValue) {
        List<String> definitions = slangMap.get(slang);
        int id = definitions.indexOf(curValue);
        definitions.set(id, newValue);
        this.updateFile(dictFile);
    }

    public void deleteSlang(String slang, String definition) {
        List<String> list = slangMap.get(slang);
        if (list.size() == 1) {
            slangMap.remove(slang);
        }
        else {
            list.remove(definition);
        }
        this.updateFile(dictFile);
    }

    public void reset() {
        slangMap = new TreeMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(originFile))) {
            while(true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                boolean isLegit = false;
                for (char c : line.toCharArray()) {
                    if (c == '`')
                        isLegit = true;
                }
                if (!isLegit) {
                    continue;
                }
                String[] split = line.split("`");
                List<String> definition = new ArrayList<>();
                if (split.length == 2) {
                    String[] splitDefinition = split[1].split("\\|");
                    for (String def : splitDefinition) {
                        definition.add(def.trim());
                    }
                    slangMap.put(split[0], definition);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public String randomizeASlang() {
        int min = 0;
        int max = slangMap.size() - 1;
        int rand = randomizeInt(min, max);

        Set<String> keySet = slangMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);

        List<String> value = slangMap.get(keyList.get(rand));

        StringBuilder sb = new StringBuilder();
        sb.append(keyList.get(rand)).append(": ");
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i)).append(", ");

            if (i != value.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    public boolean checkCoincidence(String slang) {
        return slangMap.get(slang) != null;
    }
}
