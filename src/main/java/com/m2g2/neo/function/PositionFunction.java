package com.m2g2.neo.function;

import com.m2g2.neo.model.PositionRecord;
import com.m2g2.neo.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class PositionFunction {

    private final PositionService service;

    public PositionFunction(PositionService service) {
        this.service = service;
    }

    private static final Logger logger = LoggerFactory.getLogger(PositionFunction.class);

    @Bean
    public Function<PositionRecord, PositionRecord> savePosition() {
        return positionRecord -> {
            logger.info("In: savePosition - {}", positionRecord);
            positionRecord = service.save(positionRecord);
            logger.info("Out: savePosition - {}", positionRecord);
            return positionRecord;
        };
    }
}
