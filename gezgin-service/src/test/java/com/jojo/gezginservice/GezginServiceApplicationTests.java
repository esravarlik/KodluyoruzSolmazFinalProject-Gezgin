package com.jojo.gezginservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GezginServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

/*
class EmployeeTest {

    private Employee testEmployee1;
    private Employee testEmployee2;
    private Employee testEmployee3;
    private Employee testEmployee4;
    private Employee testEmployee5;

    @BeforeEach
    void setUp() {
        testEmployee1 = new Employee("Drew",900.0,45.0,2020);
        testEmployee2 = new Employee("David", 1001.0, 45.0, 2011);
        testEmployee3 = new Employee("Gilbert", 2000.0, 30.0, 2010);
        testEmployee4 = new Employee("Hector", 2000.0, 45.0, 2009);
        testEmployee5 = new Employee("Esra",5000.0,50,1998);
    }

    @Test
    void tax() {
        assertEquals(0.0, testEmployee1.tax(),0.1f);
        assertEquals(30.03, testEmployee2.tax(),0.1f);
        assertNotEquals(400, testEmployee1.tax(),0.01f);
        assertNotNull(testEmployee1.tax());
    }

    @Test
    void bonus() {
        assertEquals(0,testEmployee3.bonus(),0.1f);
        assertEquals(150,testEmployee4.bonus(),0.1f);
        assertNotNull(testEmployee2.bonus());
    }

    @Test
    void raiseSalary() {
        assertEquals(100.1,testEmployee2.raiseSalary(),1f);
        assertEquals(200,testEmployee4.raiseSalary(),0.01f);
        assertEquals(750,testEmployee5.raiseSalary(),0.01f);
        assertEquals(200,testEmployee3.raiseSalary(),0.01f);
        assertNotNull(testEmployee5.raiseSalary());
    }


    @AfterEach
    void tearDown() {
        if(testEmployee1 != null){
            testEmployee1 = null;
        }
        assertNull(testEmployee1);

        if(testEmployee2 != null){
            testEmployee2 = null;
        }
        assertNull(testEmployee2);

        if(testEmployee3 != null){
            testEmployee3 = null;
        }
        assertNull(testEmployee3);

        if(testEmployee4 != null){
            testEmployee4 = null;
        }
        assertNull(testEmployee4);

        if(testEmployee5 != null){
            testEmployee5 = null;
        }
        assertNull(testEmployee5);
    }

}
 */
