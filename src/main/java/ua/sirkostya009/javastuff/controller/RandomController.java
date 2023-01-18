package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.sirkostya009.javastuff.service.EquationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/math/example")
public class RandomController {

    private final EquationService service;

    @GetMapping
    public List<String> getEquations(@RequestParam("count") int count) {
        return service.generateEquations(count);
    }

}
