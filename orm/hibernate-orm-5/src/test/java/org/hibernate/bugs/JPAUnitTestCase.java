package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

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

/*
   Original QueryStream version:

        qb.stream(Employee.class)
          .filter(e -> qb.substream(e)
            .flatMap(Employee_.directReports)
            .filter(dr -> qb.substream(dr)
              .join(Employee_.annotations)
              .exists())
            .exists())
*/

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        final Root<Employee> employee = query.from(Employee.class);

        final Subquery subquery1 = query.subquery(Employee.class);
        final Root<Employee> employee2 = subquery1.correlate(employee);
        final SetJoin<Employee, Employee> directReport = employee2.join(Employee_.directReports);

        final Subquery subquery2 = subquery1.subquery(Employee.class);
        final SetJoin<Employee, Employee> directReport2 = subquery2.correlate(directReport);
        final MapJoin<Employee, String, String> annotation = directReport2.join(Employee_.annotations);

        subquery2.select(directReport2);

        subquery1.select(employee2).where(cb.exists(subquery2));

        query.select(employee).where(cb.exists(subquery1));

        entityManager.createQuery(query).getResultStream().count();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
