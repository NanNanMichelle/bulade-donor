package com.bulade.donor.authorization.service;

import com.bulade.donor.authorization.bo.PermissionInsertBO;
import com.bulade.donor.authorization.bo.PermissionQueryBO;
import com.bulade.donor.authorization.bo.PermissionUpdateBO;
import com.bulade.donor.authorization.enums.PermissionType;
import com.bulade.donor.authorization.model.Permission;
import com.bulade.donor.common.page.PageParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface PermissionsService {

    Long insert(@Valid @NotNull PermissionInsertBO permissionInsertBO);

    Permission selectById(@NotNull Long id);

    List<Permission> listAll(
        @Valid @NotNull PermissionQueryBO permissionQueryBO,
        @Valid @NotNull PageParam pageParam
    );

    Integer countAll(@Valid @NotNull PermissionQueryBO permissionQueryBO);

    Integer updateById(@Valid @NotNull PermissionUpdateBO permissionUpdateBO);

    Integer deleteById(@NotNull Long id);

    List<Permission> listByType(@NotNull PermissionType type);

}
