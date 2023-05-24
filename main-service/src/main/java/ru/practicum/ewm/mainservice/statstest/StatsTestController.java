package ru.practicum.ewm.mainservice.statstest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStat;
import ru.practicum.ewm.stats.dto.ViewStatsRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsTestController {

    private final StatsClient statsClient;

    @GetMapping("/stats")
    public List<ViewStat> logIPAndPath(ViewStatsRequest viewStatsRequest, HttpServletRequest request) {
        return (List<ViewStat>)(statsClient.stats(viewStatsRequest).getBody());
    }

    @PostMapping("/hit")
    public Object hit(@RequestBody EndpointHitDto hitDto) {
        return statsClient.hit(hitDto).getBody();
    }
}
