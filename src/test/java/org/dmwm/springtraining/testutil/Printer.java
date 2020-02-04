package org.dmwm.springtraining.testutil;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Printer {

    public void pretty(String title, Iterable iterable) {
        System.out.println(title + " = ");
        iterable.forEach(item -> System.out.println('\t' + item.toString()));
    }

}
