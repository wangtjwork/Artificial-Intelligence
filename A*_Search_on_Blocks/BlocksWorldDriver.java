import java.util.Arrays;
import java.util.Random;

public class BlocksWorldDriver {
	public static int nodesUsed = 0;
	public static int spaceUsed = 0;
	public static int stepsUsed = 0;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dataMode = args[0];
		int searchMode = 2;
		if (args[1].equals("-b")) {
			searchMode = 0;
		}
		else if (args[1].equals("-s")) {
			searchMode = 1;
		}
		args = Arrays.copyOfRange(args, 2, args.length);
		if(dataMode.equals("user")) {
			userDefinedInitialState(args, searchMode, false);
		} else if (dataMode.equals("random")) {
			randomGeneratedInitialState(args, searchMode);
		} else {
			throw new Exception("First Input must be \"user\" or \"random\"");
		}
	}
	
	private static void userDefinedInitialState(String[] args, int searchMode, boolean showOnlyInitialState)  throws Exception{
		if(args.length <= 1) {
			throw new Exception("User Defined input is not parseable!");
		}
		SearchAlgorithm sa = new SearchAlgorithm(args, searchMode);
		int stepNum = sa.Search();
		if(!showOnlyInitialState) {
			sa.printRoute();
		}
		else {
			sa.printInitialState();
		}
		int thisNodeUsed = sa.getNodesSearched();
		nodesUsed += thisNodeUsed;
		int thisSpaceUsed = sa.getSpaceSearched();
		spaceUsed += thisSpaceUsed;
		System.out.println(String.format("Total nodes searched: %d", thisNodeUsed));
		System.out.println(String.format("Most space used: %d", thisSpaceUsed));
		stepsUsed += stepNum;
		System.out.println(String.format("Number of steps: %d", stepNum));
		return;
	}
	
	private static void randomGeneratedInitialState(String[] args, int searchMode) throws Exception {
		if(args.length != 4) {
			throw new Exception("Random initial State format: random [hnMode] [#ofBlocks] [#ofStacks] [#ofExperiments] [#ofRandomSeed]");
		}
		
		int blocks = Integer.parseInt(args[0]);
		int stacks = Integer.parseInt(args[1]);
		int experiments = Integer.parseInt(args[2]);
		Random rand = new Random(Integer.parseInt(args[3]));
		for (int i = 0; i < experiments; i++) {
			String[] state = generateRandomState(blocks, stacks, rand);
			System.out.println(String.format("Now generating Number %d experiment: ", i + 1));
			userDefinedInitialState(state, searchMode, true);
			System.out.println(String.format("Number %d experiment generated. \n", i + 1));
		}
		System.out.println(String.format("Summary: for randomly generated #%d blocks, #%d stacks and experimented for %d times, the average time, space, and difficulty of problem is:", blocks, stacks, experiments));
		System.out.println("Average nodes generated: " + nodesUsed / experiments);
		System.out.println("Average space occupied: " + spaceUsed / experiments);
		System.out.println("Average steps to goal: " + stepsUsed / experiments);
	}
	
	private static String[] generateRandomState(int blocks, int stacks, Random rand) {
		String[] randomState = new String[stacks];
		Arrays.fill(randomState, "");
		for (int i = 1; i <= blocks; i++) {
			int thisStack = rand.nextInt(stacks);
			randomState[thisStack] += (char)(i- 1+'A') + "_";
		}
		for(int j = 0; j < stacks; j++) {
			if(randomState[j].length() == 0) {
				randomState[j] = "0";
			}
			else {
				randomState[j] = randomState[j].substring(0, randomState[j].length() - 1);
			}
		}
		return randomState;
	}
}
