package com.timothygeary;

import java.util.ArrayList;

/** Keeps track of all loans and repeatable transactions */
public class Account{
    private ArrayList<Loan> loans;

    public Account(){
        loans = new ArrayList<>();
    }

    public ArrayList<Loan> getLoans(){ return loans; }

    public void addLoan(Loan newLoan){ loans.add(newLoan); }

    public void addLoans(ArrayList<Loan> newLoans){
        for(Loan newLoan : newLoans){
            addLoan( newLoan );
        }
    }

    public void removeLoan(String name){
        for(int i=0; i < loans.size(); i++){
            if(loans.get(i).getName().equals( name )) loans.remove(i);
        }
    }

    public void clearLoans(){ loans.clear(); }
}
