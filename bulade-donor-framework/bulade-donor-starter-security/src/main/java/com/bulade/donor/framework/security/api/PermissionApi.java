package com.bulade.donor.framework.security.api;

import com.bulade.donor.framework.security.dto.PermissionDTO;

import java.util.List;

/**
 * 权限 API 接口
 */
public interface PermissionApi {

    List<PermissionDTO> listNoSignInPermissionsUrl();

    List<PermissionDTO> listAuthorizationPermissionsUrl();
}
