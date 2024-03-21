package org.openmrs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@TestInstance(Lifecycle.PER_CLASS)
public class JUnitTestSuit extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(JUnitTestSuit.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
}
