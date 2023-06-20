package ru.practicum.ewm.mainservice.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.mainservice.comment.entity.Comment;
import ru.practicum.ewm.mainservice.comment.repository.CommentRepository;
import ru.practicum.ewm.mainservice.comment.repository.CommentRepositoryCustom;
import ru.practicum.ewm.mainservice.event.State;
import ru.practicum.ewm.mainservice.event.entity.Event;
import ru.practicum.ewm.mainservice.event.repository.EventRepository;
import ru.practicum.ewm.mainservice.exception.NotFoundException;
import ru.practicum.ewm.mainservice.user.entity.User;
import ru.practicum.ewm.mainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentRepositoryCustom commentRepositoryCustom;

    public Comment createComment(Comment comment, long eventId, long userId) {
        User author = userRepository.findByIdOrThrowNotFoundException(userId, "User");
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED).orElseThrow(
                () -> new NotFoundException("Event with ID={0} not found", eventId)
        );
        comment.setAuthor(author)
                .setEvent(event)
                .setCreated(LocalDateTime.now())
                .setInitiator(event.getInitiator().getId() == userId);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long eventId, int from, int size) {
        return commentRepositoryCustom.getComments(eventId, from, size);
    }

    public List<Comment> getCommentsByUserId(long userId, int from, int size) {
        return commentRepositoryCustom.getCommentsByUserId(userId, from, size);
    }

    public Comment patchCommentById(Comment updateComment, long commentId, long userId) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId).orElseThrow(
                () -> new NotFoundException("Comment not found")
        );
        if (updateComment == null || updateComment.getComment().isEmpty()
                || comment.getComment().equals(updateComment.getComment())) {
            return comment;
        }
        comment.setEdited(LocalDateTime.now());
        comment.setComment(updateComment.getComment());
        return commentRepository.save(comment);
    }
}
