package org.mobica.jokon.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by jaok on 2017-01-24.
 */

@Component
public class MyBean {


    @Value(value = "${bean.value}")
    private String value;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
