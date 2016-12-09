package com.minbo.conference.solver.impl;

import com.minbo.conference.solver.Solver;
import com.minbo.conference.solver.vo.Request;
import com.minbo.conference.solver.vo.Response;

public class SolverImpl implements Solver{
	
	public void process(Object req, Object res) throws Exception {
		
		int W = ((Request) req).getMaxSize();
		int N = ((Request) req).getNumSize();
		int[] profit = ((Request) req).getProfit();
		int[] weight = ((Request) req).getWeight();

		//矩阵
		int[][] opt = new int[N + 1][W + 1];
		boolean[][] sol = new boolean[N + 1][W + 1];

		for (int n = 1; n <= N; n++) {
			for (int w = 1; w <= W; w++) {

				int option1 = opt[n - 1][w];
				int option2 = Integer.MIN_VALUE;

				if (weight[n] <= w){
					option2 = profit[n] + opt[n - 1][w - weight[n]];
				}
				
				opt[n][w] = Math.max(option1, option2);
				sol[n][w] = (option2 > option1);
			}
		}

		boolean[] take = new boolean[N + 1];
		for (int n = N, w = W; n > 0; n--) {
			if (sol[n][w]) {
				take[n] = true;
				w = w - weight[n];
			} else {
				take[n] = false;
			}
		}
		((Response) res).setTake(take);
		return;
	}
	
}
