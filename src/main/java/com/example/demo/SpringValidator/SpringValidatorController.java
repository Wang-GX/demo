package com.example.demo.SpringValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/springValidated")
public class SpringValidatorController {

    @PostMapping("testValidated")
    public void testValidated(@RequestBody @Valid ValidatorEntity validatedTestEntity) {
        System.out.println(validatedTestEntity);
    }
}

