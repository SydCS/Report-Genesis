package com.gen;

import com.gen.entity.User;
import com.gen.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChainApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserService userService;

    @Test
    void getUser() {
        User ye = userService.getUserByName("ye");
        System.out.println(ye);

        System.out.println(userService.isExist("yes"));
        System.out.println(userService.isExist("k"));

        System.out.println(userService.getUserRole("ye"));
    }

}
