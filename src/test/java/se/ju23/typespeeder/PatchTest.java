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

public class PatchTest {

    @Test
    public void testClassExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Patch");
            assertNotNull(clazz, "The class 'Patch' should exist.");
        } catch (ClassNotFoundException e) {
            Assertions.fail("The class 'Patch' does not exist.", e);
        }
    }

    @Test
    public void testPatchProperties() {
        try {
            Class<?> someClass = Class.forName("se.ju23.typespeeder.Patch");

            Field patchVersion = someClass.getDeclaredField("patchVersion");
            assertNotNull(patchVersion, "Field 'patchVersion' should exist in the Patch class.");
            assertTrue(patchVersion.getType().equals(String.class), "Field 'patchVersion' should be of type String.");

            Field realeaseDateTime = someClass.getDeclaredField("realeaseDateTime");
            assertNotNull(realeaseDateTime, "Field 'realeaseDateTime' should exist in Patch class.");

            assertTrue(realeaseDateTime.getType().equals(LocalDateTime.class), "Field 'realeaseDateTime' should be of type LocalDateTime.");

            Method getterMethod = someClass.getDeclaredMethod("getRealeaseDateTime");
            assertNotNull(getterMethod, "Getter method for field 'realeaseDateTime' should exist.");



        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            fail("Error occurred while testing properties of Patch.", e);
        }
    }
}
