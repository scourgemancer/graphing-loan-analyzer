package com.timothygeary;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/** Tests the loan class */
public class LoanTest{
    @Test
    public void testGetName(){
        Loan test = new Loan("Test Loan");
        assertEquals(test.getName(), "Test Loan");
    }

    @Test
    public void testSetName(){
        Loan test = new Loan("Test Loan");
        test.setName("different name");
        assertEquals(test.getName(), "different name");
    }

    @Test
    public void testGetAmount(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"));
        assertEquals(test.getAmount(), new BigDecimal("42"));
    }

    @Test
    public void testGetInterestRate(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"), new BigDecimal("6"), new BigDecimal("1"));
        assertEquals(test.getInterestRate(), new BigDecimal("6"));
    }

    @Test
    public void testSetInterestRate(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"), new BigDecimal("6"), new BigDecimal("1"));
        test.setInterestRate(new BigDecimal("6.25"));
        assertEquals(test.getInterestRate(), new BigDecimal("6.25"));
    }

    @Test
    public void testGetPeriod(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"), new BigDecimal("6"), new BigDecimal("1"));
        assertEquals(test.getPeriod(), BigDecimal.ONE);
    }

    @Test
    public void testSetPeriod(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"), new BigDecimal("6"), new BigDecimal("1"));
        test.setPeriod(new BigDecimal("11"));
        assertEquals(test.getPeriod(), new BigDecimal("11"));
    }

    @Test
    public void testCalculateInterest(){
        Loan test = new Loan("Test Loan", new BigDecimal("36500"), new BigDecimal("6.25"), new BigDecimal("1"));
        assertEquals(test.calculateInterest(), new BigDecimal("6.25"));
    }

    @Test
    public void testCalculateInterestWithAParameter(){
        Loan test = new Loan("Test Loan", new BigDecimal("36500"), new BigDecimal("6.25"), new BigDecimal("1"));
        assertEquals(test.calculateInterest(24), new BigDecimal("150.31"));
    }

    @Test
    public void testAccrueInterest(){
        Loan test = new Loan("Test Loan", new BigDecimal("36500"), new BigDecimal("6.25"), new BigDecimal("1"));
        assertEquals(test.accrueInterest(), new BigDecimal("36506.25"));
    }

    @Test
    public void testAccrueInterestWithAParameter(){
        Loan test = new Loan("Test Loan", new BigDecimal("36500"), new BigDecimal("6.25"), new BigDecimal("1"));
        assertEquals(test.accrueInterest(24), new BigDecimal("36650.31"));
    }

    @Test
    public void testIncrease(){
        Loan test = new Loan("Test Loan", new BigDecimal("42"), new BigDecimal("6"), new BigDecimal("1"));
        test.increase(new BigDecimal("500"));
        assertEquals(test.getAmount(), new BigDecimal("542"));
    }

    @Test
    public void testDecrease(){
        Loan test = new Loan("Test Loan", new BigDecimal("5000"));
        test.decrease(new BigDecimal("500"));
        assertEquals(test.getAmount(), new BigDecimal("4500"));
    }
}
