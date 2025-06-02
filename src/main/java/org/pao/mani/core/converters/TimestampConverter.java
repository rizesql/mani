package org.pao.mani.core.converters;

import org.pao.mani.core.Timestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TimestampConverter implements Converter<Long, Timestamp> {
    @Override
    public Timestamp convert(Long source) {
        try {
            return new Timestamp(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timestamp format: " + source, e);
        }
    }
}
