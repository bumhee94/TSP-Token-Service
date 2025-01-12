package com.example.tsp.service;

import com.example.tsp.exception.InvalidCardNumberException;
import com.example.tsp.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefIdService {

    private final EncryptionUtil encryptionUtil;
    /**
     * 카드 번호를 기반으로 고유한 참조 ID 생성
     * @param cardNumber 평문 카드 번호
     * @return 생성된 참조 ID
     */
    public String generateRefId(String cardNumber) {

        // AES복호화
        String decryptCardNumber = encryptionUtil.decrypt(cardNumber);

        String sanitizedCardNumber = sanitizeCardNumber(decryptCardNumber);
        //TR서버에서 @ValidCardNumber 어노테이션으로 검증은 하지만
        //TSP서버에서도 한번 더 검증
        // InvalidCardNumberException 카드번호 유효성 검사 - 글로벌 이셉션 GlobalExceptionHandler 정의
        if (!isValidCardNumber(sanitizedCardNumber)) {
            log.error("Invalid card number: {}", cardNumber);
            throw new InvalidCardNumberException("Invalid card number.");
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 카드 번호에서 하이픈과 공백 제거
     * @param cardNumber 입력된 카드 번호
     * @return 정리된 카드 번호
     */
    private String sanitizeCardNumber(String cardNumber) {
        return cardNumber.replaceAll("[-\\s]", "");
    }

    /**
     * 카드 번호 유효성 검증 (Luhn 알고리즘 사용)
     * @param cardNumber 정리된 카드 번호
     * @return 유효 여부
     */
    private boolean isValidCardNumber(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }
}
