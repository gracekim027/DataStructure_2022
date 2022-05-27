public class StringPair {
    private String substring;
    private Pair pair;

    public StringPair(String string, Pair index){
        substring = string;
        pair = index;
    }
    public String getSubString(){
        return substring;
    }

    public Pair getPair(){
        return pair;
    }
}
