package com.iitbh.ccms.model_db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.iitbh.ccms.model.Role;
import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.model.UserInfo.StatusEnum;

public class UserDetailsDBTests {
    private static UserDetailsDB testingUnit;

    @BeforeAll
    public static void setup() {
        testingUnit = new UserDetailsDB(null, "12345678", "theUser",
                Arrays.asList("Admin"), "admin@mail.com", "Active",
                LocalDate.parse("2024-01-30"), "Name of User", "1234", "Computer Science", "9879879879");
    }

    @Test
    public void testConvertToUserInfo() {
        UserInfo actualUserInfo = new UserInfo("12345678", "theUser", "Name of User", "admin@mail.com",
                StatusEnum.ACTIVE, LocalDate.parse("2024-01-30"));
        actualUserInfo.roles(Arrays.asList(Role.ADMIN));
        actualUserInfo.setPassword("1234");
        actualUserInfo.setDepartment("Computer Science");
        actualUserInfo.phoneNumber("9879879879");
        assertEquals(actualUserInfo, testingUnit.convertToUserInfo());
    }

    @Test
    public void testUserInfoConstructor() {
        UserInfo actualUserInfo = new UserInfo("12345678", "theUser", "Name of User", "admin@mail.com",
                StatusEnum.ACTIVE, LocalDate.parse("2024-01-30"));
        actualUserInfo.roles(Arrays.asList(Role.ADMIN));
        actualUserInfo.setPassword("1234");
        actualUserInfo.setDepartment("Computer Science");
        actualUserInfo.phoneNumber("9879879879");
        assertEquals(testingUnit, new UserDetailsDB(actualUserInfo));
    }
}
