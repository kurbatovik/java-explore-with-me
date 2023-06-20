package ru.practicum.ewm.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.ewm.mainservice.exception.NotFoundException;

import java.util.Optional;

@NoRepositoryBean
public interface Repository<T, ID> extends JpaRepository<T, ID> {
    default T findByIdOrThrowNotFoundException(ID id, String type) {
        Optional<T> optional = findById(id);
        return optional.orElseThrow(
                () -> new NotFoundException("{0} with ID: {1} was not found", type, id)
        );
    }
}
