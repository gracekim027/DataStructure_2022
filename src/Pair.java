public class Pair {
    private int key;
    private int value;

    public Pair(int key_, int value_){
        key = key_;
        value = value_;
    }

    public int getKey(){return key;}
    public int getValue(){return value;}

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(key).append(", ").append(value).append(")");
        return sb.toString();
    }

    public boolean equals(Pair newPair){
       return ((newPair.getKey() == key) && (newPair.getValue() == value));
    }
}
