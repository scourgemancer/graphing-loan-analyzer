package com.timothygeary;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Represents a loan, storing its relevant information and applicable functions **/
public class Loan{
    //Relevant information about the loan
    public String name;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal period; //in days


    //Getters and setters for the above variables
    public String getName(){ return name; }
    public void setName(String newName){ this.name = newName; }

    public BigDecimal getInterestRate(){ return interestRate; }
    public void setInterestRate(BigDecimal newInterestRate){ this.interestRate = newInterestRate; }

    public BigDecimal getPeriod(){ return period; }
    public void setPeriod(BigDecimal newPeriod){ this.period = newPeriod; }

    public BigDecimal getAmount(){ return amount; }


    //A few simple constructors for flexibility
    public Loan(){ }

    public Loan(String n){
        this();
        this.name = n;
    }

    public Loan(String n, BigDecimal a){
        this(n);
        amount = a;
    }

    public Loan(String n, BigDecimal a, BigDecimal iR, BigDecimal p){
        this(n, a);
        this.interestRate = iR;
        this.period = p;
    }


    //Relevant functions for the loan class
    /** Calculates interest for the loan's next period */
    public BigDecimal calculateInterest(){
        return interestRate.divide(new BigDecimal(100), 5, RoundingMode.CEILING)  //convert from percent to decimal
                .multiply(period)
                .divide(new BigDecimal("365.25"), 11, RoundingMode.CEILING)
                .add(BigDecimal.ONE)
                .multiply(amount)           //get what the new amount would be
                .subtract(amount);         //now just take the difference
    }

    /** Calculates interest for the loan over it's next n-many periods */
    public BigDecimal calculateInterest(int n){
        if(n <= 0) return BigDecimal.ZERO; //if it's zero or a negative number, just return zero
        BigDecimal tempAmount = new BigDecimal( amount.toString() );
        while(n > 0){ //just keep accruing interest using the same formula as calculateInterest()
             tempAmount = interestRate.divide(new BigDecimal(100), 5, RoundingMode.CEILING)  //convert from percent to decimal
                     .multiply(period)
                     .divide(new BigDecimal("365.25"), 11, RoundingMode.CEILING)
                     .add(BigDecimal.ONE)
                     .multiply(tempAmount);
            n--;
        }
        tempAmount = tempAmount.subtract(amount); //now take the difference
        return tempAmount;
    }

    /** Accrues interest on the loan and returns it's new amount */
    public BigDecimal accrueInterest(){
        amount = amount.add(calculateInterest());
        return amount;
    }

    /** Accrues interest on the loan for the given number of periods and returns it's new amount */
    public BigDecimal accrueInterest(int n){
        amount = amount.add(calculateInterest(n));
        return amount;
    }

    /** Increases the loan's amount and returns it's new amount */
    public BigDecimal increase(BigDecimal requestedFunds){
        amount = amount.add(requestedFunds);
        return amount;
    }

    /** Decreases the loan's amount and returns any leftover money */
    public BigDecimal decrease(BigDecimal payment){
        if(payment.compareTo(amount) < 0){
            amount = amount.subtract(payment);
            return BigDecimal.ZERO;
        }else{//they payed off the loan!
            payment = payment.subtract(amount);
            return  payment;
        }
    }
}
