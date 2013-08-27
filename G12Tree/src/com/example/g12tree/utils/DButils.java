package com.example.g12tree.utils;

import java.sql.SQLException;
import java.util.HashMap;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DButils {

	public static HashMap<String, SQLContainer> createContainer() {
		HashMap<String, SQLContainer> sqlContainers = new HashMap<String, SQLContainer>();
		
		try {
			

			final JDBCConnectionPool pool = new SimpleJDBCConnectionPool(
					"com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/ministry_db", "root", "dags");

			TableQuery tq = new TableQuery("memberinfo", pool);
			SQLContainer container = new SQLContainer(tq);
			sqlContainers.put("memberinfo", container);

			TableQuery tq2 = new TableQuery("bsrelation", pool);
			SQLContainer container2 = new SQLContainer(tq2);
			sqlContainers.put("bsrelation", container2);

			TableQuery tq3 = new TableQuery("stats", pool);
			SQLContainer container3 = new SQLContainer(tq3);
			sqlContainers.put("stats", container3);

						
		} catch (SQLException e) {
			throw new RuntimeException();
		}

		return sqlContainers;
	}

}
