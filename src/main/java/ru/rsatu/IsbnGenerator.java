package ru.rsatu;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
@ApplicationScoped
@Default
@ThirteenDigits
public class IsbnGenerator implements NumberGenerator {

    public String generateNumber() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
}
