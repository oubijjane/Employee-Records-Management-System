package com.records.demo.service;

import com.records.demo.dao.UserDAO;
import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userDAO);
    }

    @Test
    void findAll() {
        underTest.findAll();

        verify(userDAO).findAll();
    }

    @Test
    void findByEmail() {
        Users user = new Users();

        String email = "email@email.com";
        when(userDAO.findByEmail(email)).thenReturn(user);
        Users byEmail = underTest.findByEmail(email);

        verify(userDAO).findByEmail(email);
        assertThat(byEmail).isEqualTo(userDAO.findByEmail(email));
    }

    @Test
    void findById() {

        Users user = new Users();

        int id = 1;
        when(userDAO.findById(id)).thenReturn(Optional.of(user));
        Users byId = underTest.findById(id);

        verify(userDAO).findById(id);
        assertThat(userDAO.findById(id).isPresent()).isTrue();
        assertThat(byId).isEqualTo(userDAO.findById(id).get());
    }

    @Test
    void save() {
        Users user = new Users();

        underTest.save(user);

        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);

        verify(userDAO).save(userCaptor.capture());

        Users capturedUser = userCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void deleteById() {
        int id = 0;

        //when
        underTest.deleteById(id);

        //then
        verify(userDAO).deleteById(id);
    }
}