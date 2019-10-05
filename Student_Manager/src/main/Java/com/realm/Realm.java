package com.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

public class Realm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 得到UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        /**
         * 和数据库的用户名密码进行比较
         * @Param principal 认证的用户
         * @Param credentials 认证凭证
         */
        Object principal = username;
        Object credentials = "123";

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, getName());
        return info;
    }
}
