package com.user.repository;

import com.user.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<UserEntity> hasEmail(String email) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email));

    }


    public static Specification<UserEntity> mobileAvailable() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("mobileNo")));
    }

    public static Specification<UserEntity> mobileNotAvailable() {
        return (root, query, cb) ->
                cb.or(
                        cb.isNull(root.get("mobileNo")),
                        cb.equal(root.get("mobileNo"), "")
                );


    }
}
