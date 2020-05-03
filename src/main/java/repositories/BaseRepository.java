package repositories;

import model.factories.SessionFactoryHelper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseRepository<T> {
    private Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.type = type;
    }


    public void save(T object) {
        Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
        Transaction tx = getTransaction();
        currentSession.save(object);
        tx.commit();
    }

    public void delete(T object) {
        Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
        Transaction tx = getTransaction();
        currentSession.delete(object);
        tx.commit();
    }

    public List findAll() {
        Session session = SessionFactoryHelper.getCurrentSession();
        getTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(type);
        Root<T> root = cr.from(type);
        cr.select(root);

        return session.createQuery(cr).getResultList();
    }


    public Stream<T> findBy(HashMap<String, String> criterios) {
        Session session = SessionFactoryHelper.getCurrentSession();
        getTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = builder.createQuery(type);
        Root<T> root = cr.from(type);
        cr.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        criterios.forEach((key, value) -> predicates.add(builder.equal(root.get(key), value)));
        cr.where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(cr).getResultList().stream();
    }


    public Optional<T> findById(int id) {
        Session session = SessionFactoryHelper.getCurrentSession();
        getTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = builder.createQuery(type);
        Root<T> root = cr.from(type);
        cr.select(root);
        cr.where(builder.equal(root.get("id"), id));
        try {
            return Optional.ofNullable(session.createQuery(cr).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }


    private Transaction getTransaction() {
        Session session = SessionFactoryHelper.getCurrentSession();
        return session.getTransaction().isActive() ? session.getTransaction() : session.beginTransaction();
    }
}
