package org.test.tjc.model;

import javax.persistence.*;

@Entity
@Table( name = "testentity" )
public class TestEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int id;
	
	@Column ( name = "name" )
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
}
