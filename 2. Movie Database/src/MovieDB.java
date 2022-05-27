import java.util.Iterator;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {

	MyLinkedList<Genre> movieDB;

    public MovieDB() {
    	this.movieDB = new MyLinkedList<>();
    }

	public boolean searchIfExists(MovieDBItem item){
		//search if the movie is in the genreList
		boolean exists = false;
		for (Genre genres : movieDB){
			if (genres.getItem().equals(item.getGenre())){
				for (MovieDBItem movie: genres.getListPerGenre()){
					if (movie.compareTo(item) == 0) {
						exists = true;
						break;
					}
				}
			}
		}
		return exists;
	}


	public boolean searchIfGenreExists(MovieDBItem item){
		boolean exists = false;
		for (Genre genre: movieDB){
			if (genre.getItem().equals(item.getGenre())){
				exists = true;
				break;
			}
		}
		return exists;
	}

    public void insert(MovieDBItem item) {
        // Insert the given item to the MovieDB.
		if (movieDB.isEmpty() || !searchIfGenreExists(item)){
			Genre newGenre = new Genre(item.getGenre());
			newGenre.addMovie(item);
			movieDB.add2(newGenre);
		}else{
			if (!searchIfExists(item)){
				//when it does not already exist.
				for (Genre genre: movieDB){
					if (genre.getItem().equals(item.getGenre())){
						genre.addMovie(item);
					}
				}
		}

		}
        //System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
		if (searchIfExists(item)) {
			for (Genre genre : movieDB) {
				if (genre.getItem().equals(item.getGenre())) {
					genre.removeMovie(item);
				}
			}
		}
		//System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
	}

    public MyLinkedList<MovieDBItem> search(String term) {
        MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
		for (Genre genre: movieDB){
			for (MovieDBItem movie: genre.getListPerGenre()){
				if (movie.getTitle().contains(term)){
					results.add(movie);
				}
			}
		}

		//System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        //System.err.printf("[trace] MovieDB: ITEMS\n");
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		//there is no genre in movieDB!
		for (Genre genre: movieDB){
			if (!genre.getListPerGenre().isEmpty()) {
				for (MovieDBItem movie : genre.getListPerGenre()) {
					results.add(movie);
				}
			}
		}
    	return results;
    }

}

class Genre extends Node<String> implements Comparable<Genre> {

	private MyLinkedList<MovieDBItem> listPerGenre;
	public String genreName;


	public Genre(String name) {
		super(name);
		this.listPerGenre = new MyLinkedList<>();
		this.genreName = name;
		//throw new UnsupportedOperationException("not implemented yet");
	}

	public MyLinkedList<MovieDBItem> getListPerGenre(){
		return listPerGenre;
	}

	public void addMovie(MovieDBItem item) {
		listPerGenre.add2(item);
	}

	public void removeMovie(MovieDBItem item){
		for (Iterator<MovieDBItem> iter = listPerGenre.iterator(); iter.hasNext();) {
			MovieDBItem data = iter.next();
			if (data.compareTo(item) == 0) {
				iter.remove();
			}
		}
	}


	@Override
	public int compareTo(Genre o) {
		//compare item to o, 조금 수정하기
		String compare = o.getItem();
		return this.getItem().compareTo(compare);
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int hashCode() {
		//return the hashcode of the item
		return this.getItem().hashCode();
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean equals(Object obj) {
		//returns true or false compare the obj, compare classes, if the obj is null
		Genre other = (Genre)obj;
		boolean equal = obj != null && obj.getClass() == this.getClass();
		if (obj == this || this.equals(other)){
		}
		return equal;
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String toString(){
		return genreName;
	}
}
