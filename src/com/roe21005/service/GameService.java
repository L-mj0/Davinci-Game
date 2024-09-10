package com.roe21005.service;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.roe21005.dao.UserDao;
import com.roe21005.entity.User;
import com.roe21005.util.MyBatisUtil;

public class GameService {

	private final Random random = new Random();
	
	public List<String> generateInitialStones() {
        List<String> stones = new ArrayList<>();
        stones.add(generateStone("w"));
        stones.add(generateStone("w"));
        stones.add(generateStone("b"));
        stones.add(generateStone("b"));
        stones.sort(Comparator.comparingInt(this::extractNumber));
        System.out.println(stones);
        return stones;
    }

    public String generateStone(String color) {
        int number = random.nextInt(12);
       
        return color + number;
    }
    
    private int extractNumber(String stone) {
        return Integer.parseInt(stone.substring(1));
    }

}
