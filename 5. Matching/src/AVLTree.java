
public class AVLTree {

    public AVLNode root;
    public int numOfNodes;
    public AVLTree() {
        root = null;
        numOfNodes = 0;
    }

    //returns the node of where the substring is
    //that node also contains the linked list of pairs
    public AVLNode searchString(String substring) {
       return searchItem(root, substring);
    }

    public AVLNode searchItem(AVLNode tNode, String x) {
        if (tNode == null) return null;
        else if (x.compareTo(tNode.getKeyString()) == 0) return tNode;
        else if (x.compareTo(tNode.getKeyString()) < 0) return searchItem(tNode.getLeft(), x);
        else return searchItem(tNode.getRight(), x);
    }

    public void insert(StringPair item) {
        //adding another stringPair
        root = insertItem(root, item);
    }

    public int getNumOfNodes(){
        return numOfNodes;
    }

    //returns root, do not have to set root in here
    private AVLNode insertItem(AVLNode tNode, StringPair item) {
        //when there is no node in tree
        if (tNode == null) {
            tNode = new AVLNode(item);
            numOfNodes ++;
        } else {
            int compare = tNode.getKeyString().compareTo(item.getSubString());
            if (compare>0){
                //item is smaller than root, should traverse left
                tNode.setLeft(insertItem(tNode.getLeft(), item));
                tNode.height = 1 + Math.max(tNode.getRightHeight(), tNode.getLeftHeight());
                int type = needBalance(tNode);
                if (type != NO_NEED) {
                    tNode = balanceAVL(tNode, type);
                }
            }else if (compare == 0){
                tNode.idxList.add(item.getPair());
            }else{
                tNode.setRight(insertItem(tNode.getRight(), item));
                tNode.height = 1 + Math.max(tNode.getRightHeight(), tNode.getLeftHeight());
                int type = needBalance(tNode);
                if (type != NO_NEED) {
                    tNode = balanceAVL(tNode, type);
                }
            }
    }
        return tNode;
    }

    private AVLNode balanceAVL(AVLNode tNode, int type) {
        AVLNode returnNode = null;
        switch (type) {
            case LL:
                returnNode = rightRotate(tNode);
                break;
            case LR:
                tNode.setLeft(leftRotate(tNode.getLeft()));
                returnNode = rightRotate(tNode);
                break;
            case RR:
                returnNode = leftRotate(tNode);
                break;
            case RL:
                tNode.setRight(rightRotate(tNode.getRight()));
                returnNode = leftRotate(tNode);
                break;
            default:
                System.out.println("Wrong!");
                break;
        }
        return returnNode;
    }

    private AVLNode leftRotate(AVLNode t) {
        AVLNode RChild = t.getRight();
        if (RChild == null)
            System.out.println("shouldn't be NIL");
        else {
            AVLNode RLChild = RChild.getLeft();
            RChild.setLeft(t);
            t.setRight(RLChild);
            t.height = 1 + Math.max(t.getLeftHeight(), t.getRightHeight());
            RChild.height = 1 + Math.max(RChild.getRightHeight(), RChild.getLeftHeight());
        }
        return RChild;
    }

    private AVLNode rightRotate(AVLNode t) {
        AVLNode LChild = t.getLeft();
        if (LChild == null) {
            System.out.println("shouldn't be NIL");
        }else {
            AVLNode LRChild = LChild.getRight();
            LChild.setRight(t);
            t.setLeft(LRChild);
            t.height = 1 + Math.max(t.getRightHeight(), t.getLeftHeight());
            LChild.height = 1 + Math.max(LChild.getRightHeight(), LChild.getLeftHeight());

        }
        return LChild;
    }

    private final int LL = 1, LR = 2, RR = 3, RL = 4, NO_NEED = 0, ILLEGAL = -1;

    private int needBalance(AVLNode t) {
        int type = ILLEGAL;
        if (t.getLeftHeight() + 2 <= t.getRightHeight()) {
            if ((t.getRight().getLeftHeight()) <= t.getRight().getRightHeight()) {
                type = RR;
            } else
                type = RL;
        } else if ((t.getLeftHeight()) >= t.getRightHeight() + 2) {
            if ((t.getLeft().getLeftHeight()) >= t.getLeft().getRightHeight()) {
                type = LL;
            } else
                type = LR;
        }else
            type = NO_NEED;
        return type;
    }


    public void print() {
        if (root == null){
            //for this slot, there was no string
            System.out.println("EMPTY");
        }else{
            //prints string and recursion to child nodes
            root.nodePrint();
            System.out.println();
        }
    }

    public AVLNode getRoot(){
        return root;
    }
    }


