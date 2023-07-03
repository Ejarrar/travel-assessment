package com.afkl.travel.exercise.scope;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Data
@Component
public class ApplicationScoped {

    private int count=1;
    private int error;
}
