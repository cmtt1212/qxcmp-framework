package com.qxcmp.framework.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    public void testStoreWithFixSize() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.jpg").getInputStream(), "jpg", 100, 100);
        assertEquals(true, imageOptional.isPresent());

        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageOptional.get().getContent()));

        assertEquals(100, bufferedImage.getWidth());
        assertEquals(100, bufferedImage.getHeight());
    }

    @Test
    public void testStoreWithAutoSize() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.jpg").getInputStream(), "jpg");
        assertEquals(true, imageOptional.isPresent());

        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageOptional.get().getContent()));

        assertEquals(572, bufferedImage.getWidth());
        assertEquals(572, bufferedImage.getHeight());
    }

    @Test
    public void testStoreAsJpgFormatFromJpgFormat() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.jpg").getInputStream(), "jpg");
        assertEquals(true, imageOptional.isPresent());
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageOptional.get().getContent()));
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        while (imageReaders.hasNext()) {
            ImageReader imageReader = imageReaders.next();
            assertEquals("JPEG", imageReader.getFormatName());
        }
    }

    @Test
    public void testStoreAsPngFormatFromPngFormat() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.png").getInputStream(), "png");
        assertEquals(true, imageOptional.isPresent());
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageOptional.get().getContent()));
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        while (imageReaders.hasNext()) {
            ImageReader imageReader = imageReaders.next();
            assertEquals("png", imageReader.getFormatName());
        }
    }

    @Test
    public void testStoreAsJpgFormatFromPngFormat() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.png").getInputStream(), "jpg");
        assertEquals(true, imageOptional.isPresent());
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageOptional.get().getContent()));
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        while (imageReaders.hasNext()) {
            ImageReader imageReader = imageReaders.next();
            assertEquals("JPEG", imageReader.getFormatName());
        }
    }

    @Test
    public void testStoreAsPngFormatFromJpgFormat() throws Exception {
        Optional<Image> imageOptional = imageService.store(new ClassPathResource("/assets/testImage.jpg").getInputStream(), "png");
        assertEquals(true, imageOptional.isPresent());
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageOptional.get().getContent()));
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        while (imageReaders.hasNext()) {
            ImageReader imageReader = imageReaders.next();
            assertEquals("png", imageReader.getFormatName());
        }
    }
}
