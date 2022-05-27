
/******************************************************************************
 * MovieDB의 인터페이스에서 공통으로 사용하는 클래스.
 */
public class MovieDBItem implements Comparable<MovieDBItem> {

    private final String genre;
    private final String title;

    public MovieDBItem(String genre, String title) {
        if (genre == null) throw new NullPointerException("genre");
        if (title == null) throw new NullPointerException("title");

        this.genre = genre;
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString(){
        return title;
    }

    @Override
    public int compareTo(MovieDBItem other) {
        // compareTo 가 어떤 정보가 필요한걸까..
        int compare = 0;

        if (!this.genre.equals(other.getGenre())){
            compare = -2;
            //아예 different genre
        }else{
            compare = this.title.compareTo(other.getTitle());
        }
        //if compare = 0 : same movie
        // if compare > 0 : this is alphabetically before other
        // if compare < 0 : this is alphabetically behind other
        return compare;
        //throw new UnsupportedOperationException();
    }

    //@Override
    /*public boolean equals(Object obj) {
        boolean equal = false;
        MovieDBItem other = (MovieDBItem) obj;

        if (!this.genre.equals(other.genre) || !this.title.equals(other.title)){
        }
        if (this.equals(obj) || this.equals(other)){
            equal = true;
        }
        return equal;
    }*/

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

}
