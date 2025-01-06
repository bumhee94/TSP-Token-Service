package com.example.tsp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RefIdService {

    /**
     * 카드 번호를 기반으로 고유한 참조 ID 생성
     * @param cardNumber 평문 카드 번호
     * @return 생성된 참조 ID
     */
    public String generateRefId(String cardNumber) {
        // 카드 번호 유효성 검증 >> Luhn 알고리즘을 사용하여 카드 번호 유효성 검증
        // 하이픈, 띄어쓰기 제거 후 검증
        cardNumber = cardNumber.replaceAll("[-\\s]", "");
        if (!isValidCardNumber(cardNumber)) {
            log.error("유효하지 않은 카드 번호: {}", cardNumber);
            throw new RuntimeException("유효하지 않은 카드번호입니다.");
        }

        // 고유한 참조 ID UUID로 생성
        String refId = "REF-" + cardNumber + "-" + UUID.randomUUID();
        log.info("참조 ID 생성 완료: {}", refId);
        return refId;
    }

    /**
     * Luhn 알고리즘을 사용하여 카드 번호 유효성 검증
     * @param cardNumber 카드 번호
     * @return 유효하면 true, 아니면 false
     */
    private static boolean isValidCardNumber(String cardNumber) {
        // 카드 번호가 null이거나 숫자가 아닌 경우
        if (cardNumber == null || !cardNumber.matches("\\d+")) {
            log.warn("카드 번호가 null이거나 숫자가 아님: {}", cardNumber);
            return false;
        }
        int totalSum = 0;
        boolean isSecondDigit = false;

        // 카드 번호를 오른쪽에서 왼쪽으로 순회
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            // 두 번째 숫자마다 두 배 처리
            if (isSecondDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            totalSum += digit;
            isSecondDigit = !isSecondDigit;
        }

        boolean isValid = totalSum % 10 == 0;
        if (!isValid) {
            log.warn("유효하지 않은 카드 번호 (Luhn 알고리즘 실패): {}", cardNumber);
        }
        return isValid;
    }
}
