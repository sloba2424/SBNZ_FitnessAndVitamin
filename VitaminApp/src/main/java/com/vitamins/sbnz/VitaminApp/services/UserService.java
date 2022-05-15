package com.sbnz.ibar.services;

import com.sbnz.ibar.model.User;
import com.sbnz.ibar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
    }

    @Transactional
    public User changeProfile(User entity) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!entity.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(entity.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Username already taken");
            }
        }
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        return userRepository.save(user);
    }

    public Iterable<User> getAll() {
        return null;
    }

    public User getById(UUID id) {
        return null;
    }

    public User create(User entity) throws Exception {
        return null;
    }

    public boolean delete(UUID id) throws Exception {
        return false;
    }

    public Reader update(UUID id, Reader entity) throws Exception {
        return null;
    }

    public User update(UUID id, User entity) throws Exception {
        return null;
    }

    public User findByEmail(String email) {
        return this.loadUserByUsername(email);
    }

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));

//		TODO

//		String newPassword = grpService.generateRandomPassword();
//		user.setPassword(passwordEncoder.encode(newPassword));
//		user.setLastPasswordResetDate(new Date().getTime());
//		userRepository.save(user);
//		mailService.sendMail(user.getEmail(), "Password reset",
//				"Hi " + user.getFirstName() + ",\n\nYour new password is: " + newPassword + ".\n\n\nTeam 9");
    }
}
