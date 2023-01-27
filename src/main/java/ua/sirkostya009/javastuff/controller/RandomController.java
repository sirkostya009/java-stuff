package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.sirkostya009.javastuff.service.EquationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/math/example")
public class RandomController {

    private final EquationService service;

    @GetMapping
    public List<String> getEquations(@RequestParam("count") int count) {
        log.info("Requested getEquations with count=" + count);
        var generated = service.generateEquations(count);
        log.info("Generated response: " + generated);
        return generated;
    }

}
