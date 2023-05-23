package ru.practicum.ewm.mainservice.statstest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.ViewStatsRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(path = "/stats-test")
@RequiredArgsConstructor
public class StatsTestController {

    private final StatsClient statsClient;

    @GetMapping
    public void logIPAndPath(HttpServletRequest request) {
        statsClient.hit(request);
        ViewStatsRequest viewStatsRequest = new ViewStatsRequest()
                .setStart(LocalDateTime.of(2004, 4, 4, 4, 44))
                .setEnd(LocalDateTime.of(2044, 4, 4, 4, 44));
        log.info("{}", statsClient.stats(viewStatsRequest));
    }

}
