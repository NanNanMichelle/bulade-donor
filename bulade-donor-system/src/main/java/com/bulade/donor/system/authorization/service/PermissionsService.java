package com.bulade.donor.system.authorization.service;

import com.bulade.donor.system.authorization.bo.PermissionInsertBO;
import com.bulade.donor.system.authorization.bo.PermissionQueryBO;
import com.bulade.donor.system.authorization.bo.PermissionUpdateBO;
import com.bulade.donor.system.authorization.enums.PermissionType;
import com.bulade.donor.system.authorization.model.Permission;
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
