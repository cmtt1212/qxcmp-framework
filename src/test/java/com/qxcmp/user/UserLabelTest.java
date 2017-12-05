package com.qxcmp.user;

import com.google.common.collect.ImmutableList;
import com.qxcmp.domain.Label;
import com.qxcmp.domain.LabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLabelTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LabelService labelService;

    @Test
    public void testUserLabelCreate() throws Exception {
        Label label = labelService.create("user", "vip").orElseThrow(() -> new Exception(""));
        User user = get("user1");
        Optional<User> newUser = userService.update(user.getId(), u -> u.getLabels().add(label));
        assertTrue(newUser.isPresent());
        assertEquals(1, newUser.get().getLabels().size());
    }

    @Test
    public void testUserLabelDuplicate() throws Exception {
        Label label = labelService.create("user", "vip2").orElseThrow(() -> new Exception(""));
        User user = get("user2");
        Optional<User> newUser = userService.update(user.getId(), u -> u.getLabels().addAll(ImmutableList.of(label, label)));
        assertTrue(newUser.isPresent());
        assertEquals(1, newUser.get().getLabels().size());
    }

    @Test
    public void testUserLabelRename() throws Exception {
        Label label = labelService.create("user", "vip3").orElseThrow(() -> new Exception(""));
        User user = get("user3");
        Optional<User> newUser = userService.update(user.getId(), u -> u.getLabels().addAll(ImmutableList.of(label, label)));
        assertTrue(newUser.isPresent());
        assertEquals(1, newUser.get().getLabels().size());
        newUser.get().getLabels().forEach(l -> assertEquals("vip3", l.getName()));
        labelService.update(label.getId(), l -> l.setName("vipAfter"));
        Optional<User> afterUser = userService.findOne(newUser.get().getId());
        assertTrue(afterUser.isPresent());
        afterUser.get().getLabels().forEach(l -> assertEquals("vipAfter", l.getName()));
    }

    @Test
    public void testMultipleLabel() throws Exception {
        Label l1 = labelService.create("user", "l1").orElseThrow(() -> new Exception(""));
        Label l2 = labelService.create("user", "l2").orElseThrow(() -> new Exception(""));
        Label l3 = labelService.create("user", "l3").orElseThrow(() -> new Exception(""));
        User user = get("user4");
        Optional<User> newUser = userService.update(user.getId(), u -> u.getLabels().addAll(ImmutableList.of(l1, l2, l3)));
        assertTrue(newUser.isPresent());
        assertEquals(3, newUser.get().getLabels().size());
    }

    private User get(String username) throws Exception {
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return userService.create(() -> {
                User user = userService.next();
                user.setUsername(username);
                return user;
            }).orElseThrow(() -> new Exception(""));
        }
    }
}