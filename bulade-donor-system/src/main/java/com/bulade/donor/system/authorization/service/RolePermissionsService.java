package com.bulade.donor.system.authorization.service;

import com.bulade.donor.system.authorization.bo.RolePermissionInsertBO;
import com.bulade.donor.system.authorization.bo.RolePermissionQueryBO;
import com.bulade.donor.system.authorization.model.RolePermission;
import com.bulade.donor.common.page.PageParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RolePermissionsService {

    Long insert(@Valid @NotNull RolePermissionInsertBO rolePermissionInsertBO);

    RolePermission selectById(@NotNull Long id);

    Integer deleteById(@NotNull Long id);

    List<RolePermission> listAll(
        @Valid @NotNull RolePermissionQueryBO rolePermissionQueryBO,
        @Valid @NotNull PageParam pageParam
    );

    Integer countAll(@Valid @NotNull RolePermissionQueryBO rolePermissionQueryBO);

    List<Long> listPermissionIdByRoleIds(@NotNull List<Long> roleIds);

}
