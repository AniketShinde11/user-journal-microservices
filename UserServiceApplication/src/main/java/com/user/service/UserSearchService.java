package com.user.service;

import com.user.dto.UserResponseDTO;
import com.user.dto.UserSearchDTO;
import com.user.entity.UserEntity;
import com.user.repository.UserRepository;
import com.user.repository.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserResponseDTO mapToResponse(UserEntity user) {

        UserResponseDTO response = new UserResponseDTO();


        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMobileNo(user.getMobileNo());
        response.setRoles(user.getRoles());

        return response;
    }


    public List<UserResponseDTO> searchUsers(UserSearchDTO searchDTO) {

        Specification<UserEntity> spec =
                Specification.where(null);

    /*
     Add email filter if provided
    */
        if (searchDTO.getEmail() != null && !searchDTO.getEmail().isBlank()) {

            spec = spec.and(
                    UserSpecification.hasEmail(
                            searchDTO.getEmail()
                    )
            );
        }

    /*
     Add mobile filter
    */
        if (Boolean.TRUE.equals(searchDTO.getMobileAvailable())) {

            spec = spec.and(
                    UserSpecification.mobileAvailable()
            );
        }

        if (Boolean.FALSE.equals(
                searchDTO.getMobileAvailable())) {

            spec = spec.and(
                    UserSpecification.mobileNotAvailable()
            );
        }

        return userRepository.findAll(spec)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
