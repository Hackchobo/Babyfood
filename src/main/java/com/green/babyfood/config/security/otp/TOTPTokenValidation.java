package com.green.babyfood.config.security.otp;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

@Component
public class TOTPTokenValidation {
    public static boolean validate(String inputCode, String secretKey) {
        String code = getTOTPCode(secretKey);
        return code.equals(inputCode);
    }

    // OTP 검증 요청 때마다 개인키로 OTP 생성
    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        // 실제로는 로그인한 회원에게 생성된 개인키가 필요합니다.
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
}
