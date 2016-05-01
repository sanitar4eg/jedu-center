package edu.netcracker.center.repository;

import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.path.StringPath;
import edu.netcracker.center.domain.QStudent;
import edu.netcracker.center.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long>, QueryDslPredicateExecutor<Student>, QuerydslBinderCustomizer<QStudent> {

    @Override
    default public void customize(QuerydslBindings bindings, QStudent root) {
        bindings.bind(String.class).first(
            (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
