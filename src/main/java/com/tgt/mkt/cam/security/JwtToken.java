package com.tgt.mkt.cam.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by z083387 on 3/5/18.
 */

@Getter
@Setter
public class JwtToken {
    String email;

    String username;

    String firstName;

    String lastName;

    private String role;
}
