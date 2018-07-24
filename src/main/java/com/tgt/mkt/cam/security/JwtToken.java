package com.tgt.mkt.cam.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Saugata.Dutta on 05/06/18.
 */

@Getter
@Setter
public class JwtToken {
    String email;

    String documentNumber;

    String jti;
    
    String issuer;
}
