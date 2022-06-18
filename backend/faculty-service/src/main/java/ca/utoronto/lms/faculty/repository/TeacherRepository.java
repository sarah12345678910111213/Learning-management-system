package ca.utoronto.lms.faculty.repository;

import ca.utoronto.lms.faculty.model.Teacher;
import ca.utoronto.lms.shared.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where cast(x.id as string) like :search "
                    + "or x.user.firstName like :search or x.user.lastName like :search")
    Page<Teacher> findContaining(Pageable pageable, String search);
}