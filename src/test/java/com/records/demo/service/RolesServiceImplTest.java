package com.records.demo.service;

import com.records.demo.dao.RolesDAO;
import com.records.demo.entity.Employee;
import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolesServiceImplTest {

    @Mock
    private RolesDAO rolesDAO;

    private RolesService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RolesServiceImpl(rolesDAO);
    }

    @Test
    void findAll() {
        underTest.findAll();

        verify(rolesDAO).findAll();
    }

    @Test
    void findById() {
        Roles role = new Roles(new Users(), "admin");

        int id =  role.getId();

        when(this.rolesDAO.findById(id)).thenReturn(Optional.of(role));
        Roles byId =underTest.findById(id);

        verify(rolesDAO).findById(id);
        assertThat(rolesDAO.findById(id).isPresent()).isTrue();
        assertThat(byId).isEqualTo(rolesDAO.findById(id).get());

    }

    @Test
    void save() {
        Roles role = new Roles(new Users(), "admin");

        underTest.save(role);

        ArgumentCaptor<Roles> roleCaptor = ArgumentCaptor.forClass(Roles.class);

        verify(rolesDAO).save(roleCaptor.capture());

        Roles capturedRole = roleCaptor.getValue();

        assertThat(capturedRole).isEqualTo(role);
    }

    @Test
    void deleteById() {
        int id = 0;

        //when
        underTest.deleteById(id);

        //then
        verify(rolesDAO).deleteById(id);
    }

    @Test
    void findByEmail() {

        Roles role = new Roles(new Users(), "admin");

        String email = "test@email.com";
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(role);
        when(this.rolesDAO.findByEmail(email)).thenReturn(rolesList);
        List<Roles> byEmail = underTest.findByEmail(email);
        verify(rolesDAO).findByEmail(email);
        assertThat(byEmail.size()).isEqualTo(1);
    }
}