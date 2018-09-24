package ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import kalaha.GameState;

public class MinimaxTree {

	private Node root;
	private ArrayList<MinimaxTree> subtrees;
	private int player;
//	public int depthFirst(GameState n,int l,int best) {
//		
//		
//		
//	}
	
	private void buildTreeDFS(GameState board,int depth) {
		root = new Node(board,0,0);
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		int counter = depth;
		while(counter<= depth)
		{
			while(!stack.isEmpty()) {
				System.out.println("Popping from stack");
					Node current = stack.pop();
					GameState currState = current.getState().clone();
					GameState rest = currState.clone();
				for(int i= 1; i<7; i++){
					if(currState.moveIsPossible(i)) {
						
						currState.makeMove(i);    
						GameState stateI = currState.clone();  
						Node childI = new Node(stateI,0,0);
						
						System.out.println("I am a new game representation.");
						System.out.println(childI.getState().toString());
						
						childI.parent = current;
						current.children.add(childI);
						stack.push(childI);
						
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						}
					
					currState = rest.clone();
	
					}
				}
			}
			counter--;

		
	}
	
	private void buildTreeBFS(GameState board) {
		int level = 0;
		root = new Node(board,level,0);
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(root);
		
		int max_depth = 7;
		for (int i = 0; i < max_depth; i++) {	
			
			System.out.println("New level: " + i);
			LinkedList<Node> nextLevel = new LinkedList<>();
			for (Node current : queue) {

				// GameState restore = current.getState().clone(); //next player is 1

				for (int j = 1; j < 7; j++) {
					
					GameState currState = current.getState().clone(); // next player is 1

					if (currState.moveIsPossible(j)) {

						currState.makeMove(j); // a move is done and next player is 2
						Node childI = new Node(currState, level, 0);

//						System.out.println("I am a new game representation.Level: " + level);
//						System.out.println(childI.getState().toString());

						childI.parent = current;
						current.children.add(childI);
						nextLevel.add(childI);

					}

				}
			}
			queue = nextLevel;

		}
		
//		while (!queue.isEmpty()) {
//			
//			Node current = queue.poll();
//			
//			GameState currState = current.getState().clone(); //next player is 1
//			GameState restore = current.getState().clone(); //next player is 1
//			
//			
//			for (int i = 1; i<7; i++) {
//				
//				if (currState.moveIsPossible(i)) {
//					
//					currState.makeMove(i);    //a move is done and next player is 2
//					GameState stateI = currState.clone();  
//					Node childI = new Node(stateI,level,0);
//					
//					System.out.println("I am a new game representation.Level: " + level);
//					System.out.println(childI.getState().toString());
//					
//					try {
//						Thread.sleep(50);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					childI.parent = current;
//					current.children.add(childI);
//					queue.add(childI);
//					
//				}
//				
//				currState = restore.clone();
//			}
//		}		
	}

	
	public MinimaxTree (GameState board) {
		buildTreeBFS(board);
		
	}
	
	public MinimaxTree(GameState board, int player) {
		this.player = player;
	}
	
	public int iterativeDeep(GameState state, Node root, boolean turn) {
		
		int max = -1;
		
		for (int i=1;i<7; i++) {
			max = Math.max(max,iterativeDeepRec(state, root, i, turn));
		}
		
		return max;
		
	}
	
	public int iterativeDeepRec(GameState state, Node n, int maxDepth, boolean turn) {

		if (maxDepth <= 0) {

			if (player == 1) {
				return n.scoreS;
			} else if (player == 2) {
				return n.scoreN;
			}

		} 
		
		return 0; 
			

	}
	
	class Node {
		
		//the level of the node
		private int level;
		//the first choice that led to this node
		private int path; 
		//the score in this node
		private int scoreN, scoreS;
		
		private GameState state;
		
		private Node parent;
		
		private ArrayList<Node> children;
		
		public Node(GameState board, int level, int path) {
			
			this.state = board;
			this.path = path;
			
			//Scores for both players
			String st = state.toString();
			String[] tokens = st.split(";");
			scoreN = Integer.parseInt(tokens[0]);
			scoreS = Integer.parseInt(tokens[7]);
			
			this.level = level;
			
			children = new ArrayList<>();
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
		
		public int getLevel() {
			return level;
		}
		
		
	}
}
