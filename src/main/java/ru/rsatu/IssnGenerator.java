package ru.rsatu;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@EightDigits
public class IssnGenerator implements NumberGenerator {

    public String generateNumber() {
        return "8-" + Math.abs(new Random().nextInt());
    }
}