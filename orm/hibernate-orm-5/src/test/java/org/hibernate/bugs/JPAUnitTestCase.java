package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.bugs.Foo_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Foo> cq = cb.createQuery(Foo.class);
        final Root<Foo> foo = cq.from(Foo.class);
        cq.select(foo)
          .where(cb.lessThanOrEqualTo(
              cb.function("TIME", String.class, foo.get(Foo_.startTime)), "17:00:00"));

        final TypedQuery<Foo> tq = entityManager.createQuery(cq);

        tq.getResultStream();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
