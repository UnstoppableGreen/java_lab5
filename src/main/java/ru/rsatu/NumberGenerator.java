package ru.rsatu;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
@ApplicationScoped
@Dependent
public interface NumberGenerator {
	public String generateNumber();
}
