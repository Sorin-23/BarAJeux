package barJeux.dao;

import java.util.List;

public interface IDAO<T,K> {

	
	public List<T> findAll();
	public T findById(K id);
	public void insert(T obj);
	public void update(T obj);
	public void delete(K id);
}
