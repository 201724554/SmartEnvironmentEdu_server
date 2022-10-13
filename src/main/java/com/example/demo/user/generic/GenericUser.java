package com.example.demo.user.generic;

import com.example.demo.user.model.entity.User;

public class GenericUser<T extends User> {
    private T userEntity;
}
