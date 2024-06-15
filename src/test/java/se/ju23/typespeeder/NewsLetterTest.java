package se.ju23.typespeeder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class NewsLetterTest {

    @Test
    public void testClassExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.NewsLetter");
            assertNotNull(clazz, "The class 'NewsLetter' should exist.");
        } catch (ClassNotFoundException e) {
            Assertions.fail("The class 'NewsLetter' does not exist.", e);
        }
    }
    @Test
    public void testNewsLetterContentLength() {
        try {
            Class<?> newsLetterClass = Class.forName("se.ju23.typespeeder.NewsLetter");

            Field contentField = newsLetterClass.getDeclaredField("content");
            assertNotNull(contentField, "Field 'content' should exist in NewsLetter.");

            assertTrue(contentField.getType().equals(String.class), "Field 'content' should be of type String.");

            Object instance = newsLetterClass.getDeclaredConstructor().newInstance();
            Field field = newsLetterClass.getDeclaredField("content");
            field.setAccessible(true);
            String contentValue = (String) field.get(instance);


        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            fail("Error occurred while testing NewsLetter content field length.", e);
        }
    }

    @Test
    public void testNewsLetterPublishDateTime() {
        try {
            Class<?> someClass = Class.forName("se.ju23.typespeeder.NewsLetter");

            Field publishDateTime = someClass.getDeclaredField("publishDateTime");
            assertNotNull(publishDateTime, "Field 'publishDateTime' should exist in NewsLetter class.");

            assertTrue(publishDateTime.getType().equals(LocalDateTime.class), "Field 'publishDateTime' should be of type LocalDateTime.");

            
            Method getterMethod = someClass.getDeclaredMethod("getPublishDateTime");
            assertNotNull(getterMethod, "Getter method for the field 'publishDateTime' should exist.");


        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            fail("Error occurred while testing properties of NewsLetter.", e);
        }
    }
}
