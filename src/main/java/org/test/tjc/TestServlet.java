package org.test.tjc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Objects;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//import org.test.tjc.model.TestEntity;

@WebServlet( urlPatterns = {"/index.html"} )
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@PersistenceUnit(unitName="persunit")
    private EntityManagerFactory factory;
	
	@Resource(name="jdbc/test_ds")
	private DataSource dataSource;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			try ( Connection con = dataSource.getConnection(); ) {			
				Objects.requireNonNull(con, "failed get connection jndi");				
				out.println("connection url from datasource resource\n"+con.getMetaData().getURL()+"\n");
			}	
		} catch ( Exception e ) {
			out.println(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			try {
				Connection con = em.unwrap(java.sql.Connection.class);
				Objects.requireNonNull(con, "failed get connection from jpa");
				
				out.println("connection url from entity manager\n"+con.getMetaData().getURL()+"\n");
				
				//TestEntity testEntity = em.find(TestEntity.class, 1);
				
				//out.println(testEntity);
				
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			}
		} catch (Exception e) {
			out.println(e.getMessage());
			e.printStackTrace();
		}	
	}	
}