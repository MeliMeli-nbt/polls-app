package utc.cntt2.k61.pollsappserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc.cntt2.k61.pollsappserver.domain.Role;
import utc.cntt2.k61.pollsappserver.domain.RoleName;
import utc.cntt2.k61.pollsappserver.domain.User;
import utc.cntt2.k61.pollsappserver.dto.SignUpRequest;
import utc.cntt2.k61.pollsappserver.dto.UserProfile;
import utc.cntt2.k61.pollsappserver.exception.AppException;
import utc.cntt2.k61.pollsappserver.exception.ResourceNotFoundException;
import utc.cntt2.k61.pollsappserver.repository.PollRepository;
import utc.cntt2.k61.pollsappserver.repository.RoleRepository;
import utc.cntt2.k61.pollsappserver.repository.UserRepository;
import utc.cntt2.k61.pollsappserver.repository.VoteRepository;

import java.util.Collections;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PollRepository pollRepository,
                       VoteRepository voteRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isAvailableEmail(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isAvailableUserName(String userName) {
        return !userRepository.existsByUsername(userName);
    }

    public UserProfile getUserProfile(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", userName));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    public User createNewUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
        return result;
    }

    public boolean hasExistingUserByUserName(SignUpRequest signUpRequest) {
        return userRepository.existsByUsername(signUpRequest.getUsername());
    }

    public boolean hasExistingUserByEmail(SignUpRequest signUpRequest) {
        return userRepository.existsByEmail(signUpRequest.getEmail());
    }
}
