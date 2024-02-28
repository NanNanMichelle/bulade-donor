package com.bulade.donor.system.api;

import com.bulade.donor.system.authorization.enums.PermissionType;
import com.bulade.donor.system.authorization.service.PermissionsService;
import com.bulade.donor.framework.security.api.PermissionApi;
import com.bulade.donor.framework.security.dto.PermissionDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private PermissionsService permissionsService;

    @Override
    public List<PermissionDTO> listNoSignInPermissionsUrl() {
        var noSignInPermissions = permissionsService.listByType(PermissionType.NO_SIGN_IN);
        if (CollectionUtils.isEmpty(noSignInPermissions)) {
            return List.of();
        }
        return noSignInPermissions.stream().map(t -> PermissionDTO.of(t.getId(), t.getName(), t.getController()))
            .toList();
    }

    @Override
    public List<PermissionDTO> listAuthorizationPermissionsUrl() {
        var noSignInPermissions = permissionsService.listByType(PermissionType.AUTHORIZATION);
        if (CollectionUtils.isEmpty(noSignInPermissions)) {
            return List.of();
        }
        return noSignInPermissions.stream().map(t -> PermissionDTO.of(t.getId(), t.getName(), t.getController()))
            .toList();
    }
}
