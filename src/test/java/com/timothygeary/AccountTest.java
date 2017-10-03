package com.timothygeary;

import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class AccountTest{
    @Test
    public void testGetLoans(){
        Account test = new Account();
        ArrayList<Loan> empty =  new ArrayList<>();
        assertEquals(test.getLoans(), empty);
    }

    @Test(dependsOnMethods = "testGetLoans")
    public void testAddLoan(){
        Account test = new Account();
        Loan testLoan = new Loan("Test1");
        test.addLoan( testLoan );
        ArrayList<Loan> loanList = new ArrayList<>();
        loanList.add( testLoan );
        assertEquals(test.getLoans(), loanList);
    }

    @Test(dependsOnMethods = { "testAddLoan", "testGetLoans" } )
    public void testAddLoans(){
        Account test = new Account();
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("Loan1"));
        loans.add(new Loan("Loan2"));
        loans.add(new Loan("Loan3"));
        test.addLoans( loans );
        assertEquals(test.getLoans(), loans);
    }

    @Test(dependsOnMethods = { "testAddLoans", "testGetLoans" } )
    public void testRemoveLoan(){
        Account test = new Account();
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("Loan1"));
        loans.add(new Loan("Loan2"));
        loans.add(new Loan("Loan3"));
        test.addLoans( loans );
        test.removeLoan("Loan2");
        loans.remove(1);
        assertEquals(test.getLoans(), loans);
    }

    @Test(dependsOnMethods = { "testAddLoans", "testGetLoans" } )
    public void testClearLoans(){
        Account test = new Account();
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("Loan1"));
        loans.add(new Loan("Loan2"));
        loans.add(new Loan("Loan3"));
        test.addLoans( loans );
        test.clearLoans();
        loans.clear();
        assertEquals(test.getLoans(), loans);
    }
}
