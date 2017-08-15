package com.qxcmp.framework.web;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SystemConfigService systemConfigService;

    @MockBean
    private UserService userService;

    private List<User> userRepo = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {

        userRepo.add(createUser("normalUser", "123456", true, true, true, true));
        userRepo.add(createUser("accountLockUser", "123456", false, true, true, true));
        userRepo.add(createUser("accountExpireUser", "123456", true, false, true, true));
        userRepo.add(createUser("credentialExpireUser", "123456", true, true, false, true));
        userRepo.add(createUser("accountDisableUser", "123456", true, true, true, false));
        userRepo.add(createUser("lockTestUser", "123456", true, true, true, true));

        given(userService.findById("voidUser")).willReturn(Optional.empty());
        given(userService.findById("normalUser")).willReturn(Optional.of(userRepo.get(0)));
        given(userService.findById("accountLockUser")).willReturn(Optional.of(userRepo.get(1)));
        given(userService.findById("accountExpireUser")).willReturn(Optional.of(userRepo.get(2)));
        given(userService.findById("credentialExpireUser")).willReturn(Optional.of(userRepo.get(3)));
        given(userService.findById("accountDisableUser")).willReturn(Optional.of(userRepo.get(4)));
        given(userService.findById("lockTestUser")).willReturn(Optional.of(userRepo.get(5)));
    }

    @Test
    public void testUserNotExist() throws Exception {
        mockMvc.perform(post("/login").param("username", "voidUser").param("password", "123456").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: Authentication.userNotFound"));
    }

    @Test
    public void testBadCredential() throws Exception {
        mockMvc.perform(post("/login").param("username", "normalUser").param("password", "111111").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: Bad credentials"));
    }

    @Test
    public void testNormalLogin() throws Exception {
        mockMvc.perform(post("/login").param("username", "normalUser").param("password", "123456").with(csrf()))
                .andExpect(status().isFound());
    }

    @Test
    public void testAccountLockLogin() throws Exception {
        mockMvc.perform(post("/login").param("username", "accountLockUser").param("password", "123456").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: User account is locked"));
    }

    @Test
    public void testAccountExpiredLogin() throws Exception {
        mockMvc.perform(post("/login").param("username", "accountExpireUser").param("password", "123456").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: User account has expired"));
    }

    @Test
    public void testCredentialExpiredLogin() throws Exception {
        mockMvc.perform(post("/login").param("username", "credentialExpireUser").param("password", "123456").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: User credentials have expired"));
    }

    @Test
    public void testAccountDisabledLogin() throws Exception {
        mockMvc.perform(post("/login").param("username", "accountDisableUser").param("password", "123456").with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Authentication Failed: User is disabled"));
    }

    private User createUser(String username, String password, boolean accountNotLock, boolean accountNotExpired, boolean credentialNotExpired, boolean accountEnabled) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAccountNonLocked(accountNotLock);
        user.setAccountNonExpired(accountNotExpired);
        user.setCredentialsNonExpired(credentialNotExpired);
        user.setEnabled(accountEnabled);
        return user;
    }
}