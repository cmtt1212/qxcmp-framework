package com.qxcmp.label;

import com.qxcmp.domain.Label;
import com.qxcmp.domain.LabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

    @Autowired
    private LabelService labelService;

    @Test
    public void testCreate() throws Exception {
        Optional<Label> label = labelService.create("type1", "label1");
        assertTrue(label.isPresent());
        assertEquals("type1", label.get().getType());
        assertEquals("label1", label.get().getName());
    }

    @Test
    public void testDuplication() throws Exception {
        Optional<Label> label1 = labelService.create("type1", "label2");
        Optional<Label> label2 = labelService.create("type1", "label2");
        assertTrue(label1.isPresent());
        assertFalse(label2.isPresent());
    }

    @Test
    public void testModify() throws Exception {
        Optional<Label> label = labelService.create("type3", "before");
        assertTrue(label.isPresent());
        Long id = label.get().getId();
        Date dateCreated = label.get().getDateCreated();
        Date dateModified = label.get().getDateModified();
        assertEquals("type3", label.get().getType());
        assertEquals("before", label.get().getName());
        Optional<Label> afterLabel = labelService.update(id, l -> l.setName("after"));
        assertTrue(afterLabel.isPresent());
        assertEquals(dateCreated, afterLabel.get().getDateCreated());
        assertNotEquals(dateModified, afterLabel.get().getDateModified());
        assertEquals(id, afterLabel.get().getId());
        assertEquals("after", afterLabel.get().getName());
    }

    @Test
    public void testModifyToDuplicate() throws Exception {
        Optional<Label> label1 = labelService.create("type4", "label1");
        Optional<Label> label2 = labelService.create("type4", "label2");
        assertTrue(label1.isPresent());
        assertTrue(label2.isPresent());
        assertFalse(labelService.update(label2.get().getId(), label -> label.setName("label1")).isPresent());
    }
}