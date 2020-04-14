package com.mikaelfrancoeur.demostockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockResponse{
	private double C;
	private int pc;
	private int T;
	private int H;
	private double L;
	private int O;

	public void setC(double C){
		this.C = C;
	}

	public double getC(){
		return C;
	}

	public void setPc(int pc){
		this.pc = pc;
	}

	public int getPc(){
		return pc;
	}

	public void setT(int T){
		this.T = T;
	}

	public int getT(){
		return T;
	}

	public void setH(int H){
		this.H = H;
	}

	public int getH(){
		return H;
	}

	public void setL(double L){
		this.L = L;
	}

	public double getL(){
		return L;
	}

	public void setO(int O){
		this.O = O;
	}

	public int getO(){
		return O;
	}

	@Override
 	public String toString(){
		return 
			"StockResponse{" + 
			"c = '" + C + '\'' +
			",pc = '" + pc + '\'' + 
			",t = '" + T + '\'' + 
			",h = '" + H + '\'' + 
			",l = '" + L + '\'' + 
			",o = '" + O + '\'' + 
			"}";
		}
}
