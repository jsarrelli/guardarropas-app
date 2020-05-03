package services;

import exepciones.NoEntityFoundException;
import repositories.BaseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public abstract class BaseService<T> {

    protected BaseRepository repository;

    public void save(T object) {
        repository.save(object);
    }

    public void delete(T object) {
        repository.delete(object);
    }

    public List findAll() {
        return repository.findAll();
    }

    public Stream<T> findBy(HashMap<String, String> criterios) {
        return repository.findBy(criterios);
    }

    public T findById(int id) throws NoEntityFoundException {
        try {
            return (T) repository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NoEntityFoundException("No se encontro entidad con id: " + id + ", papurri");
        }
    }

}
