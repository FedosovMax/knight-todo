package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.rest.mapper.RestRoleMapper;
import com.knighttodo.todocore.rest.request.RoleRequestDto;
import com.knighttodo.todocore.rest.response.RoleResponseDto;
import com.knighttodo.todocore.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static com.knighttodo.todocore.Constants.ROLES_BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + ROLES_BASE_URL)
@Slf4j
public class RoleResource {

    private final RoleService roleService;
    private final RestRoleMapper roleMapper;

    @PostMapping
    public ResponseEntity<RoleResponseDto> addRole(@Valid @RequestBody RoleRequestDto roleRequestDto) {
        log.info("Rest request to add role : {}", roleRequestDto);
        RoleVO roleVO = roleService.save(roleMapper.toRoleVO(roleRequestDto));
        return new ResponseEntity<>(roleMapper.toRoleResponseDto(roleVO), HttpStatus.CREATED);
    }
}
