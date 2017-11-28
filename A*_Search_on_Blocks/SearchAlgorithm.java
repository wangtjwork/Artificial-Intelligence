import java.util.*;

public class SearchAlgorithm {
	SearchNode start;
	Queue<SearchNode> q;
	Map<String, SearchNode> seen;
	SearchNode goalState;
	int searchMode;
	int largestQueueSize;
	
	public SearchAlgorithm (String[] blocks, int searchMode) {
		this.searchMode = searchMode;
		start = new SearchNode(blocks);
		q = new PriorityQueue<SearchNode>();
		q.offer(start);
		seen = new HashMap<String ,SearchNode>();
		seen.put(start.toString(), start);
		goalState = null;
		largestQueueSize = 1;
	}
	
	public int Search() {
		while(q.size() != 0) {
			largestQueueSize = Math.max(q.size(), largestQueueSize);
			SearchNode n = q.poll();
			List<Move> nextMoves = n.AvailableMoves();
			for (Move m : nextMoves) {
				SearchNode child = new SearchNode(n, m, this.searchMode);
				if(child.isGoal()) {
					seen.put(child.toString(), child);
					goalState = child;
					return child.getDepth();
				}
				if(seen.containsKey(child.toString())) {
					SearchNode lastSeen = seen.get(child.toString());
					if(lastSeen.depth > child.depth) {
						seen.put(child.toString(), child);
					}
				} else {
					seen.put(child.toString(), child);
					q.offer(child);
				}
			}
		}
		return -1;
	}
	
	public void printInitialState() {
		System.out.print("Initial state of the search is: \n");
		System.out.print(this.start.printEachState() + "\n");
	}
	
	public void printRoute() {
		SearchNode iter = goalState;
		List<SearchNode> route = new ArrayList<SearchNode>();
		while(iter != null) {
			iter = seen.get(iter.toString());
			route.add(iter);
			iter = iter.parentPtr;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = route.size() - 1; i >= 0; i--) {
			sb.append(route.get(i).printEachState());
		}
		System.out.print(sb.toString());
	}
	
	public int getNodesSearched() {
		return SearchNode.cnt;
	}
	
	public int getSpaceSearched() {
		return largestQueueSize;
	}
}
