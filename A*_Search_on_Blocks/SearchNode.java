import java.util.*;
import java.util.stream.*;

public class SearchNode implements Comparable {
	public static final int NOHN = 0;
	public static final int STUPIDHN = 1;
	public static final int CLEVERHN = 2;
	public static int cnt = 0;
	ArrayList<ArrayList<Integer>> state;
	SearchNode parentPtr;
	Move previousMove;
	int depth;
	int fn;
	
	public SearchNode (String[] blocks) {
		state = new ArrayList<ArrayList<Integer>>();
		for(String stack : blocks) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			if(!stack.equals("0")) {
				String[] strNums = stack.split("_");
				for(String strNum : strNums) {
					temp.add(strNum.charAt(0) - 'A' + 1);
				}
			}
			state.add(temp);
		}
		parentPtr = null;
		depth = 0;
		previousMove = null;
		fn = depth;
		cnt = 1;
	}
	
	public SearchNode(SearchNode par, Move curMove, int searchMode) {
		state = par.ApplyMove(curMove);
		parentPtr = par;
		previousMove = curMove;
		depth = par.depth + 1;
		if(searchMode == NOHN) {
			fn = depth + this.getNoHn();
		} else if (searchMode == STUPIDHN) {
			fn = depth + this.getStupidHn();
		} else {
			fn = depth + this.getCleverHn();
		}
		cnt++;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public SearchNode toParent() {
		return this.parentPtr;
	}
	
	public String printEachState() {
		StringBuffer sb = new StringBuffer();
		if(previousMove != null) {
			sb.append(String.format("Move: From Stack #%d, To Stack #%d. \n", 
					previousMove.fromStack, previousMove.toStack));
		}
		sb.append("State: \n");
		for (int i = 0; i < state.size(); i++) {
			List<Integer> blocksInOneStack = state.get(i);
			if(i != 0) {
				sb.append('\n');
			}
			sb.append("#" + blocksInOneStack.stream().map(b -> String.valueOf(((char)(b.intValue() - 1 + 'A'))))
					.collect(Collectors.joining("")));
		}
		sb.append('\n');
		return sb.toString();
	}
	
	public List<Move> AvailableMoves(){
		List<Move> moves = new ArrayList<Move>();
		for(int i = 0; i < state.size(); i++) {
			for(int j = 0; j < state.size(); j++) {
				if(i != j && state.get(i).size() != 0) {
					moves.add(new Move(i, j));
				}
			}
		}
		return moves;
	}
	
	private int getNoHn() {
		return 0;
	}
	
	private int getStupidHn() {
		int notInPlace = 0;
		List<Integer> goalStack = state.get(0);
		for(int i = 0; i < goalStack.size(); i++) {
			if(goalStack.get(i) != i + 1) {
				notInPlace += 1;
			}
		}
		for(int j = 1; j < state.size(); j++) {
			notInPlace += state.get(j).size();
		}
		return notInPlace;
	}
	
	private int getCleverHn() {
		int notInPlace = 0;
		List<Integer> goalStack = state.get(0);
		for(int i = 0; i < goalStack.size(); i++) {
			if(goalStack.get(i) != i + 1) {
				notInPlace += 2;
			}
		}
		for(int j = 1; j < state.size(); j++) {
			List<Integer> tempStack = state.get(j);
			if(tempStack.size() != 0) {
				notInPlace += tempStack.size();
				int thisStackNum = 0;
				for(int k = 0; k < tempStack.size() - 1; k++) {
					if(thisStackNum > tempStack.size() - k - 1) {
						break;
					}
					int largerThan = 0;
					for (int l = k + 1; l < tempStack.size(); l++) {
						if(tempStack.get(l) > tempStack.get(k)) {
							largerThan += 1;
						}
					}
					thisStackNum = Math.max(thisStackNum, largerThan);
				}
				notInPlace += thisStackNum;
			}
		}
		return notInPlace;
	}
	
	public boolean isGoal() {
		for (int i = 1; i < state.size(); i++) {
			if(state.get(i).size() != 0) {
				return false;
			}
		}
		List<Integer> firstStack = state.get(0);
		for (int j = 0; j < firstStack.size() - 1; j++) {
			if(firstStack.get(j) >= firstStack.get(j + 1)) {
				return false;
			}
		}
		return true;
	}
	
	private ArrayList<ArrayList<Integer>> ApplyMove(Move m) {
		ArrayList<ArrayList<Integer>> newState = cloneList(state);
		ArrayList<Integer> fromStack = newState.get(m.fromStack);
		int blockFetched = fromStack.remove(fromStack.size() - 1);
		newState.get(m.toStack).add(blockFetched);
		return newState;
	}
	
	private ArrayList<ArrayList<Integer>> cloneList(ArrayList<ArrayList<Integer>> lists){
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();
		for(List<Integer> list : lists) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(Integer l : list) {
				temp.add(new Integer(l));
			}
			res.add(temp);
		}
		return res;
	}
	
	@Override
	public int compareTo(Object n2) {
		return this.fn - ((SearchNode)n2).fn;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchNode other = (SearchNode) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < state.size(); i++) {
			List<Integer> blocksInOneStack = state.get(i);
			String blocksArray = "";
			blocksArray = "0" + blocksInOneStack.stream().map(b -> b.toString())
					.collect(Collectors.joining(""));
			sb.append(blocksArray);
		}
		return sb.toString();
	}
}
