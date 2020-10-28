package com.itheima.service;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Map<String, Object>> findMenu(String username);
}
