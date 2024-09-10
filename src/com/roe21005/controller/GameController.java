package com.roe21005.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.roe21005.entity.Card;
import com.roe21005.entity.User;
import com.roe21005.service.GameService;
import com.roe21005.service.UserService;

@Controller
public class GameController {
	//private final Random random = new Random();
	private Card cardDeck;
	private int hp = 7;
	
	public GameController(){
		this.cardDeck = new Card();
		this.cardDeck.shuffle();
	}
	
	@RequestMapping("/game")
	public String gamePage(HttpSession session, Model model) {
	    System.out.println("game: success mapping to game");

	    // Check if playerStones and computerStones are already in session
	    List<String> playerStones = (List<String>) session.getAttribute("playerStones");
	    List<String> computerStones = (List<String>) session.getAttribute("computerStones");

	    if (playerStones == null || computerStones == null) {
	        // Initialize player and computer stones if not present in session
	    	playerStones = new ArrayList<>();
	    	computerStones = new ArrayList<>();
	    	for(int i=0;i<4;i++){
	    		playerStones.add(cardDeck.drawCard(i < 2 ? "w" : "b"));
	    		computerStones.add(cardDeck.drawCard(i < 2 ? "w" : "b"));
	    	}

	        // Store in session
	        session.setAttribute("playerStones", playerStones);
	        session.setAttribute("computerStones", computerStones);
	        session.setAttribute("cards", cardDeck.getcomputerstates());
	        session.setAttribute("hp",hp);

	        System.out.println("Generated new stones.");
	    } else {
	        System.out.println("Using existing stones from session.");
	    }

	    // Add to model to render in view
	    model.addAttribute("playerStones", playerStones);
	    model.addAttribute("computerStones", computerStones);
	    model.addAttribute("cards",cardDeck.getcomputerstates());
	    
        model.addAttribute("whiteCardCount", cardDeck.getW().size());
        model.addAttribute("blackCardCount", cardDeck.getB().size());
        model.addAttribute("hp",hp);
        
	    System.out.println("playerStones: " + playerStones);
	    System.out.println("computerStones: " + computerStones);
	    System.out.println("cardDeck: " + cardDeck.getcomputerstates());

	    return "game";
	}
    
    @PostMapping("/game/draw")
    public String drawCard(@RequestParam("color")String color,HttpSession session, Model model) {
        if (cardDeck.iswDeckEmpty()&& cardDeck.isbDeckEmpty()) {
            model.addAttribute("message", "游戏结束");
            
        } else {
        	String card = cardDeck.drawCard(color);
            if (card == null) {
                model.addAttribute("message", "卡牌已被抽完");
            } else {
                List<String> playerStones = (List<String>) session.getAttribute("playerStones");
                playerStones.add(card);
                playerStones.sort(Comparator.comparingInt(this::extractNumber));
                session.setAttribute("playerStones", playerStones);
                model.addAttribute("newCard", card);
                System.out.println("playerStones: " + playerStones);
                System.out.println("card: " + card);

                // 电脑随机抽卡
                Random random = new Random();
                String computerColor = random.nextBoolean() ? "w" : "b";
                String computerCard = cardDeck.drawCard(computerColor);
                if (computerCard == null) {
                    computerColor = computerColor.equals("w") ? "b" : "w";
                    computerCard = cardDeck.drawCard(computerColor);
                }

                if (computerCard == null) {
                    model.addAttribute("message", "游戏结束");
                } else {
                    List<String> computerStones = (List<String>) session.getAttribute("computerStones");
                    computerStones.add(computerCard);
                    computerStones.sort(Comparator.comparingInt(this::extractNumber));
                    session.setAttribute("computerStones", computerStones);
                    model.addAttribute("computerNewCard", computerCard);
                    System.out.println("computerStones: " + computerStones);
                    System.out.println("computerCard: " + computerCard);
                }
                
            }
        }
        
        model.addAttribute("whiteCardCount", cardDeck.getW().size());
        model.addAttribute("blackCardCount", cardDeck.getB().size());

        model.addAttribute("playerStones", session.getAttribute("playerStones"));
        model.addAttribute("computerStones", session.getAttribute("computerStones"));

        return "draw";
    }
    
    @RequestMapping("/game/reset")
    public String resetGame(HttpSession session,Model model) {
        this.cardDeck = new Card();
        this.cardDeck.shuffle();

        List<String> playerStones = new ArrayList<>();
        List<String> computerStones = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerStones.add(cardDeck.drawCard(i < 2 ? "w" : "b"));
            computerStones.add(cardDeck.drawCard(i < 2 ? "w" : "b"));
        }

        session.setAttribute("playerStones", playerStones);
        session.setAttribute("computerStones", computerStones);
        session.setAttribute("cards", cardDeck.getcomputerstates());
        session.setAttribute("whiteCardCount", cardDeck.getW().size());
        session.setAttribute("blackCardCount", cardDeck.getB().size());
        session.setAttribute("hp", 7); // Reset HP
        
	    model.addAttribute("playerStones", playerStones);
	    model.addAttribute("computerStones", computerStones);
	    model.addAttribute("cards",cardDeck.getcomputerstates());
	    
        model.addAttribute("whiteCardCount", cardDeck.getW().size());
        model.addAttribute("blackCardCount", cardDeck.getB().size());
        model.addAttribute("hp",hp);
        

        return "redirect:/game";
    }

    @PostMapping("/game/updateState")
    @ResponseBody
    public String updateCardState(@RequestParam("card") String card, @RequestParam("state") int state, HttpSession session,Model model) {
    	cardDeck.setComputerState(card, state);
    	session.setAttribute("cards", cardDeck.getcomputerstates());
    	model.addAttribute("cards",cardDeck.getcomputerstates());

        return "success";
    }
    
    @PostMapping("/game/setplayercard")
    @ResponseBody
    public String setplayerCard(@RequestParam("card") String card, @RequestParam("state") int state, HttpSession session,Model model) {
    	cardDeck.setplayerState(card, state);
    	session.setAttribute("playercards", cardDeck.getplayerstates());
    	model.addAttribute("playercards",cardDeck.getplayerstates());
    	
    	// 减少 HP 并检查是否游戏结束
	    Integer hp = (Integer) session.getAttribute("hp");
	    if (hp != null) {
	        hp -= 1;
	        session.setAttribute("hp", hp);
	        model.addAttribute("hp", hp);
	        if (hp < 0) {
	            return "game_over";
	        }
	    }

        return "success";
    }
    
    private int extractNumber(String stone) {
    	return Integer.parseInt(stone.substring(1));
    }
	
}
