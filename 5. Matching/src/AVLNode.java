import java.util.LinkedList;

public class AVLNode {

    public LinkedList<Pair> idxList;
    public String keyString;
    private AVLNode left, right;
    public int height;

    public String getKeyString(){
        return keyString;
    }

    public AVLNode(StringPair item){
        //this is case if there is no sub string list (actually making new node)
        idxList = new LinkedList<>();
        keyString = item.getSubString();
        idxList.add(item.getPair());
        height = 1;
    }

    public LinkedList<Pair> getList(){
        return idxList;
    }

    public void nodePrint(){
        //node traversal
        System.out.print(keyString);
        if (left != null){
            System.out.print(" ");
            left.nodePrint();
        }
        if (right != null){
            System.out.print(" ");
            right.nodePrint();
        }
    }

    public AVLNode getLeft(){
        return left;
    }

    public AVLNode getRight(){
        return right;
    }

    public void setLeft(AVLNode leftNode){
        left = leftNode;
    }

    public void setRight(AVLNode rightNode){
        right = rightNode;
    }

    /*public void addtoList(StringPair item){
        idxList.add(item.getPair());
    }*/

    //since left can be null, should use getter
    public int getLeftHeight(){
        if (left == null){
            return 0;
        }else
            return left.height;
    }

    public int getRightHeight(){
        if (right == null){
            return 0;
        }else
            return right.height;
    }
    }

