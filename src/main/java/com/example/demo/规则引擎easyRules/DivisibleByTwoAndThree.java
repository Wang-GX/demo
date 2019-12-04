package com.example.demo.规则引擎easyRules;

import org.jeasy.rules.annotation.*;
import org.jeasy.rules.support.UnitRuleGroup;

@Rule(name = "被2和3同时整除", description = "number如果被2和3同时整除，打印：number is two and three")
public class DivisibleByTwoAndThree extends UnitRuleGroup {

    public DivisibleByTwoAndThree(Object... rules) {
        for (Object rule : rules) {
            addRule(rule);
        }
    }

    @Override //优先级注解：return 数值越小，优先级越高
    public int getPriority() {
        return 0;
    }
}
