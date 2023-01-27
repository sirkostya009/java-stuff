package ua.sirkostya009.javastuff.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Service
public class EquationServiceImpl implements EquationService {

    private final static int ORIGIN = 0, BOUND = 100;

    private final List<String> operators = List.of("+", "-", "*", "/");

    @Override
    public List<String> generateEquations(int count) {
        return Stream.generate(this::generateEquation).limit(count).toList();
    }

    @Override
    public String generateEquation() {
        var a = ThreadLocalRandom.current().nextInt(ORIGIN, BOUND);
        var operator = operators.get(ThreadLocalRandom.current().nextInt(operators.size()));
        var b = ThreadLocalRandom.current().nextInt(ORIGIN, BOUND);

        return a + " " + operator + " " + b;
    }

}
