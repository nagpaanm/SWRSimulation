/*
Anmol Nagpal (Sheridan College)

Safe Withdrawal Rate Simulation - investment/retirement calculator.

Data from: 
    http://basehitinvesting.com/the-stock-market-a-look-at-the-last-200-years/


This application simulates the balance annually given an initial withdrawal 
rate. It accurately simulates real-life market conditions, and assumes a nominal
rate of return value of about 7.5%, and an inflation rate of 2.5%. Thus, this
application assumes a 5% real rate of return moving forward, which is lower than
the historical average of about 6.5-7%.

To use application,
    1. Enter starting balance, aka "nest-egg" amount (double)
    2. Enter annual withrawal rate (double)
    3. Enter number of years that will be spent withrawing this amount (int)
    4. Enter number of trials to simulate (int)

 */

package swrsimulation;


import java.util.Scanner;

public class SWRSimulation {

    public static void main(String[] args) {
        double cumulativeInterest = 0;
        double totalCumulativeInterest = 0;
        final double INFLATIONRATE = 2.5;
        int inflationCounter = 0;
        int counter = 0;
        int negativePeriods = 0;
        int periodsLT3 = 0;
        int periodsLT4 = 0;
        int periodsLT5 = 0;
        int negativeYears = 0;
        int crashes = 0;
        int countAmountLessThanStartingAmount = 0;
        int countAmountLessThanZero = 0;
        String failureYears = "";
        
        Scanner k = new Scanner(System.in);
        System.out.print("Enter starting amount: ");
        double startingAmount = k.nextDouble();
        System.out.print("Enter withdrawal rate: ");
        double wR = k.nextDouble();
        System.out.print("Enter length of period: ");
        int period = k.nextInt();
        System.out.print("Enter number of periods: ");
        int repetitions = k.nextInt();
        
        final double INITIALAMOUNT = startingAmount;
        double balance = startingAmount;
        
        while(counter < repetitions){
            System.out.println("---------------------------------------------");
            System.out.println("Trial: " + counter);
            System.out.println("Starting Amount: " + balance);
            for(int i = 1; i <=period; i++){
                double interest = Math.round((Math.random() * 26) * 100.0) / 
                        100.0;
                int negative = (int)(Math.random() * 4);
                if(negative == 0){ //25% chance of being negative
                    negativeYears += 1;
                    interest = (int)(Math.random() * 100);
                    if(interest >= 0 && interest <=86){ //86% chance < 0
                        interest = Math.round((Math.random() * 10) * 100.0) / 
                        100.0;
                        interest *= -1;
                    }else if(interest > 86 && interest <=95){ // 9% chance < -10
                        interest = Math.round(((Math.random() * 10) + 10) * 
                                100.0) / 100.0;
                        interest *= -1;
                    }else if(interest >95){ //5% chance of < -20
                        interest = Math.round(((Math.random() * 20) + 20) * 
                                100.0) / 100.0;
                        interest *= -1;
                        crashes += 1;
                    }
                }else if(interest > 5 && interest < 15){
                        interest *= 0.5;
                }else if(interest > 22){
                    interest*=1.35;
                }
                
                cumulativeInterest += interest;
                System.out.println("Year: " + i);
                System.out.println("\tInterest: " + interest);
                startingAmount += (startingAmount * (interest/100));
                double withdrawalAmount = (INITIALAMOUNT * (wR/100));
                if(interest < 0){
                    withdrawalAmount = (balance * ((wR/2)/100));
                }
                double wAIF = withdrawalAmount * 
                        ((INFLATIONRATE / 100) * inflationCounter);
                balance -= withdrawalAmount;
                balance -= wAIF;
                balance += (balance * (interest/100));
                System.out.println("\tAmount withdrawn: " + withdrawalAmount);
                System.out.println("\tAmount withdrawn "
                        + "(with inflation): "  + (withdrawalAmount + wAIF));
                System.out.println("\tRemaining balance: " + balance);
                inflationCounter += 1;
            }
            System.out.println("---------------------------------------------");
            System.out.println("average Interest: " + 
                    cumulativeInterest/period);
            counter += 1;
            totalCumulativeInterest += cumulativeInterest;
            if(cumulativeInterest/period < 0){
                negativePeriods +=1;
            }
            else if(cumulativeInterest/period < 3){
                periodsLT3 +=1;
            }
            else if(cumulativeInterest/period < 4){
                periodsLT4 +=1;
            }
            else if(cumulativeInterest/period < 5){
                periodsLT5 +=1;
            }
    
            if(balance < INITIALAMOUNT){
                countAmountLessThanStartingAmount +=1;
            }
            
            if(balance <= 0){
                countAmountLessThanZero +=1;
                failureYears += counter + ": ";
            }
            cumulativeInterest = 0;
            inflationCounter = 0;
            balance = INITIALAMOUNT;
            System.out.println();
        }
        System.out.println("Total average interest: " + 
                Math.round((totalCumulativeInterest / (repetitions * period)) * 
                        100.0) / 100.0);
        System.out.println("Total number of negative periods: " + 
                negativePeriods);
        System.out.println("Total number of negative years: " + negativeYears);
        System.out.println("Total number of crashes: " + crashes);
        System.out.println("Total number of periods returning less than 3%: " + 
                periodsLT3);
        System.out.println("Total number of periods returning less than 4%: " + 
                periodsLT4);
        System.out.println("Total number of periods returning less than 5%: " + 
                periodsLT5);
       System.out.println("Total number of periods with balance less than"
               + " starting balance: " + countAmountLessThanStartingAmount);
       System.out.println("Total number of periods with balance less than"
               + " or equal to 0: " + countAmountLessThanZero + ". Trials: " +
               failureYears);
    }
}
