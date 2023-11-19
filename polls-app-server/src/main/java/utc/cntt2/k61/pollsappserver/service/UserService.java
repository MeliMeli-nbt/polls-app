package utc.cntt2.k61.pollsappserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utc.cntt2.k61.pollsappserver.domain.User;
import utc.cntt2.k61.pollsappserver.dto.UserProfile;
import utc.cntt2.k61.pollsappserver.exception.ResourceNotFoundException;
import utc.cntt2.k61.pollsappserver.repository.PollRepository;
import utc.cntt2.k61.pollsappserver.repository.UserRepository;
import utc.cntt2.k61.pollsappserver.repository.VoteRepository;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PollRepository pollRepository,
                       VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
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
}
