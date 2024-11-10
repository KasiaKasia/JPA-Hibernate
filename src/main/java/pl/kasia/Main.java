package pl.kasia;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String selectQuery = "SELECT p FROM Person p";
		Query query = entityManager.createQuery(selectQuery);
		List<Person> people = query.getResultList();

		people.forEach(System.out::println);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
		Root<Person> root = criteriaQuery.from(Person.class);
		criteriaQuery.select(root);
		TypedQuery<Person> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Person> peopleQuery = typedQuery.getResultList();
		System.out.println(peopleQuery);
		peopleQuery.forEach(System.out::println);

	}

}
