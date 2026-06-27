package com.chitkara.bfhl.service;

import com.chitkara.bfhl.config.UserProperties;
import com.chitkara.bfhl.dto.BfhlRequest;
import com.chitkara.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private final UserProperties userProperties;

    public BfhlServiceImpl(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> data = request.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<Character> lettersForConcat = new ArrayList<>();
        long sum = 0;

        for (String item : data) {
            if (item == null) {
                continue;
            }
            if (item.isEmpty()) {
                specialCharacters.add(item);
                continue;
            }

            if (isNumeric(item)) {
                long value = Long.parseLong(item);
                sum += value;
                if (value % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                String upper = item.toUpperCase();
                alphabets.add(upper);
                for (char c : item.toCharArray()) {
                    if (Character.isLetter(c)) {
                        lettersForConcat.add(c);
                    }
                }
            } else {
                specialCharacters.add(item);
            }
        }

        BfhlResponse response = new BfhlResponse();
        response.setSuccess(true);
        response.setUserId(userProperties.buildUserId());
        response.setEmail(userProperties.getEmail());
        response.setRollNumber(userProperties.getRollNumber());
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcatString(buildConcatString(lettersForConcat));
        return response;
    }

    private boolean isNumeric(String value) {
        if (value.startsWith("+") || value.startsWith("-")) {
            if (value.length() == 1) {
                return false;
            }
            return value.substring(1).chars().allMatch(Character::isDigit);
        }
        return value.chars().allMatch(Character::isDigit);
    }

    private boolean isAlphabetic(String value) {
        return value.chars().allMatch(Character::isLetter);
    }

    private String buildConcatString(List<Character> letters) {
        if (letters.isEmpty()) {
            return "";
        }

        List<Character> reversed = new ArrayList<>(letters);
        Collections.reverse(reversed);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            result.append(i % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return result.toString();
    }
}
