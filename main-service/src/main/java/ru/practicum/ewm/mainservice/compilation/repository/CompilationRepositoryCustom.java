package ru.practicum.ewm.mainservice.compilation.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.mainservice.compilation.entity.Compilation;
import ru.practicum.ewm.mainservice.event.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CompilationRepositoryCustom {
    private final EntityManager entityManager;

    public List<Compilation> findCompilations(Boolean pinned, int from, int size) {
        Criteria criteria = new Criteria(entityManager);
        Root<Compilation> compilationRoot = getCompilationRootWithFetchAll(criteria.query);

        CriteriaQuery<Compilation> query = criteria.query.select(compilationRoot);
        if (pinned != null) {
            query.where(criteria.builder.equal(compilationRoot.get("pinned"), pinned));
        }

        log.info("Parameters: pinned={}, from={}, size={}", pinned, from, size);

        TypedQuery<Compilation> typedQuery = entityManager.createQuery(criteria.query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    private static class Criteria {
        CriteriaBuilder builder;
        CriteriaQuery<Compilation> query;

        Criteria(EntityManager entityManager) {
            builder = entityManager.getCriteriaBuilder();
            query = builder.createQuery(Compilation.class);
        }
    }

    private Root<Compilation> getCompilationRootWithFetchAll(CriteriaQuery<Compilation> query) {
        Root<Compilation> compilationRoot = query.from(Compilation.class);

        Fetch<Compilation, Event> eventFetch = compilationRoot.fetch("events", JoinType.LEFT);
        eventFetch.fetch("initiator", JoinType.LEFT);
        eventFetch.fetch("category", JoinType.LEFT);
        eventFetch.fetch("location", JoinType.LEFT);
        return compilationRoot;
    }
}
