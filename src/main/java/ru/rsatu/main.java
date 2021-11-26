package ru.rsatu;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

public class main {
	
    @Inject @Default
    private static IsbnGenerator numberGenerator13_1;
    
    @Inject @ThirteenDigits
    private static NumberGenerator numberGenerator13_2;
    
    @Inject @EightDigits
    private static NumberGenerator numberGenerator8;
    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			System.out.println("Inject Default: "+ numberGenerator13_1.generateNumber()+"Inject ThirteenDigits: "+"Inject EightDigits: "+ numberGenerator8.generateNumber());

	}

}
