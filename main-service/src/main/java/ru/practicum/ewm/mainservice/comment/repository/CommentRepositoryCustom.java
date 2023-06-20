package ru.practicum.ewm.mainservice.comment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.mainservice.comment.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Repository
public class CommentRepositoryCustom {
    private final EntityManager entityManager;

    public List<Comment> getComments(long eventId, int from, int size) {
        log.info("Find comments with parameters: eventId={}, from={}, size={}", eventId, from, size);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);
        commentRoot.fetch("author");

        criteriaQuery.select(commentRoot).where(criteriaBuilder.equal(commentRoot.get("event"), eventId));

        TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size);

        return typedQuery.getResultList();
    }

    public List<Comment> getCommentsByUserId(long userId, int from, int size) {
        log.info("Find comments by UserId={}, from={}, size={}", userId, from, size);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);
        commentRoot.fetch("author");

        criteriaQuery.select(commentRoot).where(criteriaBuilder.equal(commentRoot.get("author"), userId));

        TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size);

        return typedQuery.getResultList();
    }
}
