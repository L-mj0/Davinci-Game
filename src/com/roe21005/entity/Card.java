package com.roe21005.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
	  private List<String> w;
	  private List<String> b;
	  private int[] computerstates;
	  private int[] playerstates;

	  public Card(){
		  w = new ArrayList<>();
		  b = new ArrayList<>(); 
		  computerstates = new int[24]; // 0-11 w, 12-23 b
		  playerstates = new int[24];
		  for(int i = 0;i<=11;i++){
			  w.add("w"+i);
			  b.add("b"+i);
			  computerstates[i]=0;
			  computerstates[i+12]=0;
			  playerstates[i]=0;
			  playerstates[i+12]=0;
		  }
		  // w.add("w-");
		  // b.add("b-");
	  }
	  
	  public int[] getplayerstates(){
		  return computerstates;
	  }
	  
	  public int[] getcomputerstates(){
		  return computerstates;
	  }
	  
	  public List<String> getW(){
		  return w;
	  }
	  
	  public List<String> getB(){
		  return b;
	  }
	  
	  public String drawCard(String type){
		  List<String> cards = type.equals("w")?w:b;
		  if(cards.isEmpty()){
			  return null;
		  }
		  return cards.remove(0);
	  }
	  
	  public void shuffle(){
		  // Ëæ»ú´òÂÒ¿¨ÅÆ
		  Collections.shuffle(w);
		  Collections.shuffle(b);
	  }
	  
	  public boolean iswDeckEmpty(){
		  return w.isEmpty();
	  }
	  
	  public boolean isbDeckEmpty(){
		  return b.isEmpty();
	  }
	  
	    public int getCardState(String card) {
	        int index = getCardIndex(card);
	        if (index == -1) return -1;
	        System.out.println("card.java:"+index+" "+computerstates[index]);
	        return computerstates[index];
	    }

	  public void setComputerState(String card, int state) {
	        int index = getCardIndex(card);
	        if (index != -1) {
	        	computerstates[index] = state;
	        }
	    }
	  
	  public void setplayerState(String card,int state){
		  int index = getCardIndex(card);
		  if(index!=-1){
			  playerstates[index]=state;
		  }
	  }

	    private int getCardIndex(String card) {
	        char type = card.charAt(0);
	        int number = Integer.parseInt(card.substring(1));
	        if (type == 'w') {
	            return number;
	        } else if (type == 'b') {
	            return 12 + number;
	        } else {
	            return -1;
	        }
	    }
}
