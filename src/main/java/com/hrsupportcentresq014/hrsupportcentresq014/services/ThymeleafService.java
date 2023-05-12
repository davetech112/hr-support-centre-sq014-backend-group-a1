package com.hrsupportcentresq014.hrsupportcentresq014.services;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
