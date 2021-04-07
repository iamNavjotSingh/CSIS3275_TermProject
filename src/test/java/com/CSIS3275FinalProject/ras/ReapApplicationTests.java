package com.CSIS3275FinalProject.ras;
import com.CSIS3275FinalProject.ras.controller.AdminController;
import com.CSIS3275FinalProject.ras.controller.BadgeController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReapApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("This test works - kovid");
	}

	@Test
	public void test1 () {
		AdminController ac = new AdminController();
		int output = ac.countA("Professor Reza Ghaeli");
		assertEquals(2, output);
	}

	@Test
	public void test2 () {
		AdminController ac = new AdminController();
		int output = ac.countA("Kovid Sehgal");
		assertEquals(1, output);
	}

	@Test
	public void test3 () {
		BadgeController bg = new BadgeController();
		int output = bg.square(5);
		assertEquals(25, output);
	}

	@Test
	public void test4 () {
		BadgeController bg = new BadgeController();
		int output = bg.square(7);
		assertEquals(49, output);
	}

	/* //This test will fail as the value entered is incorrect :)
	@Test
	public void test5 () {
		BadgeController bg = new BadgeController();
		int output = bg.square(10);
		assertEquals(50, output);
	} */
}
