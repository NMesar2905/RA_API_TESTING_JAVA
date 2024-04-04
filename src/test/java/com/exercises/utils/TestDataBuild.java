package com.exercises.utils;

import com.exercises.pojo.User;

public class TestDataBuild {

	public User addUpdateUser(String name, String job) {
		User user = new User();
		user.setName(name);
		user.setJob(job);

		return user;
	}
	
}
