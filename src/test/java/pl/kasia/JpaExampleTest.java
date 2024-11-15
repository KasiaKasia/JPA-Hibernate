package pl.kasia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class JpaExampleTest {
	@Test
	void createAndUpdatePersonTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA");

		try {
			EntityManager entityManager = entityManagerFactory.createEntityManager();

			String name = "Kasia";
			int age = 12;

			createPerson(entityManager, name, age);

			List<Person> allPeople = getAllPeople(entityManager);

			int newAge = 30;
			Person person = allPeople.get(0);
			updateAge(entityManager, person, newAge);

			Assertions.assertEquals(1, allPeople.size());
			Assertions.assertEquals(30, allPeople.get(0).getAge());
			entityManager.close();

		} finally {
			entityManagerFactory.close();
		}
	}

	private void updateAge(EntityManager entityManager, Person person, int newAge) {
		entityManager.getTransaction().begin();
		person.setAge(newAge);

		entityManager.getTransaction().commit();
	}

	private List<Person> getAllPeople(EntityManager entityManager) {
		entityManager.getTransaction().begin();
		String select = "SELECT p FROM Person p";

		Query query = entityManager.createQuery(select);
		List<Person> resultList = query.getResultList();
		
		System.out.println(resultList);
		
		entityManager.getTransaction().commit();
		return resultList;
	}

	private void createPerson(EntityManager entityManager, String name, int age) {
		entityManager.getTransaction().begin();

		Person person = new Person();
		person.setLastName(name);
		person.setAge(age);

		entityManager.persist(person);
		entityManager.getTransaction().commit();
	}
}
