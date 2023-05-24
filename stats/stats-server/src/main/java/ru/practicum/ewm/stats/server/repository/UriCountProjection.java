package ru.practicum.ewm.stats.server.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UriCountProjection {
    String uri;
    Long count;
}
