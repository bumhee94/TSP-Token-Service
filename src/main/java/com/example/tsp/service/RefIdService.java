package com.example.tsp.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefIdService {

    public String generateRefId(String cardNumber) {
        // 평문 카드 번호 검증
        // 카드 번호의 유효성 검사는 일반적으로 Luhn 알고리즘 사용한다
        if(!isValidCardNumber(cardNumber)){
            throw new RuntimeException("유효하지 않은 카드번호 입니다.");
        }
        // 카드 번호 + UUID를 활용한 유니크한 refId 생성
        return "REF-" + cardNumber + "-" + UUID.randomUUID();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        // 카드 번호가 null이거나 숫자가 아닌 경우 false 반환
        if (cardNumber == null || !cardNumber.matches("\\d+")) {
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
                // 두 배 값이 9보다 크면 각 자릿수를 더함
                if (digit > 9) {
                    digit -= 9;
                }
            }

            totalSum += digit;
            // 다음 자리의 숫자는 두 번째 자리로 처리
            isSecondDigit = !isSecondDigit;
        }

        // 총합이 10으로 나누어 떨어지면 유효한 카드 번호
        return totalSum % 10 == 0;
    }
}
