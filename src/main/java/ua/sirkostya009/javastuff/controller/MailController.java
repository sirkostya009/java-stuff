package ua.sirkostya009.javastuff.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.javastuff.dto.MailDto;
import ua.sirkostya009.javastuff.dto.StatusDto;
import ua.sirkostya009.javastuff.service.MailService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {

    private final MailService service;

    @PostMapping
    public MailDto post(@RequestBody @Valid MailDto dto) {
        return MailDto.of(service.post(dto));
    }

    @GetMapping("/{id}")
    public MailDto id(@PathVariable String id) {
        return MailDto.of(service.getWithId(id));
    }

    @GetMapping("/check-status/{id}")
    public StatusDto checkStatus(@PathVariable String id) {
        return service.getStatusWithId(id);
    }

}
