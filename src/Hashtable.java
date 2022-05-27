import java.lang.reflect.Array;

public class Hashtable{

    public AVLTree[] table;

    public Hashtable(int n){
        table = (AVLTree[]) Array.newInstance(AVLTree.class, n);
        for(int i=0; i < n ; i++){
            table[i] = new AVLTree();
        }
    }

    /*
    public void search(String pattern){
    }*/

    public void printSlot(int slot){
        //print method of AVLtree
        table[slot].print();
    }

    public int hash(String substring){
        //hash function, add all char and mod100
        int length = substring.length();
        int all = 0;
        for (int i=0; i<length; i++){
            all += substring.charAt(i);
        }
        return all % 100;
    }

    public void insert(StringPair item){
        int slot = hash(item.getSubString());
        table[slot].insert(item);
    }

    public AVLTree searchSlot(int slot){
        return table[slot];
    }

    public int getValues(int slot){
        return table[slot].getNumOfNodes();
    }

    /*public T find(M item){
        // returns the items in slot
        return table[item.hashCode()];
    }*/

}
