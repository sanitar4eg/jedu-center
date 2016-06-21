package edu.netcracker.center.service.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.*;
import edu.netcracker.center.repository.AuthorityRepository;
import edu.netcracker.center.repository.UserRepository;
import edu.netcracker.center.security.AuthoritiesConstants;
import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.repository.CuratorRepository;
import edu.netcracker.center.service.MailService;
import edu.netcracker.center.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static edu.netcracker.center.domain.QCurator.curator;

/**
 * Service Implementation for managing Curator.
 */
@Service
@Transactional
public class CuratorServiceImpl implements CuratorService {

    private final Logger log = LoggerFactory.getLogger(CuratorServiceImpl.class);

    private final UserRepository userRepository;

    private final CuratorRepository curatorRepository;

    private final MailService mailService;

    private final UserService userService;

    private final AuthorityRepository authorityRepository;

    @Inject
    public CuratorServiceImpl(UserRepository userRepository, CuratorRepository curatorRepository,
                              MailService mailService, UserService userService, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.curatorRepository = curatorRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a curator.
     *
     * @return the persisted entity
     */
    public Curator save(Curator curator) {
        log.debug("Request to save Curator : {}", curator);
        Curator result = curatorRepository.save(curator);
        return result;
    }

    @Transactional
    public Curator register(Curator curator, String baseUrl) {
//        TODO: REPLACE THIS
        User result = userRepository.findOneByLogin(curator.getEmail())
            .map(user ->{
                user.setActivated(true);
                return userRepository.save(user);
            })
            .orElseGet(() -> {
                    User user = createUser(curator);
                    mailService.sendCreationEmail(user, baseUrl);
                    return user;
                });
        curator.setUser(result);
        return curatorRepository.save(curator);
    }

    @Transactional
    public Curator disable(Long id) {
        Curator curator = curatorRepository.findOne(id);
        Optional.ofNullable(curator).ifPresent(curator1 -> {
            userRepository.findOneByLogin(curator1.getEmail())
                .ifPresent(user -> {
                    user.setActivated(false);
                    userRepository.save(user);
                });
        });
        curator.setIsActive(false);
        return curatorRepository.save(curator);
    }

    /**
     * get all the curators.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Curator> findAll(Predicate predicate, Pageable pageable) {
        log.debug("Request to get all Curators");
        Page<Curator> result = curatorRepository.findAll(predicate, pageable);
        return result;
    }

    /**
     * get one curator by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Curator findOne(Long id) {
        log.debug("Request to get Curator : {}", id);
        Curator curator = curatorRepository.findOne(id);
        return curator;
    }

    /**
     * delete the  curator by id.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Curator : {}", id);
        Curator curator = curatorRepository.findOne(id);
        curator.getRecalls().forEach(recall -> recall.setCurator(null));
        curator.getRecalls().clear();
        curator.getStudents().forEach(student -> student.setCurator(null));
        curator.getStudents().clear();
        curatorRepository.delete(curator);
    }

    /**
     * get the  curator by user.
     */
    public Curator findByUser(User user) {
        log.debug("Request to get Curator by user: {}", user.getLogin());
        Predicate predicate = new BooleanBuilder().and(curator.user.eq(user));
        return curatorRepository.findOne(predicate);
    }

    private User createUser(Curator curator) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.USER));
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.CURATOR));
        return userService.createUserForEC(curator.getFirstName(), curator.getLastName(),
            curator.getEmail(), authorities);
    }
}
