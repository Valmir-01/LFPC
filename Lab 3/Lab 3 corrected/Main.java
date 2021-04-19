import java.util.*;
import java.lang.*;

public class Main {
    static  String stringHasEKey;
    static  String unitNonterminal;
    public static String [] terminalsCfg;
    public static String [] nonterminalsCfg;
    public static String [] allAlphabets;
    public static String [] newStates;
    public static Map<String, HashSet<String>> alphabets;
    public static Map<String, HashSet<String>> tmpAlphabetsMap;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String RESET = "\033[0m";

    public static void main(String[] args) {

        terminalsCfg = new String[]{"e, a, b"}; //e - epsilon
        nonterminalsCfg = new String[]{"S, A, B, C, E"};
        allAlphabets = new String[]{"aB, AC, a, ACSC, BC, b, aA, e, BA, bB"};
        newStates = new String[]{" ", "X", "Y", "Z", "W", "V", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
        alphabets = new HashMap<String, HashSet<String>>();

        Map<String, HashSet<String>> tmpAlphabetsMap = new HashMap<String, HashSet<String>>();
        HashSet<String> tmp = new HashSet<>();


        HashSet<String> forS = new HashSet<String>();
        HashSet<String> forA = new HashSet<String>();
        HashSet<String> forB = new HashSet<String>();
        HashSet<String> forC = new HashSet<String>();
        HashSet<String> forE = new HashSet<String>();

        forS.add("aB");
        forS.add("AC");
        forA.add("a");
        forA.add("ACSC");
        forA.add("BC");
        forB.add("b");
        forB.add("aA");
        forC.add("e");
        forB.add("BA");
        forE.add("bB");


        alphabets.put("S", forS);
        alphabets.put("A", forA);
        alphabets.put("B", forB);
        alphabets.put("C", forC);
        alphabets.put("E", forE);

        //Search for S nonterminal in the RHS
        if (Methods.findS(allAlphabets)) {
            HashSet<String> fornewS = new HashSet<String>();
            fornewS.add("S");
            alphabets.put("S'", fornewS);

        }


        //find the nonterminal(Bigletter) that contains epsilon in alphabet
        for (Map.Entry<String, HashSet<String>> alphabetsEntry : alphabets.entrySet()) {
            for (String value : alphabetsEntry.getValue()) {
                if (alphabetsEntry.getValue().contains("e"))
                    tmp.add(alphabetsEntry.getKey());
            }
        }


        //find alphabets that contains key of epsilon
        for (String nonterminal : alphabets.keySet()) {
            for (String value : alphabets.get(nonterminal)) {
                if (hasEKey(value,tmp)){
                    newAlphabets((HashMap<String, HashSet<String>>) tmpAlphabetsMap, nonterminal, value, stringHasEKey);
                }
            }
        }
        //add new expressions to the main alphabet set
        for(String key:tmpAlphabetsMap.keySet()){
            HashSet<String> set= new HashSet<String>() {{
                addAll(alphabets.get(key));
                addAll(tmpAlphabetsMap.get(key)); } };
            alphabets.put(key,set);
        }

        for( String value : tmp)
        alphabets.get(value).remove("e");

        System.out.println(ANSI_BLUE + "INITIAL MAP" + RESET);
        Methods.printMap((HashMap<String, HashSet<String>>) alphabets);

        Units newUnits = new Units(alphabets, terminalsCfg, nonterminalsCfg ,newStates);
        TwoUnits newTwoUnits = new TwoUnits(alphabets, newStates);
        TermNonterm doit = new TermNonterm(alphabets, newStates);

        System.out.println(ANSI_BLUE + "CHOMSKY NORMAL FORM" + RESET);
        Methods.printMap((HashMap<String, HashSet<String>>) alphabets);

    }
    static boolean hasEKey(String value, HashSet<String> set){

        for (String s : set) {
            if (value.contains(s)) {
                stringHasEKey = s;
                return true;
            }
        }
        return false;
    }

    //function to add new expressions to alphabets of keys
    static void newAlphabets(HashMap<String, HashSet<String>> anAlphabet, String nonterm, String expression, String keyToRemove){
        char keyToRemoveAsChar = keyToRemove.charAt(0);
        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == keyToRemoveAsChar){

                HashSet<String> aSet = new HashSet<>();
                aSet.add(expression.substring(0, i));
                anAlphabet.put(nonterm, aSet);
            }
        }
    }

}