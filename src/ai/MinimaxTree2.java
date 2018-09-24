package ai;

import java.util.ArrayList;
import java.util.LinkedList;

import kalaha.GameState;

/**
 *
 * @author cristinariscovoi
 */
public class MinimaxTree2 {

	private Node root;
	private GameState state;
	private int player;
	boolean turn;

	public MinimaxTree2(GameState state, int player, boolean turn) {
		// this.player=player;
		// this.turn=turn;
		//
		// root=new Node(state,0,0);
		//
		this.state = state;
		buildTreeBFS(state);

	}

	public int findBestMove() {
		int score = -1;
		int bestScore = -1;
		int choice = 1, bestC = 1;
		for (Node child : root.getChildren()) {
			System.out.println(child.toString());
			score = minimax(child, true, 1, 3);
			if (score > bestScore) {
				bestScore = score;
				bestC = choice;
			}
			choice++;
		}
		return bestC;

	}

	private void buildTreeBFS(GameState state) {
		int level = 0;
		root = new Node(state, level, 0);
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(root);

		int max_depth = 4;
		for (int i = 0; i < max_depth; i++) {

			level++;

			LinkedList<Node> nextLevel = new LinkedList<>();

			for (Node current : queue) {
				// simulates all the possible moves on this node
				for (int j = 1; j < 7; j++) {
					GameState currState = current.getState().clone();
					if (currState.moveIsPossible(j)) {
						currState.makeMove(j);
						Node childI = new Node(currState, level, 0);
//						childI.parent = current;
						current.getChildren().add(childI);
						nextLevel.add(childI);
					}
				}
			}

			queue = nextLevel;

		}
	}

	private int minimax(Node n, boolean turn, int depth, int maxDepth) {
		// base case
		if (depth >= maxDepth) {
			if (player == 1) {
				return n.getScoreS();
			}
			if (player == 2) {
				return n.getScoreN();
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// recursive case
		if (turn == true) {
			System.out.println("Max turn. Depth = " + depth);
			int max = -1;
			for (Node child : n.getChildren()) {
				System.out.println(child.toString());
				// max has an extra chance of playing
				if (player == n.getState().getNextPlayer()) {
					max = Math.max(max, minimax(child, true, depth + 1, maxDepth));
				} else {
					// max doesn't have an extra chance of playing
					max = Math.max(max, minimax(child, false, depth + 1, maxDepth));
				}	
			}
			n.setValue(max);
			return max;
		} else {
				System.out.println("Min turn. Depth = " + depth);
				int min = 0;
				for (Node child : n.getChildren()) {
					System.out.println(child.toString());
					// the opponent of max has an extra turn
					if (player != n.getState().getNextPlayer()) {
						min = Math.min(min, minimax(child, false, depth + 1, maxDepth));
					} else {
						// max will be the next to play
						min = Math.min(min, minimax(child, true, depth + 1, maxDepth));
					}
				}
				n.setValue(min);
				return min;
			}
		}
	
	
}   

       
    
    class Node{
        private int level;
        private int scoreS, scoreN;
        private int choice;
        private int value;
        private GameState state;
        private Node parent;
        private ArrayList<Node> children; 
        
        public Node(GameState state, int level, int choice){
            this.state=state;
            this.level=level;
            this.choice=choice;
            this.parent = null;
            this.children = new ArrayList<>();
            
            String s=state.toString();
            String []tokens=s.split(";");
            scoreN=Integer.parseInt(tokens[0]);
            scoreS=Integer.parseInt(tokens[7]);
        }
        
		
		public int getScoreS() {
			return scoreS;
		}
        
		public int getScoreN() {
			return scoreN;
		}
		
		public GameState getState() {
			return state;
		}
		
		public void setState(GameState gs) {
			this.state = gs;
		}
		
		public ArrayList<Node> getChildren() {
			return this.children;
		}
		
		public int getLevel() {
			return level;
		}
		
		public void setValue(int val) {
			this.value = val;
		}
           
		public String toString() {
			return this.getState() + ";Level: " + level;
		}
    }    
    

